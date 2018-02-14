package us.infinz.pawelcwieka.organiser.service;

import org.joda.time.DateTime;

public class Clock {


    public static DateTime getDateTimeFromTimeStamp(Long timeStamp){

        return new DateTime(timeStamp);

    }

    public static String getHourFromDateTime(DateTime dateTime){

       return String.valueOf(dateTime.getHourOfDay());

    }

    public static String getMinutesFromDateTime(DateTime dateTime){

        return String.valueOf(dateTime.getMinuteOfHour());

    }

    public static String getHourFromDateTime(Long timeStamp){

        return String.valueOf(getDateTimeFromTimeStamp(timeStamp).getHourOfDay());

    }

    public static String getMinutesFromDateTime(Long timeStamp){

        return String.valueOf(getDateTimeFromTimeStamp(timeStamp).getMinuteOfHour());

    }

    public static Long getTimeStampInMilis(DateTime day, String hours, String minutes){

        DateTime newDateTime = day.withHourOfDay(Integer.valueOf(hours))
                .withMinuteOfHour(Integer.valueOf(minutes))
                .withSecondOfMinute(0);

        return newDateTime.getMillis();

    }


}
