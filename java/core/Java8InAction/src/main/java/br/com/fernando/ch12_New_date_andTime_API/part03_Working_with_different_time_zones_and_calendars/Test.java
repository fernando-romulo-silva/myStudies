package br.com.fernando.ch12_New_date_andTime_API.part03_Working_with_different_time_zones_and_calendars;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.TimeZone;

// Working with different time zones and calendars
public class Test {

    // Applying a time zone to a point in time
    public static void test00() {
	// A time zone is a set of rules corresponding to a region in which the standard time is the same.
	// There are about 40 of them held in instances of the ZoneRules class. You can simply call
	// getRules() on a ZoneId to obtain the rules for that given time zone

	final ZoneId romeZone = ZoneId.of("Europe/Rome");

	// The region IDs are all in the format “{area}/{city}” and the set of available locations is the one
	// supplied by the IANA Time Zone Database. You can also convert an old TimeZone object to a
	// ZoneId by using the new method toZoneId:

	final ZoneId zoneId = TimeZone.getDefault().toZoneId();

	final LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
	final ZonedDateTime zdt1 = date.atStartOfDay(zoneId);
	System.out.println(zdt1);

	final LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
	final ZonedDateTime zdt2 = dateTime.atZone(romeZone);
	System.out.println(zdt2);

	final Instant instant = Instant.now();
	final ZonedDateTime zdt3 = instant.atZone(romeZone);
	System.out.println(zdt3);
    }

    // Fixed offset from UTC/Greenwich
    public static void test01() {

	// Greenwich, for nstance, you can use this notation to say that “New York is five hours behind London."
	// The -05:00 offset indeed corresponds to the US Eastern Standard Time.

	final ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
	System.out.println(newYorkOffset);
    }

    // Using alternative calendar systems
    public static void test02() {
	// You can create an instance of one of these classes out of a LocalDate. More
	// generally, you can create any other Temporal instance using their from static factory

	final LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
	final JapaneseDate japaneseDate = JapaneseDate.from(date);
	System.out.println(japaneseDate);

	// The designers of the Date and Time API advise using LocalDate instead of Chrono-LocalDate for
	// most cases; this is because a developer could make assumptions in their code that unfortunately
	// aren’t true in a multicalendar system

	final Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
	final ChronoLocalDate now = japaneseChronology.dateNow();

	System.out.println(now);

	// Islamic calendar
	// Out of the new calendars added to Java 8, the HijrahDate (Islamic calendar) seems to be the
	// most complex because it can have variants

	// Get current Hijrah date; then change it to have the first day of Ramadan, which is the 9th month.
	final HijrahDate ramadanDAte = HijrahDate.now() //
	    .with(ChronoField.DAY_OF_MONTH, 1) //
	    .with(ChronoField.MONTH_OF_YEAR, 9);

	final StringBuilder sb = new StringBuilder();

	sb.append("Ramadan starts on ") //
	    .append(IsoChronology.INSTANCE.date(ramadanDAte))// IsoChronology.INSTANCE is a static instance of the IsoChronology class.
	    .append(" and ends on ") //
	    .append(IsoChronology.INSTANCE.date( // Ramanda starts on 2014-06-28 and ends on 2014-07-27
	                                         ramadanDAte.with(TemporalAdjusters.lastDayOfMonth())));

	System.out.println(sb);
    }

    // ==================================================================================
    public static void main(String[] args) {
	test00();
    }

}
