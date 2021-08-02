package br.com.fernando.ch05_working_with_streams.part04_Reducing;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Reducing
public class Test {

    // Summing the elements
    public static void test1() {

	final int[] numbers = new int[] { 4, 5, 3, 9 };

	// Before we investigate how to use the reduce method, it helps to first see how you’d sum the
	// elements of a list of numbers using a for-each loop:

	int sum = 0;

	for (final int x : numbers) {
	    sum += x;
	}

	System.out.println(sum); //

	// Each element of numbers is combined iteratively with the addition operator to form a result.
	// You reduce the list of numbers into one number by repeatedly using addition. There are two
	// parameters in this code:
	//
	//  The initial value of the sum variable, in this case 0
	//  The operation to combine all the elements of the list, in this case +
	//
	// This is where the reduce operation, which abstracts over this pattern of
	// repeated application, can help. You can sum all the elements of a stream as follows:

	final List<Integer> numbersList = Arrays.asList(4, 5, 3, 9);

	sum = numbersList.stream() //
	    .reduce(0, (a, b) -> a + b);

	// reduce takes two arguments:
	//  An initial value, here 0.
	//  A BinaryOperator<T> to combine two elements and produce a new value; here you use the lambda (a, b) -> a + b.

	// You can make this code more concise by using a method reference. In Java 8 the Integer class
	// now comes with a static sum method to add two numbers, which is just what you want instead
	// of repeatedly writing out the same code as lambda:
	final int sumNew = numbersList.stream().reduce(0, Integer::sum);
	System.out.println(sumNew);
    }

    // No initial value
    public static void test12() {

	// There’s also an overloaded variant of reduce that doesn’t take an initial value, but it returns an
	// Optional object:

	final List<Integer> numbers = Arrays.asList(4, 5, 3, 9);
	final Optional<Integer> sum = numbers.stream().reduce((a, b) -> (a + b));

	System.out.println(sum);
    }

    // Maximum and minimum
    public static void test2() {

	final List<Integer> numbers = Arrays.asList(4, 5, 3, 9);

	// It turns out that reduction is all you need to compute maxima and minima as well! Let’s see how
	// you can apply what you just learned about reduce to calculate the maximum or minimum
	// element in a stream. As you saw, reduce takes two parameters:
	//
	//  An initial value
	//  A lambda to combine two stream elements and produce a new value

	final Optional<Integer> max1 = numbers.stream().reduce((a, b) -> {
	    if (a >= b) {
		return a;
	    } else {
		return b;
	    }
	});

	System.out.println(max1);

	// or

	final Optional<Integer> max2 = numbers.stream().reduce(Integer::max);
	System.out.println(max2);

	// To calculate the minimum, you need to pass Integer.min to the reduce operation instead of Integer.max:
	final Optional<Integer> min = numbers.stream().reduce(Integer::min);
	
	System.out.println(min);
    }
    
    // Stream operations: stateless vs. stateful
    public static void test21() {
	
	// Operations like map and filter take each element from the input stream and produce zero or one
	// result in the output stream. These operations are thus in general stateless: they don’t have an internal state.
	//
	// But operations like reduce, sum, and max need to have internal state to accumulate the result.
	// In this case the internal state is small. In our example it consisted of an int or double. The
	// internal state is of bounded size no matter how many elements are in the stream being processed.
	//
	// The 	internal state is of bounded size no matter how many elements are in the stream being processed.
	//
	// By contrast, some operations such as sorted or distinct seem at first to behave like filter or
	// map—all take a stream and produce another stream (an intermediate operation), but there’s a
	// crucial difference. Both sorting and removing duplicates from a stream require knowing the
	// previous history to do their job. We call these operations stateful operations
    }

    public static void main(String[] args) {
    }

}
