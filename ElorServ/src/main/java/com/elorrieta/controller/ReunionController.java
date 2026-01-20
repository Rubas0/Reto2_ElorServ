package com.elorrieta.controller;

import com.elorrieta.dto.ReunionDTO;
import com.elorrieta.service.ReunionService;
import org.springframework.beans.factory. annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org. springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para gestión de reuniones
 */
@RestController
@RequestMapping("/api/reuniones")
@CrossOrigin(origins = "*")
public class ReunionController {

    @Autowired
    private ReunionService reunionService;

    @GetMapping
    public ResponseEntity<List<ReunionDTO>> getAllReuniones() {
        try {
            List<ReunionDTO> reuniones = reunionService.getAll();
            return ResponseEntity.ok(reuniones);
        } catch (Exception e) {
            System. err.println("Error al obtener reuniones: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReunionById(@PathVariable int id) {
        try {
            ReunionDTO reunion = reunionService.getById(id);

            if (reunion == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Reunión no encontrada"));
            }

            return ResponseEntity.ok(reunion);

        } catch (Exception e) {
            System.err.println("Error al obtener reunión: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createReunion(@RequestBody ReunionDTO reunionDTO) {
        try {
            if (reunionDTO.getProfesor() == null || reunionDTO.getAlumno() == null) {
                return ResponseEntity
                        . badRequest()
                        .body(createErrorResponse("Faltan campos obligatorios (profesor, alumno)"));
            }

            if (reunionDTO.getTitulo() == null || reunionDTO.getTitulo().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("El título es obligatorio"));
            }

            if (reunionDTO.getEstado() == null || reunionDTO.getEstado().isEmpty()) {
                reunionDTO.setEstado("pendiente");
                reunionDTO.setEstadoEus("zain");
            }

            if (reunionDTO.getIdCentro() == null || reunionDTO.getIdCentro().isEmpty()) {
                reunionDTO.setIdCentro("15112");
            }

            ReunionDTO savedReunion = reunionService. save(reunionDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedReunion);

        } catch (Exception e) {
            System. err.println("Error al crear reunión: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReunion(@PathVariable int id, @RequestBody ReunionDTO reunionDTO) {
        try {
            ReunionDTO existing = reunionService.getById(id);

            if (existing == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Reunión no encontrada"));
            }

            reunionDTO.setId(id);
            ReunionDTO updatedReunion = reunionService.save(reunionDTO);

            return ResponseEntity.ok(updatedReunion);

        } catch (Exception e) {
            System. err.println("Error al actualizar reunión: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReunion(@PathVariable int id) {
        try {
            ReunionDTO reunion = reunionService.getById(id);

            if (reunion == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Reunión no encontrada"));
            }

            reunionService. delete(id);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            System.err.println("Error al eliminar reunión: " + e. getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable int id, @RequestBody Map<String, String> estadoRequest) {
        try {
            String nuevoEstado = estadoRequest. get("estado");

            if (nuevoEstado == null || nuevoEstado.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("El campo 'estado' es obligatorio"));
            }

            List<String> estadosValidos = List.of("pendiente", "aceptada", "cancelada", "confirmada", "conflicto");
            if (!estadosValidos.contains(nuevoEstado. toLowerCase())) {
                return ResponseEntity
                        .badRequest()
                        .body(createErrorResponse("Estado inválido.  Estados válidos: " + String.join(", ", estadosValidos)));
            }

            boolean cambiado = reunionService.cambiarEstado(id, nuevoEstado);

            if (! cambiado) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Reunión no encontrada"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response. put("message", "Estado de reunión cambiado correctamente");
            response.put("nuevoEstado", nuevoEstado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System. err.println("Error al cambiar estado de reunión: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<?> getReunionesByProfesor(@PathVariable int profesorId) {
        try {
            List<ReunionDTO> reuniones = reunionService.getReunionesProfesor(profesorId);
            return ResponseEntity.ok(reuniones);

        } catch (Exception e) {
            System.err.println("Error al obtener reuniones del profesor: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<?> getReunionesByAlumno(@PathVariable int alumnoId) {
        try {
            List<ReunionDTO> reuniones = reunionService.getReunionesAlumno(alumnoId);
            return ResponseEntity.ok(reuniones);

        } catch (Exception e) {
            System. err.println("Error al obtener reuniones del alumno: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        return error;
    }
}