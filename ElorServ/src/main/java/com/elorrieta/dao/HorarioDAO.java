package com.elorrieta.dao;

import com.elorrieta.entities.Horario;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HorarioDAO {

    public Horario getById(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Horario horario = session.createQuery("from Horario h where h.id = :input", Horario.class).setParameter("input", id).uniqueResult();
            transaction.commit();
            session.close();
            return horario;
        } catch (Exception e) {
            System.out.println("Error al buscar el horario:" + e.getMessage());
            return null;
        }
    }

    public List<Horario> getAll() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<Horario> horarios = session.createQuery("from Horario").list();
            transaction.commit();
            session.close();
            return horarios;
        } catch (Exception e) {
            System.out.println("Error al obtener los horarios:" + e.getMessage());
            return null;
        }
    }


    public void add(Horario entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Error al añadir el horario: " + e.getMessage());
        }
    }


    public void update(Horario entity) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Horario horario = session.get(Horario.class, entity.getId());
            if (horario != null) {
                horario.setDia(entity.getDia());
                horario.setHora(entity.getHora());
                horario.setProfe(entity.getProfe());
                horario.setModulo(entity.getModulo());
                horario.setAula(entity.getAula());
                horario.setObservaciones(entity.getObservaciones());
                session.merge(horario);
                transaction.commit();
                session.close();
            } else {
                System.out.println("Horario no encontrado para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el horario: " + e.getMessage());
        }
    }


    public static void delete(int id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Horario horario = session.get(Horario.class, id);
            if (horario != null) {
                session.remove(horario);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.err.println("Error al eliminar el horario" + e.getMessage());
        }
    }
}
