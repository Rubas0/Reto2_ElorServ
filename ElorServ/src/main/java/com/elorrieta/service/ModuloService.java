package com.elorrieta.service;

import com.elorrieta.entities.Modulo;
import com.elorrieta.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation. Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    public Modulo getById(int id) {
        Optional<Modulo> modulo = moduloRepository.findById(id);
        return modulo.orElse(null);
    }

    public List<Modulo> getAll() {
        return moduloRepository.findAll();
    }

    public void save(Modulo modulo) {
        moduloRepository.save(modulo);
    }

    public void delete(int id) {
        moduloRepository.deleteById(id);
    }

    public List<Modulo> getModulosPorCiclo(int cicloId) {
        return moduloRepository.findByCicloId(cicloId);
    }

    public List<Modulo> getModulosPorCicloYCurso(int cicloId, byte curso) {
        return moduloRepository.findByCicloIdAndCurso(cicloId, curso);
    }
}