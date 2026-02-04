package com.elorrieta.service;

import com.elorrieta.dto.LoginRequestDTO;
import com.elorrieta.dto.LoginResponseDTO;
import com.elorrieta.dto.UserDTO;
import com.elorrieta.encriptado.CryptSHA;
import com.elorrieta.entities.User;
import com.elorrieta.mapper.UserMapper;
import com.elorrieta.repository.UserRepository;
import com.elorrieta.tcpEnvios.mensajes.parts.FilterParts;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de usuarios
 * Devuelve DTOs
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    // Instancia de CryptSHA para hashear contraseñas
    private CryptSHA cryptSHA = new CryptSHA();

    // ========== CRUD BÁSICO (devuelve DTOs) ==========

    public UserDTO getById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDTO).orElse(null);
    }

    public UserDTO getByUsername(String username) {
        Optional<User> user = userRepository.findByUsernameWithTipo(username);
        return user.map(userMapper::toDTO).orElse(null);
    }

    public List<UserDTO> getByTipo(int tipo) {
        List<User> users = userRepository.findByTipo(tipo);
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public User getUserEntityByUsername(String username) {
        Optional<User> user = userRepository.findByUsernameWithTipo(username);
        return user.orElse(null);
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<User> getAllProfesoresEntities() {
        List<User> users = userRepository.findAllProfesores();
        // Inicializar las relaciones lazy de User
        for (User user : users) {
            if (user.getTipo() != null) {
                Hibernate.initialize(user.getTipo());
            }
        }
        return users;
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        
        // Hashear la contraseña antes de guardar
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(cryptSHA.cifrarTexto(user.getPassword()));
        }
        
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    // ========== MÉTODOS DE AUTENTICACIÓN ==========

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userRepository.findByUsernameWithTipo(loginRequest.getUsername());

        if (userOpt.isEmpty()) {
            return new LoginResponseDTO(false, "Usuario no encontrado");
        }

        User user = userOpt.get();

        // Hashear la contraseña recibida y comparar con la almacenada
        String passwordHashed = cryptSHA.cifrarTexto(loginRequest.getPassword());
        if (!user.getPassword().equals(passwordHashed)) {
            return new LoginResponseDTO(false, "Contraseña incorrecta");
        }

        UserDTO userDTO = userMapper.toDTO(user);
        return new LoginResponseDTO(true, "Login correcto", userDTO);
    }

    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMapper::toDTO).orElse(null);
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            System.out.println("Usuario no encontrado");
            return false;
        }

        User user = userOpt.get();

        // Hashear la contraseña vieja para comparar
        String oldPasswordHashed = cryptSHA.cifrarTexto(oldPassword);
        if (!user.getPassword().equals(oldPasswordHashed)) {
            System.out.println("Contraseña actual incorrecta");
            return false;
        }

        // Hashear la nueva contraseña antes de guardar
        user.setPassword(cryptSHA.cifrarTexto(newPassword));
        userRepository.save(user);

        System.out.println("Contraseña cambiada correctamente");
        return true;
    }

    public boolean resetPassword(String email) {
        System.out.println("Email: " + email);
        
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            System.out.println("Usuario no encontrado con ese email");
            return false;
        }

        User user = userOpt.get();
        System.out.println("Usuario encontrado: " + user.getUsername());
        
        // Generar contraseña en texto plano
        String newPassword = generateRandomPassword();
        System.out.println("Password TEXTO PLANO (se enviará al email): " + newPassword);
        
        // Hashear con SHA
        String passwordHasheada = cryptSHA.cifrarTexto(newPassword);
        System.out.println("Password HASHEADA (se guardará en BD): " + passwordHasheada);
        
        // Guardar en BD hasheada con SHA
        user.setPassword(passwordHasheada);
        userRepository.save(user);
        System.out.println("Password HASHEADA guardada en BD");

        // Enviar email con la contraseña EN TEXTO PLANO
        System.out.println("Enviando email con password en TEXTO PLANO");
        emailService.sendPasswordResetEmail(email, user.getUsername(), newPassword);

        System.out.println("Contraseña reseteada para: " + email);
        System.out.println("Reset correcto :D");
        return true;
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    public List<User> getStudentsByProfessorId(int professorId) {
        List<User> listaAlumnos = userRepository.findStudentsByProfessorId(professorId);
        for (User alumno : listaAlumnos) {
            // Evitar problemas de FetchType.LAZY
            Hibernate.initialize(alumno);
            // Inicializar también las relaciones lazy de User
            if (alumno.getTipo() != null) {
                Hibernate.initialize(alumno.getTipo());
            }
        }
        return listaAlumnos;
    }

    public List<User> getUserByFilter(FilterParts filterParts) {
        Integer idCiclo, curso;
        Integer profesorId = filterParts.getUser().getId();
        try {
            idCiclo = filterParts.getCiclo().getId();
        } catch (NullPointerException e) {
            System.out.println("Ciclo es nulo en el filtro");
            idCiclo = null;
        }
        try {
            curso = filterParts.getMatriculaciones().getCurso();
        } catch (NullPointerException e) {
            System.out.println("Curso es nulo en el filtro");
            curso = null;
        }
        List<User> listaAlumnos = userRepository.findUsersByFilter(idCiclo, curso, profesorId);
        for (User alumno : listaAlumnos) {
            // Evitar problemas de FetchType.LAZY
            Hibernate.initialize(alumno);
            // Inicializar también las relaciones lazy de User
            if (alumno.getTipo() != null) {
                Hibernate.initialize(alumno.getTipo());
            }
        }
        return listaAlumnos;
    }

    public void updatePhotoUrl(int id, String string) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setArgazkiaUrl(string);
            userRepository.save(user);
        } else {
            System.out.println("Usuario no encontrado con ID: " + id);
        }
    }
}