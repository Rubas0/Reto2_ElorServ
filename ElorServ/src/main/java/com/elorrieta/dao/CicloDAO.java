package com.elorrieta.dao;

import com.elorrieta.entities.Ciclo;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CicloDAO {

    public Ciclo getById(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Ciclo ciclo = session.createQuery("from Ciclo c where c.id = :input", Ciclo.class).setParameter("input", id).uniqueResult();
            transaction.commit();
            session.close();
            return ciclo;
        } catch (Exception e) {
            System.out.println("Error al buscar el ciclo:" + e.getMessage());
            return null;
        }
    }

    public List<Ciclo> getAll() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<Ciclo> ciclos = session.createQuery("from Ciclo").list();
            transaction.commit();
            session.close();
            return ciclos;
        } catch (Exception e) {
            System.out.println("Error al obtener los ciclos:" + e.getMessage());
            return null;
        }
    }


    public void add(Ciclo entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error al añadir el ciclo: " + e.getMessage());
        }
    }


    public void update(Ciclo entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Ciclo ciclo = session.get(Ciclo.class, entity.getId());
            if (ciclo != null) {
                ciclo.setNombre(entity.getNombre());
                session.merge(ciclo);
                transaction.commit();
                session.close();
            } else {
                System.out.println("Ciclo no encontrado para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el ciclo: " + e.getMessage());
        }
    }


    public static void delete(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Ciclo ciclo = session.get(Ciclo.class, id);
            if (ciclo != null) {
                session.remove(ciclo);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.err.println("Error al eliminar el ciclo" + e.getMessage());
        }
    }
}
