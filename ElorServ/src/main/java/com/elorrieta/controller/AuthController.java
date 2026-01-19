package com.elorrieta.controller;

import com.elorrieta.entities.User;
import com.elorrieta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller REST para autenticación
 * 
 * Endpoints:
 * - POST /api/auth/login
 * - POST /api/auth/reset-password
 * 
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
     * Login de usuario
     * 
     * @param loginRequest JSON con username y password
     * @return 200 OK + datos del usuario si login correcto
     *         401 UNAUTHORIZED si credenciales incorrectas
     *         500 INTERNAL_SERVER_ERROR si hay error en servidor
     * 
     * Ejemplo petición:
     * {
     *   "username": "profesor1",
     *   "password":  "1234"
     * }
     * 
     * Ejemplo respuesta exitosa:
     * {
     *   "success": true,
     *   "message": "Login correcto",
     *   "user": {
     *     "id": 1,
     *     "username": "profesor1",
     *     "email": "profesor1@elorrieta.com",
     *     "nombre": "Juan",
     *     "apellidos":  "García López",
     *     "tipo":  {
     *       "id": 1,
     *       "name": "Profesor",
     *       "nameEu": "Irakaslea"
     *     }
     *   }
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        try {
            // Validar que vengan los campos obligatorios
            if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("El campo 'username' es obligatorio"));
            }

            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("El campo 'password' es obligatorio"));
            }

            // Intentar login
            User user = userService.login(loginRequest);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(createErrorResponse("Usuario o contraseña incorrectos"));
            }

            // Login exitoso - NO devolver el password
            user.setPassword(null);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login correcto");
            response.put("user", user);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * POST /api/auth/reset-password
     * 
     * Resetear contraseña (genera una aleatoria y la envía por email)
     * 
     * @param resetRequest JSON con email del usuario
     * @return 200 OK si se envió el email correctamente
     *         404 NOT_FOUND si el usuario no existe
     *         500 INTERNAL_SERVER_ERROR si hay error
     * 
     * Ejemplo petición:
     * {
     *   "email": "profesor1@elorrieta.com"
     * }
     * 
     * Ejemplo respuesta exitosa:
     * {
     *   "success": true,
     *   "message": "Se ha enviado una nueva contraseña a tu email"
     * }
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> resetRequest) {
        try {
            String email = resetRequest.get("email");

            if (email == null || email.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("El campo 'email' es obligatorio"));
            }

            String newPassword = userService.resetPassword(email);

            if (newPassword == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("No existe un usuario con ese email"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Se ha enviado una nueva contraseña a tu email");
            // TODO: Cuando implementemos MailService, aquí se enviará el email

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error en reset password: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * POST /api/auth/change-password
     * 
     * Cambiar contraseña de un usuario logueado
     * 
     * @param changeRequest JSON con userId, oldPassword, newPassword
     * @return 200 OK si se cambió correctamente
     *         400 BAD_REQUEST si la contraseña actual es incorrecta
     *         404 NOT_FOUND si el usuario no existe
     */
    @PostMapping("/change-password")
    public ResponseEntity<? > changePassword(@RequestBody Map<String, Object> changeRequest) {
        try {
            Integer userId = (Integer) changeRequest.get("userId");
            String oldPassword = (String) changeRequest.get("oldPassword");
            String newPassword = (String) changeRequest.get("newPassword");

            if (userId == null || oldPassword == null || newPassword == null) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("Faltan campos obligatorios"));
            }

            boolean changed = userService.changePassword(userId, oldPassword, newPassword);

            if (! changed) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("Contraseña actual incorrecta"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Contraseña cambiada correctamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error en change password: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        return error;
    }
}