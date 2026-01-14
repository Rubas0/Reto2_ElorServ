package com.elorrieta.dao;

import com.elorrieta.entities.Modulo;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ModuloDAO {

    public Modulo getById(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Modulo modulo = session.createQuery("from Modulo m where m.id = :input", Modulo.class).setParameter("input", id).uniqueResult();
            transaction.commit();
            session.close();
            return modulo;
        } catch (Exception e) {
            System.out.println("Error al buscar el modulo:" + e.getMessage());
            return null;
        }
    }

    public List<Modulo> getAll() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<Modulo> modulos = session.createQuery("from Modulo").list();
            transaction.commit();
            session.close();
            return modulos;
        } catch (Exception e) {
            System.out.println("Error al obtener los modulos:" + e.getMessage());
            return null;
        }
    }


    public void add(Modulo entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error al añadir el modulo: " + e.getMessage());
        }
    }


    public void update(Modulo entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Modulo modulo = session.get(Modulo.class, entity.getId());
            if (modulo != null) {
                modulo.setNombre(entity.getNombre());
                session.merge(modulo);
                transaction.commit();
                session.close();
            } else {
                System.out.println("Modulo no encontrado para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el ciclo: " + e.getMessage());
        }
    }


    public static void delete(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Modulo modulo = session.get(Modulo.class, id);
            if (modulo != null) {
                session.remove(modulo);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.err.println("Error al eliminar el modulo:" + e.getMessage());
        }
    }
}
