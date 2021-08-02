package br.com.fernando.ch13_thinking_functionally.part03_Recursion_vs_iteration;

import java.util.stream.LongStream;

// Recursion vs. iteration
public class Test {

    public static void test00() {
	// In Java, recursive forms are typically less efficient
	//
	// In general, making a recursive function call is much more expensive than the single machine-level branch
	// instruction needed to iterate. Why? Every time the factorialRecursive function is called, a new
	// stack frame is created on the call stack to hold the state of each function call (the multiplication
	// it needs to do) until the recursion is done. This means your recursive definition of factorial will
	// take memory proportional to its input.
	//
	// Tail-recursive factorial
	//
	// The function factorialHelper is tail recursive because the recursive call is the last thing that
	// happens in the function. By contrast in our previous definition of factorial-Recursive, the last
	// thing was a multiplication of n and the result of a recursive call.
	//
	// The bad news is that Java doesn’t support this kind of optimization. But adopting tail recursion
	// may be a better practice than classic recursion because it opens the way to eventual compiler
	// optimization. Many modern JVM languages such as Scala and Groovy can optimize those uses of
	// recursion, which are equivalent to iteration
    }

    static int factorialIterative(int n) {
	int r = 1;
	
	for (int i = 1; i <= n; i++) {
	    r *= i;
	}
	
	return r;
    }

    static long worstFactorialRecursive(long n) {
	return n == 1 ? 1 : n * worstFactorialRecursive(n - 1);
    }

    static long factorialTailRecursive(long n) {
	return factorialHelper(1, n);
    }

    static long factorialHelper(long acc, long n) {
	return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }

    static long factorialStreams(long n) {
	return LongStream //
	    .rangeClosed(1, n) //
	    .reduce(1, (long a, long b) -> a * b);
    }
}
