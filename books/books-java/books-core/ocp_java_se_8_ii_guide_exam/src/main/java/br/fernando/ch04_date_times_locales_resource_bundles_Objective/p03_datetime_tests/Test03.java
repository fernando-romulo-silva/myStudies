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

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following are true regarding the new Date-Time API of Java 8? You had to select 2 options

        // 1º It uses the calendar system defined in ISO-8601 as the default calendar.
        // The core classes in the Date-Time API have names such as LocalDateTime, ZonedDateTime, and OffsetDateTime. All of these use the
        // ISO calendar system.
        //
        // 2º Most of the actual date related classes in the Date-Time API such as LocalDate, LocalTime, and LocalDateTime are immutable.
        // These classes do not have any setters. Once created you cannot change their contents. Even their constructors are private.
        //
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following lines of code print

        java.time.LocalDate dt = java.time.LocalDate.parse("2015-01-01").minusMonths(1).minusDays(1).plusYears(1);
        System.out.println(dt);
        //
        // 2015-11-30
        //
        // Explanation
        // Observe that most of the methods of LocalDate (as well as LocalTime and LocalDateTime) return an object of the same class.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code print when run?

        LocalDateTime greatDay = LocalDateTime.parse("2015-01-01"); // 2
        String greatDayStr = greatDay.format(DateTimeFormatter.ISO_DATE_TIME); // 3
        System.out.println(greatDayStr);// 4

        // 2 will throw an exception at run time.

        // It will throw a DateTimeException because it doesn't have time component.
        // Exception in thread "main" java.time.format.DateTimeParseException: Text '2015-01-01' could not be parsed at index 10.
        // A String such as 2015-01-01T17:13:50 would have worked.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following code print when compiled and run?

        Instant start = Instant.parse("2015-06-25T16:13:30.00z");
        start.plus(10, ChronoUnit.HOURS);
        System.out.println(start);

        Duration timeToCook = Duration.ofHours(1);
        Instant readyTime = start.plus(timeToCook);
        System.out.println(readyTime);

        LocalDateTime ltd = LocalDateTime.ofInstant(readyTime, ZoneId.of("GMT+2"));
        System.out.println(ltd);

        // 1. The first println prints the same Instant because calling plus on an Instance doesn't change that Instance. It returns a new instance.
        // 2015-06-25T16:13:30Z

        // 2. Adding 1 hour to 16:13, will change it to 17:13, which is what the second println prints.
        // 2015-06-25T17:13:30Z

        // 3. A Timezone of GMT+2 means that in that time zone, the time is 2 hours ahead of GMT. Thus, when it is 17:13 in GMT, it is 19:13 in GMT+2.
        // 2015-06-25T19:13:30
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given:
        LocalDate d1 = LocalDate.parse("2015-02-05", DateTimeFormatter.ISO_DATE);
        LocalDate d2 = LocalDate.of(2015, 2, 5);
        LocalDate d3 = LocalDate.now();
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        // Assuming that the current date on the system is 5th Feb, 2015, which of the following will be a part of the output?
        //
        // 2015-02-05
        //
        // Explanation
        // All the three printlns will produce 2015-02-05 because the ISO_DATE pattern is yyy-MM-dd
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following line of code print?
        System.out.println(LocalDate.of(2015, Month.JANUARY, 31).format(DateTimeFormatter.ISO_DATE_TIME));

        // Exception at run time.
        // Observe that you are creating a LocalDate and not a LocalDateTime.
        // LocalDate doesn't have time component and therefore, you cannot format it with a formatter that expects time component such as
        // DateTimeFormatter.ISO_DATE_TIME.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code print?
        Duration d = Duration.ofMillis(1100);
        System.out.println(d);
        d = Duration.ofSeconds(61);
        System.out.println(d);

        // PT1.1S
        // PT1M1S

        // Explanation
        // The format of the returned string will be PTnHnMnS, where n is the relevant hours, minutes or seconds part of the duration.
        // Any fractional seconds are placed after a decimal point i the seconds section. If a section has a zero value, it is omitted.
        // The hours, minutes and seconds will all have the same sign.
        // Examples:     
        // "20.345 seconds"                 -- "PT20.345S     
        // "15 minutes" (15 * 60 seconds)   -- "PT15M"     
        // "10 hours" (10 * 3600 seconds)   -- "PT10H"     
        // "2 days" (2 * 86400 seconds)     -- "PT48H"
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code print?

        Duration d = Duration.ofHours(25);
        System.out.println(d);
        Period p = Period.ofDays(1);
        System.out.println(p);

        // PT25H
        // 1. Duration string starts with PT (because duration is "time" based i.e. hours/minutes/seconds) and Period string starts with
        // just P (because period does not include time. It contains only years, months, and days).

        // P1D
        // 2. Duration does not convert hours into days. i.e. 25 hours will remain as 25 hours instead of 1 day and 1 hour.

    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following are valid ways to create a LocalDateTime?

        java.time.LocalDateTime.of(2015, 10, 1, 10, 10);
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code print when run?

        Period p = Period.between(LocalDate.of(2015, Month.SEPTEMBER, 2), LocalDate.of(2015, Month.JULY, 1));
        System.out.println(p);

        Duration d = Duration.between(LocalDateTime.of(2015, Month.SEPTEMBER, 2, 1, 0), LocalDateTime.of(2015, Month.SEPTEMBER, 2, 10, 10));
        System.out.println(d);

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_05();
    }
}
