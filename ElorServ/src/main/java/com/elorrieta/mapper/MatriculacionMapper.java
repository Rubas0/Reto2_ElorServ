package com.elorrieta. mapper;

import com.elorrieta.dto.MatriculacionDTO;
import com.elorrieta.entities.Matriculaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatriculacionMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CicloMapper cicloMapper;

    public MatriculacionDTO toDTO(Matriculaciones matriculacion) {
        if (matriculacion == null) {
            return null;
        }

        MatriculacionDTO dto = new MatriculacionDTO();
        dto.setId(matriculacion. getId());
        dto.setCurso(matriculacion.getCurso());
        dto.setFecha(matriculacion.getFecha());

        // Mapear alumno (AlumnoDTO - solo datos básicos)
        if (matriculacion.getAlum() != null) {
            dto.setAlumno(userMapper.toAlumnoDTO(matriculacion.getAlum()));
        }

        // Mapear ciclo
        if (matriculacion.getCiclo() != null) {
            dto.setCiclo(cicloMapper.toDTO(matriculacion.getCiclo()));
        }

        return dto;
    }

    public Matriculaciones toEntity(MatriculacionDTO dto) {
        if (dto == null) {
            return null;
        }

        Matriculaciones matriculacion = new Matriculaciones();
        matriculacion.setId(dto.getId());
        matriculacion.setCurso(dto.getCurso());
        matriculacion.setFecha(dto.getFecha());

        return matriculacion;
    }
}