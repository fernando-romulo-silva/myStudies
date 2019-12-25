package org.nandao.ch06ConcurrencyEnhancements.part04CompletableFutures;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Test {

    public static FutureTask<String> readPage(final String url) {

        final Callable<String> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                return "Test 1";
            } catch (final InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        return new FutureTask<String>(task);
    }

    // Futures
    public static void test1() {
        final FutureTask<String> future = readPage("http://www.somelink.com");

        final ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(future);

        System.out.println("future done? " + future.isDone());

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (final InterruptedException e) {
            throw new IllegalStateException("task interrupted", e);
        }

        // Now in fairness, there has been some support for futures in java.util.concurrent,
        // but an essential piece was missing. There was no easy way of saying: “When the
        // result becomes available, here is how to process it.” This is the crucial feature
        // that the new CompletableFuture<T> class provides.
        System.out.println("future done, again? " + future.isDone());

        executor.shutdown();
    }

    // Composing Futures
    // https://blog.krecan.net/2013/12/25/completablefutures-why-to-use-async-methods/
    // First of all, if the CompletableFuture that executes the doSomethingAndReturnA has completed, 
    // the invocation of the thenApply will happen in the caller thread. 
    // If the CompletableFutures hasn't been completed the Function passed to thenApply will be invoked 
    // in the same thread as doSomethingAndReturnA.
    private static int convertToB(final String a) {
        System.out.println("convertToB: " + Thread.currentThread().getName());
        return Integer.parseInt(a);
    }

    private static String doSomethingAndReturnA() {
        System.out.println("doSomethingAndReturnA: " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        return "1";
    }

    public static void test2() {

        // The thenApply method doesn’t block either. It returns another future. When the
        // first future has completed, its result is fed to the getLinks method, and the return
        // value of that method becomes the final result.
        // This composability is the key aspect of the CompletableFuture class. Composing future
        // actions solves a serious problem in programming asynchronous applications.
        //The traditional approach for dealing with nonblocking calls is to use event
        // handlers. The programmer registers a handler for the next action after completion.
        // Of course, if the next action is also asynchronous, then the next action after
        // that is in a different event handler

        final CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> doSomethingAndReturnA());
        final CompletableFuture<Integer> future2 = future1.thenApply(a -> convertToB(a));

        try {
            future2.get();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }

        // And the output is:
        // doSomethingAndReturnA: ForkJoinPool.commonPool-worker-1
        // convertToB: ForkJoinPool.commonPool-worker-1
        //
        // So, when the first operation is slow (i.e. the CompletableFuture is not yet completed) both calls occur in the same thread. 
        // But if the we were to remove the Thread.sleep-call from the doSomethingAndReturnA the output (may) be like this:
        //
        // doSomethingAndReturnA: ForkJoinPool.commonPool-worker-1
        // convertToB: main
        //
        // Note that convertToB call is in the main thread.
    }

    // Completable Futures
    public static void test3() {

    }

    public static void main(final String[] args) {
        test2();
    }

}
