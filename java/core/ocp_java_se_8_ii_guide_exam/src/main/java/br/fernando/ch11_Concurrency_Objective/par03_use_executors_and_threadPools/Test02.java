package br.fernando.ch11_Concurrency_Objective.par03_use_executors_and_threadPools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test02 {

    static void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // =========================================================================================================================================
    // Cached Thread Pools
    static void test01_01() throws Exception {
        // A cached thread pool will create new threads as they are needed and reuse threads that have become free.
        // Threads that have been idle for 60 seconds are removed from the pool.

        ExecutorService ex = Executors.newCachedThreadPool();

        // Watch out! Without some type of external limitation, a cached thread pool may be used to create more threads than your system can handle.
    }

    // =========================================================================================================================================
    // Fixed Thread Pools—Most Common
    static void test01_02() throws Exception {

        // A fixed thread pool is constructed using a numeric argument (4 in the preceding example) that specifies the number of threads used
        // to execute tasks. This type of poolwill probably be the one you use the most because it prevents an application from
        // overloading a system with too many threads.

        ExecutorService ex = Executors.newFixedThreadPool(4);

        // Tasks that cannot be executed immediately are placed on an unbounded queue for later execution.

        // You might base the number of threads in a fixed thread pool on some attribute of the system your application is executing on.
        // By tying the number of threads to system resources, you can create an application that scales with changes in system
        // hardware. To query the number of available processors, you can use the java.lang.Runtime class.

        Runtime rt = Runtime.getRuntime();
        int cpus = rt.availableProcessors();

        System.out.println("Available CPUS:" + cpus);
    }

    // =========================================================================================================================================
    // ThreadPoolExecutor
    static void test01_03() throws Exception {
        // You will typically use the Executors factory methods instead of creating ThreadPoolExecutor instances directly, but you
        // can cast the fixed or cached thread pool ExecutorService references if you need access to the additional methods.
        // The following example shows how you could dynamically adjust the thread count of a pool at runtime:

        ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        tpe.setCorePoolSize(8);
        tpe.setMaximumPoolSize(8);

    }

    // =========================================================================================================================================
    // Single Thread Pools
    static void test01_04() throws Exception {

        // A single thread pool uses a single thread to execute tasks. Tasks that cannot be executed immediately are placed on an unbounded queue
        // for later execution.

        ExecutorService ex = Executors.newSingleThreadExecutor();

        // Unlike a fixed thread pool executor with a size of 1, a single thread executor prevents any adjustments to the number of threads in the pool.
    }

    // =========================================================================================================================================
    // Scheduled Thread Pool
    static void test01_05() throws Exception {
        // A ScheduledThreadPoolExecutor enables tasks to be executed after a delay or at repeating intervals.
        // Here, we see some thread-scheduling code in action:

        ScheduledExecutorService ftses = Executors.newScheduledThreadPool(5); // multi-threaded version

        // Our task
        Runnable r = () -> System.out.println("Run!");

        ftses.schedule(r, 5, TimeUnit.SECONDS); // run once after a delay

        // adn begin again evetry 4 seconds
        ftses.scheduleAtFixedRate(r, 2, 5, TimeUnit.SECONDS); // begin after a a 2sec delay
        // and begin again 5 seconds *after* completing the last execution
    }

    // =========================================================================================================================================
    static void summary() throws Exception {

        Runnable r01 = () -> {
            System.out.println("Execute 01");
            sleep();
        };
        Runnable r02 = () -> {
            System.out.println("Execute 02");
            sleep();
        };
        Runnable r03 = () -> {
            System.out.println("Execute 03");
            sleep();
        };

        // *****************************************************************************************************************
        // Cached Thread Pools
        // *****************************************************************************************************************
        // A cached thread pool will create new threads as they are needed and reuse threads that have become free.
        // Threads that have been idle for 60 seconds are removed from the pool.

        ExecutorService ex01 = Executors.newCachedThreadPool();

        ex01.execute(r01);
        ex01.execute(r02);
        ex01.execute(r03);

        ex01.shutdown();

        // *****************************************************************************************************************
        // Fixed Thread Pools -
        // *****************************************************************************************************************
        // Creates an executor with a maximum number of threads at any time. If you send more tasks than the number of threads,
        // the remaining tasks will be blocked until there is a free thread to process them
        ExecutorService ex02 = Executors.newFixedThreadPool(2);

        ex02.execute(r01);
        ex02.execute(r02);
        ex02.execute(r03); // will wait for r01 and r02 finishes

        ex02.shutdown();

        // *****************************************************************************************************************
        // Single Thread Pools - Only one thread will execution, 
        // *****************************************************************************************************************
        ExecutorService ex03 = Executors.newSingleThreadExecutor();

        ex03.execute(r01);
        ex03.execute(r02); // wait for r01 finishes
        ex03.execute(r03); // wait for r02 finishes

        ex03.shutdown();

        // *****************************************************************************************************************
        // Scheduled Thread Pool
        // *****************************************************************************************************************
        // Enables tasks to be executed after a delay or at repeating intervals.
        ScheduledExecutorService ex04 = Executors.newScheduledThreadPool(5); // multi-threaded version

        ex04.schedule(r01, 5, TimeUnit.SECONDS); // run once after a delay

        // and begin again every 4 seconds
        ex04.scheduleAtFixedRate(r02, 2, 5, TimeUnit.SECONDS);

        // If it is necessary to have a fixed length delay between iterations of the task, scheduleWithFixedDelay() should be used. 
        ex04.scheduleWithFixedDelay(r02, 100, 150, TimeUnit.MILLISECONDS);

        ex04.shutdown();
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
