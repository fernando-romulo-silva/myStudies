package br.com.fernando.ch08_refactoring_testing_and_debugging.part04_Debugging;

import static java.util.stream.Collectors.toList;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

// Debugging
public class Test {

    // Examining the stack trace
    public static void test01() {
        // Unfortunately, due to the fact that lambda expressions don’t have names, stack traces can be
        // slightly puzzling. Consider the following simple code made to fail on purpose:
        List<Point> points = Arrays.asList(new Point(12, 2), null);
        points.stream().map(p -> p.getX()).forEach(System.out::println);
        //
        // Exception in thread "main" java.lang.NullPointerException
        // at br.com.fernando.ch08_refactoring_testing_and_debugging.part04_Debugging.Test.lambda$0(Test.java:13) // What does $0 in this line mean?
        // at br.com.fernando.ch08_refactoring_testing_and_debugging.part04_Debugging.Test$$Lambda$1/1870252780.apply(Unknown Source)
        //
        // They mean that the error occurred inside a lambda expression. Unfortunately, because lambda
        // expressions don’t have a name, the compiler has to make up a name to refer to them. In this
        // case it’s lambda$main$0, which isn’t very intuitive. This can be problematic if you have large
        // classes containing several lambda expressions.
        //
        // Note that if a method reference refers to a method declared in the same class as where it’s used,
        // then it will appear in the stack trace. For instance, in the following example
        //
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.stream().map(Test::divideByZero).forEach(System.out::println);
        // Exception in thread "main" java.lang.ArithmeticException: / by zero
        // at br.com.fernando.ch08_refactoring_testing_and_debugging.part04_Debugging.Test.divideByZero(Test.java:35) // divideByZero appears in stack trace!
        // at br.com.fernando.ch08_refactoring_testing_and_debugging.part04_Debugging.Test$$Lambda$1/757108857.apply(Unknown Source)
    }

    public static int divideByZero(int n) {
        return n / 0;
    }

    // Logging information
    public static void test02() {

        final List<Integer> numbers = Arrays.asList(2, 3, 4, 5);

        numbers.stream() //
            .map(x -> x + 17) //
            .filter(x -> x % 2 == 0) //
            .limit(3) //
            .forEach(System.out::println);
        // 
        // Unfortunately, once you call forEach, the whole stream is consumed. What would be really
        // useful is to understand what each operation (map, filter, limit) produces in the pipeline of a stream.
        //
        // This is where the stream operation peek can help. Its purpose is to execute an action on each
        // element of a stream as it’s consumed. But it doesn’t consume the whole stream like forEach does;
        // it forwards the element it performed an action on to the next operation in the pipeline
        System.out.println("");
        //
        numbers.stream() //
            .peek(x -> System.out.println("taking from stream: " + x)).map(x -> x + 17) // Print the current element consumed from the source
            .peek(x -> System.out.println("after map: " + x)).filter(x -> x % 2 == 0) // Print the result of the map operation.
            .peek(x -> System.out.println("after filter: " + x)).limit(3) // Print the number selected after the filter operation.
            .peek(x -> System.out.println("after limit: " + x)).collect(toList()); // Print the number selected after the 'limit' operation.
    }

    // =======================================================================================
    public static void main(String[] args) {
        test02();
    }
}
