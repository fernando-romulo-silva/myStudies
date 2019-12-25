package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p03_datetime_tests;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test07 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Identify correct statements about Java Date and Time API.

        // Classes used to represent dates and times in java.time package are thread safe

        // All classess in java.time package such as classes for date, time, date and time combined, time zones, instants, duration, and
        // clocks are immutable and thread-safe.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following classes should you use to represent just a date without any time or zone information?

        // java.time.LocalDate

        // Java 8 introduces a new package java.time to deal with dates. The old classes such as java.util.Date are not recommended anymore.
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
        //
        // There are two important things to note here:
        //
        // 1. Duration string starts with PT (because duration is "time" based i.e. hours/minutes/seconds) and Period string starts with just "P"
        // (because period does not include time. It contains only years, months, and days).
        //
        // 2. Duration does not convert hours into days. i.e. 25 hours will remain as 25 hours instead of 1 day and 1 hour.
        //
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
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
        // You want to print the date that represents upcoming tuesday from now even if the current day is a tuesday.
        // Which of the following lines of code accomplishe(s) this?

        System.out.println(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)));

        System.out.println(TemporalAdjusters.next(DayOfWeek.TUESDAY).adjustInto(LocalDate.now()));

        // Error, but interesting ...
        // This will return today's date if it is a tuesday, which is not what the question wants.
        System.out.println(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)));
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
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
    // What will the following code print when run?
    static void test01_10() throws Exception {
        // What will the following code print when run?

        Period p = Period.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1));
        System.out.println(p);

        Duration d = Duration.between(LocalDateTime.now(), LocalDateTime.of(2015, Month.SEPTEMBER, 2, 10, 10));
        System.out.println(d);

        // Assume that the local time on the system at the time this code is run is 2nd Sep 2015 1:00 AM.

        // P-1D
        // PT9H10M
        //
        // Note that if the second date is before the first date, a minus sign is included in the output.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
