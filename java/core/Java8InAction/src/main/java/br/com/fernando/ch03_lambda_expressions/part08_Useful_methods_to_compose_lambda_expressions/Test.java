package br.com.fernando.ch03_lambda_expressions.part08_Useful_methods_to_compose_lambda_expressions;

import static java.util.Comparator.comparing;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import br.com.fernando.Apple;
import br.com.fernando.Letter;

// Useful methods to compose lambda expressions
public class Test {

    // Composing Comparators
    public static void test1() {
	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	final Comparator<Apple> c = Comparator.comparing(Apple::getWeight); // sorting by decreasing weight

	// Reversed order
	// What if you wanted to sort the apples by decreasing weight? There’s no need to create a different
	// instance of a Comparator. The interface includes a default method reverse that imposes the
	// reverse ordering of a given comparator. So you can simply modify the previous example to sort
	// the apples by decreasing weight by reusing the initial Comparator:

	inventory.sort(comparing(Apple::getWeight).reversed());

	// Chaining Comparators
	// This is all nice, but what if you find two apples that have the same weight? Which apple should
	// have priority in the sorted list? You may want to provide a second Comparator to further refine
	// the comparison.

	inventory.sort(comparing(Apple::getWeight) //
	    .reversed() // Sorting by decreasing weight
	    .thenComparing(Apple::getColor) // Sorting further by color when tow apples have same weight
	);
    }

    // Composiing Predicates
    public static void test2() {

	final Predicate<Apple> redApple = (a) -> a.getColor().equals("red");

	// The Predicate interface includes three methods that let you reuse an existing Predicate to create
	// more complicated ones: negate, and, and or.

	final Predicate<Apple> notRedApple = redApple.negate();

	// You may want to combine two lambdas to say that an apple is both red and heavy with the and method:

	final Predicate<Apple> redAndHeavyApple = redApple.and(a -> a.getWeight() > 150);

	// You can combine the resulting predicate one step further to express aplles that are red and heavy
	// or jus gree apples:
	final Predicate<Apple> redAndHeavyApplesOrGreen = redApple //
	    .and(a -> a.getWeight() > 150) //
	    .or(a -> "gree".equals(a.getColor()));
	// Chaining Predicate's methods to construct a more complex Predicate object
    }

    // Composing Functions
    public static void test3() {
	// Finally, you can also compose lambda expressions represented by the Function interface. The
	// Function interface comes with two default methods for this, andThen and compose, which both
	// return an instance of Function.
	//
	final Function<Integer, Integer> f = x -> x + 1;
	final Function<Integer, Integer> g = x -> x * 2;
	//
	// The method andThen returns a function that first applies a given function to an input and then
	// applies another function to the result of that application.
	final Function<Integer, Integer> h1 = f.andThen(g); // In math you'd write g(f(x)) or (g o f) (x)
	//
	final int result1 = h1.apply(1); // returns 4
	//
	//
	// You can also use the method compose similarly to first apply the function given as argument to
	// compose and then apply the function to the result.
	final Function<Integer, Integer> h2 = f.compose(g); // In math you'd write f(g(x)) or (f o g) (x)
	final int result2 = h2.apply(1); // returns 3
	//
	//
	// Let’s say you have various utility methods that do text transformation on
	// a letter represented as a String:
	final Function<String, String> addHeader = Letter::addHeader;

	final Function<String, String> transformationPipeline1 = addHeader //
	    .andThen(Letter::checkSpelling) //
	    .andThen(Letter::addFooter); //

	// A second pipeline might be only adding a header and footer without checking for spelling:
	final Function<String, String> transformationPipeline2 = addHeader //
	    .andThen(Letter::addFooter);
    }

    public static void main(String[] args) {

    }

}
