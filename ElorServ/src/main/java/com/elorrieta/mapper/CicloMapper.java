package com.elorrieta.mapper;

import com.elorrieta.dto.CicloDTO;
import com.elorrieta.entities.Ciclo;
import org.springframework.stereotype.Component;

@Component
public class CicloMapper {

    public CicloDTO toDTO(Ciclo ciclo) {
        if (ciclo == null) {
            return null;
        }

        CicloDTO dto = new CicloDTO();
        dto.setId(ciclo.getId());
        dto.setNombre(ciclo.getNombre());

        return dto;
    }

    public Ciclo toEntity(CicloDTO dto) {
        if (dto == null) {
            return null;
        }

        Ciclo ciclo = new Ciclo();
        ciclo.setId(dto.getId());
        ciclo.setNombre(dto.getNombre());

        return ciclo;
    }
}