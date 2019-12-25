package br.com.fernando.ch12_New_date_andTime_API.part01_LocalDate_LocalTime_Instant_Duration_and_Period;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

// LocalDate, LocalTime, Instant, Duration, and Period
public class Test {

    // Working with LocalDate and LocalTime
    public static void test01() {

	// An instance of this class is an immutable object representing just a plain
	// date without the time of day. In particular, it doesn’t carry any information about the time zone.

	final LocalDate date1 = LocalDate.of(2014, 3, 18); // 2014-03-18

	final int year1 = date1.getYear(); // 2014
	System.out.println(year1);

	final Month month1 = date1.getMonth(); // March
	System.out.println(month1);

	final int day = date1.getDayOfMonth(); // 18
	System.out.println(day);

	final DayOfWeek dow = date1.getDayOfWeek(); // Tuesday
	System.out.println(dow);

	final int len1 = date1.lengthOfMonth(); // 31 (days of in March)
	System.out.println(len1);

	final boolean leap = date1.isLeapYear(); // false (not a leap year)
	System.out.println(leap);

	// It's also possible to obtains the current date from the system clock using the now factory method:
	final LocalDate today1 = LocalDate.now();
	System.out.println(today1);

	// The ChronoField enumeration implements this interface, so you can conveniently use an element of that
	// enumeration with the get method, as shown in the next listing
	final int year2 = date1.get(ChronoField.YEAR);
	final int month2 = date1.get(ChronoField.MONTH_OF_YEAR);
	final int day2 = date1.get(ChronoField.DAY_OF_MONTH);

	System.out.println(year2);
	System.out.println(month2);
	System.out.println(day2);

	// The first one accepts an hour and a minute and the second one also accepts a second. Just like the
	// LocalDate class, the LocalTime class provides some getter methods to access its values, as shown in the following listing.

	final LocalTime time1 = LocalTime.of(13, 45, 20);

	final int hour1 = time1.getHour(); // 13
	final int minute1 = time1.getMinute(); // 45
	final int second1 = time1.getSecond(); // 20

	System.out.println(hour1);
	System.out.println(minute1);
	System.out.println(second1);

	// Both LocalDate and LocalTime can be created by parsing a String representing them.
	// You can achieve this using their parse static methods:
	final LocalDate date3 = LocalDate.parse("2014-03-18");
	final LocalTime time3 = LocalTime.parse("13:45:20");

	System.out.println(date3);
	System.out.println(time3);
    }

    // Combining a date and a time
    public static void test02() {

	final LocalDate date1 = LocalDate.of(2014, 3, 18); // 2014-03-18
	final LocalTime time1 = LocalTime.of(13, 45, 20);

	final LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
	System.out.println(dt1);

	final LocalDateTime dt2 = LocalDateTime.of(date1, time1);
	System.out.println(dt2);

	final LocalDateTime dt3 = date1.atTime(13, 45, 20);
	System.out.println(dt3);

	final LocalDateTime dt4 = date1.atTime(time1);
	System.out.println(dt4);

	final LocalDateTime dt5 = time1.atDate(date1);
	System.out.println(dt5);

	// Note that it’s possible to create a LocalDateTime by passing a time to a LocalDate, or conversely
	// a date to a LocalTime, using respectively their atTime or atDate methods. You can also extract
	// the LocalDate or LocalTime component from a LocalDateTime using the toLocalDate and
	// toLocalTime methods:

	final LocalDate date2 = dt1.toLocalDate(); // 2014-03-18
	System.out.println(date2);

	final LocalTime time2 = dt1.toLocalTime(); // 13:45:20
	System.out.println(time2);
    }

    // Instant: a date and time for machines
    public static void test03() {

	// From a machine point of view, the most natural format to model time is with a single large number representing a point
	// on a continuous timeline.

	Instant.ofEpochSecond(3);
	Instant.ofEpochSecond(3, 0);

	// There’s a supplementary overloaded version of the ofEpochSecond static factory method that accepts a
	// second argument that’s a nanosecond adjustment to the passed number of seconds. This overloaded version
	// adjusts the nanosecond argument, ensuring that the stored nanosecond fraction is between 0 and 999,999,999.
	// This means all the following invocations of the ofEpochSecond factory method will return exactly the same Instant:

	Instant.ofEpochSecond(2, 1_000_000_000); // One billion nanoseconds (1 second) after 2 seconds
	Instant.ofEpochSecond(4, -1_000_000_000); // One billion nanosecods (1 second) before 4 seconds
    }

    // Defining a Duration or a Period
    public static void test04() {

	// The next natural step is to create a duration between two temporal objects. The between static factory
	// method of the Duration class serves exactly this purpose. You can create a duration between two LocalTimes,
	// two LocalDateTimes, or two Instants as follows:

	final LocalTime time1 = LocalTime.of(13, 45, 20);
	final LocalTime time2 = LocalTime.of(14, 15, 10);

	final LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
	final LocalDateTime dt2 = LocalDateTime.of(2018, Month.MARCH, 15, 19, 00, 00);

	final Instant instant1 = Instant.now();

	try {
	    Thread.sleep(1000);
	} catch (final InterruptedException e) {

	}

	final Instant instant2 = Instant.now();

	final Duration d1 = Duration.between(time1, time2);
	System.out.println(d1);

	final Duration d2 = Duration.between(dt1, dt2);
	System.out.println(d2);

	final Duration d3 = Duration.between(instant1, instant2);
	System.out.println(d3);

	// When you need to model an amount of time in terms of years, months, and days, you can use
	// the Period class. You can find out the difference between two LocalDates with the between
	// factory method of that class:

	final Period tenDays1 = Period.between(LocalDate.of(2014, 3, 8), //
	                                       LocalDate.of(2014, 3, 18));

	System.out.println(tenDays1);

	// The Duration and Period classes have other convenient factory methods to create
	// instances of them directly, in other words, without defining them as the difference between two
	// temporal objects, as shown in the next listing
	final Duration threeMinutes1 = Duration.ofMinutes(3);
	System.out.println(threeMinutes1);

	final Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
	System.out.println(threeMinutes2);

	final Period tenDays2 = Period.ofDays(10);
	System.out.println(tenDays2);

	final Period threeWeeks1 = Period.ofWeeks(3);
	System.out.println(threeWeeks1);

	final Period twoYearsSixMonthsOneDay1 = Period.of(2, 6, 1);
	System.out.println(twoYearsSixMonthsOneDay1);
    }

    // ======================================================================================================== //
    public static void main(String[] args) {
	test01();
    }
}
