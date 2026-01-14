package com.elorrieta.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pruebaHibernate.entities.User;
import pruebaHibernate.interfaces.DefaultInterface;
import pruebaHibernate.util.HibernateUtil;

import java.util.List;

public class UserDAO implements DefaultInterface {


    public static void deleteUser(int id) {

    }

    @Override
    public Object getById(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            transaction.commit();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("from User").list();
            transaction.commit();
            session.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Object user) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error al añadir el usuario: " + e.getMessage());
        }
    }

    @Override
    public void update(Object entity) {

    }

    @Override
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
            System.err.println("Error al eliminar el usuario" + e.getMessage());
        }
    }
}
