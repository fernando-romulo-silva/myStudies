package br.com.fernando.ch03_lambda_expressions.part04_Using_functional_interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

// Using functional interfaces
//
// There are several functional interfaces already available in the Java API
// such as Comparable, Runnable, and Callable
public class Test {

    // Predicate
    // You might want to use this interface when you need to represent a
    // boolean expression that uses an object of type T.
    public static void test1() {

	final List<String> listOfStrings = Arrays.asList("Test1", "Test3");

	final Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();

	final List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);

	System.out.println(nonEmpty);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {

	final List<T> results = new ArrayList<>();

	for (final T s : list) {
	    if (p.test(s)) {
		results.add(s);
	    }
	}

	return results;
    }

    // Consumer
    // You might use this interface when you need to access an object of type T and perform some operations on it.
    // For example, you can use it to create a method forEach, which takes a list of Integers and applies an
    // operation on each element of that list.
    public static void test2() {

	final List<Integer> list = Arrays.asList(1, 2, 3, 4);

	forEach(list, (Integer i) -> System.out.println(i));
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {

	for (final T i : list) {
	    c.accept(i);
	}
    }

    // Function
    // You might use this interface when you need to define a lambda that maps information from an input object to
    // an output (for example, extracting the weight of an apple or mapping a string to its length). In
    // the listing that follows we show how you can use it to create a method map to transform a list of
    // Strings into a list of Integers containing the length of each String.
    public static void test3() {

	final List<String> list = Arrays.asList("lambdas", "in", "action");
	final List<Integer> result = map(list, (String s) -> s.length());
	System.out.println(result);
    }

    public static <T, R> List<R> map(final List<T> list, Function<T, R> f) {
	final List<R> result = new ArrayList<>();

	for (final T t : list) {
	    result.add(f.apply(t));
	}

	return result;
    }

    // Primitive specializations
    public static void test4() {
	// Java 8 brings a specialized version of the functional interfaces we described earlier in order to
	// avoid autoboxing operations when the inputs or outputs are primitives.

	// No boxing
	final IntPredicate evenNumbers = (int i) -> i % 2 == 0;
	evenNumbers.test(1000);

	// Boxing
	final Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 2;
	oddNumbers.test(1000);

	// Use case ================================= Example of lambda Matching ======================================== functional interface
	//
	// A boolean expression ===================== (List<String> list) -> list.isEmpty() ============================= Predicate<List<String>>
	//
	// Creating objects ========================= () -> new Apple(10) =============================================== Supplier<Apple>
	//
	// Consuming from an object ================= (Apple a) -> System.out.println(a.getWeight()) ==================== Consumer<Apple>
	//
	// Select/extract from an object ============ (String s) -> s.length() ========================================== Function<String, Integer> or ToIntFunction<String>
	//
	// Combine two values ======================= (int a, int b) -> a * b =========================================== IntBinaryOperator
	//
	// Compare two objects ====================== (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()) == Comparator<Apple> or BiFunction<Apple, Apple, Integer> or ToIntBiFunction<Apple, Apple>
	//
	// What about exceptions, lambdas, and functional interfaces?
	// Note that none of the functional interfaces allow for a checked exception to be thrown. You have
	// two options if you need a lambda expression to throw an exception: define your own functional
	// interface that declares the checked exception, or wrap the lambda with a try/catch block.
	//
	// But you may be using an API that expects a functional interface such as Function<T, R>
	final Function<BufferedReader, String> f = (BufferedReader b) -> {
	    try {
		return b.readLine();
	    } catch (final IOException e) {
		throw new RuntimeException(e);
	    }
	};

    }

    public static void main(final String[] args) {
    }
}
