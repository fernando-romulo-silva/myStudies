package br.fernando.ch09_Streams_Objective.par02_How_to_create_pipeline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Test01 {

    // =========================================================================================================================================
    // Create a Stream from a Collection
    static void test01_01() {
        final List<Double> tempsInPhoenix = Arrays.asList(123.5, 118.0, 113.0, 112.5, 115.8, 117.0, 110.2, 110.1, 106.4);

        final long qtDays01 = tempsInPhoenix //
                .stream() // Stream the List of Doubles, This method is a default method of the Collection interface and so is inherited by all classes that implement Collection
                .filter(t -> t > 110.0) // filter the stream
                .count(); // count the Doubles that pass the filter test

        System.out.println("Number of days over 110 in 10 day period: " + qtDays01);

        // Don’t forget that a Map ( HashMap , TreeMap , etc.) is not a collection inheriting from
        // Collection . If you want to stream a Map , you must first use the entrySet() method to
        // turn the Map into a Set , which is a Collection type:

        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("Boi", 6);
        myMap.put("Zooey", 3);
        myMap.put("Charis", 8);

        long count = myMap // The map
                .entrySet() // get a Set of Map.Entry objects
                .stream() // stream the Set
                .filter(d -> d.getValue() > 4) // filter the Map.Entry objects
                .count(); // count the objects

        System.out.println("Number of items in the map with value > 4: " + count);
    }

    // =========================================================================================================================================
    // Build a Stream with Stream.of()
    static void test01_02() {
        // We can use Stream.of() to create a stream from our array of Integers, myNums , like this:
        Integer[] myNums = { 1, 2, 3 };

        Stream<Integer> myStream01 = Stream.of(myNums);

        // We can make that code even shorter by skipping declaring the array altogether and supply Integer values directly, like this:
        Stream<Integer> myStream02 = Stream.of(1, 2, 3);
    }

    // =========================================================================================================================================
    // Create a Stream from an Array
    static void test01_03() {
        // Here’s another example of streaming an array, this time, an array of Strings:

        final String[] dogs = { "Boi", "Zooey", "Charis" }; // make an array

        final Stream<String> dogStream = Arrays.stream(dogs); // stream it

        System.out.println("Number of dogs in array: " + dogStream.count()); // count it
    }

    // =========================================================================================================================================
    // Create a Stream from a File
    static void test01_04() {
        // The forEach() method on a stream works much like the forEach() method on a
        // collection. It takes a Consumer , which we can represent with a lambda expression, and
        // processes each line from the file in the body of the lambda.

        try (final Stream<String> stream = Files.lines(Paths.get("words"))) {

            stream.forEach(d -> System.out.println(d));

        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    // =========================================================================================================================================
    // Primitive Value Streams
    static void test01_05() {

        // As you might expect, there are also primitive streams designed to avoid autoboxing, for double s, int s, and long s.
        // These are DoubleStream , IntStream , and LongStream , respectively. So, you can create a DoubleStream like this:

        DoubleStream s3 = DoubleStream.of(406.13, 406.42, 407.18, 409.01);

        // Keep in mind the difference between a Stream<Double> and DoubleStream.
        // The first is a stream of Double objects; the second is a stream of double values.
    }

    // =========================================================================================================================================
    // Why Streams?
    static void test01_06() {
        // The main reason to use streams is when you start doing multiple intermediate
        // operations. So far, we’ve been performing only one intermediate operation: a filter,
        // using a variety of different Predicates to filter the data we get from the stream before
        // we count it. However, when we use multiple intermediate operations, we start seeing the benefits of streams.

        final List<String> names = Arrays.asList("Boi", "Charis", "Zooey", "Bokeh", "Clover", "Aiko");

        names.stream() // create the stream
                .filter(s -> s.startsWith("B") || s.startsWith("C")) // filter by first letter
                .filter(s -> s.length() > 3) // Filter by length
                .forEach(System.out::println); // print
    }

    // =========================================================================================================================================
    static void summary() {
        final List<Double> tempsInPhoenix = Arrays.asList(123.5, 118.0, 113.0, 112.5, 115.8, 117.0, 110.2, 110.1, 106.4);

        // create a stream from a Collection
        final long qtDays01 = tempsInPhoenix //
                .stream() //
                .filter(t -> t > 110.0).count();

        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("Boi", 6);
        myMap.put("Zooey", 3);
        myMap.put("Charis", 8);

        // Map don't extends Collections, so you must first use the entrySet()

        myMap.entrySet().stream() //
                .filter(d -> d.getValue() > 4) // filter the Map.Entry objects
                .count(); // count the objects

        // you can create a stream from Arrays
        Integer[] myNums = { 1, 2, 3 };

        Stream.of(myNums);
        // or
        Stream.of(2, 4, 5, 6); // var args

        // you can create from file

        try (final Stream<String> stream = Files.lines(Paths.get("words"))) {

            stream.forEach(d -> System.out.println(d));

        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }

        // Primitive Value Streams

        IntStream s1 = IntStream.of(4, 5, 52, 57);
        LongStream s2 = LongStream.of(545l, 656l, 44l, 5);
        DoubleStream s3 = DoubleStream.of(406.13, 406.42, 407.18, 409.01);
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_06();
    }
}
