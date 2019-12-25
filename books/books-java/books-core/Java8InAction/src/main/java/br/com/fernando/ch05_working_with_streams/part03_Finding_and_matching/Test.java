package br.com.fernando.ch05_working_with_streams.part03_Finding_and_matching;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.fernando.Dish;

// Finding and matching
public class Test {

    // Checking to see if a predicate matches at least one element
    public static void test1() {
	// The anyMatch method can be used to answer the question “Is there an element in the stream
	// matching the given predicate?” For example, you can use it to find out whether the menu has a
	// vegetarian option:

	final Stream<Dish> dishStream = Dish.menu.stream();
	//
	if (dishStream.anyMatch(Dish::isVegetarian)) {
	    System.out.println("The menu is (somewhat) vegetarian friendly!!");
	}
	// The anyMatch method returns a boolean and is therefore a terminal operation.
    }

    // Checking to see if a predicate matches all elements
    public static void test2() {

	// The allMatch method works similarly to anyMatch but will check to see if all the elements of the
	// stream match the given predicate. For example, you can use it to find out whether the menu is
	// healthy (that is, all dishes are below 1000 calories):

	final Stream<Dish> dishStream1 = Dish.menu.stream();

	final boolean isHealthy1 = dishStream1.allMatch(d -> d.getCalories() < 1000);

	System.out.println(isHealthy1);

	// noneMatch
	//
	// The opposite of allMatch is noneMatch. It ensures that no elements in the stream match the
	// given predicate. For example, you could rewrite the previous example as follows using
	// noneMatch:

	final Stream<Dish> dishStream2 = Dish.menu.stream();

	final boolean isHealthy2 = dishStream2.noneMatch(d -> d.getCalories() >= 1000);

	System.out.println(isHealthy2);

	// These three operations, anyMatch, allMatch, and noneMatch, make use of what we call
	// short-circuiting, a stream version of the familiar Java short-circuiting && and || operators.
    }

    // Short-circuiting evaluation
    //
    // Some operations don’t need to process the whole stream to produce a result. For example, say
    // you need to evaluate a large boolean expression chained with and operators. You need only find
    // out that one expression is false to deduce that the whole expression will return false, no matter
    // how long the expression is; there’s no need to evaluate the entire expression. This is what
    // short-circuiting refers to.
    //
    // In relation to streams, certain operations such as allMatch, noneMatch, findFirst, and findAny
    // don’t need to process the whole stream to produce a result. As soon as an element is found, a
    // result can be produced. Similarly, limit is also a short-circuiting operation: the operation only
    // needs to create a stream of a given size without processing all the elements in the stream.
    //
    // Such operations are useful, for example, when you need to deal with streams of infinite size, because
    // they can turn an infinite stream into a stream of finite size.
    //

    // Finding an element
    public static void test3() {
	// The findAny method returns an arbitrary element of the current stream. It can be used in
	// conjunction with other stream operations. For example, you may wish to find a dish that’s
	// vegetarian. You can combine the filter method and findAny to express this query

	final Optional<Dish> dish = Dish.menu.stream() //
	    .filter(Dish::isVegetarian)//
	    .findAny();
    }

    // Optional in a nutshel
    public static void test31() {
	// The Optional<T> class (java.util.Optional) is a container class to represent the existence or
	// absence of a value. In the previous code, it’s possible that findAny doesn’t find any element.
	// Instead of returning null, which is well known for being error prone
	//
	//  isPresent() returns true if Optional contains a value, false otherwise.
	//
	//  ifPresent(Consumer<T> block) executes the given block if a value is present. We introduced the
	// Consumer functional interface in chapter 3; it lets you pass a lambda that takes an argument of type
	// T and returns void.
	//
	//  T get() returns the value if present; otherwise it throws a NoSuchElement-Exception.
	//
	//  T orElse(T other) returns the value if present; otherwise it returns a default value.
    }

    // Finding the first element
    public static void test4() {
	// Some streams have an encounter order that specifies the order in which items logically appear
	// in the stream (for example, a stream generated from a List or from a sorted sequence of data).
	// For such streams you may wish to find the first element. There’s the findFirst method for this,
	// which works similarly to findAny.
	//

	final List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);

	final Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream() //
	    .map(x -> x * x) //
	    .filter(x -> x % 3 == 0) //
	    .findFirst(); // 9

	// When to use findFirst and findAny
	// You may wonder why we have both findFirst and findAny. The answer is parallelism. Finding
	// the first element is more constraining in parallel. If you don’t care about which element is
	// returned, use findAny because it’s less constraining when using parallel streams.
    }

}
