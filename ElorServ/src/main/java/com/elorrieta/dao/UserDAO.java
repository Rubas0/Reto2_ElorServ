package com.elorrieta.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.elorrieta.entities.User;
import com.elorrieta.util.HibernateUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO para gestión de usuarios
 * Incluye métodos CRUD básicos y funciones de autenticación
 */
@Component
public class UserDAO {

    public User getById(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session. beginTransaction();
            User user = session.get(User. class, id);
            transaction. commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al buscar el usuario: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<User> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<User> users = session. createQuery("from User", User. class).list();
            transaction. commit();
            return users;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al obtener los usuarios: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public void add(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al añadir el usuario: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void update(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil. getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = session.get(User. class, entity.getId());
            if (user != null) {
                user.setEmail(entity.getEmail());
                user.setUsername(entity.getUsername());
                user.setPassword(entity.getPassword());
                user.setNombre(entity.getNombre());
                user.setApellidos(entity.getApellidos());
                user.setDni(entity.getDni());
                user.setDireccion(entity.getDireccion());
                user.setTelefono1(entity.getTelefono1());
                user.setTelefono2(entity.getTelefono2());
                user. setTipo(entity.getTipo());
                user.setArgazkiaUrl(entity.getArgazkiaUrl());
                session.merge(user);
                transaction.commit();
            } else {
                System.out. println("Usuario no encontrado para actualizar.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al actualizar el usuario:  " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void delete(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session. beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    // ========== MÉTODOS EXTRA ==========

    /**
     * Método de login que verifica credenciales
     * NOTA: Por ahora compara passwords en claro
     * TODO: Implementar BCrypt cuando añadamos la capa de seguridad
     */
    public User login(User userLogin) {
        Session session = null;
        Transaction transaction = null;
        User ret = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // JOIN FETCH fuerza la carga del tipo de usuario (evita LazyInitializationException)
            ret = session.createQuery(
                    "FROM User U JOIN FETCH U.tipo WHERE U. username = :username", 
                    User.class)
                    .setParameter("username", userLogin.getUsername())
                    .uniqueResult();
            
            transaction.commit();
            
            if (ret == null) {
                System.out.println("Usuario no encontrado.");
                return null;
            } else if (! ret.getPassword().equals(userLogin.getPassword())) {
                System.out.println("Contraseña incorrecta.");
                return null;
            } else {
                System.out. println("Login correcto.");
                return ret;
            }
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public User getUserByEmail(String email) {
        Session session = null;
        Transaction transaction = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            user = session.createQuery("FROM User U WHERE U.email = :email", User.class)
                    . setParameter("email", email)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System. out.println("Error al buscar el usuario por email: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return user;
    }
}