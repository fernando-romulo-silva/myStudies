package br.fernando.ch09_Streams_Objective.par04_Operating_on_stream;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Test01 {

    // =========================================================================================================================================
    //
    static void test01_01() {
        // “map-filter-reduce” : It perfectly describes what we are often doing with streams: we might “map” an input to a slightly different
        // output, then “filter” that output by some criteria, and finally “reduce” to a single value or to a printed output.
        //
        // You’ve already seen the Java Stream ’s filter() method. As we said earlier, filter() takes a Predicate and tests each element in
        // the stream pipeline, producing a stream of elements that pass the test.
        //
        // The Stream ’s map() method is another intermediate operation; however, unlike filter() , map() doesn’t winnow down the elements
        // of a stream; rather, map() transforms elements of a stream
        //
        // Reduce is both a general method, reduce() , as well as a specific method like count() and others you’ll see shortly.
        // All reductions are also terminal operations. Reduction operations are designed to combine multiple inputs into one
        // summary result
        //
        // map-filter-reduce on java 7

        final List<Integer> num01 = Arrays.asList(1, 2, 3, 4, 5, 6);

        long result01 = 0;

        for (Integer n : num01) {
            int square = n * n;

            if (square > 20) {
                result01 = result01 + 1;
                System.out.println("Square of " + n + " is: " + square);
            }
        }

        System.out.println("Result: " + result01);

        final List<Integer> nums02 = Arrays.asList(1, 2, 3, 4, 5, 6);

        long result02 = nums02.stream() //
                // .peek(n -> System.out.println("Number is: " + n + ", ")) // print the number
                .map(n -> n * n) // map values in stream to squares
                .filter(n -> n > 20) // keep only squares > 20 (filter intermediate op)
                .peek(n -> System.out.println("Square is: " + n + ", ")) // print the square
                .count(); // count the squares > 20 (reduction op)

        System.out.println("Result (stream): " + result02);

        // The method peek() is an intermediate operation that allows you to “peek” into the
        // stream as the elements flow by. It takes a Consumer and produces the same exact
        // stream as it’s called on, so it doesn’t change the values or filter them in any way.
    }

    // =========================================================================================================================================
    static void summary() {
        final List<Integer> nums02 = Arrays.asList(1, 2, 3, 4, 5, 6);

        // “map-filter-reduce” it's the stream do

        long result02 = nums02.stream() //
                .peek(n -> System.out.println("Number is: " + n + ", ")) // print the number, intermediate operation
                .map(n -> n * n) // map values in stream to squares, return a stream of Integer, intermediate operation
                .filter(n -> n > 20) // keep only squares > 20 (filter intermediate op)
                .peek(n -> System.out.println("Square is: " + n + ", ")) // print the square
                .count(); // count the squares > 20 (reduction op), terminal operation

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
