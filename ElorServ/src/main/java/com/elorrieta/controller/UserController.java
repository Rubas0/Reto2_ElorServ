package com.elorrieta.controller;

import com.elorrieta.entities.User;
import com.elorrieta.service.UserService;
import org. springframework.beans.factory.annotation. Autowired;
import org. springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para gestión de usuarios
 * 
 * Endpoints:
 * - GET    /api/users
 * - GET    /api/users/{id}
 * - POST   /api/users
 * - PUT    /api/users/{id}
 * - DELETE /api/users/{id}
 * - POST   /api/users/{id}/photo
 * - GET    /api/users/{id}/photo
 */

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Ruta donde se guardan las fotos
    private static final String UPLOAD_DIR = "uploads/photos/";

    /**
     * GET /api/users
     * 
     * Obtener todos los usuarios
     * 
     * @return 200 OK + lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAll();
            
            // Quitar passwords antes de enviar
            users.forEach(user -> user.setPassword(null));
            
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/users/{id}
     * 
     * Obtener un usuario por ID
     * 
     * @param id ID del usuario
     * @return 200 OK + usuario
     *         404 NOT_FOUND si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<? > getUserById(@PathVariable int id) {
        try {
            User user = userService.getById(id);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            // Quitar password antes de enviar
            user. setPassword(null);

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * POST /api/users
     * 
     * Crear un nuevo usuario
     * 
     * @param user Datos del usuario
     * @return 201 CREATED + usuario creado
     *         400 BAD_REQUEST si faltan datos
     */
    @PostMapping
    public ResponseEntity<? > createUser(@RequestBody User user) {
        try {
            // Validaciones básicas
            if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("Faltan campos obligatorios (username, email, password)"));
            }

            userService.save(user);

            // Quitar password antes de devolver
            user.setPassword(null);

            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        } catch (Exception e) {
            System. err.println("Error al crear usuario: " + e.getMessage());
            return ResponseEntity
                    . status(HttpStatus.INTERNAL_SERVER_ERROR)
                    . body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * PUT /api/users/{id}
     * 
     * Actualizar un usuario existente
     * 
     * @param id ID del usuario
     * @param user Nuevos datos del usuario
     * @return 200 OK + usuario actualizado
     *         404 NOT_FOUND si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<? > updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            User existing = userService.getById(id);

            if (existing == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            user.setId(id);
            userService.save(user);

            // Quitar password antes de devolver
            user.setPassword(null);

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * DELETE /api/users/{id}
     * 
     * Eliminar un usuario
     * 
     * @param id ID del usuario
     * @return 204 NO_CONTENT si se eliminó correctamente
     *         404 NOT_FOUND si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<? > deleteUser(@PathVariable int id) {
        try {
            User user = userService.getById(id);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            userService.delete(id);

            return ResponseEntity. noContent().build();

        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * POST /api/users/{id}/photo
     * 
     * Subir foto de perfil de un usuario
     * 
     * @param id ID del usuario
     * @param file Archivo de imagen
     * @return 200 OK si se subió correctamente
     *         404 NOT_FOUND si el usuario no existe
     *         400 BAD_REQUEST si el archivo es inválido
     */
    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        try {
            User user = userService.getById(id);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            if (file.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("El archivo está vacío"));
            }

            // Validar que sea una imagen
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("El archivo debe ser una imagen"));
            }

            // Crear directorio si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generar nombre único para la foto
            String extension = getFileExtension(file.getOriginalFilename());
            String filename = "user_" + id + "_" + System.currentTimeMillis() + extension;
            Path filepath = Paths.get(UPLOAD_DIR + filename);

            // Guardar archivo
            Files.write(filepath, file.getBytes());

            // Actualizar URL en la base de datos
            user.setArgazkiaUrl(filename);
            userService.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response. put("message", "Foto subida correctamente");
            response. put("photoUrl", filename);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            System.err.println("Error al guardar foto: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al guardar la foto"));
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e. getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * GET /api/users/{id}/photo
     * 
     * Descargar foto de perfil de un usuario
     * 
     * @param id ID del usuario
     * @return 200 OK + imagen
     *         404 NOT_FOUND si el usuario o la foto no existen
     * 
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<?> getPhoto(@PathVariable int id) {
        try {
            User user = userService.getById(id);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            if (user.getArgazkiaUrl() == null || user.getArgazkiaUrl().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("El usuario no tiene foto"));
            }

            Path filepath = Paths.get(UPLOAD_DIR + user.getArgazkiaUrl());

            if (! Files.exists(filepath)) {
                return ResponseEntity
                        . status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Archivo de foto no encontrado"));
            }

            byte[] imageBytes = Files.readAllBytes(filepath);

            // Determinar el tipo MIME
            String contentType = Files.probeContentType(filepath);
            if (contentType == null) {
                contentType = "image/jpeg"; // Por defecto
            }

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);

        } catch (IOException e) {
            System.err.println("Error al leer foto: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al leer la foto"));
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        return error;
    }
}