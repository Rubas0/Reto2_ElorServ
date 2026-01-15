package com.elorrieta.dao;

import com.elorrieta.entities.Matriculaciones;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO para gestión de matriculaciones
 */
@Component
public class MatriculacionesDAO {

    public Matriculaciones getById(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Matriculaciones matriculaciones = session.createQuery("from Matriculaciones m where m.id = :input", Matriculaciones.class)
                    .setParameter("input", id)
                    .uniqueResult();
            transaction.commit();
            return matriculaciones;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al buscar la matriculacion: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<Matriculaciones> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<Matriculaciones> matriculaciones = session.createQuery("from Matriculaciones", Matriculaciones.class).list();
            transaction.commit();
            return matriculaciones;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System. out.println("Error al obtener las matriculaciones: " + e. getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public void add(Matriculaciones entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al añadir la matriculacion: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void update(Matriculaciones entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Matriculaciones matriculaciones = session.get(Matriculaciones. class, entity.getId());
            if (matriculaciones != null) {
                matriculaciones.setAlum(entity.getAlum());
                matriculaciones.setCiclo(entity.getCiclo());
                matriculaciones.setCurso(entity.getCurso());
                matriculaciones.setFecha(entity.getFecha());
                session.merge(matriculaciones);
                transaction.commit();
            } else {
                System.out.println("Matriculacion no encontrada para actualizar.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al actualizar la matriculacion: " + e.getMessage());
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
            Matriculaciones matriculacion = session.get(Matriculaciones.class, id);
            if (matriculacion != null) {
                session.remove(matriculacion);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar la matriculacion: " + e. getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}