package com.elorrieta.dao;

import com.elorrieta.entities.Reuniones;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReunionesDAO {

    public Reuniones getById(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Reuniones reunion = session.createQuery("from Reuniones r where r.id = :input", Reuniones.class).setParameter("input", id).uniqueResult();
            transaction.commit();
            session.close();
            return reunion;
        } catch (Exception e) {
            System.out.println("Error al buscar la reunion:" + e.getMessage());
            return null;
        }
    }

    public List<Reuniones> getAll() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<Reuniones> reuniones = session.createQuery("from Reuniones").list();
            transaction.commit();
            session.close();
            return reuniones;
        } catch (Exception e) {
            System.out.println("Error al obtener las reuniones:" + e.getMessage());
            return null;
        }
    }


    public void add(Reuniones entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error al añadir la reunion: " + e.getMessage());
        }
    }


    public void update(Reuniones entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
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
                session.close();
            } else {
                System.out.println("Reunion no encontrada para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la reunion: " + e.getMessage());
        }
    }


    public static void delete(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Reuniones reunion = session.get(Reuniones.class, id);
            if (reunion != null) {
                session.remove(reunion);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.err.println("Error al eliminar la reunion" + e.getMessage());
        }
    }
}
