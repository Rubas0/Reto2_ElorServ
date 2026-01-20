package com.elorrieta.mapper;

import com.elorrieta.dto.TipoDTO;
import com.elorrieta.entities.Tipo;
import org.springframework.stereotype.Component;

@Component
public class TipoMapper {

    public TipoDTO toDTO(Tipo tipo) {
        if (tipo == null) {
            return null;
        }

        TipoDTO dto = new TipoDTO();
        dto.setId(tipo.getId());
        dto.setName(tipo.getName());
        dto.setNameEu(tipo.getNameEu());

        return dto;
    }

    public Tipo toEntity(TipoDTO dto) {
        if (dto == null) {
            return null;
        }

        Tipo tipo = new Tipo();
        tipo.setId(dto.getId());
        tipo.setName(dto.getName());
        tipo.setNameEu(dto.getNameEu());

        return tipo;
    }
}