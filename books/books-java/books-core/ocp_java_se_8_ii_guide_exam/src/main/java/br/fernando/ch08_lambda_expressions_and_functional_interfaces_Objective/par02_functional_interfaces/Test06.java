package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par02_functional_interfaces;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Test06 {

    // =========================================================================================================================================
    // Working with Functions
    static void test01_01() {
        // The purpose of the apply() functional method in the Function interface is to take
        // one value and turn it into another. The two values don’t have to be the same type, so in
        // the Function interface definition, we have two different type parameters, T (the type of
        // the argument to apply() ) and R (the type of the return value):

        final Function<Integer, String> answer = a -> {

            if (a == 42) {
                return "forty-two";
            } else {
                return "No answer for you";
            }
        };

        System.out.println(answer.apply(42));
        System.out.println(answer.apply(64));

        // A BiFunction is similar except the apply() method takes two arguments and returns a value:
        final BiFunction<String, String, String> firstLast = (first, last) -> first + " " + last;

        System.out.println("First and Last: " + firstLast.apply("Joe", "smith"));
    }

    // =========================================================================================================================================
    // Functions in the JDK
    static void test01_02() {
        // Let’s use both a Function and a BiFunction in an example
        // using the Map methods computeIfAbsent() and replaceAll()

        final Map<String, String> aprilWinner = new TreeMap<>();
        aprilWinner.put("April 2017", "Bob");
        aprilWinner.put("April 2016", "Annette");
        aprilWinner.put("April 2015", "Lamar");

        System.out.println("--- List, before checking April 2014 ---");
        aprilWinner.forEach((k, v) -> System.out.println(k + ": " + v));

        // no key for April 2014, so Hohn Doe gets added to the Map
        aprilWinner.computeIfAbsent("April 2014", k -> "John Doe");
        // We first use computeIfAbsent() to add a key and value to our Map of April winners
        // if that key/value pair doesn’t yet exist in the Map
        // computeIfAbsent() takes a key and a Function . The Function provides a value to
        // store in the Map for the key if a value for that key doesn’t yet exist.

        // Key April 2014 now has a value, so Jane won't be added
        aprilWinner.computeIfAbsent("April 2014", k -> "Jane Doe");

        System.out.println("--- List, after checking April 2014 ---");
        aprilWinner.forEach((k, v) -> System.out.println(k + ": " + v));

        // Use a BiFunction to replace all values in the map with uppercase values
        aprilWinner.replaceAll((key, oldValue) -> oldValue.toUpperCase());
        // replaceAll() takes a BiFunction . The lambda expression we pass to
        // replaceAll() has two arguments, a key, and the current value in the Map ,
        // and returns a new value to store in the Map for that key.

        System.out.println("--- List, after replacing values with uppercase ---");
        aprilWinner.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    // =========================================================================================================================================
    // More Functions
    static void test01_03() {
        // Function has a couple of default methods and a static method in
        // addition to its functional method, apply() : andThen() , compose() , and identity().
        //
        // * andThen() is similar to the Consumer’s andThen() method, applying Functions in sequence.
        //
        // * compose() is the same except it applies the Function s in reverse order.
        //
        // * identity() just returns its input argument

        Function<Integer, Integer> id = Function.identity();
        System.out.println(id.apply(42));
        // Imagine a scenario where you have defined a method that takes a Function as an argument that changes a value in a data
        // structure. But in some cases, you don’t want that value to change. In those cases, pass the identity Function as an easy
        // “do nothing” operation.

    }

    // =========================================================================================================================================
    // Working with Operators
    static void test01_04() {
        // However, unlike Function , it requires that the type of the argument to apply() be the same as the
        // type of the return value, so UnaryOperator is defined like this:
        // @FunctionInterface
        // static interface UnaryOperator<T> extends Function<T, T> {}

        UnaryOperator<Double> log2 = v -> Math.log(v) / Math.log(2);
        System.out.println(log2.apply(8.0));

        // Notice that you could, of course, use Function<Double, Double> instead…that’s
        // essentially the same thing. UnaryOperator saves a little typing and makes it a bit
        // clearer that you’re defining an operator that takes a value that is the same type as the
        // return value…but that’s about it.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_02();
    }
}
