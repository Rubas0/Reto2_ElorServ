package com.elorrieta.service;

import com.elorrieta.dao.TipoDAO;
import com.elorrieta.entities.Tipo;
import org. springframework.beans.factory.annotation. Autowired;
import org. springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestión de tipos de usuario
 * (Profesor, Alumno, Administrador, etc.)
 */
@Service
public class TipoService {

    @Autowired
    private TipoDAO tipoDAO;

    public Tipo getById(int id) {
        return tipoDAO.getById(id);
    }

    public List<Tipo> getAll() {
        return tipoDAO.getAll();
    }

    public void save(Tipo tipo) {
        if (tipo.getId() == null || tipo.getId() == 0) {
            tipoDAO.add(tipo);
        } else {
            tipoDAO.update(tipo);
        }
    }

    public void delete(int id) {
        tipoDAO.delete(id);
    }

    /**
     * Obtener tipo "Profesor"
     * Útil para filtrados
     */
    public Tipo getTipoProfesor() {
        // TODO: Buscar por name = "Profesor"
        // Por ahora asumimos que el ID 1 es Profesor
        return tipoDAO.getById(1);
    }

    /**
     * Obtener tipo "Alumno"
     * Útil para filtrados
     */
    public Tipo getTipoAlumno() {
        // TODO: Buscar por name = "Alumno"
        // Por ahora asumimos que el ID 2 es Alumno
        return tipoDAO.getById(2);
    }
}