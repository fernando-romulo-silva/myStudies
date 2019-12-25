package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p03_datetime_tests;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following are valid ways to create a LocalDateTime?

        java.time.LocalDateTime.of(2015, 10, 1, 10, 10);
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following are true regarding the new Date-Time API of Java 8?

        // It uses the calendar system defined in ISO-8601 as the default calendar.
        // This calendar is based on the Gregorian calendar system and is used globally as the defacto standard for representing date and time.
        // The core classes in the Date-Time API have names such as LocalDateTime, ZonedDateTime, and OffsetDateTime.
        // All of these use the ISO calendar system.
        //
        // Most of the actual date related classes in the Date-Time API such as LocalDate, LocalTime, and LocalDateTime are immutable.
        // These classes do not have any setters. Once created you cannot change their contents. Even their constructors are private.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
        // Assuming that the local time when the following code is executed is 9.30 AM, what will it print?

        LocalTime now = LocalTime.of(9, 30); // LocalTime.now();
        LocalTime gameStart = LocalTime.of(10, 15); // it's 10 am = 10h

        long timeConsumed = 0;
        long timeToStart = 0;

        if (now.isAfter(gameStart)) {
            timeConsumed = gameStart.until(now, ChronoUnit.HOURS);
        } else {
            timeToStart = now.until(gameStart, ChronoUnit.HOURS);
        }

        System.out.println(timeToStart + " " + timeConsumed);

        // 0 0
        //
        // Explanation
        // 1. The isAfter method of LocalTime returns true only if this LocalTime is after the passed LocalTime.
        // (I.e. if both are same, then it will return false) In this case, it is not. Therefore, timeConsumed will remain 0.

        // 2. The until method return the difference between the two time periods in given units. Here, the difference is 45 minutes but the 
        // unit is HOURS,  therefore, it will return 0.
        //
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following lines of code print

        java.time.LocalDate dt = java.time.LocalDate.parse("2015-01-01").minusMonths(1).minusDays(1).plusYears(1);
        System.out.println(dt);

        // 2015-11-30
        //
        // Explanation
        // Observe that most of the methods of LocalDate (as well as LocalTime and LocalDateTime) return an object of the same class.
        // This allows you to chain the calls as done in this question. However, these methods return a new object.
        // They don't modify the object on which the method is called.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following line of code print?

        System.out.println(LocalDate.of(2015, Month.JANUARY, 31).format(DateTimeFormatter.ISO_DATE_TIME));

        // Exception at run time.
        //
        // Observe that you are creating a LocalDate and not a LocalDateTime. LocalDate doesn't have time component and therefore,
        // you cannot format it with a formatter that expects time component such as DateTimeFormatter.ISO_DATE_TIME
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following options correctly add 1 month and 1 day to a given LocalDate
        LocalDate ld = LocalDate.now();

        LocalDate ld2 = ld.plus(Period.of(0, 1, 1));
        //
        // public static Period of(int years, int months, int days) Obtains a Period representing a number of years, months and days.
        // This creates an instance based on years, months and days.
        //
        // Wrong Answer
        LocalDate ld3 = ld.plus(Period.ofMonths(1).ofDays(1));
        //
        // ofXXX are static methods of Period class. Therefore, Period.ofMonths(1).ofDays(1) will give you a Period of only 1 day. 
        // The previous call to ofMonths(1) does return an instance of Period comprising 1 month but that instance is irrelevant 
        // because ofDays is a static method.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given

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
        // Since LocalDate is being created (and not LocalDateTime), none of the printlns will output the time component.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_06();
    }
}
