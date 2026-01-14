package com.elorrieta.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pruebaHibernate.entities.Tipo;
import pruebaHibernate.util.HibernateUtil;

public class TipoDAO {
    public static Tipo getTipoById(int id) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Tipo tipo = session.createQuery("from Tipo t where t.id = :input",Tipo.class).setParameter("input", id).uniqueResult();
            transaction.commit();
            session.close();
            return tipo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
