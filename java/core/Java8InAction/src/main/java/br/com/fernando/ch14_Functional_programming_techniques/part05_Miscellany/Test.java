package br.com.fernando.ch14_Functional_programming_techniques.part05_Miscellany;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Miscellany
public class Test {

    final static Map<Range, Integer> NUMBER_OF_NOTES = new HashMap<>();

    static {

	NUMBER_OF_NOTES.put(new Range(1), 30);
	NUMBER_OF_NOTES.put(new Range(2), 50);
	NUMBER_OF_NOTES.put(new Range(3), 20);
    }

    // Caching or memoization
    public static void test01() {
	
	// If you have referential transparency, there’s a clever way of avoiding this additional overhead. 
	// One standard solution to this issue is memoization—adding a cache (for example, a HashMap) to the method as a
	// wrapper—when the wrapper is called. It first consults the cache to see if the (argument, result) pair is already in the cache
	// if so, it can return the stored result immediately; otherwise, you call computeNumberOfNodes, but before returning from the 
	// wrapper you store the new (argument, result) pair in the cache.
	
	
	final Integer resut = computeNumberOfNodesUsingCache(new Range(5));
	System.out.println(resut);

    }

    static class Range {

	final int id;

	public Range(int id) {
	    super();
	    this.id = id;
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + id;
	// return result;
	// }
	//
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// final Range other = (Range) obj;
	// if (id != other.id)
	// return false;
	// return true;
	// }
    }

    static Integer computeNumberOfNodesUsingCache(Range range) {

	Integer result = NUMBER_OF_NOTES.get(range);

	if (result != null) {
	    return result;
	}

	result = computeNumberOfNodes(range);

	NUMBER_OF_NOTES.put(range, result);

	return result;
    }

    private static Integer computeNumberOfNodes(Range range) {
	return NUMBER_OF_NOTES.computeIfAbsent(range, //
	                                       Test::computeNumberOfNodes);
    }

    // =================================================================
    // Combinators
    public static void test02() {

	// In functional programming it’s common and natural to write a higher-order function (perhaps
	// written as a method) that accepts, say, two functions and produces another function somehow
	// combining these functions. The term combinator is generally used for this idea.

	// will give x ->(2*(2*(2*x))) or x -> 8*x
	final Function<Integer, Integer> f = repeat(3, (Integer x) -> 2 * x);

	// x = 10
	System.out.println(f.apply(10));
    }

    static <A> Function<A, A> repeat(int n, Function<A, A> f) {
	return n == 0 ? //
	              x -> x : // Return the "do-nothing" identify function if 'n' is zero
	              compose(f, repeat(n - 1, f)); // Otherwise do f, repeated n-1 times , followed by doing it once more
    }

    static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, B> f) {
	return x -> g.apply(f.apply(x));
    }

    // =================================================================
    public static void main(String[] args) {
	test02();
    }
}
