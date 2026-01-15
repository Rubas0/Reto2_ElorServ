package com.elorrieta.service;

import com. elorrieta.dao.HorarioDAO;
import com.elorrieta.entities.Horario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestión de horarios
 */
@Service
public class HorarioService {

    @Autowired
    private HorarioDAO horarioDAO;

    public Horario getById(int id) {
        return horarioDAO.getById(id);
    }

    public List<Horario> getAll() {
        return horarioDAO.getAll();
    }

    public void save(Horario horario) {
        if (horario.getId() == null || horario.getId() == 0) {
            horarioDAO.add(horario);
        } else {
            horarioDAO.update(horario);
        }
    }

    public void delete(int id) {
        horarioDAO.delete(id);
    }

    /**
     * Obtener horario semanal de un profesor
     * TODO: Implementar lógica para filtrar por semana específica
     * Por ahora devuelve todo el horario del profesor
     */
    public List<Horario> getHorarioProfesor(int profesorId) {
        // TODO: Filtrar por profesor_id y semana
        // Por ahora devolvemos todos los horarios
        return horarioDAO.getAll();
    }

    /**
     * Obtener horario semanal de un alumno
     * NOTA: El horario del alumno se genera dinámicamente
     * a partir de los horarios de sus profesores
     * TODO: Implementar generación dinámica
     */
    public List<Horario> getHorarioAlumno(int alumnoId) {
        // TODO: Generar horario del alumno a partir de: 
        // - Su ciclo
        // - Su curso
        // - Los horarios de los profesores que le dan clase
        return null; // Por implementar
    }
}