package com.elorrieta.dao;

import com.elorrieta.entities.Ciclo;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO para gestión de ciclos formativos
 */
@Component
public class CicloDAO {

    public Ciclo getById(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Ciclo ciclo = session.createQuery("from Ciclo c where c.id = :input", Ciclo.class)
                    .setParameter("input", id)
                    .uniqueResult();
            transaction.commit();
            return ciclo;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al buscar el ciclo: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<Ciclo> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<Ciclo> ciclos = session.createQuery("from Ciclo", Ciclo.class).list();
            transaction.commit();
            return ciclos;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al obtener los ciclos: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public void add(Ciclo entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al añadir el ciclo: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void update(Ciclo entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session. beginTransaction();
            Ciclo ciclo = session.get(Ciclo.class, entity.getId());
            if (ciclo != null) {
                ciclo.setNombre(entity.getNombre());
                session.merge(ciclo);
                transaction.commit();
            } else {
                System.out.println("Ciclo no encontrado para actualizar.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al actualizar el ciclo: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void delete(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Ciclo ciclo = session.get(Ciclo.class, id);
            if (ciclo != null) {
                session.remove(ciclo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System. err.println("Error al eliminar el ciclo: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}