package com.elorrieta.service;

import com.elorrieta.dto. TipoDTO;
import com.elorrieta.entities. Tipo;
import com.elorrieta.mapper.TipoMapper;
import com.elorrieta.repository.TipoRepository;
import org.springframework.beans.factory.annotation. Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation. Transactional;

import java. util.List;
import java. util.Optional;
import java. util.stream.Collectors;

@Service
@Transactional
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private TipoMapper tipoMapper;

    public TipoDTO getById(int id) {
        Optional<Tipo> tipo = tipoRepository.findById(id);
        return tipo.map(tipoMapper::toDTO).orElse(null);
    }

    public List<TipoDTO> getAll() {
        List<Tipo> tipos = tipoRepository.findAll();
        return tipos.stream()
                .map(tipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TipoDTO save(TipoDTO tipoDTO) {
        Tipo tipo = tipoMapper.toEntity(tipoDTO);
        Tipo savedTipo = tipoRepository.save(tipo);
        return tipoMapper.toDTO(savedTipo);
    }

    public void delete(int id) {
        tipoRepository.deleteById(id);
    }

    public TipoDTO getTipoProfesor() {
        Optional<Tipo> tipo = tipoRepository.findByName("Profesor");
        return tipo.map(tipoMapper::toDTO).orElse(null);
    }

    public TipoDTO getTipoAlumno() {
        Optional<Tipo> tipo = tipoRepository.findByName("Alumno");
        return tipo.map(tipoMapper::toDTO).orElse(null);
    }
}