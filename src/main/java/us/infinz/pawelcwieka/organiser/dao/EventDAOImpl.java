package us.infinz.pawelcwieka.organiser.dao;

import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.resource.Event;
import java.util.List;

public class EventDAOImpl implements EventDAO{

    private Database<Event> database = new DatabaseImpl<>();

    @Override
    public Event findEvent(Long eventId){

        String query = "from EVENT where ID = " + eventId;

        return database.getObjectFromDatabase(query);

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


}
