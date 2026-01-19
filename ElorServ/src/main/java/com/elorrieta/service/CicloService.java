package com.elorrieta.service;

import com.elorrieta.entities.Ciclo;
import com.elorrieta.repository.CicloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CicloService {

    @Autowired
    private CicloRepository cicloRepository;

    public Ciclo getById(int id) {
        Optional<Ciclo> ciclo = cicloRepository.findById(id);
        return ciclo.orElse(null);
    }

    public List<Ciclo> getAll() {
        return cicloRepository.findAll();
    }

    public void save(Ciclo ciclo) {
        cicloRepository.save(ciclo);
    }

    public void delete(int id) {
        cicloRepository.deleteById(id);
    }
}