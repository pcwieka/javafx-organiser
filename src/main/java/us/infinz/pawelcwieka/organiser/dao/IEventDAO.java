package us.infinz.pawelcwieka.organiser.dao;

import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.resource.Event;

import java.util.List;

public interface IEventDAO {

    public Event findEvent(Long eventId);
    public List<Event> findAllEventsForMonth(DateTime dateTime);
    public List<Event> findAllEvents();
    public Long saveEvent(Event event);
    public void deleteEvent(Event event);

}
