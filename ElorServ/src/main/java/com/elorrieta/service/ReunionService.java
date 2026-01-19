package com.elorrieta.service;

import com.elorrieta.entities.Reuniones;
import com.elorrieta.repository.ReunionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReunionService {

    @Autowired
    private ReunionesRepository reunionesRepository;

    public Reuniones getById(int id) {
        Optional<Reuniones> reunion = reunionesRepository.findById(id);
        return reunion.orElse(null);
    }

    public List<Reuniones> getAll() {
        return reunionesRepository.findAll();
    }

    public void save(Reuniones reunion) {
        reunionesRepository.save(reunion);
    }

    public void delete(int id) {
        reunionesRepository.deleteById(id);
    }

    public boolean cambiarEstado(int reunionId, String nuevoEstado) {
        Optional<Reuniones> reunionOpt = reunionesRepository.findById(reunionId);
        
        if (reunionOpt.isEmpty()) {
            System.out.println("Reunión no encontrada");
            return false;
        }
        
        Reuniones reunion = reunionOpt.get();
        reunion.setEstado(nuevoEstado);
        reunionesRepository.save(reunion);
        
        System.out.println("Estado de reunión cambiado a: " + nuevoEstado);
        return true;
    }
}