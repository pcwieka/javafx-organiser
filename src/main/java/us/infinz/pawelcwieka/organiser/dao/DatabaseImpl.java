package us.infinz.pawelcwieka.organiser.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import us.infinz.pawelcwieka.organiser.util.HibernateUtil;
import java.util.List;

public class DatabaseImpl<T> implements Database<T>{

    public  List<T> getListOfObjectsFromDatabase(String query){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<T> objectsList = null;

        try {
            tx = session.beginTransaction();

            objectsList = (List<T>) session.createQuery(query).list();

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return objectsList;

    }

    public T getObjectFromDatabase(String query){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        T object = null;

        try {
            tx = session.beginTransaction();

            object = (T) session.createQuery(query).getSingleResult();

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return object;

    }

    public Long saveObjectToDatabase(T object){


        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.saveOrUpdate(object);

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        //return object.getId();
        return 1L;


    }

    @Override
    public void deleteObjectFromDatabase(T object) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.delete(object);

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void createCustomQuery(String query) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.createQuery(query).executeUpdate();

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
