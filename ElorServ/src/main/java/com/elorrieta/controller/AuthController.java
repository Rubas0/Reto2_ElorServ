package com.elorrieta.controller;

import com.elorrieta.dto.LoginRequestDTO;
import com.elorrieta.dto.LoginResponseDTO;
import com.elorrieta.service.UserService;
import org. springframework.beans.factory.annotation. Autowired;
import org. springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para autenticación de usuarios
 * 
 * Endpoints:
 * - POST /api/auth/login
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * POST /api/auth/login
     * 
     * Login de usuario (Profesor, Alumno, Administrador)
     * 
     * @param loginRequest Objeto con username y password
     * @return 200 OK + LoginResponseDTO (con UserDTO si login correcto)
     *         401 UNAUTHORIZED si las credenciales son incorrectas
     *         500 INTERNAL_SERVER_ERROR si hay error en el servidor
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = userService.login(loginRequest);
            
            if (response. isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity. status(401).body(response);
            }

        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body(new LoginResponseDTO(false, "Error interno del servidor"));
        }
    }
}