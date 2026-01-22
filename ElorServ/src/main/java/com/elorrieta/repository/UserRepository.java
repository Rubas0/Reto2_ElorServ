package com.elorrieta.repository;

import com.elorrieta.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gestión de usuarios
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Buscar usuario por username
     */
    Optional<User> findByUsername(String username);

    /**
     * Buscar usuario por email
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    /**
     * Buscar usuario por username y cargar su tipo (JOIN FETCH)
     * Evita LazyInitializationException al acceder a user.getTipo()
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tipo WHERE u.username = :username")
    Optional<User> findByUsernameWithTipo(@Param("username") String username);

    /**
     * Verificar si existe un usuario con ese username
     */
    boolean existsByUsername(String username);

    /**
     * Verificar si existe un usuario con ese email
     */
    boolean existsByEmail(String email);

    /**
     * Buscar Usuario (Alumno) por el ID de su Profesor
     *
     * @return Lista de Usuarios (Alumnos)
     */
    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN Matriculaciones m ON m.alum.id = u.id " +
            "JOIN Modulo mod ON m.ciclo.id = mod.ciclo.id " +
            "JOIN Horario h ON h.modulo.id = mod.id " +
            "WHERE h.profe.id = :profesorId")
    List<User> findStudentsByProfessorId(@Param("profesorId") int profesorId);
}