package com.elorrieta. mapper;

import com.elorrieta.dto.ReunionDTO;
import com.elorrieta.entities.Reuniones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReunionMapper {

    @Autowired
    private UserMapper userMapper;

    public ReunionDTO toDTO(Reuniones reunion) {
        if (reunion == null) {
            return null;
        }

        ReunionDTO dto = new ReunionDTO();
        dto.setId(reunion.getId());
        dto.setEstado(reunion.getEstado());
        dto.setEstadoEus(reunion.getEstadoEus());
        dto.setTitulo(reunion.getTitulo());
        dto.setAsunto(reunion.getAsunto());
        dto.setDia(reunion.getDia());
        dto.setSemana(reunion.getSemana());
        dto.setHora(reunion.getHora());
        dto.setAula(reunion.getAula());
        dto.setIdCentro(reunion.getIdCentro());

        // Mapear profesor (ProfesorDTO)
        if (reunion.getProfesor() != null) {
            dto.setProfesor(userMapper.toProfesorDTO(reunion.getProfesor()));
        }

        // Mapear alumno (AlumnoDTO)
        if (reunion.getAlumno() != null) {
            dto.setAlumno(userMapper.toAlumnoDTO(reunion.getAlumno()));
        }

        return dto;
    }

    public Reuniones toEntity(ReunionDTO dto) {
        if (dto == null) {
            return null;
        }

        Reuniones reunion = new Reuniones();
        reunion.setId(dto.getId());
        reunion.setEstado(dto.getEstado());
        reunion.setEstadoEus(dto.getEstadoEus());
        reunion.setTitulo(dto.getTitulo());
        reunion.setAsunto(dto.getAsunto());
        reunion.setDia(dto.getDia());
        reunion.setSemana(dto.getSemana());
        reunion.setHora(dto.getHora());
        reunion.setAula(dto.getAula());
        reunion.setIdCentro(dto.getIdCentro());

        return reunion;
    }
}