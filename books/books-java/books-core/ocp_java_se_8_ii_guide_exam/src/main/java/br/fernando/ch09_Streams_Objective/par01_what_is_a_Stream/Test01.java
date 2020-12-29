package br.fernando.ch09_Streams_Objective.par01_what_is_a_Stream;

import java.util.Arrays;
import java.util.stream.Stream;

public class Test01 {

    // =========================================================================================================================================
    //
    static void test01_01() {
        // A stream is a sequence of elements (you can think of these elements as data) that can be processed with operations.

        Integer[] myNums01 = { 1, 2, 3 }; // create an array

        Stream<Integer> myStream01 = Arrays.stream(myNums01); // stream the array

        System.out.println("MyNums " + myNums01 + " MyStream " + myStream01);

        // But streams are not a data structure to organize data like a List is or an array is; rather,
        // they are a way to process data that you can think of as flowing through the stream,
        // much like water flows through a stream in the real world.

        long numElements01 = myStream01.count(); // get the number of elements in the stream

        System.out.println("Number of elemnts in the stream: " + numElements01);

        // The real power of streams comes from the “intermediate operations” you can perform between the source and
        // the end of the stream. For instance, you could filter for even numbers (intermediate operation one)

        Stream<Integer> myStream02 = Arrays.stream(myNums01);

        long numElements02 = myStream02 //
                .filter(i -> i > 1) // add an intermediate operation to filter the stream
                .count(); // terminal operation, counts the elements in a stream

        System.out.println("Number of elements > 1: " + numElements02);

        // The filter() method is calling the test() method of the Predicate you pass to filter()
        // behind the scenes, and if the value passes the test, that value gets passed on to count()
        //
        // Notice that the filter() method of myStream produces a stream. It’s a slightly modified stream
        // consisting only of elements whose values are greater than 1. We’re now calling the count() method on
        // that filtered stream, rather than on the original myStream02
        //
        // we perform a terminal operation like count() . That turns the stream into one thing—say, a number—and 
        // ends the stream. We’ll look at streams with multiple intermediate operations shortly.

        //
        // Streams can be used only once. No problem, we can just create the stream again. In Java, streams are lightweight
        // objects, so you can create multiple streams if you need to

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
