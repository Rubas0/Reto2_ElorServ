package com.elorrieta.repository;

import com.elorrieta.entities.Reuniones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para gestión de reuniones
 */
@Repository
public interface ReunionesRepository extends JpaRepository<Reuniones, Integer> {

    /**
     * Obtener todas las reuniones de un profesor
     */
    @Query("SELECT r FROM Reuniones r WHERE r.profesor.id = :profesorId")
    List<Reuniones> findByProfesorId(@Param("profesorId") Integer profesorId);

    /**
     * Obtener todas las reuniones de un alumno
     */
    @Query("SELECT r FROM Reuniones r WHERE r.alumno.id = :alumnoId")
    List<Reuniones> findByAlumnoId(@Param("alumnoId") Integer alumnoId);

    /**
     * Obtener reuniones por estado
     */
    List<Reuniones> findByEstado(String estado);

    /**
     * Obtener reuniones de un profesor por estado
     */
    @Query("SELECT r FROM Reuniones r WHERE r.profesor.id = :profesorId AND r.estado = :estado")
    List<Reuniones> findByProfesorIdAndEstado(@Param("profesorId") Integer profesorId, @Param("estado") String estado);

    /**
     * Obtener reuniones de un alumno por estado
     */
    @Query("SELECT r FROM Reuniones r WHERE r.alumno.id = :alumnoId AND r.estado = :estado")
    List<Reuniones> findByAlumnoIdAndEstado(@Param("alumnoId") Integer alumnoId, @Param("estado") String estado);
}