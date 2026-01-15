package com.elorrieta.service;

import com.elorrieta.dao.MatriculacionesDAO;
import com.elorrieta.entities.Matriculaciones;
import org.springframework.beans.factory.annotation. Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestión de matriculaciones
 */
@Service
public class MatriculacionService {

    @Autowired
    private MatriculacionesDAO matriculacionesDAO;

    public Matriculaciones getById(int id) {
        return matriculacionesDAO.getById(id);
    }

    public List<Matriculaciones> getAll() {
        return matriculacionesDAO.getAll();
    }

    public void save(Matriculaciones matriculacion) {
        if (matriculacion.getId() == null || matriculacion.getId() == 0) {
            matriculacionesDAO.add(matriculacion);
        } else {
            matriculacionesDAO.update(matriculacion);
        }
    }

    public void delete(int id) {
        matriculacionesDAO.delete(id);
    }

    /**
     * Obtener matriculaciones de un alumno específico
     */
    public List<Matriculaciones> getMatriculacionesAlumno(int alumnoId) {
        // TODO: Implementar filtrado por alumno_id
        return matriculacionesDAO.getAll();
    }

    /**
     * Obtener alumnos matriculados en un ciclo y curso específico
     */
    public List<Matriculaciones> getAlumnosPorCicloYCurso(int cicloId, byte curso) {
        // TODO: Implementar filtrado por ciclo_id y curso
        return matriculacionesDAO.getAll();
    }
}