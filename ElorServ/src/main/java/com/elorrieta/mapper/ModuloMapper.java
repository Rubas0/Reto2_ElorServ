package com.elorrieta.mapper;

import com.elorrieta.dto.ModuloDTO;
import com.elorrieta.entities.Modulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModuloMapper {

    @Autowired
    private CicloMapper cicloMapper;

    public ModuloDTO toDTO(Modulo modulo) {
        if (modulo == null) {
            return null;
        }

        ModuloDTO dto = new ModuloDTO();
        dto.setId(modulo.getId());
        dto.setNombre(modulo.getNombre());
        dto.setNombreEus(modulo.getNombreEus());
        dto.setHoras(modulo.getHoras());
        dto.setCurso(modulo.getCurso());

        // Mapear ciclo
        if (modulo.getCiclo() != null) {
            dto.setCiclo(cicloMapper.toDTO(modulo.getCiclo()));
        }

        return dto;
    }

    public Modulo toEntity(ModuloDTO dto) {
        if (dto == null) {
            return null;
        }

        Modulo modulo = new Modulo();
        modulo.setId(dto.getId());
        modulo.setNombre(dto.getNombre());
        modulo.setNombreEus(dto.getNombreEus());
        modulo.setHoras(dto.getHoras());
        modulo.setCurso(dto.getCurso());

        return modulo;
    }
}