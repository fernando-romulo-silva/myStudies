package org.nandao.cap11.p02HandlingWeeksInJava7;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class Test {

    public static void main(String[] args) {

        final Calendar calendar = Calendar.getInstance();

        if (calendar.isWeekDateSupported()) {
            System.out.println("Number of weeks in this year: " + calendar.getWeeksInWeekYear());
            System.out.println("Current week number: " + calendar.get(Calendar.WEEK_OF_YEAR));

            calendar.setWeekDate(2012, 16, 3);
            System.out.println(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(calendar.getTime()));

            calendar.setWeekDate(2022, 1, 1);
            System.out.println(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(calendar.getTime()));
        }

        SimpleTimeZone simpleTimeZone = new SimpleTimeZone( //
                -21600000, //
                "CST", //
                Calendar.MARCH, 1, -Calendar.SUNDAY, //
                7200000, // 
                Calendar.NOVEMBER, -1, Calendar.SUNDAY, //
                7200000, //
                3600000);
        
        System.out.println(simpleTimeZone.getDisplayName() + " - " + simpleTimeZone.observesDaylightTime());
    }
}
