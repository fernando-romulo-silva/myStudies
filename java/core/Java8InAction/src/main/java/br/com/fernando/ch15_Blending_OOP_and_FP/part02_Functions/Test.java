package br.com.fernando.ch15_Blending_OOP_and_FP.part02_Functions;

import java.util.function.Function;
import java.util.stream.Stream;

public class Test {

    public static void test01() {
	// As in Java, you could do the following:
	Function<String, Boolean> isLongTweet = (String s) -> s.length() > 60;
	boolean isLong = isLongTweet.apply("A very short tweet");
	System.out.println(isLong);

	// But in Hava the fllowing will reult in a compiler error because count is implicitly forced to be final

	// int count = 0;
	// Runnable inc = () -> count += 1; // error: count must be final or effectively final
	// inc.run();
	// System.out.println(count);
	// inc.run();
	// System.out.println(count);

	// Currying
	// To understand what Scala brings to the table, let’s first revisit an example in Java. You can
	// define a simple method to multiply two integers:
	int r = multiply(2, 10);

	// But this definition requires all the arguments to be passed to it. You can manually break down
	// the multiply method by making it return another function:

	final Function<Integer, Integer> multiplyCurryFunction = multiplyCurry(2);
	System.out.println(multiplyCurryFunction.apply(2)); // 

	// The function returned by multiplyCurry captures the value of x and multiplies it by its argument
	// y, returning an Integer. This means you can use multiplyCurry as follows in a map to multiply
	// each element by 2:
	Stream.of(1, 3, 5, 7) //
	    .map(multiplyCurryFunction) // will execute apply(y)
	    .forEach(System.out::println); // will produce result 2, 6, 10, 14.

    }

    static int multiply(int x, int y) {
	return x * y;
    }

    static Function<Integer, Integer> multiplyCurry(int x) {
	return (Integer y) -> x * y;
    }
    
    
    //=======================================
    public static void main(String[] args) {
	test01();
    }

}
