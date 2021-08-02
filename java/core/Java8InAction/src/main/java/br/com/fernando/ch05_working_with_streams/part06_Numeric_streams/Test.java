package br.com.fernando.ch05_working_with_streams.part06_Numeric_streams;

import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.fernando.Dish;

// Numeric streams
public class Test {

    public static void test0() {

	// The problem with this code is that there’s an insidious boxing cost. Behind the scenes each
	// Integer needs to be unboxed to a primitive before performing the summation
	final int calories = Dish.menu.stream() //
	    .map(Dish::getCalories) //
	    .reduce(0, Integer::sum); //

	System.out.println(calories);

	// Don't worry, the Streams API also supplies primitive stream
	// specializations that support specialized methods to work with streams of numbers.
    }

    // Primitive stream specializations
    public static void test1() {
	// Mapping to a numeric stream
	// The most common methods you’ll use to convert a stream to a specialized version are mapToInt,
	// mapToDouble, and mapToLong. These methods work exactly like the method map that you saw
	// earlier but return a specialized stream instead of a Stream<T>.

	final int calories = Dish.menu.stream() //
	    .mapToInt(Dish::getCalories) //
	    .sum();

	System.out.println(calories);

	// Converting back to a stream of objects
	// Similarly, once you have a numeric stream, you may be interested in converting it back to a
	// nonspecialized stream.

	final IntStream intStream = Dish.menu.stream() //
	    .mapToInt(Dish::getCalories); // Converting a Stream to a numeric stream

	final Stream<Integer> stream = intStream.boxed(); // Converting the numeric stream to a Stream

	System.out.println(stream.reduce(0, Integer::sum));

	// Default values: OptionalInt
	//
	// The sum example was convenient because it has a default value: 0. But if you want to calculate
	// the maximum element in an IntStream, you need something different because 0 is a wrong
	// result. How can you differentiate that the stream has no element and that the real maximum is 0?
	// Earlier we introduced the Optional class, which is a container that indicates the presence or
	// absence of a value. Optional can be parameterized with reference types such as Integer, String, and so on. 
	// There’s a primitive specialized version of Optional as well for the three primitive
	// stream specializations: OptionalInt, OptionalDouble, and OptionalLong.

	final OptionalInt maxCalories = Dish.menu.stream() //
	    .mapToInt(Dish::getCalories) //
	    .max(); //

	// You can now process the OptionalInt explicitly to define a default value if there’s no maximum:
	final int max = maxCalories.orElse(1); // Provide an explicit default maximum if there's no value.
	System.out.println(max);
    }

    // Numeric ranges
    public static void test2() {
	// Java 8 introduces two static methods available on IntStream and LongStream to help generate such ranges: range and
	// rangeClosed. Both methods take the starting value of the range as the first parameter and the
	// end value of the range as the second parameter. But range is exclusive, whereas rangeClosed is
	// inclusive.

	final IntStream evenNumbers = IntStream.range(1, 100) // Represents the range 1 to 100.
	    .filter(n -> n % 2 == 0); // A stream of even numbers from 1 to 100.

	System.out.println(evenNumbers.count()); // There are 50 even numers fro 1 to 100.
    }

}
