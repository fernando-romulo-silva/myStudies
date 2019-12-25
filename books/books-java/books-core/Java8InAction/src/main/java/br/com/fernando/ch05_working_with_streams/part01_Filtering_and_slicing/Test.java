package br.com.fernando.ch05_working_with_streams.part01_Filtering_and_slicing;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import br.com.fernando.Dish;

// Filtering and slicing
public class Test {

    // Filtering with a predicate
    public static void test1() {

	// The Streams interface supports a filter method (which you should be familiar with by now). This
	// operation takes as argument a predicate (a function returning a boolean) and returns a stream
	// including all elements that match the predicate.

	final List<Dish> vegetarianMenu = Dish.menu.stream() //
	    .filter(Dish::isVegetarian)//
	    .collect(toList());

	System.out.println(vegetarianMenu);

    }

    // Filtering unique elements
    public static void test2() {

	// Streams also support a method called distinct that returns a stream with unique elements
	// (according to the implementation of the hashCode and equals methods of the objects produced
	// by the stream).

	final List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

	numbers.stream() //
	    .filter(i -> i % 2 == 0) //
	    .distinct() //
	    .forEach(System.out::println);

    }

    // Truncating a stream
    public static void test3() {

	// Streams support the limit(n) method, which returns another stream that’s no longer than a
	// given size. The requested size is passed as argument to limit. If the stream is ordered, the first
	// elements are returned up to a maximum of n

	final List<Dish> dishes = Dish.menu.stream() //
	    .filter(d -> d.getCalories() > 300) //
	    .limit(3) //
	    .collect(toList());
    }

    // Skipping elements
    public static void test4() {
	// Streams support the skip(n) method to return a stream that discards the first n elements. If the
	// stream has fewer elements than n, then an empty stream is returned. Note that limit(n) and
	// skip(n) are complementary!

	final List<Dish> dishes = Dish.menu.stream() //
	    .filter(d -> d.getCalories() > 300) //
	    .skip(2) //
	    .collect(toList());
    }

    public static void main(String[] args) {
	test1();
    }
}
