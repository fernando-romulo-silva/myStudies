package br.fernando.ch11_Concurrency_Objective.par05_Parallel_Streams;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Test01 {

    // =========================================================================================================================================
    // How to Make a Parallel Stream Pipeline
    static void test01_01() {
        // Parallel streams are designed for problems you can divide and conquer, just like the problems described in the previous section.
        // In fact, parallel streams are implemented with Fork/Join tasks under the covers

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sum01 = nums.stream() //
                .parallel() //
                .peek(i -> System.out.println(i + ": " + Thread.currentThread().getName())) //
                .mapToInt(n -> n) //
                .sum();

        System.out.println("Sum is: " + sum01);

        // So we can tell the computer exactly how many workers to use by creating a custom ForkJoinPool and then submitting a task to
        // the pool for execution:

        ForkJoinPool fjp = new ForkJoinPool(2);

        try {

            int sum02 = fjp.submit( // returns a Future (FutureTask)
                    () -> nums.stream() // a callable (value returning task)
                            .parallel()// make the strem parallel
                            .peek(i -> System.out.println(i + ": " + Thread.currentThread().getName()))//
                            .mapToInt(n -> n)//
                            .sum() //
            ).get(); // from Future; get() waits for computation to complete and gets the result

            System.out.println("FJP with 2 workers, sum is: " + sum02);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // You’ve seen how to create a parallel stream with parallel(); you can also combine the methods stream() and parallel()
        // into one method, parallelStream(), like this:

        final Stream<Integer> numsStreams03 = nums.parallelStream(); // make a parallel stream

        int sum03 = numsStreams03.peek(i -> System.out.println(i + ": " + Thread.currentThread().getName())) //
                .mapToInt(n -> n) //
                .sum();

        System.out.println("Sum is: " + sum03);
        //
        // You can check to see if a stream is parallel with the isParallel() method. This might come in handy if someone hands
        // you a stream and you’re not sure.

        System.out.println("Is numsStream a parallel stream? " + numsStreams03.isParallel());

        // What if you have a parallel stream and you want to make it not parallel (i.e., sequential)?
        // You can do that with the sequential() method:
        Stream<Integer> numsStreamSeq = numsStreams03.sequential();

        System.out.println("Is numsStreamSeq a parallel stream? " + numsStreamSeq.isParallel());

    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        // **************************************************************************************************
        // Parallel Stream
        // **************************************************************************************************
        //
        // These streams can come with improved performance – at the cost of multi-threading overhead.

        long firstNum = 1;
        long lastNum = 1_000_000;

        List<Long> aList = LongStream.rangeClosed(firstNum, lastNum).boxed().collect(Collectors.toList());

        ForkJoinPool customThreadPool = new ForkJoinPool(4);

        long actualTotal = customThreadPool.submit(() -> aList.parallelStream() //
                .reduce(0L, Long::sum)) //
                .get();

        System.out.println(actualTotal);
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
