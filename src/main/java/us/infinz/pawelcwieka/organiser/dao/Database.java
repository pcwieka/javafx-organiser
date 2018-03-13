package us.infinz.pawelcwieka.organiser.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.util.HibernateSession;
import us.infinz.pawelcwieka.organiser.util.HibernateUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Database<T> implements IDatabase<T> {

    private final Class<T> tClass;

    public Database(Class<T> tClass){

        this.tClass = tClass;

    }


    public List<T> getListOfObjectsFromDatabase(String query) {

        Session session = HibernateSession.getInstance().getSession();
        Transaction tx = null;
        List<T> objectsList = null;

        try {
            tx = session.beginTransaction();

            objectsList = (List<T>) session.createQuery(query).list();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }

        return objectsList;

    }

    public T getObjectFromDatabase(Long id) {

        Session session = HibernateSession.getInstance().getSession();
        Transaction tx = null;
        T object = null;

        try {
            tx = session.beginTransaction();

            object = (T) session.get(tClass,id);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }

        return object;

    }

    public Long saveObjectToDatabase(T object) {


        Session session = HibernateSession.getInstance().getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.saveOrUpdate(object);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }

        //return object.getId();
        return 1L;


    }

    @Override
    public void deleteObjectFromDatabase(T object) {

        Session session = HibernateSession.getInstance().getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.delete(object);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }

    }

    @Override
    public void createCustomQueryUpdate(String query) {

        Session session = HibernateSession.getInstance().getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.createQuery(query).executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }

    }

    @Override
    public T createCustomQueryGet(String query) {

        Session session = HibernateSession.getInstance().getSession();
        Transaction tx = null;
        T object = null;

        try {
            tx = session.beginTransaction();

            object = (T) session.createQuery(query).getSingleResult();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }

        return object;

    }



}
