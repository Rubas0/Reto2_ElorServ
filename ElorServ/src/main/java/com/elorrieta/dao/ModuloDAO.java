package com.elorrieta.dao;

import com.elorrieta.entities. Modulo;
import com.elorrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO para gestión de módulos
 */
@Component
public class ModuloDAO {

    public Modulo getById(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil. getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Modulo modulo = session.createQuery("from Modulo m where m.id = :input", Modulo.class)
                    .setParameter("input", id)
                    .uniqueResult();
            transaction.commit();
            return modulo;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out. println("Error al buscar el modulo: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<Modulo> getAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<Modulo> modulos = session.createQuery("from Modulo", Modulo.class).list();
            transaction.commit();
            return modulos;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System. out.println("Error al obtener los modulos: " + e.getMessage());
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public void add(Modulo entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session. persist(entity);
            transaction. commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al añadir el modulo: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public void update(Modulo entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Modulo modulo = session. get(Modulo.class, entity.getId());
            if (modulo != null) {
                modulo.setNombre(entity.getNombre());
                modulo.setNombreEus(entity.getNombreEus());
                modulo.setHoras(entity.getHoras());
                modulo.setCiclo(entity.getCiclo());
                modulo.setCurso(entity.getCurso());
                session.merge(modulo);
                transaction.commit();
            } else {
                System.out.println("Modulo no encontrado para actualizar.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error al actualizar el modulo: " + e.getMessage());
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
            Modulo modulo = session.get(Modulo.class, id);
            if (modulo != null) {
                session.remove(modulo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar el modulo: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}