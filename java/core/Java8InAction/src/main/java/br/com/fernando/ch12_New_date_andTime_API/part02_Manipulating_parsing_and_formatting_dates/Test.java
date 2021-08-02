package br.com.fernando.ch12_New_date_andTime_API.part02_Manipulating_parsing_and_formatting_dates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

// Manipulating, parsing, and formatting dates
public class Test {

    // Manipulating the attributes of a LocalDate in an absolute way
    public static void test01() {

	// The most immediate and easiest way to create a modified version of an existing LocalDate is
	// changing one of its attributes, using one of its withAttribute methods. Note that all the methods
	// return a new object with the modified attribute, as shown in the following listing.

	// 2015-03-18
	final LocalDate date01 = LocalDate.of(2014, 3, 18);
	System.out.println(date01);

	// 2011-03-18
	final LocalDate date02 = date01.withYear(2011);
	System.out.println(date02);

	// 2011-03-25
	final LocalDate date03 = date02.withDayOfMonth(25);
	System.out.println(date03);

	// 2011-09-25
	final LocalDate date04 = date01.with(ChronoField.MONTH_OF_YEAR, 9);
	System.out.println(date04);

	// These methods allow you to move a Temporal back or forward a given amount of time, defined by a number
	// plus a Temporal-Unit, where the ChronoUnit enumeration
	// offers a convenient implementation of the TemporalUnit interface.

	// 2014-03-18
	final LocalDate date05 = date01.plusWeeks(1);
	System.out.println(date05);

	// 2011-03-18
	final LocalDate date06 = date05.minusYears(3);
	System.out.println(date06);

	// 2011-09-25
	final LocalDate date07 = date06.plus(6, ChronoUnit.MONTHS);
	System.out.println(date07);

	// What will the value of the date variable be after the following manipulations?
	LocalDate date = LocalDate.of(2014, 3, 18);
	date = date.with(ChronoField.MONTH_OF_YEAR, 9);
	date = date.plusYears(2).minusDays(10);
	date.withYear(2011);
	// Answer: 2016-09-08
    }

    // Working with TemporalAdjusters
    public static void test02() {

	// As you can see, TemporalAdjusters allow you to perform more complex date manipulations that
	// still read like the problem statement. Moreover, it’s relatively simple to create your own custom
	// TemporalAdjuster implementation if you can’t find a predefined TemporalAdjuster that fits your need.
	//
	// 2014-03-18
	final LocalDate date01 = LocalDate.of(2014, 3, 18);
	System.out.println(date01);

	// 2014-03-23
	final LocalDate date02 = date01.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
	System.out.println(date02);

	// 2014-03-31
	final LocalDate date03 = date02.with(TemporalAdjusters.lastDayOfMonth());
	System.out.println(date03);

	// Develop a class named NextWorkingDay, implementing the TemporalAdjuster interface that
	// moves a date forward by one day but skips Saturdays and Sundays. Doing the following

	final LocalDate date04 = date01.with(new NextWorkingDay());
	System.out.println(date04);

	// TemporalAdjuster is a functional interface, you could just pass the behavior of this adjuster in a
	// lambda expression:

	final LocalDate date05 = date01.with(temporal -> {

	    final DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

	    int dayToAdd = 1;

	    if (dow == DayOfWeek.FRIDAY) {
		dayToAdd = 3;
	    } else if (dow == DayOfWeek.SATURDAY) {
		dayToAdd = 2;
	    }

	    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
	});

	System.out.println(date05);

	// If you want to define the TemporalAdjuster with a lambda expression, it’s preferable to do it
	// using the ofDateAdjuster static factory of the TemporalAdjusters class that accepts a
	// UnaryOperator<LocalDate> as follows:

	final TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(temporal -> {

	    final DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

	    int dayToAdd = 1;

	    if (dow == DayOfWeek.FRIDAY) {
		dayToAdd = 3;
	    } else if (dow == DayOfWeek.SATURDAY) {
		dayToAdd = 2;
	    }

	    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
	});

	final LocalDate date06 = date01.with(nextWorkingDay);

	System.out.println(date06);
    }

    // This TemporalAdjuster normally moves a date forward one day, except if today is a Friday or
    // Saturday, in which case it advances the dates three or two days, respectively.
    private static class NextWorkingDay implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
	    final DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK)); // Read the current day.

	    int dayToAdd = 1; // Normally add one day.

	    if (dow == DayOfWeek.FRIDAY) { // But add threee days if today is a Friday.
		dayToAdd = 3;
	    }

	    if (dow == DayOfWeek.SATURDAY) { // Or tow if it's a Saturday.
		dayToAdd = 2;
	    }

	    // Return the modified date adding the right number of days.
	    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
	}
    }

    // Printing and parsing date-time objects
    public static void test03() {
	// The easiest way to create a formatter is through its static factory
	// methods and constants. The constants such as BASIC_ISO_DATE and ISO_LOCAL_DATE are
	// just predefined instances of the DateTimeFormatter class.

	final LocalDate date01 = LocalDate.of(2014, 3, 18);
	System.out.println(date01);

	final String s1 = date01.format(DateTimeFormatter.BASIC_ISO_DATE); // 20140318
	System.out.println(s1);

	final String s2 = date01.format(DateTimeFormatter.ISO_LOCAL_DATE);// 2014-03-18
	System.out.println(s2);

	// The DateTimeFormatter class also supports a static factory method that lets you create a formatter
	// from a specific pattern, as shown in the next listing.

	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	final String formattedDate = date01.format(formatter);
	System.out.println(formattedDate);

	final LocalDate date2 = LocalDate.parse(formattedDate, formatter);
	System.out.println(date2);

	// Here the LocalDate’s format method produces a String representing the date with the requested
	// pattern. Next, the static parse method re-creates the same date by parsing the generated String
	// using the same formatter. The ofPattern method also has an overloaded version allowing you to
	// create a formatter for a given Locale, as shown in the following listing.

	final DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
	final String formattedDate2 = date01.format(italianFormatter); // 18. marzo 2014
	System.out.println(formattedDate2);

	final LocalDate date3 = LocalDate.parse(formattedDate2, italianFormatter);
	System.out.println(date3);

	// Finally, in case you need even more control, the DateTimeFormatterBuilder class lets you define
	// complex formatters step by step using meaningful methods. In addition, it provides you with the
	// ability to have case-insensitive parsing, lenient parsing (allowing the parser to use heuristics to
	// interpret inputs that don’t precisely match the specified format), padding, and optional sections
	// of the formatter

	final DateTimeFormatter italianFormatter2 = new DateTimeFormatterBuilder() //
	    .appendText(ChronoField.DAY_OF_MONTH) //
	    .appendLiteral(". ") //
	    .appendText(ChronoField.MONTH_OF_YEAR) //
	    .appendLiteral(" ") //
	    .appendText(ChronoField.YEAR) //
	    .parseCaseInsensitive() //
	    .toFormatter(Locale.ITALIAN);

	System.out.println(italianFormatter2);
    }

    // ==================================================================================================== //
    public static void main(String[] args) {
	test03();
    }

}
