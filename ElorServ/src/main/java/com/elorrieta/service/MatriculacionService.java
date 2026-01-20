package com.elorrieta.service;

import com.elorrieta.dto.MatriculacionDTO;
import com.elorrieta. entities.Matriculaciones;
import com.elorrieta.mapper.MatriculacionMapper;
import com.elorrieta.repository.MatriculacionesRepository;
import org.springframework. beans.factory.annotation.Autowired;
import org.springframework. stereotype.Service;
import org. springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MatriculacionService {

    @Autowired
    private MatriculacionesRepository matriculacionesRepository;

    @Autowired
    private MatriculacionMapper matriculacionMapper;

    public MatriculacionDTO getById(int id) {
        Optional<Matriculaciones> matriculacion = matriculacionesRepository.findById(id);
        return matriculacion.map(matriculacionMapper::toDTO).orElse(null);
    }

    public List<MatriculacionDTO> getAll() {
        List<Matriculaciones> matriculaciones = matriculacionesRepository. findAll();
        return matriculaciones.stream()
                .map(matriculacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MatriculacionDTO save(MatriculacionDTO matriculacionDTO) {
        Matriculaciones matriculacion = matriculacionMapper.toEntity(matriculacionDTO);
        Matriculaciones savedMatriculacion = matriculacionesRepository.save(matriculacion);
        return matriculacionMapper.toDTO(savedMatriculacion);
    }

    public void delete(int id) {
        matriculacionesRepository.deleteById(id);
    }

    public List<MatriculacionDTO> getMatriculacionesAlumno(int alumnoId) {
        List<Matriculaciones> matriculaciones = matriculacionesRepository. findByAlumnoId(alumnoId);
        return matriculaciones.stream()
                .map(matriculacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MatriculacionDTO> getAlumnosPorCicloYCurso(int cicloId, byte curso) {
        List<Matriculaciones> matriculaciones = matriculacionesRepository.findByCicloIdAndCurso(cicloId, curso);
        return matriculaciones.stream()
                .map(matriculacionMapper::toDTO)
                .collect(Collectors.toList());
    }
}