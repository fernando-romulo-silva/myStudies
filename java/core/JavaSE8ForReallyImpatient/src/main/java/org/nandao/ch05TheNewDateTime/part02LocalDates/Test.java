package org.nandao.ch05TheNewDateTime.part02LocalDates;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

// API designers recommend that you do not use zoned time
// unless you really want to represent absolute time instances. Birthdays, holidays,
// schedule times, and so on are usually best represented as local dates or times.
// A LocalDate is a date, with a year, month, and day of the month

public class Test {

    public static void main(final String[] args) {
        final LocalDate today = LocalDate.now(); // Today’s date

        System.out.println("Today:" + today);

        LocalDate alonzosBirthday = LocalDate.of(1903, 6, 14);
        alonzosBirthday = LocalDate.of(1903, Month.JUNE, 14);

        System.out.println("Alonzos Birthday:" + alonzosBirthday);

        final LocalDate programmersDay = LocalDate.of(2014, 1, 1).plusDays(255);
        // September 13, but in a leap year it would be September 12

        System.out.println("Programmers Day:" + programmersDay);

        final LocalDate independenceDay = LocalDate.of(2017, 7, 4);
        final LocalDate christmas = LocalDate.of(2017, 12, 25);

        independenceDay.until(christmas, ChronoUnit.DAYS); // 174 days

        System.out.println("independence Day until christmas: " + independenceDay.until(christmas, ChronoUnit.DAYS)); // 174 days;

        // Recall that the difference between two time instants is a Duration. The
        // equivalent for local dates is a Period, which expresses a number of elapsed
        // years, months, or days. You can call birthday.plus(Period.ofYears(1)), to get the
        // birthday next year. Of course, you can also just call birthday.plusYears(1). But
        // birthday.plus(Duration.ofDays(365)) won’t produce the correct result in a leap year
        System.out.println("Alonzos Birthday plus 1 year:" + alonzosBirthday.plus(Period.ofYears(1)));

        // The getDayOfWeek yields the weekday, as a value of the DayOfWeek enumeration.
        // DayOfWeek.MONDAY has the numerical value 1, and DayOfWeek.SUNDAY has the value 7. For example:
        System.out.println("Day of week:" + LocalDate.of(1900, 1, 1).getDayOfWeek().getValue());

    }

}
