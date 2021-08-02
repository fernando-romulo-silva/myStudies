package org.nandao.cap10.p01UsingJoinForkFrameworkJava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// The join/fork framework is an approach that supports breaking a problem into smaller and
// smaller pieces, solving them in parallel, and then combining the results. The new java.
// util.concurrent.ForkJoinPool class supports this approach. It is designed to work
// with multi-core systems, ideally with dozens or hundreds of processors. Currently, few desktop
// platforms support this type of concurrency, but future machines will. With fewer than four
// processors, there will be little performance improvement.

public class Test {

    private static int numbers[] = new int[100000];

    private static class SumOfSquaresTask extends RecursiveTask<Long> {

        private static final long serialVersionUID = 1L;

        private final int thresholdTHRESHOLD = 1000;

        private int from;

        private int to;

        public SumOfSquaresTask(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            long sum = 0L;
            int mid = (to + from) >>> 1;

            if ((to - from) < thresholdTHRESHOLD) {

                for (int i = from; i < to; i++) {
                    sum += numbers[i] * numbers[i];
                }

                return sum;

            } else {

                List<RecursiveTask<Long>> forks = new ArrayList<>();
                SumOfSquaresTask task1 = new SumOfSquaresTask(from, mid);
                SumOfSquaresTask task2 = new SumOfSquaresTask(mid, to);

                forks.add(task1);
                task1.fork();
                forks.add(task2);
                task2.fork();

                for (RecursiveTask<Long> task : forks) {
                    sum += task.join();
                }

                return sum;
            }
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }

        long startTime;
        long stopTime;
        long sum = 0L;

        startTime = System.currentTimeMillis();

        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i] * numbers[i];
        }

        System.out.println("Sum of squares: " + sum);
        stopTime = System.currentTimeMillis();
        System.out.println("Iterative solution time: " + (stopTime - startTime));

        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        startTime = System.currentTimeMillis();

        long result = forkJoinPool.invoke(new SumOfSquaresTask(0, numbers.length));

        System.out.println("forkJoinPool: " + forkJoinPool.toString());

        stopTime = System.currentTimeMillis();
        System.out.println("Sum of squares: " + result);
        System.out.println("Fork/join solution time: " + (stopTime - startTime));
    }

}
