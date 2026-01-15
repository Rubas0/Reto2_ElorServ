package com.elorrieta.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.elorrieta.entities.User;
import com.elorrieta.util.HibernateUtil;

import java.util.List;

public class UserDAO {

    public static User getById(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            transaction.commit();
            session.close();
            return user;
        } catch (Exception e) {
            System.out.println("Error al buscar el usuario:" + e.getMessage());
            return null;
        }
    }


    public static List<User> getAll() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("from User").list();
            transaction.commit();
            session.close();
            return users;
        } catch (Exception e) {
            System.out.println("Error al obtener los usuarios:" + e.getMessage());
            return null;
        }
    }

    public void add(User entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error al añadir el usuario: " + e.getMessage());
        }
    }

    public void update(User entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, entity.getId());
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
                user.setTipo(entity.getTipo());
                user.setArgazkiaUrl(entity.getArgazkiaUrl());
                session.merge(user);
                transaction.commit();
                session.close();
            }
            else {
                System.out.println("Usuario no encontrado para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
        }
    }


    public void delete(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    //------------------------------ FUNCIONES EXTRA --------------------------------//


    public static User login(User userLogin) {
        User ret;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            ret = session.createQuery("FROM User U JOIN FETCH U.tipo WHERE U.username = :username", User.class)
                    .setParameter("username", userLogin.getUsername())
                    .uniqueResult();
            // forzar la carga del tipo de usuario

            transaction.commit();
            session.close();
            if (ret == null) {
                System.out.println("Usuario no encontrado.");
                return null;
            } else if (!ret.getPassword().equals(userLogin.getPassword())) {
                System.out.println("Contraseña incorrecta.");
                return null;
            }
            else{
                System.out.println("Login correcto.");
                return ret;
            }

        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return null;
        }
    }

}
