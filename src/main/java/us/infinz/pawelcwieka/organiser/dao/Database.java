package us.infinz.pawelcwieka.organiser.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import us.infinz.pawelcwieka.organiser.resource.AuditColumns;
import us.infinz.pawelcwieka.organiser.util.HibernateUtil;
import java.util.List;

public class Database<T> implements IDatabase<T> {

    private final Class<T> tClass;

    public Database(Class<T> tClass){

        this.tClass = tClass;

    }


    public List<T> getListOfObjectsFromDatabase(String query) {

        Session session = HibernateUtil.getSessionFactory().openSession();

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
            session.close();
        }

        return objectsList;

    }

    public T getObjectFromDatabase(Long id) {

        Session session = HibernateUtil.getSessionFactory().openSession();

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
            session.close();
        }

        return object;

    }

    public <T extends AuditColumns> Long saveObjectToDatabase(T object) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        Long objectId = null;

        try {
            tx = session.beginTransaction();

            session.saveOrUpdate(object);
            objectId = object.getId();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return objectId;

    }

    @Override
    public void deleteObjectFromDatabase(T object) {

        Session session = HibernateUtil.getSessionFactory().openSession();

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
            session.close();
        }

    }

    @Override
    public void createCustomQueryUpdate(String query) {

        Session session = HibernateUtil.getSessionFactory().openSession();

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
            session.close();
        }

    }

    @Override
    public T createCustomQueryGet(String query) {

        Session session = HibernateUtil.getSessionFactory().openSession();

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
            session.close();
        }

        return object;

    }



}
