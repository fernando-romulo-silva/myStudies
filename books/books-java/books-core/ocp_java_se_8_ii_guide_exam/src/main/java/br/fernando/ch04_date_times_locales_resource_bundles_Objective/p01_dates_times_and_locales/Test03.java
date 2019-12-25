package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p01_dates_times_and_locales;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Test03 {

    // =========================================================================================================================================
    // Formatting Output with DateTimeFormatter
    // Earlier we used DateTimeFormatter to specify a pattern when parsing a date string.
    // We can also use DateTimeFormatter when we display a datetime as a string.
    //
    static void test01() {
        ZonedDateTime totalityAustin = ZonedDateTime.of(2024, 4, 8, 13, 35, 56, 0, ZoneId.of("US/Central"));

        // The formatter specifies a format to use for formatting the datetime, using allowed letters and symbols
        // (see the DateTimeFormatter documentation for all the options)
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm");

        //
        System.out.println("Totality date/time written for sister in Europe: " + totalityAustin.format(formatter));

        // Alternatively, we could specify a format style and a locale:
        final DateTimeFormatter shortFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

        System.out.println("Totality date/time in UK Locale: " + totalityAustin.format(shortFormatter.withLocale(Locale.UK)));

        // For instance, of() methods create a new date from, typically, a sequence of numbers specifying the year, month, day, and so on.
        //
        // The parse() methods create a new date by parsing a string that’s either in a standard ISO format already or by using a formatter.
        //
        // The with() methods allow you to adjust a date with a TemporalAdjuster to make a new date. plusX() and minusX() methods create a new
        // datetime object from an existing one by adding and subtracting TemporalUnits or longs representing weeks, minutes, and so on.
    }

    // =========================================================================================================================================
    // Using Dates and Times with Locales
    // The Locale class is your ticket to understanding how to internationalize your code.
    static void test02() {

        final DateTimeFormatter shortFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

        // The API says a locale is “a specific geographical, political, or cultural region.”
        // The two Locale constructors you’ll need to understand for the exam are
        // Locale(String language)
        // Locale(String language, String country)
        //
        // If you want to represent basic Italian in your application, all you need is the Language code. If, on the other hand,
        // you want to represent the Italian used in Switzerland, you’d want to indicate that the country is Switzerland
        // (yes, the Country code for Switzerland is "CH" ), but that the language is Italian:
        Locale locIT = new Locale("it");
        Locale locCH = new Locale("it", "CH");

        LocalDate now = LocalDate.now();

        System.out.println(now.format(shortFormatter.withLocale(locIT)));
        System.out.println(now.format(shortFormatter.withLocale(locCH)));

        Locale myLocale = Locale.getDefault();

        System.out.println("My locale: " + myLocale);

        LocalDateTime aDateTime = LocalDateTime.of(2024, 4, 8, 13, 35, 56);

        final DateTimeFormatter mediumFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        final DateTimeFormatter longFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);

        System.out.println("The date and time: " + aDateTime.format(mediumFormatter));

        ZonedDateTime zDateTime = ZonedDateTime.of(aDateTime, ZoneId.of("US/Pacific"));

        Locale locPT = new Locale("pt"); // portugal
        Locale locBR = new Locale("pt", "BR"); // Brazil
        Locale locIN = new Locale("hi", "IN"); // India
        Locale locJA = new Locale("ja"); // Japan
        Locale locDK = new Locale("da", "DK"); // Denmark

        System.out.println("");
        System.out.println("Italy (Long) " + zDateTime.format(longFormatter.withLocale(locIT)));
        System.out.println("Italy (Short) " + zDateTime.format(shortFormatter.withLocale(locIT)));

        System.out.println("");
        System.out.println("Portugal (Long) " + zDateTime.format(longFormatter.withLocale(locPT)));
        System.out.println("Portugal (Short) " + zDateTime.format(shortFormatter.withLocale(locPT)));

        System.out.println("");
        System.out.println("Brazil (Long) " + zDateTime.format(longFormatter.withLocale(locBR)));
        System.out.println("Brazil (Short) " + zDateTime.format(shortFormatter.withLocale(locBR)));

        System.out.println("");
        System.out.println("India (Long) " + zDateTime.format(longFormatter.withLocale(locIN)));
        System.out.println("India (Short) " + zDateTime.format(shortFormatter.withLocale(locIN)));

        System.out.println("");
        System.out.println("Japan (Long) " + zDateTime.format(longFormatter.withLocale(locJA)));
        System.out.println("Japan (Short) " + zDateTime.format(shortFormatter.withLocale(locJA)));

        System.out.println("");
        System.out.println("Denmark (Long) " + zDateTime.format(longFormatter.withLocale(locDK)));
        System.out.println("Denmark (Short) " + zDateTime.format(shortFormatter.withLocale(locDK)));

        // There are a couple more methods in Locale ( getDisplayCountry() and getDisplayLanguage() )
        // that you need to know for the exam. These methods let you create strings that represent a given
        // locale’s country and language in terms of both the default locale and any other locale:

        System.out.println("");
        System.out.println("Denmark, country: " + locDK.getDisplayCountry());
        System.out.println("Denmark, country, local: " + locDK.getDisplayCountry(locDK));
        System.out.println("Denmark, country: " + locDK.getDisplayLanguage());
        System.out.println("Denmark, country, local: " + locDK.getDisplayLanguage(locDK));

        //
        System.out.println("");
        System.out.println("Brazil, country: " + locBR.getDisplayCountry());
        System.out.println("Brazil, country, local: " + locBR.getDisplayCountry(locBR));
        System.out.println("Brazil, country: " + locBR.getDisplayLanguage());
        System.out.println("Brazil, country, local: " + locBR.getDisplayLanguage(locBR));

        //
        System.out.println("");
        System.out.println("Switzerland, country: " + locCH.getDisplayCountry());
        System.out.println("Switzerland, country, local: " + locCH.getDisplayCountry(locCH));
        System.out.println("Switzerland, country: " + locCH.getDisplayLanguage());
        System.out.println("Switzerland, country, local: " + locCH.getDisplayLanguage(locCH));
    }

    // =========================================================================================================================================
    // Orchestrating Date- and Time-Related Classe
    static void test03() {

        final ZoneId zoneId = ZoneId.of("US/Pacific");

        // For instance, you need to know that if you’re creating a new ZonedDateTime from an existing
        // LocalDate and LocalTime , you need a ZoneId too; if you want to do date formatting for a
        // specific locale, you need to create your Locale object before your DateTimeFormatter object because you’ll need your
        // Locale object as an argument to your DateTimeFormatter method

        // LocalDate
        LocalDate ld01 = LocalDate.now();
        LocalDate ld02 = LocalDate.of(2017, 8, 21);
        LocalDate ld03 = LocalDate.parse("2017-08-21");
        //
        // LocalTime
        LocalTime lt01 = LocalTime.now();
        LocalTime lt02 = LocalTime.of(10, 19, 36);
        LocalTime lt03 = LocalTime.parse("10:19:36");
        //
        // LocalDateTime
        LocalDateTime ldt01 = LocalDateTime.now();
        LocalDateTime ldt02 = LocalDateTime.of(ld01, lt01);
        LocalDateTime ldt03 = LocalDateTime.parse("2017-04-08T10:19:36");
        LocalDateTime ldt04 = LocalDateTime.parse("2017-04-08T10:19:36", DateTimeFormatter.ISO_DATE_TIME);
        //
        // ZonedDateTime
        ZonedDateTime zdt01 = ZonedDateTime.now();
        ZonedDateTime zdt02 = ZonedDateTime.of(ldt01, zoneId);
        ZonedDateTime zdt03 = ZonedDateTime.parse("2017-04-08T10:19:36");
        //
        // OffsetDateTime
        OffsetDateTime osdt01 = OffsetDateTime.now();
        OffsetDateTime osdt02 = OffsetDateTime.of(ldt01, ZoneOffset.of("-05:00"));
        OffsetDateTime osdt03 = OffsetDateTime.parse("2017-04-08T10:19:36-05:00");
        //
        // DateTimeFormatter
        DateTimeFormatter dtf01 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dtf02 = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.CHINA);
        //
        // Instant
        Instant i01 = Instant.now();
        zdt01.toInstant();
        ldt01.toInstant(ZoneOffset.of("+5"));
        //
        // Duration
        Duration d01 = Duration.between(ld01, zdt03);
        Duration d02 = Duration.ofMinutes(5);
        //
        // Period
        Period p01 = Period.between(ld01, ld01);
        Period p02 = Period.ofDays(3);
        //
        // Locale
        Locale l01 = Locale.getDefault();
        Locale l02 = new Locale("pt");
        Locale l03 = new Locale("pt", "BR");
        //
        // Key adjustment Options amd Excamples (all methods create a new datetime object)
        //
        // LocalDate
        ld01.minusDays(3);
        ld02.plusWeeks(1);
        ld03.withYear(2018);
        //
        // LocalTime
        lt01.minus(3, ChronoUnit.MINUTES);
        lt02.plusMinutes(3);
        lt03.withHour(12);
        //
        // LocaDateTime
        ldt01.minusDays(3);
        ldt02.plusMinutes(10);
        ldt03.plus(Duration.ofMinutes(5));
        ldt04.withMonth(2);
        //
        // ZonedDateTime
        zdt01.withZoneSameInstant(ZoneId.of("US/Pacific"));
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test02();
    }
}
