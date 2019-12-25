package br.com.fernando.ch03_lambda_expressions.part01_Lambdas_in_a_nutshell;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import br.com.fernando.Apple;

// Lambdas in a nutshell
public class Test {

    public static void test1() {

        final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        // Before:
        Comparator<Apple> byWeight1 = new Comparator<Apple>() {

            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        };

        inventory.sort(byWeight1);

        // After (with lambda expressions):
        final Comparator<Apple> byWeight2 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
        inventory.sort(byWeight2);

        // A lambda expression is composed of parameters, an arrow, and a body.
        //
        //  A list of parameters — In this case it mirrors the parameters of the compare method of a Comparator—two Apples.
        //  An arrow — The arrow -> separates the list of parameters from the body of the lambda.
        //  The body of the lambda — Compare two Apples using their weights. The expression is considered the lambda’s return value.

        // Examples of lambdas
        //
        // A boolean expression
        Predicate<List<String>> f1 = (List<String> list) -> inventory.isEmpty();

        // Creating objects
        Supplier<Apple> f2 = () -> new Apple(10, "green");

        // Consuming from an object
        Consumer<Apple> f3 = (Apple a) -> {
            System.out.println(a.getWeight());
        };
        
        // Select/extract from an object
        // (String s) -> s.length();
       
       // Combine two values
       // (int a, int b) -> a * b
       
       // Compare two objects 
       //  (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
    }

    public static void main(String[] args) {

    }

}
