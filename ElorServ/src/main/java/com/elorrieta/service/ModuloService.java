package com.elorrieta.service;

import com.elorrieta.dto.ModuloDTO;
import com.elorrieta.entities.Modulo;
import com.elorrieta.mapper.ModuloMapper;
import com.elorrieta.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util. List;
import java.util. Optional;
import java.util. stream.Collectors;

@Service
@Transactional
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private ModuloMapper moduloMapper;

    public ModuloDTO getById(int id) {
        Optional<Modulo> modulo = moduloRepository.findById(id);
        return modulo. map(moduloMapper::toDTO).orElse(null);
    }

    public List<ModuloDTO> getAll() {
        List<Modulo> modulos = moduloRepository.findAll();
        return modulos. stream()
                .map(moduloMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ModuloDTO save(ModuloDTO moduloDTO) {
        Modulo modulo = moduloMapper. toEntity(moduloDTO);
        Modulo savedModulo = moduloRepository.save(modulo);
        return moduloMapper. toDTO(savedModulo);
    }

    public void delete(int id) {
        moduloRepository.deleteById(id);
    }

    public List<ModuloDTO> getModulosPorCiclo(int cicloId) {
        List<Modulo> modulos = moduloRepository.findByCicloId(cicloId);
        return modulos.stream()
                .map(moduloMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ModuloDTO> getModulosPorCicloYCurso(int cicloId, byte curso) {
        List<Modulo> modulos = moduloRepository.findByCicloIdAndCurso(cicloId, curso);
        return modulos.stream()
                .map(moduloMapper::toDTO)
                .collect(Collectors. toList());
    }
}