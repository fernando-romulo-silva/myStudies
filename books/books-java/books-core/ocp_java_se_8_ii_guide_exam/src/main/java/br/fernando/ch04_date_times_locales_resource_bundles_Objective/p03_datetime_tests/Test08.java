package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p03_datetime_tests;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test08 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given:
        LocalDate d1 = LocalDate.parse("2015-02-05", DateTimeFormatter.ISO_DATE);
        LocalDate d2 = LocalDate.of(2015, 2, 5);
        LocalDate d3 = LocalDate.now();
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        // Assuming that the current date on the system is 5th Feb, 2015, which of the following will be a part of the output?
        //
        // None of the above.
        //
        // All the three printlns will produce 2015-02-05.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Given the getDateString method:
        // Which of the following statements are correct?

        // The code will compile but will always throw a DateTimeException (or its subclass) at run time.

        // Note that LocalDateTime class does not contain Zone information but ISO_ZONED_DATE_TIME requires it. Thus, it will throw the
        // following exception: Exception in thread "main" java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: OffsetSeconds
    }

    public String getDateString(LocalDateTime ldt) {
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ldt);
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
        // Identify correct statements about Java Date and Time API.

        // Classes used to represent dates and times in java.time package are thread safe

        // All classess in java.time package such as classes for date, time, date and time combined, time zones, instants, duration, and 
        // clocks are immutable and thread-safe.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Identify the correct statements.
        //
        // LocalDate, LocalTime, and LocalDateTime implement TemporalAccessor.
        //
        // Explanation
        //
        // Here are some points that you should keep in mind about the new Date/Time classes introduced in Java 8
        //
        // 1. They are in package java.time and they have no relation at all to the old java.util.Date and java.sql.Date.
        //
        // 2. java.time.TemporalAccessor is the base interface that is implemented by LocalDate, LocalTime, and LocalDateTime concrete classes.
        // This interface defines read-only access to temporal objects, such as a date, time, offset or some combination of these, which are
        // represented by the interface TemporalField.
        //
        // 3. LocalDate, LocalTime, and LocalDateTime classes do not have any parent/child relationship among themselves. As their names imply,
        // LocalDate contains just the date information and no time information, LocalTime contains only time and no date, while LocalDateTime
        // contains date as well as time. None of them contains zone information. For that, you can use ZonedDateTime.
        // These classes are immutable and have no public constructors. You create objects of these classes using their static factory
        // methods such as of(...) and from(TemporalAccessor ).
        //
        // 4. Formatting of date objects into String and parsing of Strings into date objects is done by java.time.format.DateTimeFormatter class.
        // This class provides public static references to readymade DateTimeFormatter objects through the fields
        // named ISO_DATE, ISO_LOCAL_DATE, ISO_LOCAL_DATE_TIME,
        //
        // 5. Besides dates, java.time package also provides Period and Duration classes. Period is used for quantity or amount of time in
        // terms of years, months and days, while Duration is used for quantity or amount of time in terms of hour, minute, and seconds.
        // Durations and periods differ in their treatment of daylight savings time when added to ZonedDateTime.
        // A Duration will add an exact number of seconds, thus a duration of one day is always exactly 24 hours. By contrast, a
        // Period will add a conceptual day, trying to maintain the local time.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
        // What will the following code print when run?
        Period p = Period.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1));
        System.out.println(p);

        Duration d = Duration.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1));
        System.out.println(d);

        // It will throw an exception at run time.
        //
        // The call to Duration.between will throw java.time.temporal.UnsupportedTemporalTypeException because LocalDate.now() does not have
        // a time component, while Duration.between method needs Temporal arguments that have a time component.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
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
        test01_01();
    }
}
