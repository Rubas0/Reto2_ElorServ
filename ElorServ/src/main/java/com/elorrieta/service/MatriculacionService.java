package com.elorrieta.service;

import com.elorrieta.entities.Matriculaciones;
import com.elorrieta.repository.MatriculacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatriculacionService {

    @Autowired
    private MatriculacionesRepository matriculacionesRepository;

    public Matriculaciones getById(int id) {
        Optional<Matriculaciones> matriculacion = matriculacionesRepository.findById(id);
        return matriculacion.orElse(null);
    }

    public List<Matriculaciones> getAll() {
        return matriculacionesRepository.findAll();
    }

    public void save(Matriculaciones matriculacion) {
        matriculacionesRepository.save(matriculacion);
    }

    public void delete(int id) {
        matriculacionesRepository.deleteById(id);
    }

    public List<Matriculaciones> getMatriculacionesAlumno(int alumnoId) {
        return matriculacionesRepository.findByAlumnoId(alumnoId);
    }

    public List<Matriculaciones> getAlumnosPorCicloYCurso(int cicloId, byte curso) {
        return matriculacionesRepository.findByCicloIdAndCurso(cicloId, curso);
    }
}