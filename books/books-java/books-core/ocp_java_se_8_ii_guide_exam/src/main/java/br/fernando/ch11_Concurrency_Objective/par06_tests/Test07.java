package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test07 {

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
        // You have a collection (say, an ArrayList) which is read by multiple reader threads and which is modified by a single writer thread.
        // The collection allows multiple concurrent reads but does not tolerate concurrent read and write.
        // Which of the following strategies will you use to obtain best performance?

        //
        // Encapsulate the collection into another class and use ReadWriteLock to manage read and write access.
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
        // Which of the following methods are available in java.util.concurrent.ConcurrentMap in addition to the methods
        // provided by java.util.Map?

        Map<String, Object> map01 = new HashMap<>();

        // None of the above.

        // from Map not from ConcurrentMap
        map01.putIfAbsent("new1", new Object());

        // Explanation

        // ConcurrentMap is important for the exam. You should go the API description for this interface. In short:
        //
        // It is a Map providing additional atomic putIfAbsent, remove, and replace methods.
        //
        // Memory consistency effects: As with other concurrent collections, actions in a thread prior to placing an object into a ConcurrentMap
        // as a key or value happen-before actions subsequent to the access or removal of that object from the ConcurrentMap in another thread.
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
    static void test01_07() throws Exception {
        // What will the following code print?

        AtomicInteger ai = new AtomicInteger();

        Stream<String> stream = Stream.of("old", "king", "cole", "was", "a", "merry", "old", "soul").parallel();

        stream.filter(e -> {
            ai.incrementAndGet();
            return e.contains("o");

        }).allMatch(x -> x.indexOf("o") > 0);

        System.out.println("AI = " + ai);

        // Any number between 1 to 8
        //
        // Explanation
        // 1. In the given code, stream refers to a parallel stream. This implies that the JVM is free to break up the original stream into
        // multiple smaller streams, perform the operations on these pieces in parallel, and finally combine the results.
        //
        // 2. Here, the stream consists of 8 elements. It is, therefore, possible for a JVM running on an eight core machine to split this
        // stream into 8 streams (with one element each) and invoke the filter operation on each of them. If this happens, ai will
        // be incremented 8 times.
        //
        // 3. It is also possible that the JVM decides not to split the stream at all. In this case, it will invoke the filter predicate on
        // the first element (which will return true) and then invoke the allMatch predicate (which will return false because "old".indexOf("o") is 0).
        // Since allMatch is a short circuiting terminal operation, it knows that there is no point in checking other elements because the result
        // will be false anyway. Hence, in this scenario, ai will be incremented only once.
        //
        // 4. The number of pieces that the original stream will be split into could be anything between 1 and 8 and by applying the same logic
        // as above, we can say that ai will be incremented any number of times between 1 and 8.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following correctly identify the differences between a Callable and a Runnable?

        // A Callable cannot be passed as an argument while creating a Thread but a Runnable can be.
        // new Thread( aRunnable ); is valid. But new Thread( aCallable ); is not.
        //
        // A Callable needs to implement call() method while a Runnable needs to implement run() method.
        //
        // A Callable can return a value but a Runnable cannot.
        //
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following classes will you use to generate random int values from multiple threads without degrading performance?

        // java.util.concurrent.ThreadLocalRandom

        // int nextInt(int least, int bound) Returns a pseudorandom, uniformly distributed value between the given least value (inclusive) and bound (exclusive).
        // Example usage:

        int r = ThreadLocalRandom.current().nextInt(1, 11); // This will return a random int from 1 to 10

        // Explanation
        // Consider instead using ThreadLocalRandom in multithreaded designs.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given the following code:
        AtomicInteger status = new AtomicInteger(0);

        int oldstatus = status.get();
        /* valid code here */
        int newstatus = 10;

        // Assuming that an instance of this class is shared among multiple threads, you want to update the status to newstatus only if the
        // oldstatus has not changed. Which of the following lines of code will you use?

        status.compareAndSet(oldstatus, newstatus);

        // compareAndSet(expectedValue, newValue) is meant exactly for this purpose. It first checks if the current value is same as the
        // expected value and if so, updates to the new value.

        // Explanation
        // public final boolean compareAndSet(int expect, int update) Atomically sets the value to the given updated value if the current
        // value == the expected value. Parameters: expect - the expected value update - the new value Returns: true if successful.
        // False return indicates that the actual value was not equal to the expected value.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Which of the following statements are true regarding the Fork/Join framework?
        //
        // The worker threads in the ForkJoinPool extend java.lang.Thread and are created by a factory.
        // By default, they are created by the default thread factory but another factory may be passed in the constructor. They do extend Thread.
        //
        // One worker thread may steal work from another worker thread.
        // A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing: all threads in the pool 
        // attempt to find and execute subtasks created by other active tasks (eventually blocking waiting for work if none exist). 
        // This enables efficient processing when most tasks spawn other subtasks (as do most ForkJoinTasks).
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
