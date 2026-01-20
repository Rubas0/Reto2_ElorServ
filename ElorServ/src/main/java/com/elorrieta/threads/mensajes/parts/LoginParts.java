package com.elorrieta.threads.mensajes.parts;

import java.io.Serializable;

public class LoginParts implements Serializable {

    private static final long serialVersionUID = -8632164890217148L;
    private String username;
    private String password;

    public LoginParts() {
    }

    public LoginParts(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
