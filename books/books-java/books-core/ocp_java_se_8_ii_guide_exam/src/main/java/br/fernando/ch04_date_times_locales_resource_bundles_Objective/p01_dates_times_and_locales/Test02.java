package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p01_dates_times_and_locales;

import java.time.Clock;
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
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

class Test02 {

    // =========================================================================================================================================
    // Zoned Dates and Times
    // Local dates and times are great when you don’t need to worry about the time zone, but sometimes we need to share dates and times with people
    // in other time zones, so knowing which time zone you’re in or they’re in becomes important.
    //
    // All time zones are based on Greenwich Mean Time (GMT), the time in Greenwich, England. GMT is a time zone.
    // Your time zone will either be ahead of or behind GMT. For instance, in Madras, OR, for the eclipse, you are GMT-7, meaning you are seven hours behind GMT.
    static void test01() {

        LocalDateTime eclipseDay = LocalDateTime.parse("2017-08-21 10:19", DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm"));

        final ZoneId zoneId = ZoneId.of("US/Pacific");

        ZonedDateTime zTotalityDateTime = ZonedDateTime.of(eclipseDay, zoneId);

        // In this example, the ZoneId is “ US/Pacific ,” which happens to be GMT-7
        // (which you may also see written as UTC-7). You can use either “US/Pacific” or “GMT-7” as the ZoneId .

        System.out.println("Date and time totality begins with time zone: " + zTotalityDateTime);

        // How did you know the name of the ZoneId ?
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        List<String> zoneList = new ArrayList<>(zoneIds);
        Collections.sort(zoneList);

        zoneList.stream() //
                .filter((p) -> p.contains("US")) //
                .forEach((p) -> System.out.println(p));

        // Let’s get back to daylight savings time. Recall that the U.S. Pacific time zone is either GMT-7 (in winter, standard time) or GMT-8 (in summer, daylight savings time).

        // That means when you’re creating a ZonedDateTime for Madras, Oregon (or any other place that uses daylight savings), you’re going to need to
        // know if you’re currently in daylight savings time.

        zoneId.getRules(); // resturns a ZoneRules object that has all the rules

        // about time zones, including daylight savings and standard time.
        System.out.println("Is Daylight Savings in effect at time of totality: " + zoneId.getRules().isDaylightSavings(zTotalityDateTime.toInstant()));
    }

    // =========================================================================================================================================
    // Date and Time Adjustments
    // However, you can create a new datetime object from an existing datetime object, and java.time.* provides
    // plenty of adjusters that make it easy to do so. In other words, rather than modifying an existing datetime, you
    // just make a new datetime from the existing one
    static void test02() {

        LocalDateTime eclipseDay = LocalDateTime.parse("2017-08-21 10:19", DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm"));

        ZonedDateTime zTotalityDateTime = ZonedDateTime.of(eclipseDay, ZoneId.of("US/Pacific"));

        // Adjust date time to next Thursday
        ZonedDateTime followingThursdayDateTime = zTotalityDateTime.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));

        System.out.println("Thursday following the totality: " + followingThursdayDateTime);

        // The class TemporalAdjusters has a whole slew of handy methods to make a TemporalAdjuster for a variety of scenarios,
        // such as firstDayOfNextYear(), lastDayOfMonth() , and more.
    }

    // =========================================================================================================================================
    // Periods
    static void test03() {
        // Periods
        // Totality begins in Austin, TX in 2024 at 1:35pm adn 56 seconds
        // Notice we used 13 to specify 1 PM . Austin is in the U.S. Central time zone, so we get the zoneId using that name
        ZonedDateTime totalityAustin = ZonedDateTime.of(2024, 4, 8, 13, 35, 56, 0, ZoneId.of("US/Central"));
        System.out.println("Next total eclipse in the US, date/time in Austin, TX: " + totalityAustin);

        // Now let’s create the reminder for one month before this date and time by creating a
        // Period that represents one month and subtract it from the date and time for the eclipse:
        Period period = Period.ofMonths(1);

        // Notice how the period is displayed, with “ P ” meaning period and “ 1M ” meaning month.
        System.out.println("Period is " + period);
        ZonedDateTime reminder = totalityAustin.minus(period);

        System.out.println("DateTime of 1 month reminder: " + reminder);

        // While we're here, let's see how to create a LocalDateTime from the ZonedDateTime for people who are in Austin:
        System.out.println("Zoned Dateime (Madras, OR) of reminder: " + reminder.withZoneSameInstant(ZoneId.of("US/Pacific")));

        // Nice for us that Java correctly computed the time using ZoneRules behind the scenes.
    }

    // =========================================================================================================================================
    static void test04() {
        // Durations
        // We can compute the time in a couple of ways; we’re going to do it using ChronoUnit and Duration.
        // ChronoUnit is an enum in java.time.temporal that provides a set of predefined units of time periods.
        //
        // For instance, ChronoUnit.MINUTES represents the concept of a minute. ChronoUnit also supplies a
        // method between() that we can use to compute a ChronoUnit time period between two times
        //
        // Once we have the number of minutes between two times, we can use that to create a Duration.
        // Duration s have all kinds of handy methods for computing things, like adding and subtracting hours and minutes
        // and seconds, or converting a Duration into a number of seconds or milliseconds, and so on

        // Eclipse begins in Austin, TX
        LocalTime begins = LocalTime.of(12, 17, 32); // 12:17:32

        // Totality in Austin, TX
        LocalTime totality = LocalTime.of(13, 35, 56); //

        System.out.println("Eclipse begins at " + begins + " and totality is at " + totality);

        // Now, let’s use a ChronoUnit to compute the number of minutes between begins and totality

        long betweenMins = ChronoUnit.MINUTES.between(begins, totality);
        System.out.println("Minutes between begin and totality: " + betweenMins);

        // Let’s turn this into a Duration . As you might expect, we can turn the number of minutes into
        // a Duration using Duration.ofMinutes() :
        final Duration betweenDuration = Duration.ofMinutes(betweenMins);

        // PT means “period of time,” meaning Duration (rather than Period ), and then 1H18M
        // means “1 hour and 18 minutes” corresponding to our 78 minutes.
        System.out.println("Duration: " + betweenDuration);

        LocalTime totalityBegins = begins.plus(betweenDuration);
        System.out.println("Totality begins, computed: " + totalityBegins);
    }

    // =========================================================================================================================================
    static void test05() {
        // Instants
        // An Instant represents an instant in time. Instant s can’t be represented as just one long , like
        // you might be used to, because an Instant includes nanoseconds, so the seconds plus the nanoseconds is too big for a long

        ZonedDateTime totalityAustin = ZonedDateTime.of(2024, 4, 8, 13, 35, 56, 0, ZoneId.of("US/Central"));

        Instant totalityInstant = totalityAustin.toInstant();
        System.out.println("Austin's eclipse instant is: " + totalityInstant);

        // represent now
        Instant nowInstant = Instant.now();

        long minsBetween = ChronoUnit.MINUTES.between(nowInstant, totalityInstant);

        Duration durationBetweenInstants = Duration.ofMinutes(minsBetween);

        System.out.println("Minutes between " + minsBetween + ", is duration " + durationBetweenInstants);

        // Lastly, if you want to get the number of seconds since January 1, 1970, from an Instant , use the method getEpochSecond()

        Instant now = Instant.now();
        System.out.println("Seconds since epoch: " + now.getEpochSecond());
    }

    // =========================================================================================================================================
    // A Few Other Handy Methods and Examples
    // Let’s add another reminder before the next eclipse, say, for three days before the
    // eclipse, and then let’s figure out what day of the week this reminder will occur
    static void test06() {

        ZonedDateTime totalityAustin = ZonedDateTime.of(2024, 4, 8, 13, 35, 56, 0, ZoneId.of("US/Central"));

        // Another reminder 3 days before
        System.out.println("DateTime of 3 day reminder: " + totalityAustin.minus(Period.ofDays(3)));

        // what day of the week is that?
        // We see that the three-day reminder is on April 5, which is a Friday:
        System.out.println("Day of week for 3 day reminder: " + totalityAustin.minus(Period.ofDays(3)).getDayOfWeek());

        // And we really should call our sister in Paris a couple of hours after the next eclipse to tell her how it was:
        ZonedDateTime localParis = totalityAustin.withZoneSameInstant(ZoneId.of("Europe/Paris"));

        System.out.println("Eclipse happens at " + localParis + " Paris time");

        System.out.println("Phone sister at 2 hours after totality: " + totalityAustin.plusHours(2) + ", " + localParis.plusHours(2) + " Paris time.");

        System.out.println("Is eclipse still in the future? " + ZonedDateTime.now().isBefore(totalityAustin));

        System.out.println("Is 2024 a leap year? " + totalityAustin.toLocalDate().isLeapYear());
    }

    // =========================================================================================================================================
    static void summary() {
        // **************************************************************************
        // ZonedId
        // **************************************************************************
        final ZoneId zId1 = ZoneId.of("America/Sao_Paulo");
        System.out.println(zId1);

        // How did you know the name of the ZoneId ?
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(zoneIds);

        zoneIds.stream() //
                .filter((p) -> p.contains("America")) //
                .sorted((p1, p2) -> p1.compareTo(p2)) //
                .forEach((p) -> System.out.println(p));

        // **************************************************************************
        // ZoneRules
        // **************************************************************************
        ZoneRules rId1 = zId1.getRules();

        ZonedDateTime zdt1 = ZonedDateTime.now(zId1);

        // dayLight Savings = Horario Verao
        rId1.isDaylightSavings(zdt1.toInstant());

        // **************************************************************************
        // ZonedDateTime
        // **************************************************************************
        // Only ZonedDateTime, don't exists ZonedDate or ZonedTime.

        ZonedDateTime zdt2 = ZonedDateTime.now(); // your ZoneId

        ZonedDateTime zdt3 = ZonedDateTime.now(zId1); // local date time from determined place

        ZonedDateTime zdt4 = ZonedDateTime.now(Clock.systemDefaultZone());

        // **************************************************************************
        // TemporalAdjusters
        // **************************************************************************
        // Adjust date time to next Thursday
        TemporalAdjuster tadNextThursday = TemporalAdjusters.next(DayOfWeek.THURSDAY);

        ZonedDateTime followingThursdayDateTime = zdt2.with(tadNextThursday);

        // first Day of Month (the month of 
        TemporalAdjuster tadFirstDayOfMonth = TemporalAdjusters.firstDayOfMonth();

        LocalDate ld1 = LocalDate.now();

        ld1.with(tadFirstDayOfMonth);

        // **************************************************************************
        // Periods - Period is for Date, only date (days, months, weeks and years)
        // **************************************************************************
        Period p1 = Period.ofMonths(1);
        // Notice how the period is displayed, with “ P ” meaning period and “ 1M ” meaning month.
        System.out.println(p1);// P1M

        Period p2 = Period.ofDays(5);
        System.out.println(p2); // P5D - Period 5 Days

        System.out.println(Period.ofDays(60)); // P60D  60 days, don't format to month

        // * Attention *
        System.out.println(Period.between(LocalDate.of(2015, Month.SEPTEMBER, 2), LocalDate.of(2015, Month.SEPTEMBER, 1))); // P-1D ?

        System.out.println(Period.between(LocalDate.of(2015, Month.SEPTEMBER, 2), LocalDate.of(2015, Month.JULY, 1))); // P-2M-1D ?
        // So, Note that if the second date is before the first date, a minus sign is included in the output.

        ZonedDateTime reminder = zdt1.minus(p1);

        Period p = Period.ofMonths(0);
        System.out.println(p); // P0D

        System.out.println("DateTime of 1 month reminder: " + reminder);

        // **************************************************************************
        // Durations - Durations is for Time, only time (hours, minutes, seconds, ...)
        // **************************************************************************
        Duration d1 = Duration.ofHours(27); // 27 hours

        Duration.of(3, ChronoUnit.HOURS);

        System.out.println(d1); // PT2H - Period of Time 27 Hours

        System.out.println(Duration.ofSeconds(61)); // PT1M1S it's format to Minutes

        System.out.println(Duration.ofMinutes(611)); // PT1H1M it's format to Hours

        System.out.println(Duration.ofMillis(61100)); // PT1M1.1S

        // but not only it...

        System.out.println(Duration.ofDays(2)); // PT48H it's format hours

        reminder.plus(d1);

        Duration d3 = Duration.ofMinutes(0);
        System.out.println(d3); // PT0S

        // **************************************************************************
        // ChronoUnit - Unit of Time
        // **************************************************************************
        long betweenMinutes = ChronoUnit.MINUTES.between(zdt1, followingThursdayDateTime);

        ChronoUnit.DAYS.between(zdt1, followingThursdayDateTime);

        ChronoUnit.YEARS.between(zdt1, followingThursdayDateTime);

        ChronoUnit.WEEKS.between(zdt1, followingThursdayDateTime);

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        System.out.println(Duration.ofDays(2)); // PT48H it's format hours
    }
}
