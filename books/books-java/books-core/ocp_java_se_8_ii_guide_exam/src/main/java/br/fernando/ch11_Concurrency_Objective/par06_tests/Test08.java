package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test08 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Consider the Student code:

        class Student {

            private Map<String, Integer> marksObtained = new HashMap<String, Integer>();

            private ReadWriteLock lock = new ReentrantReadWriteLock();

            public void setMarksInSubject(String subject, Integer marks) {
                // 1 INSERT CODE HERE
                lock.writeLock().lock();

                try {
                    marksObtained.put(subject, marks);
                } finally {
                    // 2 INSERT CODE HERE
                    lock.writeLock().unlock();
                }

            }

            public double getAverageMarks() {

                lock.readLock().lock(); // 3

                double sum = 0.0;
                try {
                    for (Integer mark : marksObtained.values()) {
                        sum = sum + mark;
                    }
                    return sum / marksObtained.size();
                } finally {
                    lock.readLock().unlock();// 4
                }
            }
        }

        // What should be inserted at //1 and //2?

        // lock.writeLock().lock();
        // and
        // lock.writeLock().unlock();

        // Explanation
        //
        // From a ReadWriteLock, you can get one read lock (by calling lock.readLock() ) and one write lock (by calling lock.writeLock()).
        //
        // Even if you call these methods multiple times, the same lock is returned.
        // A read lock can be locked by multiple threads simultaneously (by calling lock.readLock().lock() ), if the write lock is free.
        //
        // If the write lock is not free, a read lock cannot be locked. The write lock can be locked (by calling lock.writeLock().lock() )
        // only by only one thread and only when no thread already has a read lock or the write lock. In other words, if one thread is reading,
        // other threads can read, but no thread can write. If one thread is writing, no other thread can read or write.
        //
        // Methods that do not modify the collection (i.e. the threads that just "read" a collection) should acquire a read lock and threads
        // that modify a collection should acquire a write lock.
        //
        // The benefit of this approach is that multiple reader threads can run without blocking if the write lock is free.
        // This increases performance for read only operations. The following is the complete code that you should try to run:

        final Student s = new Student();

        // create one thread that keeps adding marks
        new Thread() {

            public void run() {
                int x = 0;
                while (true) {
                    int m = (int) (Math.random() * 100);
                    s.setMarksInSubject("Sub " + x, m);
                    x++;
                }
            }
        }.start();

        // create 5 threads that get average marks
        for (int i = 0; i < 5; i++) {
            new Thread() {

                public void run() {
                    while (true) {
                        double av = s.getAverageMarks();
                        System.out.println(av);
                    }
                }
            }.start();
        }

        // Note that if you remove the line //1, //2, //3, and //4, (i.e. if you don't use any locking), you will see a ConcurrentModificationException.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Identify correct statements about the fork/join framework?

        // 1º It is well suited for computation intensive tasks that can be broken into smaller pieces recursively.

        // 2º A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What can be inserted at //2 so that 6 will be printed by the following code?

        AtomicInteger ai = new AtomicInteger(5);

        // 2 INSERT CODE HERE
        int x = ai.incrementAndGet();

        // The ai.incrementAndGet method atomically increments the current value by 1 and returns the new value.
        // Therefore, 5 will be incremented by 1 to 6 and 6 will be returned.

        // or

        x = ai.addAndGet(1);

        // The addAndGet method atomically adds the given value to the current value and returns the new value.
        // Here, we are passing 1. So 1 will be added to 5 and 6 will be returned.

        System.out.println(x);
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Complete the following pseudo code for the compute method of a RecursiveAction:

        // processDirectly(currentDataSet); //Line 10
        // splitDataSet(currentDataSet); //Line 13
        // invokeAll(subactions); //Line 15

        // Explanation
        //
        // This is the general logic of how the fork/join frame work is used:
        //
        // 1. First check whether the task is small enough to be performed directly without forking. If so, perform it without forking.
        //
        // 2. If no, then split the task into multiple small tasks (at least 2) and submit the subtasks back to the pool using invokeAll(list of tasks).
    }

    static class RecursiveAction {

        private DataSet currentDataSet;

        public RecursiveAction(DataSet currentDataSet) {
            super();
            this.currentDataSet = currentDataSet;
        }

        void compute() {
            if (isSmallEnough(currentDataSet)) {
                processDirectly(currentDataSet); // LINE 10
            } else {
                List<DataSet> list = splitDataSet(currentDataSet); // LINE 13

                // build MyRecursiveAction objects for each DataSet
                RecursiveAction left = new RecursiveAction(list.get(0));
                RecursiveAction right = new RecursiveAction(list.get(1));

                List<RecursiveAction> subactions = Arrays.asList(left, right);
                invokeAll(subactions);// LINE 15
            }
        }
    }

    static class DataSet {

    }

    static boolean isSmallEnough(DataSet currentDataSet) {
        return false;
    }

    static void processDirectly(DataSet currentDataSet) {

    }

    static List<DataSet> splitDataSet(DataSet currentDataSet) {
        return null;
    }

    static void invokeAll(List<RecursiveAction> actions) {
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
        // You want to execute your tasks after a given delay. Which ExecutorService would you use?

        // ScheduledExecutorService
        // An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.
        // The schedule methods create tasks with various delays and return a task object that can be used to cancel or check execution.
        //
        // Explanation
        // Following is a usage example that sets up a ScheduledExecutorService to beep every ten seconds for an hour:

        new BeeperControl().beepForAnHour();
    }

    static class BeeperControl {

        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        public void beepForAnHour() {

            final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(() -> System.out.println("beep"), 10, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> beeperHandle.cancel(true), 60 * 60, TimeUnit.SECONDS);
        }
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given:

        class Counter {

            // 1
            AtomicInteger count = new AtomicInteger(0);

            public void increment() {
                // 2
                count.incrementAndGet();
            }

            // other valid code
        }

        // This class is supposed to keep an accurate count for the number of times the increment method is called. Several classes share an
        // instance of this class and call its increment method. What should be inserted at //1 and //2?

        // AtomicInteger count = new AtomicInteger(0); at //1
        // count.incrementAndGet(); at //2
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
        // Note that the concurrent use of the same 'java.util.Random' instance across threads may encounter contention and consequent poor performance.
        // Consider instead using ThreadLocalRandom in multithreaded designs.
        // There are a few basic questions about generating random numbers in the exam.
        // Besides Math.random() method, you should know about ThreadLocalRandom class.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
