package us.infinz.pawelcwieka.organiser.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class ICalendar{


    public static InputStream createICalendarEvent(Date startDate, Date endDate, String title, String location, String description) throws IOException {


        DateTime eventStart = new DateTime(startDate);
        DateTime eventEnd = new DateTime(endDate);

        Calendar icalendar = new Calendar();
        icalendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        icalendar.getProperties().add(Version.VERSION_2_0);
        icalendar.getProperties().add(CalScale.GREGORIAN);

        VEvent event = new VEvent(eventStart, eventEnd,title);

        event.getProperties().add(new Location(location));
        event.getProperties().add(new Description(description));

        UidGenerator ug = new UidGenerator("1");
        event.getProperties().add(ug.generateUid());

        icalendar.getComponents().add(event);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(icalendar,out);

        return new ByteArrayInputStream(out.toByteArray());

    }

}
