package com.elorrieta.repository;

import com.elorrieta.entities.Matriculaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework. data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype. Repository;

import java.util.List;

/**
 * Repository para gestión de matriculaciones
 */
@Repository
public interface MatriculacionesRepository extends JpaRepository<Matriculaciones, Integer> {

    /**
     * Obtener todas las matriculaciones de un alumno
     */
    @Query("SELECT m FROM Matriculaciones m WHERE m. alum. id = :alumnoId")
    List<Matriculaciones> findByAlumnoId(@Param("alumnoId") Integer alumnoId);

    /**
     * Obtener alumnos matriculados en un ciclo y curso específico
     */
    @Query("SELECT m FROM Matriculaciones m WHERE m.ciclo. id = :cicloId AND m.curso = :curso")
    List<Matriculaciones> findByCicloIdAndCurso(@Param("cicloId") Integer cicloId, @Param("curso") Byte curso);

    /**
     * Obtener alumnos matriculados en un ciclo
     */
    @Query("SELECT m FROM Matriculaciones m WHERE m.ciclo.id = : cicloId")  // ⭐ SIN espacio
    List<Matriculaciones> findByCicloId(@Param("cicloId") Integer cicloId);
}