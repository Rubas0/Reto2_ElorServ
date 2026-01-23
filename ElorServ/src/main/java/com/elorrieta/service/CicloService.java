package com.elorrieta.service;

import com.elorrieta.dto.CicloDTO;
import com.elorrieta.entities.Ciclo;
import com.elorrieta.mapper.CicloMapper;
import com.elorrieta.repository. CicloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream. Collectors;

@Service
@Transactional
public class CicloService {

    @Autowired
    private CicloRepository cicloRepository;

    @Autowired
    private CicloMapper cicloMapper;

    public CicloDTO getById(int id) {
        Optional<Ciclo> ciclo = cicloRepository.findById(id);
        return ciclo.map(cicloMapper::toDTO).orElse(null);
    }

    public List<CicloDTO> getAll() {
        List<Ciclo> ciclos = cicloRepository.findAll();
        return ciclos.stream()
                .map(cicloMapper:: toDTO)
                .collect(Collectors.toList());
    }

    public List<Ciclo> getAllEntities(){
        return cicloRepository.findAll();
    }

    public CicloDTO save(CicloDTO cicloDTO) {
        Ciclo ciclo = cicloMapper.toEntity(cicloDTO);
        Ciclo savedCiclo = cicloRepository.save(ciclo);
        return cicloMapper.toDTO(savedCiclo);
    }

    public void delete(int id) {
        cicloRepository.deleteById(id);
    }
}