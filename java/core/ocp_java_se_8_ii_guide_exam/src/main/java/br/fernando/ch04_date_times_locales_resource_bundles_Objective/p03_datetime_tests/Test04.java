package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p03_datetime_tests;

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

public class Test04 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following line of code print?

        System.out.println(LocalDate.of(2015, Month.JANUARY, 31).format(DateTimeFormatter.ISO_DATE_TIME));

        // Exception at run time.
        // Observe that you are creating a LocalDate and not a LocalDateTime. LocalDate doesn't have time component and therefore,
        // you cannot format it with a formatter that expects time component such as DateTimeFormatter.ISO_DATE_TIME.

    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following classes should you use to represent just a date without any time or zone information?

        // java.time.LocalDate
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
        // What will the following code print?

        Duration d = Duration.ofHours(25);
        System.out.println(d);
        Period p = Period.ofDays(1);
        System.out.println(p);

        // PT25H
        // P1D

        // 1. Duration string starts with PT (because duration is "time" based i.e. hours/minutes/seconds) and Period string starts with
        // just P (because period does not include time. It contains only years, months, and days).
        //
        // 2. Duration does not convert hours into days. i.e. 25 hours will remain as 25 hours instead of 1 day and 1 hour.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // ISO_DATE = '2011-12-03' or '2011-12-03+01:00'. +time offset

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
        // All the three printlns will produce 2015-02-05.

    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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

        // * Attention *
        // Duration counts in terms of hours, minutes, and seconds. Therefore, days are converted into hours.
        // That is why the first println prints PT24H and not PT1D.
        //

        // A Duration of 0 is printed as 0S and a Period of 0 is printed as 0D.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
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

        // Explanation
        // 1. The isAfter method of LocalTime returns true only if this LocalTime is after the passed LocalTime.
        // (I.e. if both are same, then it will return false) In this case, it is not. Therefore, timeConsumed will remain 0.
        //
        // 2. The until method return the difference between the two time periods in given units.
        // Here, the difference is 45 minutes but the unit is HOURS, therefore, it will return 0
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given that daylight Savings Time starts on March 8th at 2 AM in US/Eastern time zone.
        // (As a result, 2 AM becomes 3 AM.), what will the following code print?

        LocalDateTime ld1 = LocalDateTime.of(2015, Month.MARCH, 8, 2, 0);
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of("US/Eastern"));
        LocalDateTime ld2 = LocalDateTime.of(2015, Month.MARCH, 8, 3, 0);
        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of("US/Eastern"));
        long x = ChronoUnit.HOURS.between(zd1, zd2);
        System.out.println(x);

        // 0

        // Explanation
        // Think of it as follows - The time difference between two dates is simply the amount of time you need to go from date 1 to date 2.
        //
        // So if you want to go from 2AM to 3AM, how many hours do you need? On a regular day, you need 1 hour. That is, if you add 1 hour to 2AM,
        // you will get 3AM. However, as given in the problem statement, at the time of DST change, 2 AM becomes 3AM. That means, even though your
        // local date time is 2 AM, your ZonedDateTime is actually 3AM.
        // Therefore, you are already at 3AM, which means, there is no time difference between 2 AM and 3 AM. The answer is, therefore, 0.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given: Daylight Savings Time ends on Nov 1 at 2 AM in US/Eastern time zone. As a result, 2 AM becomes 1 AM.
        // What will the following code print ?

        LocalDateTime ld = LocalDateTime.of(2015, Month.OCTOBER, 31, 10, 0);

        ZonedDateTime date = ZonedDateTime.of(ld, ZoneId.of("US/Eastern"));
        date = date.plus(Duration.ofDays(1)); // PT24H, but it wont add a day, just 24H to 10H, minus 1 of the Daylight savings...
        System.out.println(date); // 2015-11-01T09:00-05:00[US/Eastern]

        date = ZonedDateTime.of(ld, ZoneId.of("US/Eastern"));
        date = date.plus(Period.ofDays(1)); // add a day
        System.out.println(date); // 2015-11-01T10:00-05:00[US/Eastern]

        // Explanation
        // Important thing to remember here is that Period is used to manipulate dates in terms of days, months, and years, while Duration
        // is used to manipulate dates in terms of hours, minutes, and seconds. Therefore, Period doesn't mess with the time component of
        // the date while Duration may change the time component if the date is close to the DST boundary.
        //
        // Durations and periods differ in their treatment of daylight savings time when added to ZonedDateTime. A Duration will add an exact
        // number of seconds, thus a duration of one day is always exactly 24 hours. By contrast, a Period will add a conceptual day,
        // trying to maintain the local time.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_10();
    }

}
