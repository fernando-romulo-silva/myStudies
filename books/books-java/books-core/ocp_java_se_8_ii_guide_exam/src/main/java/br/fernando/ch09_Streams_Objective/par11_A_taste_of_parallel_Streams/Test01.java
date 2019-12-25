package br.fernando.ch09_Streams_Objective.par11_A_taste_of_parallel_Streams;

import java.util.Arrays;
import java.util.List;

public class Test01 {

    // =========================================================================================================================================
    static void test01_01() {

        final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sum01 = nums.stream() // stream the numbers
                .mapToInt(n -> n) // map Integer to int for sum
                .sum(); // sum the ints

        System.out.println("Sum is: " + sum01);// result is 55

        // To make this a parallel stream, we simply call the method parallel() on the
        // stream before we sum:

        int sum02 = nums.stream() //
                .parallel() // make the stream parallel
                .mapToInt(n -> n) //
                .sum(); //

        System.out.println("Sum is: " + sum02); // result is still 55 (whew!)

        // One thing to be careful of with parallel streams is performing an operation that
        // relies on a specific ordering. If you use a parallel stream, you may get unexpected results.

        Arrays.asList("boi", "charis", "zooey", "aiko") //
                .stream() // stream the names
                .forEach(System.out::println); // display them
        // boi charis zooey aiko

        Arrays.asList("boi", "charis", "zooey", "aiko") //
                .stream() // stream the names
                .parallel() // ... in parallel
                .forEach(System.out::println);

        // You’ll potentially get a different ordering in the output every time you run the code.
        // zooey charis boi aiko
    }

    // =========================================================================================================================================
    static void summary() {
        final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sum01 = nums //
                .parallelStream() //
                .mapToInt(n -> n) //
                .sum(); //

        System.out.println(sum01);

        int sum02 = nums //
                .stream() //
                .parallel() //
                .mapToInt(n -> n) //
                .sum(); //

        System.out.println(sum02);

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
