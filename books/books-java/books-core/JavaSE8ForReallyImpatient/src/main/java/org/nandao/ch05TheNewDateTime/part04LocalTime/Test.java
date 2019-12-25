package org.nandao.ch05TheNewDateTime.part04LocalTime;

import java.time.LocalTime;

// A LocalTime represents a time of day, such as 15:30:00.
public class Test {

    public static void main(final String[] args) {

        final LocalTime rightNow = LocalTime.now();

        System.out.println("Right now:" + rightNow);

        final LocalTime bedtime = LocalTime.of(22, 30); // or LocalTime.of(22, 30, 0)

        System.out.println("Bed time:" + bedtime);

        // shows common operations with local times. The plus and minus  operations wrap around a 24-hour day. For example,
        final LocalTime wakeup = bedtime.plusHours(8); // wakeup is 6:30:00

        System.out.println("Wakeup:" + wakeup);
    }

    // There is a LocalDateTime class, representing a date and time. That class is suitable
    // for storing points in time in a fixed time zone, for example, for a schedule of
    // classes or events. However, if you need to make calculations that span the daylight
    // savings time, or if you need to deal with users in different time zones, you
    // should use the ZonedDateTime class that we discuss next.    
}
