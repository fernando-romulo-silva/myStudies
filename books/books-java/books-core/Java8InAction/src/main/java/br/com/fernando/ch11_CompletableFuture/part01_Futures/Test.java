package br.com.fernando.ch11_CompletableFuture.part01_Futures;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//
class Test {
    
    static final int DEFAULT_DELAY = 1000;

    // Futures
    public static void test01() {

        // This style of programming allows your thread to perform some other
        // tasks while the long-lasting operation is executed concurrently in a separate thread provided by
        // the ExecutorService. Then, when you can’t do any other meaningful work without having the
        // result of that asynchronous operation, you can retrieve it from the Future by invoking its get
        // method. This method immediately returns the result of the operation if it’s already completed or
        // blocks your thread, waiting for its result to be available.

        final ExecutorService exeuctor = Executors.newCachedThreadPool(); // Create an Executor-Service allowing you to submit tasks to thread pool.

        final Future<Double> future = exeuctor.submit(new Callable<Double>() { // Submit a Callable to the ExecutorService.

            @Override
            public Double call() throws Exception {
                return doSomeLongComuptation(); // Execute a long operation asysnchrously in separate thread.
            }
        });

        doSomethingElse(); // Do something else while the asysnchronous operation is progressing.

        try {
            // Retrieve the result of the asynchoronous operation, eventually blocking if it isn't available yet, but waiting at most for 1 second.
            final Double result = future.get(1, TimeUnit.SECONDS);

            System.out.println(result);

        } catch (final InterruptedException e) {
            // the current thread was interrupted while waiting
        } catch (final ExecutionException e) {
            // the computation threw an exception
        } catch (final TimeoutException e) {
            // the timeout expired before the Future completion
        }

    }

    protected static Double doSomeLongComuptation() {
	
        
        try {
            Thread.sleep(DEFAULT_DELAY);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
	
        return new Random().nextDouble();
    }

    private static void doSomethingElse() {
	
        try {
            Thread.sleep(DEFAULT_DELAY - 100);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
