package com.elorrieta.mapper;

import com. elorrieta.dto.HorarioDTO;
import com. elorrieta.entities. Horario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ModuloMapper moduloMapper;

    public HorarioDTO toDTO(Horario horario) {
        if (horario == null) {
            return null;
        }

        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario. getId());
        dto.setDia(horario.getDia());
        dto.setHora(horario.getHora());
        dto.setAula(horario.getAula());
        dto.setObservaciones(horario.getObservaciones());

        // Mapear profe (ProfesorDTO)
        if (horario.getProfe() != null) {
            dto.setProfe(userMapper. toProfesorDTO(horario.getProfe()));
        }

        // Mapear modulo
        if (horario.getModulo() != null) {
            dto.setModulo(moduloMapper.toDTO(horario. getModulo()));
        }

        return dto;
    }

    public Horario toEntity(HorarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Horario horario = new Horario();
        horario.setId(dto.getId());
        horario.setDia(dto.getDia());
        horario.setHora(dto.getHora());
        horario.setAula(dto.getAula());
        horario.setObservaciones(dto.getObservaciones());

        return horario;
    }
}