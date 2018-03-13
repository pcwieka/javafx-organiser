package us.infinz.pawelcwieka.organiser.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.resource.Event;
import us.infinz.pawelcwieka.organiser.util.HibernateSession;

import java.util.List;

public class EventDAO implements IEventDAO {

    private IDatabase<Event> database = new Database<>(Event.class);

    @Override
    public Event findEvent(Long eventId){

        return database.getObjectFromDatabase(eventId);

    }

    @Override
    public List<Event> findAllEventsForMonth(DateTime dateTime){

        String query = "from EVENT ";

        return database.getListOfObjectsFromDatabase(query);

    }

    @Override
    public List<Event> findAllEvents() {

        return database.getListOfObjectsFromDatabase("from EVENT");

    }

    @Override
    public Long saveEvent(Event event) {

        return database.saveObjectToDatabase(event);
    }

    @Override
    public void deleteEvent(Event event) {

        database.deleteObjectFromDatabase(event);

    }

    public Event getEventFromDatabase(Long id) {

        Session session = HibernateSession.getInstance().getSession();
        Transaction tx = null;
        Event object = null;

        try {
            tx = session.beginTransaction();

            object = (Event) session.get(Event.class, id);

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
