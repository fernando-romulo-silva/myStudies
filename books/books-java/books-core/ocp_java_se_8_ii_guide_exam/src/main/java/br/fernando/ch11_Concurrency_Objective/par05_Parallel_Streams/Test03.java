package br.fernando.ch11_Concurrency_Objective.par05_Parallel_Streams;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class Test03 {

    // =========================================================================================================================================
    // Associative Operations
    static void test01_01() throws Exception {
        // That is, we can compute the sum of a + b, and then add c, or we can compute b + c, and then add a, and get the same result.
        //
        // Some operations are definitely not associative. We discovered in Chapter 9 that computing the average of a stream of
        // numbers is not associative.

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // We make the stream parallel, then we map to a stream of double s so we can compute the average and return a double value;
        // then we reduce using our own reduction function, which sums two numbers from the stream and divides by 2
        OptionalDouble avg = nums //
                .parallelStream() // make a parallel stream
                .mapToDouble(n -> n) // maek a tream of doubles
                .reduce((d1, d2) -> (d1 + d2) / 2); // reduce with (bad) average

        avg.ifPresent((a) -> System.out.println("Average of parallel stream with reduce: " + a)); // 5.125 // wrong!

        // we do the same except we reduce with the built-in average() stream method, which is implemented in a way
        // to produce the correct result
        OptionalDouble avg2 = nums//
                .parallelStream() // make a parallel stream
                .mapToDouble(n -> n) // make a tream of doubles
                .average(); // reduce with built-in average

        avg2.ifPresent((a) -> System.out.println("Average of parallel stream with average: " + a)); // 5.5 // correct!
    }

    // =========================================================================================================================================
    // Stateless Operations (and Streams)
    static void test01_02() throws Exception {
        // A stateless operation in a stream pipeline is an operation that does not depend on the context in which it’s operating. Before we talk
        // more about what stateless operations are, let’s talk about what they are not. There are two main ways you can create a stateful stream
        // pipeline. The first is with side effects.
    }

    // =========================================================================================================================================
    // Side Effects and Parallel Streams
    static void test01_03() throws Exception {
        // Another way you can create side effects is to modify the field of an object. In sequential stream pipelines, you can get away with these
        // kinds of side effects, and you may recall from Chapter 9 how we created a list of DVDs from a file input stream.

        Count count = new Count();

        IntStream stream = IntStream.range(0, 50); // generate a stream of integers, 0-49

        int sum = stream //
                .parallel() // make the stream parallel
                .filter(i -> { // filter the stream

                    if (i % 10 == 0) { // only count numbers divisible by 10
                        count.counter++; // there should be 5!
                        return true;
                    }

                    return false;
                }) //
                .sum(); // sum up the integers

        System.out.println("Sum: " + sum + ", count: " + count.counter);

        // The first time we ran this code, we got: Sum: 100, count: 5
        //
        // The next time: sum: 100, count: 4

        // That’s because, when we are using a parallel stream, multiple threads are accessing the Count object to modify the counter field
        // and Count is not thread-safe.
        //
        // We could use a synchronized object to store our counter and that would solve the problem, but it would also defeat the purpose of using
        // parallel streams in this example.
        //
        // We can fix the code above by removing the count in the parallel stream pipeline and computing it separately from the sum
    }

    static class Count {

        int counter = 0;
    }

    // =========================================================================================================================================
    // Stateful Operations in the Stream Pipeline
    static final int SIZE = 900_000_000;

    static final int LIMIT = 400_000_000;

    static void test01_04() throws Exception {
        // Another way we can create stateful stream pipelines is with stateful stream operations. A stateful stream operation is one
        // that requires some knowledge about the stream in order to operate.
        IntStream stream01 = IntStream.range(0, 10);

        long sum01 = stream01 //
                .limit(5) //
                .sum(); //

        System.out.println("Sum is: " + sum01);

        // Now think about this operation, limit(5) . This is a stateful operation. Why?
        // Because it requires context: the stream has to keep some intermediate state to know
        // when it has five items and can stop streaming from the source (that is, short circuit the stream).
        //
        System.out.println(" ---------------------------------------------------------- ");

        long sum02 = 0, startTime, endTime, duration;

        IntStream stream02 = IntStream.range(0, SIZE);

        startTime = Instant.now().toEpochMilli();

        sum02 = stream02 //
                .limit(LIMIT) //
                .sum();

        endTime = Instant.now().toEpochMilli();

        duration = endTime - startTime;

        System.out.println("Sequencial Items summed in " + duration + " milliseconds; sum is: " + sum02);
        // Sequencial Items summed in 433 milliseconds; sum is: 1914453504

        System.out.println(" ---------------------------------------------------------- ");

        long sum03 = 0;

        IntStream stream03 = IntStream.range(0, SIZE);

        startTime = Instant.now().toEpochMilli();

        sum03 = stream03 //
                .parallel() //
                .limit(LIMIT) //
                .sum();

        endTime = Instant.now().toEpochMilli();

        duration = endTime - startTime;

        System.out.println("Parallel items summed in " + duration + " milliseconds; sum is: " + sum03);
        // Parallel items summed in 55 milliseconds; sum is: 1914453504

        // So adding parallel() to this stream will not improve performance and might
        // even hurt performance because now that state has to be synchronized across threads.
    }

    // =========================================================================================================================================
    //
    static void test01_05() throws Exception {
        // The performance is worse! Repeated runs yield running times of between 33 and 36 milliseconds each time. Increasing the SIZE to 400 million
        // yields similar results.
        //
        // One thing we should consider here is that the overhead of creating eight threads might be contributing to the performance problem.
        //
        // To really test that a parallel pipeline can hurt (or, at least, not help) when using limit() , we should try our experiment with a custom
        // ForkJoinPool and set the number of threads ourselves.
        //

        long sum = 0, startTime, endTime, duration;

        ForkJoinPool fjp = new ForkJoinPool(16); // limit FJP to 1 thread

        IntStream stream = IntStream.range(0, SIZE);

        startTime = Instant.now().toEpochMilli();

        try {

            startTime = Instant.now().toEpochMilli();

            sum = fjp.submit( //
                    () -> stream //
                            .parallel() // test sequential first
                            .limit(LIMIT) //
                            .sum())
                    .get();

            endTime = Instant.now().toEpochMilli();

            duration = endTime - startTime;

            System.out.println("Fork Join items summed in " + duration + " milliseconds; sum is: " + sum);
            // Serial
            // 01 Thread - Fork Join items summed in 477 milliseconds; sum is: 1914453504
            //
            // Parallel
            // 04 Thread - Fork Join items summed in 95 milliseconds; sum is: 1914453504
            // 08 Thread - Fork Join items summed in 90 milliseconds; sum is: 1914453504
            // 16 Thread - Fork Join items summed in 94 milliseconds; sum is: 1914453504

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    // =========================================================================================================================================
    static void summary() throws Exception {
        // ****************************************************************************************
        // Associative Operations
        // ****************************************************************************************
        // Use associative operations in parallel, That is, we can compute the sum of a + b, and then add c, or we can compute b + c, and then
        // add a, and get the same result.

        // ****************************************************************************************
        // Stateful and Stateless
        // ****************************************************************************************
        // Those intermediate operations are distinct(), sorted(), limit(), skip(). All other operations are stateless.
        //
        // These stateful intermediate operations incorporate state from previously processed elements when processing the current element.
        //
        // Stream operations that are not necessarily stateful include map() , filter() , and reduce() (among others), so there’s a lot we can do
        // with streams that is stateless, although, as with all things related to concurrency, we need to be careful.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_05();
    }
}
