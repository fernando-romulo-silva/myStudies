package br.com.fernando.ch03_lambda_expressions.part09_Similar_ideas_from_mathematics;

import java.util.function.DoubleFunction;

// Similar ideas from mathematics
public class Test {

    // Integration
    public static void test1() {

	// Suppose you have a (mathematical, not Java) function f, perhaps defined by
	// f(x) = x + 10

    }

    // Connecting to Java 8 lambdas
    public static void test2() {

	// Now, as we mentioned earlier, Java 8 uses the notation (double x) -> x+10 (a lambda expression)
	// for exactly this purpose; hence you can write

	integrate((double x) -> x, 3, 7);
    }

    public static double integrate(DoubleFunction<Double> f, double a, double b) {
	return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;
    }

    public static void main(String[] args) {

    }

}
