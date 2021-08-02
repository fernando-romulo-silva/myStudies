package br.com.fernando.ch07_parallel_data_processing_and_performance.part01_Parallel_streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// Parallel streams
public class Test {

    // Parallel streams
    public static void test00() {

	// This operation seems to be a good candidate to leverage parallelization, especially for large
	// values of n. But where do you start? Do you synchronize on the result variable? How many
	// threads do you use? Who does the generation of numbers? Who adds them up?

    }

    public static long sequentialSum(final long n) {
	return Stream //
	    .iterate(1L, i -> i + 1) // Generate the infinte stream of natural numbers
	    .limit(n)// Limit it to the first n numbers
	    .reduce(0L, Long::sum); // Reduce the stream by summing all the numbers
    }

    public static long iterativeSum(long n) {
	long result = 0;
	for (long i = 1L; i <= n; i++) {
	    result += i;
	}
	return result;
    }

    // Turning a sequential stream into a parallel one
    public static void test01() {
	// You can make the former functional reduction process (that is, summing) run in parallel by
	// turning the stream into a parallel one; call the method parallel
	//
	// The difference is that the Stream is internally divided into multiple chunks.
	// As a result, the reduction operation can work on the various chunks independently and in parallel
    }

    public static long parallelSum(final long n) {
	return Stream //
	    .iterate(1L, i -> i + 1) // Generate the infinte stream of natural numbers
	    .limit(n) // Limit it to the first n numbers
	    .parallel() // turn the stream into a parallel one.
	    .reduce(0L, Long::sum); // Reduce the stream by summing all the numbers
    }

    public static void test011() {

	final List<String> list = Arrays.asList("one", "two", "three");

	// Note that, in reality, calling the method parallel on a sequential stream doesn’t imply any
	// concrete transformation on the stream itself. Internally, a boolean flag is set to signal that you
	// want to run in parallel all the operations that follow the invocation to parallel. Similarly, you can
	// turn a parallel stream into a sequential one by just invoking the method sequential on it. Note
	// that you might think that you could achieve finer-grained control over which operations you
	// want to perform in parallel and which one sequentially while traversing the stream by
	// combining these two methods. For example, you could do something like the following:

	list.stream() //
	    .parallel() //
	    .filter(x -> x.length() > 2) //
	    .sequential() //
	    .map(x -> x.getBytes()) //
	    .parallel(); //

	// But the last call to parallel or sequential wins and affects the pipeline globally. In this example,
	// the pipeline will be executed in parallel because that’s the last call in the pipeline.
	//
	// Parallel streams internally use the default ForkJoinPool, which by default has as many threads as you have processors, as
	// returned by Runtime.getRuntime().availableProcessors().
	//
	// But you can change the size of this pool using the system property
	// java.util.concurrent.ForkJoinPool.common.parallelism, as in the following example:
	System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");

	// You now have three methods executing exactly the same operation in three different ways (iterative
	// style, sequential reduction, and parallel reduction), so let’s see which is the fastest one!
    }

    // Measuring stream performance
    public static void test02() {

	// You should expect that the iterative version using a traditional for loop runs much faster
	// because it works at a much lower level and, more important, doesn’t need to perform any boxing
	// or unboxing of the primitive values. If you try to measure its performance with
	//
	// Sequential sum done in: 372 msecs
	System.out.println("Sequential sum done in: " + measureSumPerf(Test::sequentialSum, 10_000_000) + " msecs");

	// Iterative sum done in: 14 msecs
	System.out.println("Iterative sum done in: " + measureSumPerf(Test::iterativeSum, 10_000_000) + " msecs");

	// Parallel sum done in: 327 msecs
	System.out.println("Parallel sum done in: " + measureSumPerf(Test::parallelSum, 10_000_000) + " msecs");

	//
	// This is quite disappointing: the parallel version of the summing method is much slower than the
	// sequential one. How can you explain this unexpected result? There are actually two issues mixed
	// together:
	//  iterate generates boxed objects, which have to be unboxed to numbers before they can be added.
	//
	//  iterate is difficult to divide into independent chunks to execute in parallel.
	//

	// Specialized parallel iterative sum done in: 7 msecs
	System.out.println("Specialized Parallel iterative sum done in: " + measureSumPerf(Test::parallelRangedSum, 10_000_000) + " msecs");

	// Specialized iterative sum done in: 16 msecs
	System.out.println("Specialized iterative sum done in: " + measureSumPerf(Test::rangedSum, 10_000_000) + " msecs");

	// SideEffect parallel sum done in:
	System.out.println("SideEffect parallel sum done in: " + measureSumPerf(Test::sideEffectParallelSum, 10_000_000L) + " msecs");
    }

    // Using more specialized methods
    public static void test022() {
	// So how can you leverage your multicore processors and use the stream to perform a parallel sum
	// in an effective way?
	//
	//  LongStream.rangeClosed works on primitive long numbers directly so there’s no boxing and
	// unboxing overhead.
	//
	//  LongStream.rangeClosed produces ranges of numbers, which can be easily split into independent
	// chunks. For example, the range 1–20 can be split into 1–5, 6–10, 11–15, and 16–20.
	//
	// Nevertheless, keep in mind that parallelization doesn’t come for free. The parallelization process
	// itself requires you to recursively partition the stream, assign the reduction operation of each
	// substream to a different thread, and then combine the results of these operations in a single
	// value. But moving data between multiple cores is also more expensive than you might expect, so
	// it’s important that work to be done in parallel on another core takes longer than the time
	// required to transfer the data from one core to another. In general, there are many cases where it
	// isn’t possible or convenient to use parallelization. But before you use a parallel Stream to make
	// your code faster, you have to be sure that you’re using it correctly; it’s not helpful to produce a
	// result in less time if the result will be wrong. Let’s look at a common pitfall.
    }

    public static long rangedSum(long n) {
	return LongStream //
	    .rangeClosed(1, n) //
	    .reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
	return LongStream //
	    .rangeClosed(1, n) //
	    .parallel() //
	    .reduce(0L, Long::sum);
    }

    // Using parallel streams correctly
    public static void test03() {

	// The main cause of errors generated by misuse of parallel streams is the use of algorithms that
	// mutate some shared state. Here’s another way to implement the sum of the first n natural
	// numbers but by mutating a shared accumulator;
	//
	// This time the performance of your method isn’t important: the only relevant thing is that each
	// execution returns a different result, all very distant from the correct value of 50000005000000.
	// This is caused by the fact that multiple threads are concurrently accessing the accumulator and
	// in particular executing total += value, which, despite its appearance, isn’t an atomic operation.
    }

    public static long sideEffectParallelSum(long n) {
	final Accumulator accumulator = new Accumulator();

	LongStream //
	    .rangeClosed(1, n) //
	    .parallel() //
	    .forEach(accumulator::add);

	return accumulator.total;
    }

    public static long sideEffectSum(long n) {
	final Accumulator accumulator = new Accumulator();

	LongStream //
	    .rangeClosed(1, n)//
	    .forEach(accumulator::add);

	return accumulator.total;
    }

    public static class Accumulator {

	public long total = 0;

	public void add(long value) {
	    total += value;
	}
    }

    // Using parallel streams effectively
    public static void test04() {

	// In general it’s impossible (and pointless) to try to give any quantitative hint on when to use a
	// parallel stream because any suggestion like “use a parallel stream only if you have at least one
	// thousand (or one million or whatever number you want) elements” could be correct for a
	// specific operation running on a specific machine, but it could be completely wrong in an even
	// marginally different context. Nonetheless, it’s at least possible to provide some qualitative
	// advice that could be useful when deciding whether it makes sense to use a parallel stream in a
	// certain situation:
	//
	//  If in doubt, measure. Turning a sequential stream into a parallel one is trivial but not always the right
	// thing to do
	//
	//  Watch out for boxing. Automatic boxing and unboxing operations can dramatically hurt performance
	//
	//  Some operations naturally perform worse on a parallel stream than on a sequential stream.
	//
	//  Consider the total computational cost of the pipeline of operations performed by the stream.
	//
	//  For a small amount of data, choosing a parallel stream is almost never a winning decision.
	//
	//  Take into account how well the data structure underlying the stream decomposes. For instance, an
	// ArrayList can be split much more efficiently than a LinkedList, because the first can be evenly
	// divided without traversing it, as it’s necessary to do with the second.
	//
	//  The characteristics of a stream, and how the intermediate operations through the pipeline modify
	// them, can change the performance of the decomposition process
	//
	//  Consider whether a terminal operation has a cheap or expensive merge step (for example, the
	// combiner method in a Collector). If this is expensive, then the cost caused by the combination of the
	// partial results generated by each substream can outweigh the performance benefits of a parallel stream.
	//
	// Stream sources and decomposability
	//
	// Source ===================== Decomposability
	//
	// ArrayList ================== Excellent
	//
	// LinkedList ================= Poor
	//
	// IntStream.range ============ Excellent
	//
	// Stream.iterate ============= Poor
	//
	// HashSet ==================== Good
	//
	// TreeSet ==================== Good
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static long measureSumPerf(final Function<Long, Long> adder, long n) {
	long fastest = Long.MAX_VALUE;

	for (int i = 0; i < 10; i++) {
	    final long start = System.nanoTime();
	    final long sum = adder.apply(n);
	    final long duration = (System.nanoTime() - start) / 1_000_000;
	    System.out.println("Result: " + sum);
	    if (duration < fastest)
		fastest = duration;
	}

	return fastest;
    }

    public static void main(String[] args) {
	test02();
    }

}
