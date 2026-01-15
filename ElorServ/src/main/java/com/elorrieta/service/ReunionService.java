package com.elorrieta.service;

import com.elorrieta.dao.ReunionesDAO;
import com.elorrieta.entities.Reuniones;
import org.springframework. beans.factory.annotation.Autowired;
import org.springframework. stereotype.Service;

import java. util.List;

/**
 * Servicio para gestión de reuniones
 * Incluye lógica de negocio específica de reuniones
 */
@Service
public class ReunionService {

    @Autowired
    private ReunionesDAO reunionesDAO;

    public Reuniones getById(int id) {
        return reunionesDAO.getById(id);
    }

    public List<Reuniones> getAll() {
        return reunionesDAO.getAll();
    }

    public void save(Reuniones reunion) {
        if (reunion.getId() == null || reunion.getId() == 0) {
            reunionesDAO.add(reunion);
        } else {
            reunionesDAO.update(reunion);
        }
    }

    public void delete(int id) {
        reunionesDAO.delete(id);
    }

    /**
     * Cambiar el estado de una reunión
     * Estados: pendiente, aceptada, cancelada, confirmada
     * TODO: Enviar email cuando cambie el estado
     */
    public boolean cambiarEstado(int reunionId, String nuevoEstado) {
        Reuniones reunion = reunionesDAO.getById(reunionId);
        
        if (reunion == null) {
            System.out.println("Reunión no encontrada");
            return false;
        }
        
        reunion.setEstado(nuevoEstado);
        reunionesDAO.update(reunion);
        
        System.out.println("Estado de reunión cambiado a: " + nuevoEstado);
        // TODO: Enviar email a los participantes
        
        return true;
    }
}