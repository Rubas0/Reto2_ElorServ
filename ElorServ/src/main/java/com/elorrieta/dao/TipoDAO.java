package com.elorrieta.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.elorrieta.entities. Tipo;
import com.elorrieta.util. HibernateUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO para gestión de tipos de usuario
 */
@Component
public class TipoDAO {

    public Tipo getById(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Tipo tipo = session.createQuery("from Tipo t where t. id = :input", Tipo. class)
                    .setParameter("input", id)
                    .uniqueResult();
            transaction.commit();
            return tipo;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al buscar el tipo: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<Tipo> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<Tipo> tipos = session.createQuery("from Tipo", Tipo.class).list();
            transaction.commit();
            return tipos;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al obtener los tipos:  " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public void add(Tipo entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al añadir el tipo: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void update(Tipo entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Tipo tipo = session.get(Tipo.class, entity.getId());
            if (tipo != null) {
                tipo.setName(entity.getName());
                tipo.setNameEu(entity.getNameEu());
                session.merge(tipo);
                transaction.commit();
            } else {
                System.out.println("Tipo no encontrado para actualizar.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out. println("Error al actualizar el tipo: " + e.getMessage());
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
            Tipo tipo = session.get(Tipo.class, id);
            if (tipo != null) {
                session.remove(tipo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar el tipo: " + e. getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}