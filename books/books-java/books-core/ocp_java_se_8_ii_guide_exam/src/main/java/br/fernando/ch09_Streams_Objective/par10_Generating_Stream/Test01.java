package br.fernando.ch09_Streams_Objective.par10_Generating_Stream;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test01 {

    // =========================================================================================================================================
    // Collections, Streams, and Filters.
    static void test01_01() {
        // The iterate() operation creates an infinite sequential Stream , starting with the
        // first argument (known as the “seed“) followed by elements created by the
        // UnaryOperator that you supply as the second argument.

        Stream //
                .iterate(0, s -> s + 1) // create an infinite stream
                .forEach(System.out::println); // don't try this at home!

        Stream //
                .iterate(0, s -> s + 1) // create an infinite stream ...
                .limit(4) // but limit it to 4 things
                .forEach(System.out::println); // print the 4 things

        // To work safely with infinite streams, you need a short-circuiting operation. That
        // could be limit() , like we used above, or it can also be an operation like findFirst() ,
        // findAny() , or anyMatch()
        // ----------------------------------------------------------------------------------

        Sensor s = new Sensor();

        Optional<String> result = Stream.generate(() -> s.next()) //
                .filter(v -> v.equals("down")) // filter to get all down values
                .findFirst(); // find the first down value and stop

        result.ifPresent(System.out::println);

        // The findFirst() method is a short-circuiting method, so as soon as we find a
        // “down” value in the stream, everything stops

        // ------------------------------------------------------------------------------------
        // Although Stream.iterate() is a good way to generate numbers up to a certain
        // point, another way you can generate numbers is with range() . In practice, this might
        // actually be more useful. This is a method on the primitive streams IntStream and
        // LongStream
        IntStream numStream = IntStream.range(10, 15); // generating numbers 10 .. 14
        numStream.forEach(System.out::println);

        // If you want a stream of numbers inclusive of the second argument, use rangeClosed()

        IntStream numStream02 = IntStream.rangeClosed(10, 15); // generating numbers 10 .. 15
        numStream02.forEach(System.out::println);

        // As you saw, the limit() method allows you to limit the number of elements in a stream,
        // so you can, for instance, limit a stream to the first five even numbers in a stream:

        IntStream evensBefore10 = IntStream.rangeClosed(0, 20) // generate numbers 0 .. 20
                .filter(n -> n % 2 == 0) // filter evens
                .limit(5); // limit to 5 results

        evensBefore10.forEach(System.out::println); // 0 2 4 6 8

        // What if you want to skip the first five items instead and print only the even
        // numbers between 10 and 20?
        IntStream evensAfter10 = IntStream.rangeClosed(0, 20) // gnereate numbers 0 .. 20
                .filter(n -> n % 2 == 0) // filter evens
                .skip(5); // skip first 5 results

        evensAfter10.forEach(System.out::println); // 10 12 14 16 18 20
    }

    static class Sensor {

        String value = "up";

        int i = 0;

        public String next() {
            i = +1;
            return i > 10 ? "down" : "up";
        }
    }

    // =========================================================================================================================================
    static void summary() {
        // *****************************************************************************************************************
        // iterate Method -
        // *****************************************************************************************************************

        Stream //
                .iterate(0, s -> s + 1) // create an infinite stream, with a unary operator
                .limit(4) // but limit it to 4 things, because it going to infinite...
                .forEach(System.out::println); // print the 4 things

        // *****************************************************************************************************************
        // generate method
        // *****************************************************************************************************************
        Sensor s = new Sensor();

        Optional<String> result = Stream //
                .generate(() -> s.next()) //
                .filter(v -> v.equals("down")) // filter to get all down values
                .findFirst(); // find the first down value and stop

        result.ifPresent(System.out::println);

        // *****************************************************************************************************************
        // range method
        // *****************************************************************************************************************
        IntStream numStream = IntStream.range(10, 15); // generating numbers 10 .. 14
        numStream.forEach(System.out::println);

        IntStream numStream02 = IntStream.rangeClosed(10, 15); // generating numbers 10 .. 15
        numStream02.forEach(System.out::println);

        IntStream evensBefore10 = IntStream.rangeClosed(0, 20) // generate numbers 0 .. 20
                .filter(n -> n % 2 == 0) // filter evens
                .limit(5); // limit to 5 results

        evensBefore10.forEach(System.out::println); // 0 2 4 6 8

        IntStream evensAfter10 = IntStream.rangeClosed(0, 20) // gnereate numbers 0 .. 20
                .filter(n -> n % 2 == 0) // filter evens
                .skip(5); // skip first 5 results

        evensAfter10.forEach(System.out::println); // 10 12 14 16 18 20
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
