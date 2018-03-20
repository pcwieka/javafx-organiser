package us.infinz.pawelcwieka.organiser.dao;

import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.resource.Event;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public interface IEventDAO {

    public Event findEvent(Long eventId);
    public List<Event> findAllUserEventsForMonth(DateTime dateTime);
    public List<Event> findAllUserEvents(User user);
    public Event findUserEvent(User user, Long eventId);
    public Long saveEvent(Event event);
    public void deleteEvent(Event event);

}
