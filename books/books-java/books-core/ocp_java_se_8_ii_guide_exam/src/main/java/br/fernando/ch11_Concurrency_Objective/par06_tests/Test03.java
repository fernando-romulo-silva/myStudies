package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {

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
    static void test01_02() throws Exception {
        // Identify correct statements about the fork/join framework?

        // 1º It is well suited for computation intensive tasks that can be broken into smaller pieces recursively
        //
        // 2º A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing.
        // What this really means is that if worker thread is done with a task, it will pick up a new task irrespective of which thread created
        // that task. In a fork/join framework, any worker thread may spawn new tasks and it is not necessary that the tasks spawned by a threads
        // will be executed by that particular thread. They can be executed by any available thread.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Which of the following options correctly makes use of java.util.concurrent.Callable?

        // Since MyTask binds Callable to String, the return value of call() must be a String.

        class MyTask implements Callable<String> {

            public String call() throws Exception {
                // do something
                return "Data from callable";
            }
        }

        Future<String> result = Executors.newSingleThreadExecutor().submit(new MyTask());
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What can be inserted at //2 so that 6 will be printed by the following code?

        AtomicInteger ai = new AtomicInteger(5);

        // int x = ai.getAndIncrement(); // getAndIncrement() is a valid method call and it will increment ai to 6 but it will return the old value i.e. 5.

        int x1 = ai.incrementAndGet();

        int x2 = ai.addAndGet(1);

        // int x = ai.getAndSet(6); // ai.getAndSet(int ) is a valid method call and it will set ai to 6 but it will return the old value i.e. 5.

        System.out.println(x1);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Identify correct statements about the fork/join framework?

        // 1º It is well suited for computation intensive tasks that can be broken into smaller pieces recursively.

        // 2º A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Which of the following methods are available in java.util.concurrent.ConcurrentMap in addition to the methods provided by java.util.Map?

        // None of the above.

        // Explanation
        // It is a Map providing additional atomic putIfAbsent, remove, and replace methods.
        // Memory consistency effects: As with other concurrent collections, actions in a thread prior to placing an object into a ConcurrentMap
        // as a key or value happen-before actions subsequent to the access or removal of that object from the ConcurrentMap in another thread.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which of the following classes will you use to generate random int values from multiple threads without degrading performance?

        // java.util.concurrent.ThreadLocalRandom

        // int nextInt(int least, int bound) Returns a pseudorandom, uniformly distributed value between the given least value (inclusive) and bound (exclusive).
        // Example usage:

        int r = ThreadLocalRandom.current().nextInt(1, 11); // This will return a random int from 1 to 10

        // Explanation
        // Consider instead using ThreadLocalRandom in multithreaded designs.

    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {

        class Student {
        }

        // Given:
        List<Student> sList = new CopyOnWriteArrayList<Student>();

        // Which of the following statements are correct?

        // Multiple threads can safely add and remove objects from sList simultaneously.

        // Explanation
        // A thread-safe variant of ArrayList in which all mutative operations (add, set, and so on) are implemented by making
        // a fresh copy of the underlying array.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_09();
    }

}
