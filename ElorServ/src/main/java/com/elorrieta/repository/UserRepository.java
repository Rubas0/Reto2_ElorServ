package com.elorrieta.repository;

import com.elorrieta.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para gestión de usuarios
 * Spring Data JPA genera automáticamente la implementación
 * 
 * Métodos automáticos disponibles:
 * - findById(Integer id) :  Optional<User>
 * - findAll() : List<User>
 * - save(User user) : User
 * - deleteById(Integer id) : void
 * - existsById(Integer id) : boolean
 * - count() : long
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Buscar usuario por username
     * Spring genera automáticamente la query a partir del nombre del método
     */
    Optional<User> findByUsername(String username);

    /**
     * Buscar usuario por email
     */
    Optional<User> findByEmail(String email);

    /**
     * Login:  Buscar usuario por username con JOIN FETCH del tipo
     * (evita LazyInitializationException)
     * 
     * @Query personalizada porque necesitamos JOIN FETCH
     */
    @Query("SELECT u FROM User u JOIN FETCH u.tipo WHERE u.username = :username")
    Optional<User> findByUsernameWithTipo(@Param("username") String username);

    /**
     * Verificar si existe un usuario con ese username
     */
    boolean existsByUsername(String username);

    /**
     * Verificar si existe un usuario con ese email
     */
    boolean existsByEmail(String email);
}