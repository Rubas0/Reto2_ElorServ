package com.elorrieta.mapper;

import com.elorrieta.dto.AlumnoDTO;
import com.elorrieta.dto.ProfesorDTO;
import com.elorrieta.dto.UserDTO;
import com.elorrieta.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir User Entity ↔ UserDTO
 */
@Component
public class UserMapper {

    @Autowired
    private TipoMapper tipoMapper;

    /**
     * Convertir Entity → DTO (sin password)
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setDni(user.getDni());
        dto.setDireccion(user.getDireccion());
        dto.setTelefono1(user.getTelefono1());
        dto.setTelefono2(user.getTelefono2());
        dto.setArgazkiaUrl(user.getArgazkiaUrl());

        // Mapear tipo (si existe)
        if (user.getTipo() != null) {
            dto.setTipo(tipoMapper.toDTO(user.getTipo()));
        }

        return dto;
    }

    /**
     * Convertir Entity → AlumnoDTO (solo datos básicos)
     */
    public AlumnoDTO toAlumnoDTO(User user) {
        if (user == null) {
            return null;
        }

        com.elorrieta.dto.AlumnoDTO dto = new com.elorrieta.dto.AlumnoDTO();
        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setArgazkiaUrl(user.getArgazkiaUrl());

        return dto;
    }

    /**
     * Convertir Entity → ProfesorDTO (solo datos básicos)
     */
    public ProfesorDTO toProfesorDTO(User user) {
        if (user == null) {
            return null;
        }

        com.elorrieta.dto.ProfesorDTO dto = new com.elorrieta.dto.ProfesorDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setArgazkiaUrl(user.getArgazkiaUrl());

        return dto;
    }

    /**
     * Convertir DTO → Entity (para INSERT/UPDATE)
     * NOTA: NO incluye password (se maneja por separado)
     */
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setNombre(dto.getNombre());
        user.setApellidos(dto.getApellidos());
        user.setDni(dto.getDni());
        user.setDireccion(dto.getDireccion());
        user.setTelefono1(dto.getTelefono1());
        user.setTelefono2(dto. getTelefono2());
        user.setArgazkiaUrl(dto.getArgazkiaUrl());

        return user;
    }
}