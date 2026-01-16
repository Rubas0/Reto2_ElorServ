package com.elorrieta.controller;

import org. springframework.http.ResponseEntity;
import org. springframework.web.bind.annotation. CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework. web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller REST raíz de la API
 * 
 * Endpoint de bienvenida y estado del servidor
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    /**
     * GET /api
     * 
     * Endpoint de bienvenida y estado de la API
     * 
     * @return 200 OK + información de la API
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> apiInfo() {
        Map<String, Object> info = new HashMap<>();
        
        info.put("nombre", "ElorServ API");
        info.put("version", "1.0.0");
        info.put("descripcion", "API REST del Framework Educativo CIFP Elorrieta-Errekamari");
        info.put("status", "online");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints. put("Login", "POST /api/auth/login");
        endpoints.put("Reset Password", "POST /api/auth/reset-password");
        endpoints.put("Change Password", "POST /api/auth/change-password");
        endpoints.put("Usuarios", "GET/POST/PUT/DELETE /api/users");
        endpoints.put("Subir Foto", "POST /api/users/{id}/photo");
        endpoints.put("Descargar Foto", "GET /api/users/{id}/photo");
        endpoints.put("Reuniones", "GET/POST/PUT/DELETE /api/reuniones");
        endpoints.put("Cambiar Estado Reunión", "PUT /api/reuniones/{id}/estado");
        endpoints.put("Horarios", "GET /api/horarios");
        endpoints.put("Horario Profesor", "GET /api/horarios/profesor/{id}");
        endpoints.put("Horario Alumno", "GET /api/horarios/alumno/{id}");
        endpoints.put("Ciclos", "GET /api/ciclos");
        
        info.put("endpoints", endpoints);
        
        return ResponseEntity.ok(info);
    }

    /**
     * GET /api/health
     * 
     * Endpoint de salud del servidor (health check)
     * 
     * @return 200 OK si el servidor está funcionando
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("message", "ElorServ API está funcionando correctamente");
        
        return ResponseEntity.ok(health);
    }
}