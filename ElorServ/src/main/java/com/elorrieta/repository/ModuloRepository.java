package com.elorrieta.repository;

import com.elorrieta.entities.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para gestión de módulos
 */
@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    /**
     * Obtener módulos de un ciclo específico
     */
    @Query("SELECT m FROM Modulo m WHERE m.ciclo.id = :cicloId")
    List<Modulo> findByCicloId(@Param("cicloId") Integer cicloId);

    /**
     * Obtener módulos de un ciclo y curso específico
     */
    @Query("SELECT m FROM Modulo m WHERE m.ciclo. id = :cicloId AND m.curso = :curso")
    List<Modulo> findByCicloIdAndCurso(@Param("cicloId") Integer cicloId, @Param("curso") Byte curso);

    /**
     * Buscar módulo por nombre
     */
    List<Modulo> findByNombreContainingIgnoreCase(String nombre);
}