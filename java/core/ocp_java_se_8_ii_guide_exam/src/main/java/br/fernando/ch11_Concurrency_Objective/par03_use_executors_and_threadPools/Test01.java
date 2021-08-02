package br.fernando.ch11_Concurrency_Objective.par03_use_executors_and_threadPools;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import br.fernando.ch11_Concurrency_Objective.par03_use_executors_and_threadPools.Test03.MyCallable;

public class Test01 {

    // =========================================================================================================================================
    // Executor
    static void test01_01() {
        // In a way, an Executor is an alternative to starting new threads. Using Threads directly can be considered low-level multithreading,
        // whereas using Executors can be considered high-level multithreading. To understand how an Executor can replace manual thread creation,
        // let us first analyze what happens when starting a new thread.
    }

    // =========================================================================================================================================
    // Identifying Parallel Tasks
    static void test01_02() {
        // Some applications are easier to divide into separate tasks than others. A single-user desktop application may only have a handful of
        // tasks that are suitable for concurrent execution. Networked multiuser servers, on the other hand, have a natural division of work.
    }

    // =========================================================================================================================================
    // How Many Threads Can You Run?
    static void test01_03() {
        // Do you own a computer that can concurrently run 10,000 threads or 1,000 or even 100? Probably not—this is a trick question.
        // A quad-core CPU (with four processors per unit) might be able to execute two threads per core for a total of eight concurrently
        // executing threads. You can start 10,000 threads, but not all of them will be running at the same time.
    }

    // =========================================================================================================================================
    // Decoupling Tasks from Threads
    static void test01_04() {

        // This is where a java.util.concurrent.Executor can help. The basic usage looks something like this:
        Runnable r = () -> System.out.println("Run!");

        // As you’ll see soon, several implementations are provided in the standard Java SE libraries.
        // You can easily create your own implementations of an Executor with custom behaviors
        Executor ex01 = new SameThreadExecutor(); // details to follow

        ex01.execute(r);

        // A java.util.concurrent.Executor is used to execute the run method in a Runnable instance much like a thread. Unlike a more traditional new
        // Thread(r).start(), an Executor can be designed to use any number of threading approaches, including:
        //
        // * Not starting any threads at all (task is run in the calling thread)
        // * Starting a new thread for each task
        // * Queuing tasks and processing them with only enough threads to keep the CPU utilized
        //
        // Several Executor implementations are supplied as part of the standard Java libraries.
        // The Executors class (notice the “s” at the end) is a factory for Executor implementations.
        Executor ex02 = Executors.newCachedThreadPool(); // chosse Executor
        ex02.execute(r);
    }

    static class SameThreadExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            command.run(); // caller waits
        }
    }

    // The following Executor implementation would use a new thread for each task:
    static class NewThreadExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            Thread t = new Thread(command);
            t.start();
        }
    }

    // =========================================================================================================================================
    // ExecutorService Shutdown
    static void test01_05() throws Exception {
        // The final component to using an Executor is shutting it down once it is done processing tasks.
        // An ExecutorService should be shut down once it is no longer needed to free up system resources
        // and to allow graceful application shutdown.
        //
        // Because the threads in an ExecutorService may be nondaemon threads, they may prevent normal application termination.
        // In other words, your application stays running after completing its main method

        Callable<Integer> c = new MyCallable();

        ExecutorService ex = Executors.newCachedThreadPool();

        Future<Integer> f = ex.submit(c);

        ex.shutdown(); // no more new task, but wait finish existing tasks

        try {
            boolean term = ex.awaitTermination(2, TimeUnit.SECONDS); // wait 2 seconds for running tasks to finish
        } catch (InterruptedException e) {
            // did not wait the full 2 seconds
        } finally {
            if (!ex.isTerminated()) { // are all tasks done?
                List<Runnable> unfisnished = ex.shutdownNow(); // finishes now! But is not guarantee
                // a collection of the unfinished tasks
            }
        }

        // One good way to shyt down the ExecutorService is to use these methods combined with the awaitTermination method:

        ex.shutdown();
        try {
            if (!ex.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                ex.shutdownNow();
            }
        } catch (InterruptedException e) {
            ex.shutdownNow();
        }
    }

    // =========================================================================================================================================
    static void summary() {
        // ********************************************************************************************
        // In a way, an Executor is an alternative to starting new threads.
        // ********************************************************************************************
        //
        // Executor: is the super type of all executors. It defines only one method execute(Runnable).
        //
        // ExecutorService: is an Executor that allows tracking progress of value-returning tasks (Callable) via Future object, and manages 
        // the termination of threads. Its key methods include submit() and shutdown().
        //
        // ScheduledExecutorService : is an ExecutorService that can schedule tasks to execute after a given delay, or to execute periodically. 
        // Its key methods are schedule(), scheduleAtFixedRate() and scheduleWithFixedDelay().

        Runnable r = () -> System.out.println("Run!");

        Executor ex01 = new SameThreadExecutor();

        ex01.execute(r);

        ExecutorService ex02 = Executors.newCachedThreadPool(); // chosse Executor
        ex02.execute(r);

        // ******************************************************************************************
        // Shutdown 
        // ******************************************************************************************
        //
        // The shutdown() method doesn’t cause an immediate destruction of the ExecutorService. It will make the ExecutorService stop accepting new tasks 
        // and shut down after all running threads finish their current work.

        ex02.shutdown(); //

        // The shutdownNow() method tries to destroy the ExecutorService immediately, but it doesn’t guarantee that all the running threads will be 
        // stopped at the same time. This method returns a list of tasks which are waiting to be processed. 
        // It is up to the developer to decide what to do with these tasks.

        List<Runnable> unfisnished = ex02.shutdownNow(); // finishes now! But is not guarantee
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
