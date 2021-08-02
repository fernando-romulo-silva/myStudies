package br.com.fernando.ch07_parallel_data_processing_and_performance.part02_The_fork_join_framework;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.LongStream;

// The fork/join framework
public class Test {

    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    // Working with RecursiveTask
    public static void test01() {

	// To submit tasks to this pool, you have to create a subclass of RecursiveTask<R>, where R is the
	// type of the result produced by the parallelized task (and each of its subtasks) or of
	// RecursiveAction if the task returns no result (it could be updating other nonlocal structures,
	// though).
	//
	// This method defines both the logic of splitting the task at hand into subtasks and the algorithm
	// to produce the result of a single subtask when it’s no longer possible or convenient to further
	// divide it. For this reason an implementation of this method often resembles the following
	// pseudocode:
	//
	//

	// if (task is small enough or no longer divisible) {
	// // compute task sequentially
	// } else {
	// // split task in two subtasks
	// // call this method recursively possibly further splitting each subtask
	// // wait for the completion of all subtasks
	// // combine the results of each subtask
	// }
	//
	// In general there are no precise criteria for deciding whether a given task should be further
	// divided or not, but there are various heuristics that you can follow to help you with this decision.
	//
	// Here, the performance is worse than the version using the parallel stream, but only because
	// you’re obliged to put the whole stream of numbers into a long[] before being allowed to use it in
	// the ForkJoinSumCalculator task.

    }

    public static class ForkJoinSumCalculator extends RecursiveTask<Long> { // Extrend RecursiveTask to create a task usuable with the fork/join framework.

	private static final long serialVersionUID = 1L;

	public static final long THRESHOLD = 10_000; // The size of the array under which this task is no longer split into subtasks

	private final long[] numbers; // The array of numbers to be summed.

	private final int start; // The initial and final positions of the portion of the array processed by this subtask

	private final int end;

	public ForkJoinSumCalculator(long[] numbers) { // Public constructor used to create the main task.
	    this(numbers, 0, numbers.length);
	}

	private ForkJoinSumCalculator(long[] numbers, int start, int end) { // Private constructor used to recursively create subtasks
	    this.numbers = numbers;
	    this.start = start;
	    this.end = end;
	}

	@Override
	protected Long compute() { // Override the abstract method of RecursiveTask
	    final int length = end - start; // The size of the portion of the array summed by this task

	    if (length <= THRESHOLD) {
		return computeSequentially(); // if the size is less than or equal to the threshold, compute the result sequentially
	    }

	    final ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2); // Create a subtask to sum the first half of the array.

	    leftTask.fork(); // Asynchronously execute th newly created subtask another thread of the ForJoinPool

	    final ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end); // Create a subtask to sum the second half of the array.

	    final Long rightResult = rightTask.compute(); // execute this second subtask synchronously, potentially allowing further recursive splits.

	    final Long leftResult = leftTask.join(); // Read the result of the first subtask or wait for it if it isn't ready.

	    return leftResult + rightResult; // The result of this task is the combination of the results of the two subtasks.
	}

	private long computeSequentially() { // Simple algorithm calculating the result of a subtask when it's no longer divisible.
	    long sum = 0;
	    for (int i = start; i < end; i++) {
		sum += numbers[i];
	    }
	    return sum;
	}

	// You just need to pass the desired array of numbers to the constructor of ForkJoinSumCalculator:
	public static long forkJoinSum(long n) {
	    final long[] numbers = LongStream.rangeClosed(1, n).toArray();
	    final ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
	    return FORK_JOIN_POOL.invoke(task);
	}
    }

    // Best practices for using the fork/join framework
    public static void test02() {

	// Even though the fork/join framework is relatively easy to use, unfortunately it’s also easy to
	// misuse. Here are a few best practices to leverage it effectively:
	// 
	//  Invoking the join method on a task blocks the caller until the result produced by that task is ready.
	// For this reason, it’s necessary to call it after the computation of both subtasks has been started.
	//
	//  The invoke method of a ForkJoinPool shouldn’t be used from within a RecursiveTask. Instead,
	// you should always call the methods compute or fork directly;

    }
    
    // Work stealing
    public static void test03() {
        
        // The fork/join framework works around this problem with a technique called work stealing. In
        // practice, this means that the tasks are more or less evenly divided on all the threads in the
        // ForkJoinPool. Each of these threads holds a doubly linked queue of the tasks assigned to it, and
        // as soon as it completes a task it pulls another one from the head of the queue and starts
        // executing it.
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
	System.out.println("ForkJoin sum done in: " + measurePerf(ForkJoinSumCalculator::forkJoinSum, 10_000_000L) + " msecs");

	// System.out.println("Parallel range forkJoinSum done in: " + measurePerf(Test::parallelRangedSum, 10_000_000L) + " msecs");
	// System.out.println("Iterative Sum done in: " + measurePerf(Test::iterativeSum, 10_000_000L) + " msecs");
	// System.out.println("Sequential Sum done in: " + measurePerf(Test::sequentialSum, 10_000_000L) + " msecs");
	// System.out.println("Parallel forkJoinSum done in: " + measurePerf(Test::parallelSum, 10_000_000L) + " msecs");
	// System.out.println("Range forkJoinSum done in: " + measurePerf(Test::rangedSum, 10_000_000L) + " msecs");
	// System.out.println("SideEffect sum done in: " + measurePerf(Test::sideEffectSum, 10_000_000L) + " msecs");
	// System.out.println("SideEffect prallel sum done in: " + measurePerf(Test::sideEffectParallelSum, 10_000_000L) + " msecs");
    }

    public static <T, R> long measurePerf(Function<T, R> f, T input) {
	long fastest = Long.MAX_VALUE;
	for (int i = 0; i < 10; i++) {
	    final long start = System.nanoTime();
	    final R result = f.apply(input);
	    final long duration = (System.nanoTime() - start) / 1_000_000;
	    System.out.println("Result: " + result);
	    if (duration < fastest)
		fastest = duration;
	}
	return fastest;
    }

}
