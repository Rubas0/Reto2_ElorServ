package com.elorrieta.repository;

import com.elorrieta.entities.Ciclo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para gestión de ciclos formativos
 */
@Repository
public interface CicloRepository extends JpaRepository<Ciclo, Integer> {

    /**
     * Buscar ciclo por nombre
     */
    Optional<Ciclo> findByNombre(String nombre);

    /**
     * Verificar si existe un ciclo con ese nombre
     */
    boolean existsByNombre(String nombre);
}