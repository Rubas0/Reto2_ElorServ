package com.elorrieta.dao;

import com.elorrieta.entities. Horario;
import com. elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO para gestión de horarios
 */
@Component
public class HorarioDAO {

    public Horario getById(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Horario horario = session. createQuery("from Horario h where h.id = :input", Horario.class)
                    .setParameter("input", id)
                    .uniqueResult();
            transaction.commit();
            return horario;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out. println("Error al buscar el horario: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<Horario> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<Horario> horarios = session.createQuery("from Horario", Horario.class).list();
            transaction.commit();
            return horarios;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out. println("Error al obtener los horarios: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public void add(Horario entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session. persist(entity);
            transaction. commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al añadir el horario: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void update(Horario entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Horario horario = session. get(Horario.class, entity.getId());
            if (horario != null) {
                horario.setDia(entity.getDia());
                horario.setHora(entity.getHora());
                horario.setProfe(entity.getProfe());
                horario.setModulo(entity.getModulo());
                horario.setAula(entity.getAula());
                horario.setObservaciones(entity.getObservaciones());
                session.merge(horario);
                transaction.commit();
            } else {
                System. out.println("Horario no encontrado para actualizar.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al actualizar el horario: " + e.getMessage());
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
            Horario horario = session.get(Horario.class, id);
            if (horario != null) {
                session.remove(horario);
            }
            transaction. commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar el horario:  " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}