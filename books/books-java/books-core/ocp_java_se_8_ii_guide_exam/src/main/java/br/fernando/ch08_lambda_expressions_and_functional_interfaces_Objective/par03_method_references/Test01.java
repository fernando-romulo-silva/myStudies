package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par03_method_references;

import java.util.Arrays;
import java.util.List;

public class Test01 {

    // =========================================================================================================================================
    // Use method references with Streams.
    static void test01_01() {
        final List<String> trees = Arrays.asList("fir", "cedar", "pine");

        trees.forEach(t -> System.out.println(t));

        // Here we’re using a lambda expression to take a tree name, t , and pass it to
        // System.out.println() . This code is already pretty short; can we shorten it even
        // more? Yes! Apparently, the Java 8 authors like finding ways to avoid typing, so they
        // invented the “method reference”:

        trees.forEach(System.out::println);

        // where did the argument "t" go and don’t we need that? We know that forEach()
        // takes a Consumer, and we know what it’s consuming is tree names, which are String "s".
        // And we know that System.out.println() takes a String . A lot can be inferred from
        // this shorthand for the lambda expression.
    }

    // =========================================================================================================================================
    // Kinds of Method References
    static void test01_02() {
        // You can create method references for your own methods, too:

        final List<String> trees = Arrays.asList("fir", "cedar", "pine");

        trees.forEach(Test01::printTreeStatic);
    }

    // A “static method reference” is a method reference to a static method.
    static void printTreeStatic(String t) {
        System.out.println("Tree name: " + t);
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
