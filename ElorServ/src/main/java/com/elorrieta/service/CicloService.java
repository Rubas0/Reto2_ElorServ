package com.elorrieta.service;

import com.elorrieta.dao.CicloDAO;
import com.elorrieta.entities.Ciclo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestión de ciclos formativos
 */
@Service
public class CicloService {

    @Autowired
    private CicloDAO cicloDAO;

    public Ciclo getById(int id) {
        return cicloDAO.getById(id);
    }

    public List<Ciclo> getAll() {
        return cicloDAO.getAll();
    }

    public void save(Ciclo ciclo) {
        if (ciclo.getId() == null || ciclo.getId() == 0) {
            cicloDAO. add(ciclo);
        } else {
            cicloDAO. update(ciclo);
        }
    }

    public void delete(int id) {
        cicloDAO.delete(id);
    }
}