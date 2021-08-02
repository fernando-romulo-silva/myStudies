package br.com.fernando.ch03_lambda_expressions.part05_type_checking_type_inference_and_restrictions;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.ToIntBiFunction;

import br.com.fernando.Apple;
import br.com.fernando.ch02_passing_code_whith_behavior_parameterization.part02_Behavior_parameterization.Test.ApplePredicate;

// Type checking, type inference, and restrictions
public class Test {

    // Type checking
    public static void test1() {

	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	// The type-checking process is deconstructed as follows:
	//
	//  First, you look up the declaration of the filter method.
	//  Second, it expects as the second formal parameter an object of type Predicate-<Apple> (the target type).
	//  Third, Predicate<Apple> is a functional interface defining a single abstract method called test.
	//  Fourth, the method test describes a function descriptor that accepts an Apple and returns a boolean.
	//  Finally, any actual argument to the filter method needs to match this requirement.
	//
	// The function descriptor Apple -> matches the signature of the lambda!
	// It takes an Apple and returns a boolean, so the code type checks.

	final List<Apple> heavierThan150g = filter(inventory, (Apple a) -> a.getWeight() > 150);

	System.out.println(heavierThan150g);
    }

    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if (p.test(apple)) {
		result.add(apple);
	    }
	}
	return result;
    }

    // Same lambda, different functional interfaces
    public static void test2() {

	// Because of the idea of target typing, the same lambda expression can be associated with
	// different functional interfaces if they have a compatible abstract method signature

	final Callable<Integer> c = () -> 42;
	final PrivilegedAction<Integer> p = () -> 42;

	// In this case the first assignment has target type Callable<Integer> and the second assignment
	// has target type PrivilegedAction<Integer>.

	// we showed a similar example; the same lambda can be used with multiple different
	// functional interfaces:
	final Comparator<Apple> c1 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());

	final ToIntBiFunction<Apple, Apple> c2 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());

	final BiFunction<Apple, Apple, Integer> c3 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());

	// How could you fix the problem?
	// Object o = () -> {System.out.println("Tricky example"); };
	//
	// The context of the lambda expression is Object (the target type). But Object isn’t a functional
	// interface. To fix this you can change the target type to Runnable, which represents a function
	// descriptor () -> void:
	final Runnable r = () -> {
	    System.out.println("Tricky example");
	};
    }

    // Type inference
    public static void test3() {

	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
	// The Java compiler deduces what functional interface to associate with a lambda expression from its surrounding context (the target type),
	// meaning it can also deduce an appropriate signature for the lambda because the function descriptor is available through the target type
	//
	// Note that when a lambda has just one parameter whose type is inferred, the parentheses
	// surrounding the parameter name can also be omitted.

	final List<Apple> greenApples = filter(inventory, a -> "green".equals(a.getColor())); // No explicit type on the parameter 'a'

	System.out.println(greenApples);

	// The benefits of code readability are more noticeable with lambda expressions that have
	// parameters. For example, here's how to create a Comparator object.

	final Comparator<Apple> c1 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()); // without type inference

	final Comparator<Apple> c2 = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight()); // with type inference
    }
    
    // Restrictions on local variables
    public static void test4() {
	// All the lambda expressions we’ve shown so far used only their arguments inside their body. 
	// But lambda expressions are also allowed to use free variables 
	// (variables that aren’t the parameters and defined in an outer scope) just like anonymous classes can.
	
	final int portNumber = 1337;
	final Runnable r = () -> System.out.println(portNumber);
	
	// There’s a small twist: there are some restrictions on what you can do with these variables. 
	// Lambdas are allowed to capture (that is, to reference in their bodies) instance variables and 
	// static variables without restrictions. But local variables have to be explicitly declared final 
	// or are effectively final. In other words, lambda expressions can capture local variables that are 
	// assigned to them only once
	
	// portNumber = 455; -- error: local variables refenced from a lambda expression must be final
    }

    public static void main(String[] args) {

    }

}
