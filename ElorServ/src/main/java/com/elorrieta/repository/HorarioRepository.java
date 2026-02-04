package com.elorrieta.repository;

import com.elorrieta.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para gestión de horarios
 */
@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    /**
     * Obtener todos los horarios de un profesor
     */
    @Query("SELECT h FROM Horario h WHERE h.profe.id = :profesorId")
    List<Horario> findByProfesorId(@Param("profesorId") Integer profesorId);

    /**
     * Obtener todos los horarios de un alumno (por los módulos que cursa)
     */
    @Query("SELECT h FROM Horario h " +
            "JOIN Modulo m ON h.modulo.id = m.id " +
            "JOIN Matriculaciones mat ON mat.ciclo.id = m.ciclo.id " +
            "JOIN User u ON mat.alum.id = u.id " +
            "WHERE mat.alum.id = :alumnoId and m.curso = mat.curso")
    List<Horario> findByAlumnoId(@Param("alumnoId") Integer alumnoId);

    /**
     * Obtener horarios de un profesor por día
     */
    @Query("SELECT h FROM Horario h WHERE h.profe.id = :profesorId AND h.dia = :dia")
    List<Horario> findByProfesorIdAndDia(@Param("profesorId") Integer profesorId, @Param("dia") String dia);

    /**
     * Obtener horarios por módulo (para generar horario del alumno)
     */
    @Query("SELECT h FROM Horario h WHERE h.modulo.id = :moduloId")
    List<Horario> findByModuloId(@Param("moduloId") Integer moduloId);
}