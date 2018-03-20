package us.infinz.pawelcwieka.organiser.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.resource.Event;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.util.HibernateSession;
import us.infinz.pawelcwieka.organiser.util.HibernateUtil;

import java.util.List;

public class EventDAO implements IEventDAO {

    private IDatabase<Event> database = new Database<>(Event.class);

    @Override
    public Event findEvent(Long eventId){

        return database.getObjectFromDatabase(eventId);

    }

    @Override
    public List<Event> findAllUserEventsForMonth(DateTime dateTime){

        String query = "from EVENT ";

        return database.getListOfObjectsFromDatabase(query);

    }

    @Override
    public List<Event> findAllUserEvents(User user) {

        return database.getListOfObjectsFromDatabase("select e from EVENT e join e.users u where u.id = " +user.getId());

    }

    @Override
    public Event findUserEvent(User user, Long eventId) {

        String query = "select e from EVENT e join e.users u where u.id = " + user.getId() + " and e.id = " + Long.toString(eventId);

        return database.createCustomQueryGet(query);
    }

    @Override
    public Long saveEvent(Event event) {

        return database.saveObjectToDatabase(event);
    }

    @Override
    public void deleteEvent(Event event) {

        database.deleteObjectFromDatabase(event);

    }

    public void saveSharedEvent(User user, Event event) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        Long objectId = null;

        try {
            tx = session.beginTransaction();

           session.createNativeQuery("insert into SHARED_EVENTS (EVENT_ID, USER_ID) VALUES (" + event.getId() + ", " + user.getId()+")").executeUpdate();

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

    public void removeUserEvent(User user, Event event){

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.createNativeQuery("delete from SHARED_EVENTS where EVENT_ID = " + event.getId() + " and USER_ID = " + user.getId()).executeUpdate();

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



}
