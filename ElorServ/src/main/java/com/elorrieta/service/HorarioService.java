package com.elorrieta.service;

import com.elorrieta.entities.Horario;
import com.elorrieta.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    public Horario getById(int id) {
        Optional<Horario> horario = horarioRepository.findById(id);
        return horario.orElse(null);
    }

    public List<Horario> getAll() {
        return horarioRepository.findAll();
    }

    public void save(Horario horario) {
        horarioRepository.save(horario);
    }

    public void delete(int id) {
        horarioRepository.deleteById(id);
    }

    public List<Horario> getHorarioProfesor(int profesorId) {
        return horarioRepository. findByProfesorId(profesorId);
    }

    public List<Horario> getHorarioAlumno(int alumnoId) {
        // TODO: Generar horario del alumno dinámicamente
        return null;
    }
}