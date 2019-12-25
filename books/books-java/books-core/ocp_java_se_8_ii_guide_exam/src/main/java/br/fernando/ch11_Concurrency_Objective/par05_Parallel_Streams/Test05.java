package br.fernando.ch11_Concurrency_Objective.par05_Parallel_Streams;

import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class Test05 {

    // =========================================================================================================================================

    public static final int SIZE = 300_000_000;
    public static final int THRESHOLD = 10_000;
    public static final int MAX = 100; // array of numbers, 1 - 10
    public static final int NUM = 5;

    // A Parallel Stream Implementation of a RecursiveTask
    static void test01_01() throws Exception {

        int[] data2Sum = new int[SIZE];

        long sum = 0, startTime, endTime, duration;

        // -------------------------------------------------------------------------------
        // create an array of random number between 1 and MAX
        startTime = Instant.now().toEpochMilli();

        for (int i = 0; i < data2Sum.length; i++) {
            if (data2Sum[i] > NUM) {
                sum = sum + data2Sum[i];
            }
        }

        endTime = Instant.now().toEpochMilli();
        duration = endTime - startTime;

        System.out.println("Summed with for loop in " + duration + " millseconds; sum is " + sum);

        // -------------------------------------------------------------------------------
        // sum numbers with RecursiveTask
        ForkJoinPool fjp = new ForkJoinPool();
        SumRecursiveTask action = new SumRecursiveTask(data2Sum, 0, data2Sum.length);

        startTime = Instant.now().toEpochMilli();

        sum = fjp.invoke(action);

        endTime = Instant.now().toEpochMilli();
        duration = endTime - startTime;

        System.out.println("Summed with recursive task in " + duration + " millseconds; sum is " + sum);

        // -------------------------------------------------------------------------------
        // sum numbers with a parallel stream

        IntStream stream2sum = IntStream.of(data2Sum);

        startTime = Instant.now().toEpochMilli();

        sum = stream2sum //
                .unordered() //
                .parallel() //
                .filter(i -> i > NUM) //
                .sum();

        endTime = Instant.now().toEpochMilli();
        duration = endTime - startTime;

        System.out.println("Stream data summed in " + duration + " millseconds; sum is " + sum);

        // -------------------------------------------------------------------------------
        // sum numbers with a parallel stream, limiting workers

        ForkJoinPool fjp2 = new ForkJoinPool(4);
        IntStream stream2sum2 = IntStream.of(data2Sum);

        try {
            startTime = Instant.now().toEpochMilli();

            sum = fjp2.submit(() -> stream2sum2 //
                    .unordered() //
                    .parallel() //
                    .filter(i -> i > NUM) //
                    .sum() //
            ).get(); //

            endTime = Instant.now().toEpochMilli();
            duration = endTime - startTime;

            System.out.println("FJP4 Stream data summed in " + duration + " milliseconds; sum is : " + sum);

        } catch (Exception e) {
        }

        // As you can see, your results will vary depending on the solution you choose as well 
        // as your underlying machine architecture.

    }

    public static class SumRecursiveTask extends RecursiveTask<Long> {
        private static final long serialVersionUID = 1L;

        private int[] data;
        private int start;
        private int end;

        public SumRecursiveTask(int[] data, int start, int end) {
            super();
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {

            long tempSum = 0;

            if (end - start <= THRESHOLD) {
                for (int i = start; i < end; i++) {
                    if (data[i] > NUM) {
                        tempSum += data[i];
                    }
                }
                return tempSum;
            } else {

                int halfWay = ((end - start) / 2) + start;
                SumRecursiveTask leftTask = new SumRecursiveTask(data, start, halfWay);
                SumRecursiveTask rightTask = new SumRecursiveTask(data, halfWay, end);

                leftTask.fork(); // queu left half of task

                long sum2 = rightTask.compute(); // compute right half
                long sum1 = leftTask.join(); // compute left and join

                return sum1 + sum2;
            }
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
