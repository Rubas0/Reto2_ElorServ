package com.elorrieta.controller;

import com.elorrieta.dto.UploadPhotoResponseDTO;
import com.elorrieta.dto.UserDTO;
import com.elorrieta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    // Directorio donde se guardan las fotos de perfil de los usuarios
    @Value("${upload.path:uploads}")
    private String uploadPath;

    @Autowired
    private UserService userService;

    /**
     * GET /api/users
     * Obtener todos los usuarios (devuelve DTOs, sin password)
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/users/{id}
     * Obtener un usuario por ID (devuelve DTO, sin password)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            UserDTO user = userService.getById(id);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            System.err.println("Error al obtener usuario:  " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * GET /api/users/tipo/{tipo}
     * Obtener usuarios por tipo (devuelve DTOs, sin password)
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> getUsersByTipo(@PathVariable int tipo) {
        try{
            List<UserDTO> users = userService.getByTipo(tipo);
            return ResponseEntity.ok(users);

        } catch (Exception e) {
            System.err.println("Error al obtener usuarios por tipo: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * GET /api/users/profesor/{profesorId}/students
     * Obtener aalumnos por ID de su profesor
     */
    @GetMapping("/profesor/{profesorId}/students")
    public ResponseEntity<?> getStudentsByProfessorId(@PathVariable int profesorId) {
        try {
            List<UserDTO> students = userService.getStudentsDTOByProfessorId(profesorId);
            return ResponseEntity.ok(students);

        } catch (Exception e) {
            System.err.println("Error al obtener alumnos por ID de profesor: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * POST /api/users
     * Crear un nuevo usuario
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO savedUser = userService.save(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (Exception e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al crear usuario"));
        }
    }

    /**
     * PUT /api/users/{id}
     * Actualizar un usuario existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO existingUser = userService.getById(id);

            if (existingUser == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            userDTO.setId(id);
            UserDTO updatedUser = userService.save(userDTO);
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al actualizar usuario"));
        }
    }

    /**
     * DELETE /api/users/{id}
     * Eliminar un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            UserDTO user = userService.getById(id);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuario no encontrado"));
            }

            userService.delete(id);
            return ResponseEntity.ok(createSuccessResponse("Usuario eliminado correctamente"));

        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error al eliminar usuario"));
        }
    }


    /**
     * POST /api/users/{id}/photo
     * Sube la foto de perfil del usuario
     */
    @PostMapping("/{id}/argazkiaUrl")
    public ResponseEntity<UploadPhotoResponseDTO> uploadPhoto(
            @PathVariable int id,
            @RequestParam("photo") MultipartFile file
    ) {
        try {
            // Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new UploadPhotoResponseDTO(false, "Archivo vacío", null)
                );
            }

            // Validar tipo de archivo (solo imágenes)
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body(
                        new UploadPhotoResponseDTO(false, "Solo se permiten imágenes", null)
                );
            }

            // Crear directorio si no existe
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generar nombre único para el archivo
            String fileName = "user_" + id + "_" + System.currentTimeMillis() + ".jpg";
            Path filePath = Paths.get(uploadPath, fileName);

            // Borrar foto anterior si existe
            UserDTO user = userService.getById(id);
            if (user != null && user.getArgazkiaUrl() != null) {
                Path oldPhotoPath = Paths.get(uploadPath, user.getArgazkiaUrl());
                Files.deleteIfExists(oldPhotoPath);
            }

            // Guardar el archivo nuevo
            Files.write(filePath, file.getBytes());

            userService.updatePhotoUrl(id, "/" + fileName);

            String photoUrl = "ElorServ/src/main/resources/uploads/" + fileName;

            return ResponseEntity.ok(
                    new UploadPhotoResponseDTO(true, "Foto subida correctamente", photoUrl)
            );

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    new UploadPhotoResponseDTO(false, "Error al guardar la foto: " + e.getMessage(), null)
            );
        }
    }

    /**
     * GET /api/users/{id}/argazkiaUrl
     * Obtener la foto de perfil del usuario
     * @param id
     * @return
     */
    @GetMapping("/{id}/argazkiaUrl")
    public ResponseEntity<Resource> getPhoto(@PathVariable int id) {
        try {
            UserDTO user = userService.getById(id);

            if (user == null || user.getArgazkiaUrl() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Construir la ruta completa del archivo
            Path filePath = Paths.get(uploadPath).resolve(user.getArgazkiaUrl().substring(1));
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Determinar el tipo de contenido
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ========== MÉTODOS AUXILIARES ==========

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        return error;
    }

    private Map<String, Object> createSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return response;
    }
}