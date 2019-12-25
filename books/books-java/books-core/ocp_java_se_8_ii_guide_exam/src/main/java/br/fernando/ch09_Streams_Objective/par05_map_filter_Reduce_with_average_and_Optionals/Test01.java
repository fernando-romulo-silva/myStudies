package br.fernando.ch09_Streams_Objective.par05_map_filter_Reduce_with_average_and_Optionals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@SuppressWarnings("unused")
public class Test01 {

    static final List<Reading> readings = Arrays.asList(//
            new Reading(2017, 1, 1, 405.91), //
            new Reading(2017, 1, 8, 405.98), //
            new Reading(2017, 1, 15, 406.14), //
            new Reading(2017, 1, 22, 406.48), //
            new Reading(2017, 1, 29, 406.20), //
            new Reading(2017, 2, 5, 407.12), //
            new Reading(2017, 2, 12, 406.03) //
    );

    // =========================================================================================================================================
    // Map
    static void test01_01() {

        // As we said earlier, map() takes a Function map() applies that Function on each value in the stream.
        // So by calling mapToDouble() , we’ve converted the initial Stream of Reading objects ( Stream<Reading> ) into a stream of doubles ,
        // and that stream has the type DoubleStream .

        OptionalDouble average = readings.stream() //
                .mapToDouble(r -> r.value) //
                .filter(v -> v >= 406.0 && v < 407.00) //
                .average(); //

        // We’ve converted the initial Stream of Reading objects ( Stream<Reading> ) into a stream of doubles , and that stream has the type DoubleStream
        // The capitalization on these types can be very confusing. Don’t mix up a DoubleStream with a Stream<Double> here, or forget that ToDoubleFunction ’s
        // applyAsDouble() method returns a double .
        //
        // Note again that at this point in the computation, nothing has actually happened. The stream is lazy!
        //
        // The reduction operation we are going to use is the average() method. But if you look for average() in the Stream interface, you will not find it.
        // That’s because average() is a method of the DoubleStream interface (and its primitive cousins,IntStream and LongStream).

        // If you do try to call getAsDouble() on an empty OptionalDouble , you’ll see a NoSuchElementException

        if (average.isPresent()) {
            System.out.println("Average of 406 readings: " + average.getAsDouble());
        } else {
            System.out.println("Empty Optional");
        }
    }

    static class Reading {

        final int year;

        final int month;

        final int day;

        final double value;

        public Reading(int year, int month, int day, double value) {
            super();
            this.year = year;
            this.month = month;
            this.day = day;
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    // =========================================================================================================================================
    // Reduce
    static void test01_02() {

        // You’ve seen a couple of reductions so far: count() and average() .
        // The count() method is defined for both Stream and the primitive versions of Stream : IntStream ,
        // LongStream , and DoubleStream , while average() is defined only for the primitive streams.
        // Other handy methods defined on the primitive streams include min() , max() , and sum()

        OptionalDouble max = readings.stream() //
                .mapToDouble(r -> r.value) //
                .max();

        if (max.isPresent()) {
            System.out.println("Max of all readings: " + max.getAsDouble());
        } else {
            System.out.println("Empty optional");
        }

        final List<Reading> emptyReadings = new ArrayList<>(); // empty List

        // Why do average() , max() , and min() return optionals, but sum() does not?
        double sum = emptyReadings.stream() //
                .mapToDouble(r -> r.value) //
                .sum();

        System.out.println("Sum of all readings: " + sum);

        // By default, the sum of an empty stream is 0.
    }

    // =========================================================================================================================================
    // Using Reduce
    static void test01_03() {
        // What is sum() doing to “reduce” a DoubleStream of double values into one value?
        // It’s taking values as they flow from the stream and adding them up, so that’s what we need to do in reduce() if we want
        // to create our own sum reduction method.

        OptionalDouble sum01 = readings.stream() //
                .mapToDouble(r -> r.value) //
                .reduce((v1, v2) -> v1 + v2);

        if (sum01.isPresent()) {
            System.out.println("Sum of all readings: " + sum01.getAsDouble());
        }

        // Again, think of reductions as accumulators: they accumulate values from the stream so they can compute one value.
        // It’s as if the final station in the assembly line is putting all the values from the stream into one big box in order
        // to do the terminal operation on them.
        //
        // If you provide the identity value, you’re providing an initial value for the result of applying the accumulator function, op .

        final double sum02 = readings.stream() //
                .mapToDouble(Reading::getValue) //
                .reduce(0.0, (v1, v2) -> v1 + v2); // provide an identity

        System.out.println("Sum of all readings: " + sum02); // print 0.0 if stream is empty
    }

    // =========================================================================================================================================
    // Associative Accumulations
    static void test01_04() {

        // Reduction operations must be associative in order to work correctly with streams.
        // That’s why you can’t define your own average function with reduce() ; average is not
        // associative. So to take the average of a stream, you must use the average() method.

        OptionalDouble avgWithReduce01 = readings.stream() // stream the readings
                .mapToDouble(r -> r.value) // map to double values
                .filter(v -> v >= 406.0 && v < 407.00) // filter 406 values
                .reduce((v1, v2) -> (v1 + v2) / 2); // take the average

        if (avgWithReduce01.isPresent()) {
            System.out.println("Average01 of 406 readings: " + avgWithReduce01.getAsDouble());
        } else {
            System.out.println("Empty optional!");
        }

        OptionalDouble avgWithReduce02 = readings.stream() // stream the readings
                .mapToDouble(r -> r.value) // map to double values
                .filter(v -> v >= 406.0 && v < 407.00) // filter 406 values
                .average(); // take the average

        if (avgWithReduce02.isPresent()) {
            System.out.println("Average02 of 406 readings: " + avgWithReduce02.getAsDouble());
        } else {
            System.out.println("Empty optional!");
        }
    }

    // =========================================================================================================================================
    // map-filter-reduce Methods
    static void test01_05() {
        // The key idea to remember with map-filter-reduce is the purpose of each: map()
        // maps values to modify the type or create a new value from the existing value, but
        // without changing the number of elements in the stream you’re working with; filter()
        // potentially winnows the values you’re working with, depending on the result of the
        // Predicate test; and reduce() (and its equivalents) changes the stream of values to one
        // value (or a collection of values) as a terminal operation.
    }

    // =========================================================================================================================================
    static void summary() {
        // Map - Convert to another value
        OptionalDouble average01 = readings.stream() //
                .mapToDouble(r -> r.value) // convert Readings stream to DoubleStream
                .filter(v -> v >= 406.0 && v < 407.00) // olny elements with value >= 406.0  and < 407.00 
                .average(); // average (sum) is a reduce method only in "numbers Streams" like IntStream, LongStream, DoubleStream, etc.  

        // Reduce - Reduce to a value, need of an accumulator
        OptionalDouble sum01 = readings.stream() //
                .mapToDouble(r -> r.value) //
                .reduce((v1, v2) -> v1 + v2); // You can do your own reduce function

        final double sum02 = readings.stream() //
                .mapToDouble(Reading::getValue) //
                .reduce(0.0, (v1, v2) -> v1 + v2); // Reduce functions have the initial accumulators

        // The reduce functions need to be associative, the order of elements don't change the final value
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_04();
    }
}
