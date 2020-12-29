package br.fernando.ch11_Concurrency_Objective.par02_Use_java_util_concurrent_collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Test03 {

    // =========================================================================================================================================
    // Controlling Threads with CyclicBarrier
    static void test01_03() throws Exception {

        // Whereas blocking queues force threads to wait based on capacity or until a method is called on the queue, a CyclicBarrier can force threads
        // to wait at a specific point in the execution until all threads reach that point before continuing.
        // You can think of that point in the execution as a barrier: no thread can proceed beyond that point until all other threads have also reached it

        final List<String> result = new ArrayList<>();

        final String[] dogs1 = { "boi", "clover", "charis" };
        final String[] dogs2 = { "aiko", "zooey", "biscuit" };

        // The barrier for the threads, the second argument code runs later
        // We create a new CyclicBarrier, passing in the number of threads that will wait at the barrier and a Runnable
        // that will be run by the last thread to reach the barrier. Here, we’re using a lambda expression for this Runnable
        final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            // Once both threads reach the barrier, then the Runnable we passed to the CyclicBarrier constructor is executed by the last thread to reach the barrier.
            // This adds all the items from both arrays to the ArrayList result and prints the result.

            // copy results to list
            for (int i = 0; i < dogs1.length; i++) {
                result.add(dogs1[i]); // add dogs from array 1
            }

            for (int i = 0; i < dogs2.length; i++) {
                result.add(dogs2[i]); // add dogs from array 2
            }

            System.out.println(Thread.currentThread().getName() + " Result: " + result);
        });

        Thread t1 = new Thread(new ProcessDogs(dogs1, barrier));
        Thread t2 = new Thread(new ProcessDogs(dogs2, barrier));

        // The two threads begin running and process their respective arrays. The ProcessDogs run() method simply iterates through the array and converts the
        // first letter of the dog name to uppercase, storing the modified string back in the original array
        t1.start();
        t2.start();

        //
        System.out.println("Main Thread is done!");
    }

    static class ProcessDogs implements Runnable { // each thread will process an array of dogs

        final String dogs[];

        final CyclicBarrier barrier;

        public ProcessDogs(String[] dogs, CyclicBarrier barrier) {
            super();
            this.dogs = dogs;
            this.barrier = barrier;
        }

        public void run() { // convert first chars into uppercase

            for (int i = 0; i < dogs.length; i++) {
                String dogName = dogs[i];
                String newDogName = dogName.substring(0, 1).toUpperCase() + dogName.substring(1);
                dogs[i] = newDogName;
            }

            // Once the loop is complete, the thread then waits at the barrier by calling the barrier’s await() method.

            try {
                barrier.await(); // wait at the barrier
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " is done!");

            // When the Runnable completes, then both threads are released and can continue
            // executing where they left off at the barrier. In this example, each thread just
            // prints a message to the console indicating it’s done (with the thread name), but
            // you can imagine that in a more realistic example, they could continue on to do
            // more processing.
        }
    }

    // =========================================================================================================================================
    static void summary() {

        final int NUM_PARTIAL_RESULTS = 10;

        final int NUM_WORKERS = 2;

        final Random random = new Random();

        final List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());

        // the barrier
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM_WORKERS, () -> { // thread that will execute after the barrier ends.

            String thisThreadName = Thread.currentThread().getName();

            System.out.println(thisThreadName + ": Computing sum of " + NUM_WORKERS + " workers, having " + NUM_PARTIAL_RESULTS + " results each.");
            int sum = 0;

            for (List<Integer> threadResult : partialResults) {
                System.out.print("Adding ");
                for (Integer partialResult : threadResult) {
                    System.out.print(partialResult + " ");
                    sum += partialResult;
                }
                System.out.println();
            }

            System.out.println(thisThreadName + ": Final result = " + sum);

        });

        // **************************************************************************************************************************************************
        // CyclicBarrier - It's a synchronizer that allows a set of threads to wait for each other to reach a common execution point, also called a barrier.
        // **************************************************************************************************************************************************
        class NumberCruncherThread implements Runnable {

            @Override
            public void run() {
                String thisThreadName = Thread.currentThread().getName();
                List<Integer> partialResult = new ArrayList<>();

                // Crunch some numbers and store the partial result
                for (int i = 0; i < NUM_PARTIAL_RESULTS; i++) {
                    Integer num = random.nextInt(10);
                    System.out.println(thisThreadName + ": Crunching some numbers! Final result - " + num);
                    partialResult.add(num);
                }

                partialResults.add(partialResult);
                try {
                    System.out.println(thisThreadName + " waiting for others to reach barrier.");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    // ...
                } catch (BrokenBarrierException e) {
                    // ...
                }
            }
        }

        System.out.println("Spawning " + NUM_WORKERS + " worker threads to compute " + NUM_PARTIAL_RESULTS + " partial results each");

        for (int i = 0; i < NUM_WORKERS; i++) {
            Thread worker = new Thread(new NumberCruncherThread());
            worker.setName("Thread " + i);
            worker.start();
        }
    }

    // ==========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_03();
    }
}
