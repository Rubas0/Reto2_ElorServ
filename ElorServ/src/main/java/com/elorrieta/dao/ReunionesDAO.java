package com.elorrieta.dao;

import com.elorrieta.entities.Reuniones;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO para gestión de reuniones
 */
@Component
public class ReunionesDAO {

    public Reuniones getById(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Reuniones reunion = session.createQuery("from Reuniones r where r.id = :input", Reuniones.class)
                    .setParameter("input", id)
                    . uniqueResult();
            transaction. commit();
            return reunion;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al buscar la reunion: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<Reuniones> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<Reuniones> reuniones = session.createQuery("from Reuniones", Reuniones.class).list();
            transaction.commit();
            return reuniones;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al obtener las reuniones: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public void add(Reuniones entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al añadir la reunion: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void update(Reuniones entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Reuniones reunion = session.get(Reuniones.class, entity.getId());
            if (reunion != null) {
                reunion.setEstado(entity.getEstado());
                reunion.setEstadoEus(entity.getEstadoEus());
                reunion.setProfesor(entity.getProfesor());
                reunion.setAlumno(entity.getAlumno());
                reunion.setIdCentro(entity.getIdCentro());
                reunion.setTitulo(entity.getTitulo());
                reunion.setAsunto(entity.getAsunto());
                reunion.setAula(entity.getAula());
                reunion.setDia(entity.getDia());
                reunion.setSemana(entity.getSemana());
                reunion.setHora(entity.getHora());
                session.merge(reunion);
                transaction.commit();
            } else {
                System.out. println("Reunion no encontrada para actualizar.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al actualizar la reunion: " + e.getMessage());
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
            Reuniones reunion = session.get(Reuniones.class, id);
            if (reunion != null) {
                session.remove(reunion);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar la reunion: " + e. getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}