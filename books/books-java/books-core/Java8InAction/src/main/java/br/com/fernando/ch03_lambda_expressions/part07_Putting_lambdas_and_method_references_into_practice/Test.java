package br.com.fernando.ch03_lambda_expressions.part07_Putting_lambdas_and_method_references_into_practice;

import static java.util.Comparator.comparing;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import br.com.fernando.Apple;

// Putting lambdas and method references into practice!
public class Test {

    public static void test1() {

	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	// Step 1: Pass code
	final AppleComparator appleComparator = new AppleComparator();
	inventory.sort(appleComparator);

	// Step 2: Use an anonymous class
	inventory.sort(new Comparator<Apple>() {

	    @Override
	    public int compare(Apple a1, Apple a2) {
		return a1.getWeight().compareTo(a2.getWeight());
	    }
	});

	// Step 3: Use lambda expressions
	inventory.sort((Apple a1, Apple a2) -> //
		a1.getWeight().compareTo(a2.getWeight()) //
	);

	// We explained that the Java compiler could infer the types of the parameters of a lambda
	// expression by using the context in which the lambda appears.

	inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
    }

    public static class AppleComparator implements Comparator<Apple> {

	@Override
	public int compare(Apple a1, Apple a2) {
	    return a1.getWeight().compareTo(a2.getWeight());
	}
    }

    // Use method references
    public static void test2() {
	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	// We explained that method references are syntactic sugar for lambda expressions that forwards
	// their arguments. You can use a method reference to make your code slightly less verbose

	inventory.sort(comparing(Apple::getWeight));

	// Congratulations, this is your final solution! Why is this better than code prior to Java 8? It’s not
	// just because it’s shorter; it’s also obvious what it means, and the code reads like the problem
	// statement “sort inventory comparing the weight of the apples.”
    }

    // main
    public static void main(String[] args) {

    }

}
