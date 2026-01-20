package com.elorrieta. dto;

/**
 * DTO para recibir peticiones de login
 */
public class LoginRequestDTO {
    private String username;
    private String password;  // Hay que cifrarlo antes creo

    // Constructores
    public LoginRequestDTO() {
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}