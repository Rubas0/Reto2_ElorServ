package com.elorrieta.dto;

/**
 * DTO para devolver respuesta de login
 */
public class LoginResponseDTO {
    private boolean success;
    private String message;
    private UserDTO user;  // Solo si login correcto

    // Constructores
    public LoginResponseDTO() {
    }

    public LoginResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponseDTO(boolean success, String message, UserDTO user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}