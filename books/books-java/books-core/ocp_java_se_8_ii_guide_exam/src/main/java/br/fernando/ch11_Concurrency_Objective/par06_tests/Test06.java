package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given:

        class ItemProcessor extends Thread { // LINE 1 // implements Runnable

            CyclicBarrier cb;

            public ItemProcessor(CyclicBarrier cb) {
                this.cb = cb;
            }

            public void run() {
                System.out.println("processed");
                try {
                    cb.await();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        class Merger implements Runnable { // LINE 2

            public void run() {
                System.out.println("Value Merged");
            }
        }

        // What should be inserted in the following code such that run method of Merger will be executed only after the
        // thread started at 4 and the main thread have both invoked await?

        Merger m = new Merger();

        // 1. ItemProcessor needs to extend Thread otherwise ip.start() will not compile.

        // 2. Since there are a total two threads that are calling cb.await (one is the ItemProcessor thread and another one is the main thread),
        // you need to create a CyclicBarrier with number of parties parameter as 2. If you specify the number of parties parameter as 1,
        // Merger's run will be invoke as soon as the any thread invokes await but that is not what the problem statement wants.

        // two because the main thread will execute cb.await();
        CyclicBarrier cb = new CyclicBarrier(2, m); // LINE 3

        ItemProcessor ip = new ItemProcessor(cb);
        ip.start(); // LINE 4

        cb.await(); // wait the others threads arrive at the barrier

        System.out.println("Ends!");
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You want to execute your tasks after a given delay. Which ExecutorService would you use?

        // ScheduledExecutorService

        // Explanation
        // Following is a usage example that sets up a ScheduledExecutorService to beep every ten seconds for an hour:

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(() -> System.out.println("beep"), 10, 10, TimeUnit.SECONDS);
        scheduler.schedule(() -> beeperHandle.cancel(true), 60 * 60, TimeUnit.SECONDS);
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
        // Note that the concurrent use of the same 'java.util.Random' instance across threads may encounter contention and consequent poor performance.
        // Consider instead using ThreadLocalRandom in multithreaded designs.
        // There are a few basic questions about generating random numbers in the exam.
        // Besides Math.random() method, you should know about ThreadLocalRandom class.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given

        ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String, Object>();

        chm.put("a", "aaa");
        chm.put("b", "bbb");
        chm.put("c", "ccc");
        new Thread() {

            public void run() {
                Iterator<Entry<String, Object>> it = chm.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> en = it.next();
                    if (en.getKey().equals("a") || en.getKey().equals("b")) {
                        it.remove();
                    }
                }
            }
        }.start();

        new Thread() {

            public void run() {
                Iterator<Entry<String, Object>> it = chm.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> en = it.next();
                    System.out.print(en.getKey() + ", ");
                }
            }
        }.start();

        // Which of the following are possible outputs when the above program is run?
        //
        // It may print any combination except: a, or b, or a, b, or b, a,
        //
        // This is correct because the order of iteration is not known and so the thread that removes "a" and "b", may remove them in any order.
        // Thus, the iterator thread may or may not see "a" and/or "b" through its Iterator. However, "c" is never removed from the map and so c,
        // will always be printed.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following code fragments will you use to create an ExecutorService?

        // Executors

        // .newSingleThreadExecutor();

        // Explanation
        //
        // You need to remember the following points about a few important classes in java.util.concurrent package:
        //
        // 1. ExecutorService interface extends Executor interface. While Executor allows you to execute a Runnable, ExecutorService
        // allows you to execute a Callable.
        //
        // 2. Executors is a utility class that provides several static methods to create instances of ExecutorService. All such methods start
        // with new e.g. newSingleThreadExecutor().
        // You should at least remember the following methods: newFixedThreadPool(int noOfThreads), newSingleThreadExecutor(), newCachedThreadPool(),
        // newSingleThreadScheduledExecutor(), newScheduledThreadPool(int corePoolSize).

        ExecutorService es01 = Executors.newFixedThreadPool(2);

        ExecutorService es02 = Executors.newSingleThreadExecutor();

        ExecutorService es03 = Executors.newCachedThreadPool();

        ExecutorService es04 = Executors.newSingleThreadScheduledExecutor();

        ExecutorService es05 = Executors.newScheduledThreadPool(10);
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print?
        AtomicInteger ai = new AtomicInteger();

        Stream<String> stream = Stream.of("old", "king", "cole", "was", "a", "merry", "old", "soul").parallel();

        stream.filter(e -> {
            ai.incrementAndGet();
            return e.contains("o");
        }).allMatch(x -> x.indexOf("o") > 0);

        // Any number between 1 to 8

        // Explanation
        //
        // 1. In the given code, stream refers to a parallel stream. This implies that the JVM is free to break up the original stream into
        // multiple smaller streams, perform the operations on these pieces in parallel, and finally combine the results.
        //
        // 2. Here, the stream consists of 8 elements. It is, therefore, possible for a JVM running on an eight core machine to split this
        // stream into 8 streams (with one element each) and invoke the filter operation on each of them.
        // If this happens, ai will be incremented 8 times.
        //
        // 3. It is also possible that the JVM decides not to split the stream at all. In this case, it will invoke the filter predicate
        // on the first element (which will return true) and then invoke the allMatch predicate
        // (which will return false because "old".indexOf("o") is 0).
        // Since allMatch is a short circuiting terminal operation, it knows that there is no point in checking other elements because the
        // result will be false anyway. Hence, in this scenario, ai will be incremented only once.
        //
        // 4. The number of pieces that the original stream will be split into could be anything between 1 and 8 and by applying the same
        // logic as above, we can say that ai will be incremented any number of times between 1 and 8.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Identify correct statements about the fork/join framework?

        // 1. It increases application throughput for CPU intensive tasks.
        // Since the ForkJoinPool uses work-stealing mechanism, which permits any worker thread to execute any task created by any other
        // worker thread, it does not let the CPU remain idle. This increases application throughput.
        //
        // 2. An advantage of this framework is that it offers a portable means of expressing a parallelizable algorithm without knowing
        // in advance how much parallelism the target system will offer.
        // a. The number of threads is determined (by default) by number of CPU cores available at run time.
        // b. Since any task can be executed by any worker thread, the number of worker threads need not have to be predetermined.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following options correctly create an ExecutorService instance?

        ExecutorService es = Executors.newFixedThreadPool(2);

        // Explanation
        //
        // You need to remember the following points about a few important classes in java.util.concurrent package:
        //
        // 1. ExecutorService interface extends Executor interface. While Executor allows you to execute a Runnable, ExecutorService
        // allows you to execute a Callable.
        //
        // 2. Executors is a utility class that provides several static methods to create instances of ExecutorService. All such methods start
        // with new e.g. newSingleThreadExecutor().
        //
        // You should at least remember the following methods: newFixedThreadPool(int noOfThreads), newSingleThreadExecutor(), newCachedThreadPool(),
        // newSingleThreadScheduledExecutor(), newScheduledThreadPool(int corePoolSize).

        ExecutorService es01 = Executors.newFixedThreadPool(2);

        ExecutorService es02 = Executors.newSingleThreadExecutor();

        ExecutorService es03 = Executors.newCachedThreadPool();

        ExecutorService es04 = Executors.newSingleThreadScheduledExecutor();

        ExecutorService es05 = Executors.newScheduledThreadPool(10);
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // You have to complete a big task that operates on a large array of integers. The task has to look at each element of the array and
        // update that element. The new value of the element is generated by a utility class's static method, which takes in the existing
        // value as a parameter and returns the new value. This method is computationally very intensive.
        //
        // What would be a good approach to solve this problem?
        //
        // Subclass RecursiveAction and implement the compute() method that computes the new value but does not return anything.
        //
        // Create a RecursiveAction that subdivides the task into two, then forks one of the tasks and computes another.
        // This is a standard way of using the Fork/Join framework. You create a RecursiveTask or RecursiveAction
        // (depending on where you need to return a value or not) and in that RecursiveTask, you subdivide the task into two equal parts.
        // You then fork out one of the halfs and compute the second half.
        //
        // Explanation
        // Since there is no requirement to do anything with the newly computed value (such as summing them up), you don't need to
        // return that value to anybody. You just need to update the array element with the new value. Therefore, you don't need RecursiveTask,
        // you need RecursiveAction. The following is a possible implementation:

        class ComplicatedAction extends RecursiveAction {

            int[] ia;

            int from;

            int to;

            public ComplicatedAction(int[] ia, int from, int to) {
                this.ia = ia;
                this.from = from;
                this.to = to;
            }

            protected void compute() {

                if (from == to) { // Update the value using logic implemented somewhere else.
                    ia[from] = UtilityClass.utilityMethod(ia[from]);
                } else {
                    int mid = (from + to) / 2;
                    ComplicatedAction newtask1 = new ComplicatedAction(ia, from, mid);
                    ComplicatedAction newtask2 = new ComplicatedAction(ia, mid + 1, to);
                    newtask2.fork();
                    newtask1.compute();
                    newtask2.join();
                }
            }
        }
        ;

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };
        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedAction st = new ComplicatedAction(ia, 0, 6);
        fjp.invoke(st);
        System.out.print("New Array Values = ");

        for (int i : ia) {
            System.out.print(i + " ");
        }
    }

    private static class UtilityClass {

        public static int utilityMethod(int i) {
            return i + 1;
        }
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which method must be implemented by a class implementing the Callable interface?

        // call()

        Callable<Long> myCall = new Callable<Long>() {

            public Long call() throws Exception {
                return 10L;
            }
        };

        // or

        myCall = () -> 10L;

        ExecutorService executor = Executors.newFixedThreadPool(10);

        Future<Long> submit = executor.submit(myCall);

        // A task that returns a result and may throw an exception. Implementers define a single method with no arguments called call.
        // The Callable interface is similar to Runnable, in that both are designed for classes whose instances are potentially 
        // executed by another thread. A Runnable, however, does not return a result and cannot throw a checked exception.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
