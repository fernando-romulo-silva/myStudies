package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p01_dates_times_and_locales;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

// Working with Dates and Times
final class Test01 {

    // =========================================================================================================================================
    // The java.time.* Classes for Dates and Times.
    public static void test01() {
        final LocalDate nowDate = LocalDate.now();

        final LocalTime nowTime = LocalTime.now();

        final LocalDateTime nowDateTime01 = LocalDateTime.of(nowDate, nowTime);

        System.out.println("It's currently " + nowDateTime01 + " where I am");

        // When we run this code we see:
        //
        // It's currently 2017-10-11T14:51:19.982 where I am
        //
        // The string 2017-10-11T14:51:19.982 represents the date, October 11, 2017, and time,
        // 14:51:19.982, which is 2:51 PM and 19 seconds and 982 milliseconds. Notice that Java
        // displays a “ T ” between the date and the time when converting the LocalDateTime to a string

        // We could also write:
        final LocalDateTime nowDateTime02 = LocalDateTime.now();

        // The day of the eclipse in Madras, OR
        LocalDate eclipseDate1 = LocalDate.of(2017, 8, 21);

        LocalDate eclipseDate2 = LocalDate.parse("2017-08-21");

        System.out.println("Eclipse date: " + eclipseDate1 + ", " + eclipseDate2);

        // Eclipse begins in Madras, OR
        LocalTime begins = LocalTime.of(9, 6, 43); // 9:6:43

        // Totality starts in Mdaras, OR
        LocalTime totality = LocalTime.parse("10:19:36"); // 10:19:36

        System.out.println("Eclipse begins at " + begins + " and totality is at " + totality);

        // If you want to be precise about the format of the date and time you’re parsing into a
        // LocalDate or LocalTime or LocalDateTime , you can use DateTimeFormatter .
        //
        // You can either use one of several predefined formats or create your own format for parsing
        // using a sequence of letters and symbols. In the following example, we create a date
        // and time in a string and then tell the LocalDateTime how to parse that using a DateTimeFormatter :

        String eclipseDateTime = "2017-08-21 10:19";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");

        // only dateTime,
        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter); // use formatter

        System.out.println("Eclipse day: " + eclipseDay);

        // Of course, you can also use DateTimeFormatter to change the format of the output (again using letters and symbols):

        System.out.println("Eclipse day, formatted: " + eclipseDay.format(DateTimeFormatter.ofPattern("dd, mm, yy hh, mm")));
    }

    static void test02() {
        // LocalDateTime has several methods that make it easy to add to and subtract from dates and times.

        String eclipseDateTime = "2017-08-21 10:19";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");

        LocalDateTime eclipseDay = LocalDateTime.parse(eclipseDateTime, formatter);

        System.out.println("Mom time: " + eclipseDay.plusHours(2));

        System.out.println("Going home: " + eclipseDay.plusDays(3));

        System.out.println("What day of the week is eclipse? " + eclipseDay.getDayOfWeek());
    }

    // =========================================================================================================================================
    static void summary() {
        // **************************************************************************
        // Instant - Date for computer, numbers. Instant s can’t be represented as just one long, like you might be used to,
        // because an Instant includes nanoseconds, so the seconds plus the nanoseconds is too big for a long
        // **************************************************************************
        Instant it1 = Instant.now();

        long timeInLong = new Date().getTime();

        Instant it2 = Instant.ofEpochMilli(timeInLong);

        Instant start = Instant.parse("2015-06-25T16:13:30.00z"); // 015-06-25T16:13:30Z

        // **************************************************************************
        // LocalTime
        // **************************************************************************
        LocalTime lt1 = LocalTime.now();
        lt1 = LocalTime.now(ZoneId.of("America/Sao_Paulo"));

        System.out.println(lt1);

        LocalTime lt2 = LocalTime.of(5, 30);
        System.out.println(lt2);

        LocalTime lt3 = LocalTime.parse("14:00");
        System.out.println(lt3);

        // **************************************************************************
        // LocalDate
        // **************************************************************************
        LocalDate ld1 = LocalDate.now();
        System.out.println(ld1);

        LocalDate ld2 = LocalDate.of(2004, 4, 5);
        System.out.println(ld2);

        LocalDate ld3 = LocalDate.parse("2017-08-21");
        System.out.println(ld3);

        // **************************************************************************
        // LocalDateTime
        // **************************************************************************
        LocalDateTime ldt1 = LocalDateTime.now();
        System.out.println(ldt1);

        LocalDateTime ldt2 = LocalDateTime.of(ld1, lt1);

        ldt2 = LocalDateTime.of(2009, 4, 8, 22, 50, 2);
        System.out.println(ldt2);

        LocalDateTime ldt3 = LocalDateTime.parse("2017-08-21T10:19:00"); // yyyy-MM-ddTHH:mm:ss
        System.out.println(ldt3);

        // **************************************************************************
        // DateTimeFormatter
        // **************************************************************************
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        DateTimeFormatter dtf2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);

        DateTimeFormatter dtf3 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);

        // you can use it
        LocalDateTime localDateTime = LocalDateTime.parse("2017-08-21 10:19", dtf1);
        System.out.println(localDateTime);

        final DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm");

        // dateTime
        localDateTime.format(dtf4); // For date time, you need a formatter with date and time too.

        DateTimeFormatter dateTimeFormatterwithLocale = dtf2.withLocale(new Locale("pt", "BR"));

        System.out.println("Brazil's Format: " + dateTimeFormatterwithLocale.format(localDateTime));

        // ISO_DATE = '2011-12-03' or '2011-12-03+01:00'. +time offset 
        // if is iso_zoneddatetime = [Europe/Paris]
        LocalDate d1 = LocalDate.parse("2015-02-05", DateTimeFormatter.ISO_DATE);

        // * pay attention *
        DateTimeFormatter.ISO_ZONED_DATE_TIME.format(d1); // d1 is a localDate, not a ZoneDateTime, it'll throw a exception

        // **************************************************************************
        // Plus and Minus
        // **************************************************************************
        // Plus LocalDate
        ld1.plus(2, ChronoUnit.MONTHS);
        ld1.plus(Duration.ofHours(36)); // UnsupportedTemporalTypeException, because it's a local date, you can't plus hours.

        ld1.minusDays(4); // Return a new object, don't change the actual

        // Plus LocalDateTime
        ldt1.plusDays(4);

        ldt1.plus(2, ChronoUnit.MONTHS);

        ldt1.minus(2, ChronoUnit.YEARS);

        ldt1.plus(Period.ofDays(4));

        ldt1.plus(Duration.ofHours(36));

        ldt1.until(d1, ChronoUnit.DAYS);

        // (Local|Zone)DateTime or (Local|Zone)Date
        // plusMonths
        // plusYears
        // plusDays
        //
        // Only for (Local|Zone)Time or (Local|Zone)DateTime
        // plusHours
        // plusMinutes
        // plusNanos

        // **************************************************************************
        // with - Create a new object with field changed
        // **************************************************************************
        ldt1.withYear(2004);

        // Same idea of the plus and minus

    }

    // =========================================================================================================================================

    public static void main(String[] args) {
        summary();
    }
}
