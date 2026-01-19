package com.elorrieta.service;

import com.elorrieta.entities.User;
import com.elorrieta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de usuarios
 * Usa UserRepository (Spring Data JPA)
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ========== CRUD BÁSICO ==========

    public User getById(int id) {
        Optional<User> user = userRepository. findById(id);
        return user.orElse(null);
    }

    public List<User> getAll() {
        return userRepository. findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    // ========== MÉTODOS DE AUTENTICACIÓN ==========

    public User login(User userLogin) {
        Optional<User> userOpt = userRepository.findByUsernameWithTipo(userLogin.getUsername());
        
        if (userOpt.isEmpty()) {
            System.out.println("Usuario no encontrado");
            return null;
        }
        
        User user = userOpt.get();
        
        if (!user.getPassword().equals(userLogin.getPassword())) {
            System.out. println("Contraseña incorrecta");
            return null;
        }
        
        System.out.println("Login correcto");
        return user;
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isEmpty()) {
            System.out.println("Usuario no encontrado");
            return false;
        }
        
        User user = userOpt. get();
        
        if (! user.getPassword().equals(oldPassword)) {
            System.out.println("Contraseña actual incorrecta");
            return false;
        }
        
        user.setPassword(newPassword);
        userRepository.save(user);
        
        System.out.println("Contraseña cambiada correctamente");
        return true;
    }

    public String resetPassword(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            System.out.println("Usuario no encontrado con ese email");
            return null;
        }
        
        User user = userOpt.get();
        String newPassword = generateRandomPassword();
        user.setPassword(newPassword);
        userRepository.save(user);
        
        System.out. println("Contraseña reseteada para:  " + email);
        return newPassword;
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
}