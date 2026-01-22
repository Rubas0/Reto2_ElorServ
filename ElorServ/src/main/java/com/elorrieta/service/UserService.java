package com.elorrieta.service;

import com.elorrieta.dto.LoginRequestDTO;
import com.elorrieta.dto.LoginResponseDTO;
import com.elorrieta.dto.UserDTO;
import com.elorrieta.entities.User;
import com.elorrieta.mapper.UserMapper;
import com.elorrieta.repository.UserRepository;
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

    // ========== CRUD BÁSICO (devuelve DTOs) ==========

    public UserDTO getById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDTO).orElse(null);
    }

    public UserDTO getByUsername(String username) {
        Optional<User> user = userRepository.findByUsernameWithTipo(username);
        return user.map(userMapper::toDTO).orElse(null);
    }

    public User getUserEntityByUsername(String username) {
        Optional<User> user = userRepository.findByUsernameWithTipo(username);
        return user.orElse(null);
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper:: toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
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
        
        // TODO: Aquí debería descifrarse el password con RSA y compararse con BCrypt
        if (! user.getPassword().equals(loginRequest.getPassword())) {
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
        
        if (!user.getPassword().equals(oldPassword)) {
            System. out.println("Contraseña actual incorrecta");
            return false;
        }
        
        user.setPassword(newPassword);
        userRepository.save(user);
        
        System.out.println("Contraseña cambiada correctamente");
        return true;
    }

    @Autowired
    private EmailService emailService;

    public boolean resetPassword(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            System.out.println("Usuario no encontrado con ese email");
            return false;
        }
        
        User user = userOpt.get();
        String newPassword = generateRandomPassword();
        user.setPassword(newPassword);
        userRepository.save(user);

        // Enviar email con la nueva contraseña
        emailService.sendPasswordResetEmail(email, user.getUsername(), newPassword);

        System.out. println("Contraseña reseteada para:  " + email);
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
        return userRepository.findStudentsByProfessorId(professorId);
    }
}