package com.elorrieta.repository;

import com.elorrieta.entities.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para gestión de tipos de usuario
 */
@Repository
public interface TipoRepository extends JpaRepository<Tipo, Integer> {

    /**
     * Buscar tipo por nombre
     */
    Optional<Tipo> findByName(String name);

    /**
     * Buscar tipo por nombre en euskera
     */
    Optional<Tipo> findByNameEu(String nameEu);

    /**
     * Verificar si existe un tipo con ese nombre
     */
    boolean existsByName(String name);
}