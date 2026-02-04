package com.elorrieta.controller;

import com. elorrieta.dto.HorarioDTO;
import com. elorrieta.service.HorarioService;
import org. springframework.beans.factory.annotation. Autowired;
import org. springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework. web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para gestión de horarios
 * 
 * Endpoints:  
 * - GET /api/horarios
 * - GET /api/horarios/{id}
 * - GET /api/horarios/profesor/{profesorId}
 * - GET /api/horarios/alumno/{alumnoId}
 */
@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    /**
     * GET /api/horarios
     * 
     * Obtener todos los horarios (devuelve DTOs)
     * 
     * @return 200 OK + lista de horarios
     */
    @GetMapping
    public ResponseEntity<List<HorarioDTO>> getAllHorarios() {
        try {
            List<HorarioDTO> horarios = horarioService.getAll();
            return ResponseEntity. ok(horarios);
        } catch (Exception e) {
            System.err.println("Error al obtener horarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/horarios/{id}
     * 
     * Obtener un horario por ID (devuelve DTO)
     * 
     * @param id ID del horario
     * @return 200 OK + horario
     *         404 NOT_FOUND si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getHorarioById(@PathVariable int id) {
        try {
            HorarioDTO horario = horarioService.getById(id);

            if (horario == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Horario no encontrado"));
            }

            return ResponseEntity.ok(horario);

        } catch (Exception e) {
            System.err.println("Error al obtener horario: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * GET /api/horarios/profesor/{profesorId}
     * 
     * Obtener el horario semanal de un profesor (devuelve DTOs)
     * 
     * @param profesorId ID del profesor
     * @return 200 OK + horario del profesor
     *         404 NOT_FOUND si el profesor no tiene horario
     */
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<?> getHorarioProfesor(@PathVariable int profesorId) {
        try {
            List<HorarioDTO> horarios = horarioService.getHorarioProfesor(profesorId);

            if (horarios == null || horarios.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("El profesor no tiene horario asignado"));
            }

            return ResponseEntity.ok(horarios);

        } catch (Exception e) {
            System.err. println("Error al obtener horario del profesor: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error interno del servidor"));
        }
    }

    /**
     * GET /api/horarios/alumno/{alumnoId}
     * 
     * Obtener el horario semanal de un alumno (generado dinámicamente)
     * 
     * @param alumnoId ID del alumno
     * @return 200 OK + horario del alumno
     *         404 NOT_FOUND si el alumno no tiene horario
     */
    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<?> getHorarioAlumno(@PathVariable int alumnoId) {
        try {
            List<HorarioDTO> horarios = horarioService.getHorariosAlumno(alumnoId);

            if (horarios == null || horarios.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("No se pudo generar el horario del alumno"));
            }

            return ResponseEntity.ok(horarios);

        } catch (Exception e) {
            System.err.println("Error al generar horario del alumno: " + e.getMessage());
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