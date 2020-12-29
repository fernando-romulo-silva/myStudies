package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p03_datetime_tests;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following are true regarding the new Date-Time API of Java 8?
        //
        // It uses the calendar system defined in ISO-8601 as the default calendar.
        //
        // This calendar is based on the Gregorian calendar system and is used globally as the defacto standard for representing date and time.
        // The core classes in the Date-Time API have names such as LocalDateTime, ZonedDateTime, and OffsetDateTime.
        // All of these use the ISO calendar system.
        //
        // Most of the actual date related classes in the Date-Time API such as LocalDate, LocalTime, and LocalDateTime are immutable.
        //
        // These classes do not have any setters. Once created you cannot change their contents. Even their constructors are private.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Given the following code:

        LocalDateTime ldt = LocalDateTime.now();

        // Which of the following statements are correct?

        DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ldt);

        // The code will compile but will always throw a DateTimeException (or its subclass) at run time.
        //
        // Note that LocalDateTime class does not contain Zone information but ISO_ZONED_DATE_TIME requires it.
        // Thus, it will throw the following exception
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You want to print the date that represents upcoming tuesday from now even if the current day is a tuesday.
        // Which of the following lines of code accomplishe(s) this?

        System.out.println(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)));

        System.out.println(TemporalAdjusters.next(DayOfWeek.TUESDAY).adjustInto(LocalDate.now()));

        // Error, but interesting ...
        // This will return today's date if it is a tuesday, which is not what the question wants.
        System.out.println(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)));
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following line of code print?
        System.out.println(LocalDate.of(2015, Month.JANUARY, 31).format(DateTimeFormatter.ISO_DATE_TIME));

        // Observe that you are creating a LocalDate and not a LocalDateTime. LocalDate doesn't have time component and therefore, you cannot
        // format it with a formatter that expects time component such as DateTimeFormatter.ISO_DATE_TIME.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
        //
        // Duration counts in terms of hours, minutes, and seconds. Therefore, days are converted into hours.
        // That is why the first println prints PT24H and not PT1D.
        //
        // A Duration of 0 is printed as 0S and a Period of 0 is printed as 0D.
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
        // Assuming that the local time when the following code is executed is 9.30 AM, what will it print?

        LocalTime now = LocalTime.now();
        LocalTime gameStart = LocalTime.of(10, 15);
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

        // 2. The until method return the difference between the two time periods in given units. Here, the difference is 45 minutes but the unit is HOURS,
        // therefore, it will return 0.
        //
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following classes should you use to represent just a date without any time or zone information?
        java.time.LocalDate date = java.time.LocalDate.now();
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Identify correct statements about Java Date and Time API.

        // Classes used to represent dates and times in java.time package are thread safe

        // All classess in java.time package such as classes for date, time, date and time combined, time zones, instants, duration, and 
        // clocks are immutable and thread-safe.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code print when compiled and run?

        Instant start = Instant.parse("2015-06-25T16:13:30.00z");
        start.plus(10, ChronoUnit.HOURS);
        System.out.println(start);

        Duration timeToCook = Duration.ofHours(1);
        Instant readyTime = start.plus(timeToCook);
        System.out.println(readyTime);

        LocalDateTime ltd = LocalDateTime.ofInstant(readyTime, ZoneId.of("GMT+2"));
        System.out.println(ltd);

        // 1. The first println prints the same Instant because calling plus on an Instance doesn't change that Instance.
        // It returns a new instance that's 2015-06-26T02:13:30Z, but it wont be printed
        //
        // 2. Adding 1 hour to 16:13, will change it to 17:13, which is what the second println prints.
        //
        // 3. A Timezone of GMT+2 means that in that time zone, the time is 2 hours ahead of GMT. Thus, when it is 17:13 in GMT, it is 19:13 in GMT+2.
        //
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Given that daylight Savings Time ends on Nov 1 at 2 AM in US/Eastern time zone. (As a result, 2 AM becomes 1 AM.),
        // what will the following code print?

        LocalDateTime ld1 = LocalDateTime.of(2015, Month.NOVEMBER, 1, 2, 0);
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of("US/Eastern"));

        System.out.println(zd1); // 2015-11-01T02:00-05:00[US/Eastern]

        LocalDateTime ld2 = LocalDateTime.of(2015, Month.NOVEMBER, 1, 1, 0);
        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of("US/Eastern"));

        System.out.println(zd2); // 2015-11-01T01:00-04:00[US/Eastern]

        long x = ChronoUnit.HOURS.between(zd1, zd2);
        System.out.println(x);

        // -2

        // Explanation
        // The time difference between two dates is simply the amount of time you need to go from date 1 to date 2.
        //
        // So if you want to go from 1AM to 2AM, how many hours do you need? On a regular day, you need 1 hour.
        //
        // That is, if you add 1 hour to 1AM, you will get 2AM. However, as given in the problem statement, at the time of DST change, 2 AM becomes 1AM.
        //
        // That means, even after adding 1 hour to 1AM, you are not at 2AM. You have to add another hour to get to 2 AM.
        // In total, therefore, you have to add 2 hours to 1AM to get to 2AM.
        //
        // The answer can therefore be short listed to 2 or -2. Now, as per the JavaDoc description of the between method, it returns negative
        // value if the end is before the start.
        //
        // In the given code, our end date is 1AM, while the start date is 2AM. This means, the answer is -2.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_11();
    }
}
