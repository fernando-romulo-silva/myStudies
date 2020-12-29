package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p03_datetime_tests;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print?
        Instant ins = Instant.parse("2015-06-25T16:43:30.00z");
        ins.plus(10, ChronoUnit.HOURS);
        System.out.println(ins);

        // 2015-06-25T16:43:30Z

        // Explanation
        // java.time.Instant has several versions of plus and minus methods. However, remember that none of these methods change the Instant object itself.
        // They return a new Instant object with the modification. In this case, it would return a new Instant object containing 2015-06-26T02:43:30Z.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Assume that the local time on the system at the time this code is run is 2nd Sep 2015 1:00 AM.
        // What will the following code print when run?

        Period p = Period.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1));
        System.out.println(p);
        // P-1D

        Duration d = Duration.between(LocalDateTime.now(), LocalDateTime.of(2015, Month.SEPTEMBER, 2, 10, 10));
        System.out.println(d);
        // PT9H10M

        // Note that if the second date is before the first date, a minus sign is included in the output.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code print?

        Duration d = Duration.ofDays(1);
        System.out.println(d);

        d = Duration.ofMinutes(0);
        System.out.println(d);

        Period p = Period.ofMonths(0);
        System.out.println(p);

        // PT24H
        // PT0S
        // P0D

        // Duration counts in terms of hours, minutes, and seconds. Therefore, days are converted into hours.
        // That is why the first println prints PT24H and not PT1D.
        // A Duration of 0 is printed as 0S and a Period of 0 is printed as 0D.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following code print when run?
        // Assume that the local time on the system at the time this code is run is 2nd Sep 2015 1:00 AM.

        Period p = Period.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1));
        System.out.println(p);

        Duration d = Duration.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1)); // throw exception here
        System.out.println(d);

        // It will throw an exception at run time.
        //
        // The call to Duration.between will throw java.time.temporal.UnsupportedTemporalTypeException because LocalDate.now() does not have a 
        // time component, while Duration.between method needs Temporal arguments that have a time component.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print?
        Duration d = Duration.ofHours(25);
        System.out.println(d);
        Period p = Period.ofDays(1);
        System.out.println(p);

        // PT25H
        // P1D

        // There are two important things to note here:
        // 1. Duration string starts with PT (because duration is "time" based i.e. hours/minutes/seconds) and Period string starts with just P
        // (because period does not include time. It contains only years, months, and days).
        //
        // 2. Duration does not convert hours into days. i.e. 25 hours will remain as 25 hours instead of 1 day and 1 hour.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Which of the following classes should you use to represent just a date without any time or zone information?

        // java.time.LocalDate

        // Java 8 introduces a new package java.time to deal with dates. The old classes such as java.util.Date are not recommended anymore.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Given:
        LocalDate d1 = LocalDate.parse("2015-02-05", DateTimeFormatter.ISO_DATE);
        LocalDate d2 = LocalDate.of(2015, 2, 5);
        LocalDate d3 = LocalDate.now();
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        // Assuming that the current date on the system is 5th Feb, 2015, which of the following will be a part of the output?

        // All the three printlns will produce 2015-02-05.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code print when compiled and run?

        Instant start = Instant.parse("2015-06-25T16:13:30.00z");
        start.plus(10, ChronoUnit.HOURS);
        System.out.println(start);

        Duration timeToCook = Duration.ofHours(1);
        Instant readyTime = start.plus(timeToCook);
        System.out.println(readyTime);

        LocalDateTime ltd = LocalDateTime.ofInstant(readyTime, ZoneId.of("GMT+2"));
        System.out.println(ltd);

        // 2015-06-25T16:13:30Z
        // 2015-06-25T17:13:30Z
        // 2015-06-25T19:13:30

        // 1. The first println prints the same Instant because calling plus on an Instance doesn't change that Instance. It returns a new instance.
        //
        // 2. Adding 1 hour to 16:13, will change it to 17:13, which is what the second println prints.
        //
        // 3. A Timezone of GMT+2 means that in that time zone, the time is 2 hours ahead of GMT. Thus, when it is 17:13 in GMT, it is 19:13 in GMT+2.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Identify correct statements about Java Date and Time API

        // Classes used to represent dates and times in java.time package are thread safe.
        // All classess in java.time package such as classes for date, time, date and time combined, time zones, instants,
        // duration, and clocks are immutable and thread-safe.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following lines of code print

        java.time.LocalDate dt = java.time.LocalDate.parse("2015-01-01").minusMonths(1).minusDays(1).plusYears(1);
        System.out.println(dt);

        // 2015-11-30
        // The numbering for days and months starts with 1. Rest is simple math.

        // Observe that most of the methods of LocalDate (as well as LocalTime and LocalDateTime) return an object of the same class.
        // This allows you to chain the calls as done in this question. However, these methods return a new object.
        // They don't modify the object on which the method is called.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_03();
    }
}
