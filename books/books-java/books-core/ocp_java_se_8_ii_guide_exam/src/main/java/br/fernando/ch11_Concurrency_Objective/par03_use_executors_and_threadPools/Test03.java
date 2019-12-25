package br.fernando.ch11_Concurrency_Objective.par03_use_executors_and_threadPools;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Test03 {

    // =========================================================================================================================================
    // The Callable Interface
    static void test01_01() throws Exception {
        // The java.util.concurrent.Callable interface serves the same purpose as the Runnable interface, but provides more flexibility.
        // Unlike the Runnable interface, a Callable may return a result upon completing execution and may throw a checked exception.
        // An ExecutorService can be passed a Callable instead of a Runnable.
        //
        // The primary benefit of using a Callable is the ability to return a result. A java.util.concurrent.Future is used to obtain
        // the status and result of a Callable.
        //

        Callable<Integer> c = new MyCallable();

        ExecutorService ex = Executors.newCachedThreadPool();

        Future<Integer> f = ex.submit(c);// finishes in the future. When? I don't know.

        try {
            Integer v = f.get(); // block until done

            System.out.println("Ran: " + v);
        } catch (InterruptedException e) {
            System.out.println("Failed");
        }

        // Submitting a Callable to an ExecutorService returns a Future reference. When you use the Future to obtain the Callable’s result,
        // you will have to handle two possible exceptions:
        //
        // * InterruptedException Raised when the thread calling the Future’s get() method is interrupted before a result can be returned
        // * ExecutionException Raised when an exception was thrown during the execution of the Callable’s call() method
    }

    static class MyCallable implements Callable<Integer> {

        public Integer call() {
            // obtain a random number from 1 to 10

            // ThreadLocalRandom was introduced in Java 7 as a new way to create random numbers.
            // Math.random() and shared Random instances are thread-safe, but suffer from contention when used by multiple threads.
            int count = ThreadLocalRandom.current().nextInt(1, 11);

            for (int i = 0; i < count; i++) {
                System.out.println("Running... " + i);
            }

            return count;
        }
    }

    // =========================================================================================================================================
    static void summay() throws InterruptedException {

        ExecutorService ex = Executors.newCachedThreadPool();

        // **************************************************************************************
        // The Callable Interface - Like a Runnable, but may throw a exception and has a return
        // **************************************************************************************
        Callable<Integer> cal01 = () -> {
            int count = ThreadLocalRandom.current().nextInt(1, 11);

            for (int i = 0; i < count; i++) {
                System.out.println("Running... " + i);
            }

            return count;
        };

        Callable<Integer> cal02 = () -> {
            int count = ThreadLocalRandom.current().nextInt(5, 21);

            for (int i = 0; i < count; i++) {
                System.out.println("Running... " + i);
            }

            return count;
        };

        // **************************************************************************************
        // Future
        // **************************************************************************************
        // The submit() and invokeAll() methods return an object or a collection of objects of type Future, which allows us to get the result of a
        // task’s execution or to check the task’s status (is it running or executed).

        Future<Integer> f01 = ex.submit(cal01); // finishes in the future. When? I don't know.

        Integer result = null;
        try {
            result = f01.get(); // method get block the execution the current thread
            // or
            result = f01.get(10, TimeUnit.SECONDS); // method get block the execution the current thread until expires
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        final List<Future<Integer>> futures = ex.invokeAll(Arrays.asList(cal01, cal02));

        for (Future<Integer> future : futures) {
            future.isCancelled();
            future.isDone();
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
