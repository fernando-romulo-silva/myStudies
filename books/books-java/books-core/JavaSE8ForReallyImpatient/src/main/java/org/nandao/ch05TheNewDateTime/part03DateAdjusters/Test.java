package org.nandao.ch05TheNewDateTime.part03DateAdjusters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

// For scheduling applications, you often need to compute dates such as “the first
// Tuesday of every month.” The TemporalAdjusters class provides a number of static
// methods for common adjustments. You pass the result of an adjustment method
// to the with method. For example, the first Tuesday of a month can be computed

public class Test {

    public static void main(final String[] args) {

        final int year = 2017;

        final int month = 5;

        final LocalDate firstTuesday = LocalDate.of(year, month, 1) //
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));

        System.out.println("firstTuesday:" + firstTuesday);

        // You can also roll your own adjuster by implementing the TemporalAdjuster interface.
        // Here is an adjuster for computing the next weekday.
        final TemporalAdjuster NEXT_WORKDAY = w -> {

            /// Note that the parameter of the lambda expression has type Temporal, and it must be cast to LocalDate. 
            LocalDate result = (LocalDate) w;

            do {

                result = result.plusDays(1);

            } while (result.getDayOfWeek().getValue() >= 6);

            return result;
        };

        final LocalDate today = LocalDate.now();

        final LocalDate backToWork = today.with(NEXT_WORKDAY);

        System.out.println("backToWork:" + backToWork);

        final TemporalAdjuster NEXT_WORKDAY2 = TemporalAdjusters.ofDateAdjuster(w -> {

            LocalDate result = w; // No cast
            do {

                result = result.plusDays(1);

            } while (result.getDayOfWeek().getValue() >= 6);

            return result;
        });

        final LocalDate backToWork2 = today.with(NEXT_WORKDAY2);

        System.out.println("backToWork2:" + backToWork2);

    }

}
