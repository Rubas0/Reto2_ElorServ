package com.elorrieta.dao;

import com.elorrieta.entities.Matriculaciones;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MatriculacionesDAO {

    public Matriculaciones getById(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Matriculaciones matriculaciones = session.createQuery("from Matriculaciones m where m.id = :input", Matriculaciones.class).setParameter("input", id).uniqueResult();
            transaction.commit();
            session.close();
            return matriculaciones;
        } catch (Exception e) {
            System.out.println("Error al buscar la matriculacion:" + e.getMessage());
            return null;
        }
    }

    public List<Matriculaciones> getAll() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<Matriculaciones> matriculaciones = session.createQuery("from Matriculaciones").list();
            transaction.commit();
            session.close();
            return matriculaciones;
        } catch (Exception e) {
            System.out.println("Error al obtener las matriculaciones:" + e.getMessage());
            return null;
        }
    }


    public void add(Matriculaciones entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error al añadir la matriculacion: " + e.getMessage());
        }
    }


    public void update(Matriculaciones entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Matriculaciones matriculaciones = session.get(Matriculaciones.class, entity.getId());
            if (matriculaciones != null) {
                matriculaciones.setAlum(entity.getAlum());
                matriculaciones.setCiclo(entity.getCiclo());
                matriculaciones.setCurso(entity.getCurso());
                matriculaciones.setFecha(entity.getFecha());
                session.merge(matriculaciones);
                transaction.commit();
                session.close();
            } else {
                System.out.println("Matriculacion no encontrada para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la matriculacion: " + e.getMessage());
        }
    }


    public static void delete(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Matriculaciones ciclo = session.get(Matriculaciones.class, id);
            if (ciclo != null) {
                session.remove(ciclo);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.err.println("Error al eliminar la matriculaciones" + e.getMessage());
        }
    }
}
