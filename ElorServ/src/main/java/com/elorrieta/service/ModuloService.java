package com.elorrieta.service;

import com.elorrieta. dao.ModuloDAO;
import com.elorrieta. entities.Modulo;
import org.springframework.beans.factory. annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestión de módulos
 */
@Service
public class ModuloService {

    @Autowired
    private ModuloDAO moduloDAO;

    public Modulo getById(int id) {
        return moduloDAO.getById(id);
    }

    public List<Modulo> getAll() {
        return moduloDAO.getAll();
    }

    public void save(Modulo modulo) {
        if (modulo.getId() == null || modulo.getId() == 0) {
            moduloDAO.add(modulo);
        } else {
            moduloDAO.update(modulo);
        }
    }

    public void delete(int id) {
        moduloDAO.delete(id);
    }

    /**
     * Obtener módulos de un ciclo específico
     */
    public List<Modulo> getModulosPorCiclo(int cicloId) {
        // TODO: Implementar filtrado por ciclo_id
        return moduloDAO.getAll();
    }

    /**
     * Obtener módulos de un ciclo y curso específico
     */
    public List<Modulo> getModulosPorCicloYCurso(int cicloId, byte curso) {
        // TODO: Implementar filtrado por ciclo_id y curso
        return moduloDAO.getAll();
    }
}