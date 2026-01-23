package com.elorrieta.service;

import com.elorrieta. dto.HorarioDTO;
import com.elorrieta. entities.Horario;
import com.elorrieta.entities.User;
import com.elorrieta.mapper.HorarioMapper;
import com.elorrieta.repository.HorarioRepository;
import org.springframework.beans.factory. annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction. annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private HorarioMapper horarioMapper;

    public HorarioDTO getById(int id) {
        Optional<Horario> horario = horarioRepository.findById(id);
        return horario.map(horarioMapper::toDTO).orElse(null);
    }

    public List<HorarioDTO> getAll() {
        List<Horario> horarios = horarioRepository.findAll();
        return horarios.stream()
                .map(horarioMapper::toDTO)
                .collect(Collectors.toList());
    }


    public HorarioDTO save(HorarioDTO horarioDTO) {
        Horario horario = horarioMapper.toEntity(horarioDTO);
        Horario savedHorario = horarioRepository.save(horario);
        return horarioMapper.toDTO(savedHorario);
    }

    public void delete(int id) {
        horarioRepository.deleteById(id);
    }

    public List<HorarioDTO> getHorarioProfesor(int profesorId) {
        List<Horario> horarios = horarioRepository.findByProfesorId(profesorId);
        return horarios.stream()
                .map(horarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<Horario> getHorariosProfesor(User profesor) {
        return horarioRepository.findByProfesorId(profesor.getId());
    }

    public List<HorarioDTO> getHorarioAlumno(int alumnoId) {
        // TODO: Generar horario del alumno dinámicamente
        return null;
    }
}