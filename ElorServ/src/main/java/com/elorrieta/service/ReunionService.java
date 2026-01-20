package com.elorrieta.service;

import com.elorrieta.dto.ReunionDTO;
import com.elorrieta.entities.Reuniones;
import com.elorrieta.mapper.ReunionMapper;
import com.elorrieta.repository.ReunionesRepository;
import org.springframework.beans.factory.annotation. Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation. Transactional;

import java. util.List;
import java. util.Optional;
import java. util.stream.Collectors;

@Service
@Transactional
public class ReunionService {

    @Autowired
    private ReunionesRepository reunionesRepository;

    @Autowired
    private ReunionMapper reunionMapper;

    public ReunionDTO getById(int id) {
        Optional<Reuniones> reunion = reunionesRepository.findById(id);
        return reunion. map(reunionMapper::toDTO).orElse(null);
    }

    public List<ReunionDTO> getAll() {
        List<Reuniones> reuniones = reunionesRepository. findAll();
        return reuniones.stream()
                .map(reunionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReunionDTO save(ReunionDTO reunionDTO) {
        Reuniones reunion = reunionMapper.toEntity(reunionDTO);
        Reuniones savedReunion = reunionesRepository.save(reunion);
        return reunionMapper.toDTO(savedReunion);
    }

    public void delete(int id) {
        reunionesRepository.deleteById(id);
    }

    public boolean cambiarEstado(int reunionId, String nuevoEstado) {
        Optional<Reuniones> reunionOpt = reunionesRepository.findById(reunionId);
        
        if (reunionOpt. isEmpty()) {
            System.out.println("Reunión no encontrada");
            return false;
        }
        
        Reuniones reunion = reunionOpt.get();
        reunion.setEstado(nuevoEstado);
        reunionesRepository.save(reunion);
        
        System.out. println("Estado de reunión cambiado a: " + nuevoEstado);
        return true;
    }

    public List<ReunionDTO> getReunionesProfesor(int profesorId) {
        List<Reuniones> reuniones = reunionesRepository.findByProfesorId(profesorId);
        return reuniones.stream()
                .map(reunionMapper::toDTO)
                .collect(Collectors. toList());
    }

    public List<ReunionDTO> getReunionesAlumno(int alumnoId) {
        List<Reuniones> reuniones = reunionesRepository.findByAlumnoId(alumnoId);
        return reuniones. stream()
                .map(reunionMapper::toDTO)
                .collect(Collectors.toList());
    }
}