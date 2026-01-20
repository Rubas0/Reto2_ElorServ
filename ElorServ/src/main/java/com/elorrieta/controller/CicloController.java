package com.elorrieta.controller;

import com.elorrieta.dto.CicloDTO;
import com.elorrieta.service.CicloService;
import org.springframework.beans.factory. annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework. http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para gestión de ciclos formativos
 */
@RestController
@RequestMapping("/api/ciclos")
@CrossOrigin(origins = "*")
public class CicloController {

    @Autowired
    private CicloService cicloService;

    @GetMapping
    public ResponseEntity<List<CicloDTO>> getAllCiclos() {
        try {
            List<CicloDTO> ciclos = cicloService.getAll();
            return ResponseEntity. ok(ciclos);
        } catch (Exception e) {
            System.err.println("Error al obtener ciclos: " + e. getMessage());
            return ResponseEntity. status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<? > getCicloById(@PathVariable int id) {
        try {
            CicloDTO ciclo = cicloService.getById(id);

            if (ciclo == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Ciclo no encontrado"));
            }

            return ResponseEntity.ok(ciclo);

        } catch (Exception e) {
            System.err.println("Error al obtener ciclo:  " + e.getMessage());
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