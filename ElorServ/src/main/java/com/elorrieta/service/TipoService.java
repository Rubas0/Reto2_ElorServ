package com.elorrieta.service;

import com.elorrieta.entities.Tipo;
import com.elorrieta.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    public Tipo getById(int id) {
        Optional<Tipo> tipo = tipoRepository.findById(id);
        return tipo.orElse(null);
    }

    public List<Tipo> getAll() {
        return tipoRepository.findAll();
    }

    public void save(Tipo tipo) {
        tipoRepository.save(tipo);
    }

    public void delete(int id) {
        tipoRepository.deleteById(id);
    }

    public Tipo getTipoProfesor() {
        Optional<Tipo> tipo = tipoRepository.findByName("Profesor");
        return tipo.orElse(null);
    }

    public Tipo getTipoAlumno() {
        Optional<Tipo> tipo = tipoRepository.findByName("Alumno");
        return tipo.orElse(null);
    }
}