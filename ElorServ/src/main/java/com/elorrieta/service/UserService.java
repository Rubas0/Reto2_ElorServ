package com.elorrieta.service;

import com. elorrieta.dao.UserDAO;
import com.elorrieta.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestión de usuarios
 * Capa intermedia entre Controllers y DAOs
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    // ========== CRUD BÁSICO ==========

    public User getById(int id) {
        return userDAO.getById(id);
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public void save(User user) {
        if (user.getId() == null || user.getId() == 0) {
            userDAO.add(user);
        } else {
            userDAO.update(user);
        }
    }

    public void delete(int id) {
        userDAO.delete(id);
    }

    // ========== MÉTODOS DE AUTENTICACIÓN ==========

    /**
     * Login de usuario
     * @param userLogin Usuario con username y password
     * @return Usuario completo si las credenciales son correctas, null en caso contrario
     */
    public User login(User userLogin) {
        return userDAO.login(userLogin);
    }

    /**
     * Buscar usuario por email
     * Útil para recuperación de contraseña
     */
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    /**
     * Cambiar contraseña de un usuario
     * TODO: Implementar cifrado cuando añadamos seguridad
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        User user = userDAO.getById(userId);
        
        if (user == null) {
            System.out.println("Usuario no encontrado");
            return false;
        }
        
        // Verificar contraseña actual
        if (!user.getPassword().equals(oldPassword)) {
            System.out.println("Contraseña actual incorrecta");
            return false;
        }
        
        // Cambiar contraseña
        user.setPassword(newPassword);
        userDAO.update(user);
        
        System.out.println("Contraseña cambiada correctamente");
        return true;
    }

    /**
     * Resetear contraseña (genera una aleatoria)
     * TODO: Enviar email con la nueva contraseña
     */
    public String resetPassword(String email) {
        User user = userDAO.getUserByEmail(email);
        
        if (user == null) {
            System.out.println("Usuario no encontrado con ese email");
            return null;
        }
        
        // Generar contraseña aleatoria (8 caracteres)
        String newPassword = generateRandomPassword();
        user.setPassword(newPassword);
        userDAO.update(user);
        
        System.out.println("Contraseña reseteada para: " + email);
        // TODO: Enviar email con la nueva contraseña
        
        return newPassword;
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Genera una contraseña aleatoria de 8 caracteres
     */
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        
        return password. toString();
    }
}