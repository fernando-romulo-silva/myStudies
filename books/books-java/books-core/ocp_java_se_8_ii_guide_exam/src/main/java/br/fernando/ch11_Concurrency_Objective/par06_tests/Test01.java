package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following options correctly create an ExecutorService instance?

        ExecutorService es01 = Executors.newFixedThreadPool(2);

        // You need to remember the following points about a few important classes in java.util.concurrent package:
        //
        // 1. ExecutorService interface extends Executor interface. While Executor allows you to execute a Runnable, ExecutorService allows you to execute a Callable.
        //
        // 2. Executors is a utility class that provides several static methods to create instances of ExecutorService.
        // All such methods start with new e.g. newSingleThreadExecutor().
        //
        // You should at least remember the following methods:
        //
        // newFixedThreadPool(int noOfThreads), newSingleThreadExecutor(), newCachedThreadPool(), newSingleThreadScheduledExecutor(),
        // newScheduledThreadPool(int corePoolSize)

        ExecutorService es02 = Executors.newSingleThreadExecutor();

        ExecutorService es03 = Executors.newCachedThreadPool();

        ExecutorService es04 = Executors.newSingleThreadScheduledExecutor();

        ExecutorService es05 = Executors.newScheduledThreadPool(10);
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
        // Note that the concurrent use of the same 'java.util.Random' instance across threads may encounter contention and consequent poor performance.
        // Consider instead using ThreadLocalRandom in multithreaded designs.
        // There are a few basic questions about generating random numbers in the exam.
        // Besides Math.random() method, you should know about ThreadLocalRandom class.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Given ComplicatedTask class

        // Identify the correct statements about the above code.
        //
        // * THRESHOLD should be increased if the cost of forking a new task dominates the cost of direct computation.
        //
        // There is a cost associated with forking a new task. If the cost of actually finshing the task without forking new subtasks is less,
        // then there is not much benefit in breaking a task into smaller units.
        //
        // Therefore, a balance needs to be reached where the cost of forking is less than direct computation. THRESHOLD determines that level.
        // THRESHOLD value can be different for different tasks.
        //
        // For example, for a simple sum of the numbers, you may keep THRESHOLD high, but for say computing the sum of factorial of each numbers,
        // THRESHOLD may be low.
        //
        // * The logic for computing mid should be such that it divides the task into two equal parts in terms of cost of computation.
        //
        // The whole objective is to divide the task such that multiple threads can compute it in parallel. Therefore, it is better if two sub tasks
        // are equal in terms of cost of computation, otherwise, one thread will finish earlier than the other thereby reducing performance.

        // Example

        class ComplicatedTask extends RecursiveTask<Integer> {

            private static final long serialVersionUID = 1L;

            int[] ia;

            int from;

            int to;

            static final int THRESHOLD = 3;

            public ComplicatedTask(int[] ia, int from, int to) {
                this.ia = ia;
                this.from = from;
                this.to = to;
            }

            public int transform(int t) {
                // this is a CPU intensive operation that
                // transforms t and returns the value
                return 0;
            }

            protected Integer compute() {
                if (from + THRESHOLD > to) {
                    int sum = 0;
                    for (int i = from; i <= to; i++) {
                        sum = sum + transform(ia[i]);
                    }
                    return sum;
                } else {
                    int mid = (from + to) / 2;
                    ComplicatedTask newtask1 = new ComplicatedTask(ia, from, mid);
                    ComplicatedTask newtask2 = new ComplicatedTask(ia, mid + 1, to);
                    newtask2.fork();
                    int x = newtask1.compute();
                    int y = newtask2.join();
                    return x + y;
                }
            }
        }

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };

        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedTask st = new ComplicatedTask(ia, 0, 6);

        int sum = fjp.invoke(st);
        System.out.println("sum = " + sum);
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given Counter Class
        //
        // This class is supposed to keep an accurate count for the number of times the increment method is called.
        // Several classes share this class and call its increment method. What should be inserted at //1 ?
        //
        // Explanation
        // AtomicInteger allows you to manipulate an integer value atomically. It provides several methods for this purpose.
        // Please go through the JavaDoc API description for this class as it is important for the exam.
    }

    static class Counter {

        static AtomicInteger ai = new AtomicInteger(0);

        public static void increment() {
            // 1
            ai.incrementAndGet();
            // or
            ai.addAndGet(1);

        } // other valid code
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given classes
        // What should be inserted in the following code such that run method of Merger will be executed only after the thread started at 4 and
        // the main thread have both invoked await?

        // Make ItemProcessor extend Thread instead of implementing Runnable
        class ItemProcessor extends Thread { // implements Runnable { // LINE 1

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

        Merger m = new Merger();

        CyclicBarrier cb = new CyclicBarrier(2, m); // LINE 3

        ItemProcessor ip = new ItemProcessor(cb);

        ip.start(); // LINE 4

        cb.await();

        // 1. ItemProcessor needs to extend Thread otherwise ip.start() will not compile.
        //
        // 2. Since there are a total two threads that are calling cb.await ( one is the ItemProcessor thread and another one is the main thread),
        // you need to create a CyclicBarrier with number of parties parameter as 2.
        // If you specify the number of parties parameter as 1, Merger's run will be invoke as soon as the any thread invokes await but that is not
        // what the problem statement wants.
        //
        // Explanation:
        // Briefly, a CyclicBarrier allows multiple threads to run independently but wait at one point until all of the coordinating threads arrive
        // at that point. Once all the threads arrive at that point, all the threads can then proceed.
        // It is like multiple cyclists taking different routes to reach a particular junction. They may arrive at different times but they will wait
        // there until everyone arrives. Once everyone is there, they can go on futher independent of each other.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // You have been given an instance of an Executor and you use that instance to execute tasks.
        // How many threads will be created for executing these tasks by the Executor?

        // Number of threads created by the Executor depends on how the Executor instance was created.

        // Explanation:
        // Thus, the number of threads used by an Executor instance depends on the class of that instance and how that instance was created.
        // For example, if an instance was created using Executors.newSingleThreadExecutor(), only one thread will be created, but if it was
        // created using Executors.newFixedThreadPool(5), five threads will be created.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code print?

        // ReentrantLock rlock01 = new ReentrantLock();
        // boolean f1 = rlock01.lock();
        // System.out.println(f1);
        // boolean f2 = rlock01.lock();
        // System.out.println(f2);

        //
        // It will not compile.
        ReentrantLock rlock02 = new ReentrantLock();

        // Lock.lock() returns void.
        rlock02.lock();

        // Lock.tryLock() returns boolean. Had the code been:         
        boolean f1 = rlock02.tryLock();
        System.out.println(f1);
        boolean f2 = rlock02.tryLock();
        System.out.println(f2);

        // It would have printed: true true
        //
        //  Note that ReentrantLock implements Lock

    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given Counter class

        // This class is supposed to keep an accurate count for the number of times the increment method is called.
        // Several classes share an instance of this class and call its increment method.
        // What should be inserted at //1 and //2?
    }

    public static class Counter02 {

        // 1
        AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            // 2
            count.incrementAndGet();
        }
        // other valid code

    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given:
        ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();
        Object value = new Object();

        // Which of the given statements are correct about the following code fragment:

        if (!cache.containsKey("key"))
            cache.put("key", value);

        // (Assume that key and value refer to appropriate objects.)

        // To ensure that an entry in cache must never be overwritten, this statement should be replaced with:
        cache.putIfAbsent("key", value);

        // This method internally checks for the presence of the key and then put the key-value if it not already present in the map.
        // Both the operations (i.e. the check and put) are combined in a single method call so that they can be done atomically.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Consider the following MyCache class,

        class MyCache {

            private CopyOnWriteArrayList<String> cal = new CopyOnWriteArrayList<>();

            public void addData(List<String> list) {
                cal.addAll(list);
            }

            public Iterator getIterator() {
                return cal.iterator();
            }
        }
        //
        // Given that one thread calls the addData method on an instance of the above class and another thread calls the getIterator method on
        // the same instance at the same time and starts iterating through its values, which of the following options are correct?
        //
        // Both the threads will complete their operations successfully without getting any exception
        //
        // CopyOnWriteArrayList guarantees that the Iterator acquired from its instance will never get this exception.
        //
        // This is made possible by creating a copy of the underlying array of the data. The Iterator is backed by this duplicate array.
        // An implication of this is that any modifications done to the list are not reflected in the Iterator and no modifications can be
        // done on the list using that Iterator (such as by calling iterator.remove() ).
        //
        // Calls that try to modify the iterator will get UnsupportedOperationException.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
