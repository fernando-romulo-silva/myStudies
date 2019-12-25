package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test10 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        // You want to execute a task that returns a result without blocking.
        // Which of the following types from java.util.concurrent package will be required to achieve this?

        // ExecutorService
        // ExecutorService extends Executor and provide a submit(Callable ) method:
        // <T> Future<T> submit(Callable<T> task)
        // Submits a value-returning task for execution and returns a Future representing the pending results of the task.
        //
        // Executors
        // Executors is a utility class that contains factory methods to create various kinds of ExecutorService implementations.
        //
        // Callable
        // The difference between Callable and Runnable is that Callable.call() returns a value.
        // It may also throw an Exception. Runnable.run() cannot return any value and cannot throw any Exception.
        //
        // Future
        // When you submit a task (i.e. a Callable) to an ExecutorService using ExecutorService.submit(Callable ) method,
        // it returns a Future object immediately without blocking.
        // You can check the status of the Future object later to get the actual result once it is done.

        // Explanation
        // The following is a sample code that can be used to create and execute a task that returns the result without blocking.
        // import java.util.concurrent.Callable;
        // import java.util.concurrent.ExecutorService;
        // import java.util.concurrent.Executors;
        // import java.util.concurrent.Future;

        // Create a thread pool of two threads
        ExecutorService es = Executors.newFixedThreadPool(2);

        class MyTask implements Callable<String> {

            public String call() {
                try {
                    // simulate a long running task;
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return "Data from " + Thread.currentThread().getName();
            }
        }

        MyTask task1 = new MyTask();
        Future<String> result = es.submit(task1);
        System.out.println("Proceeding without blocking... ");
        while (!result.isDone()) {
            try {
                // check later
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Result is " + result.get());
        es.shutdown();
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Consider the following code:

        class MyCache {

            private CopyOnWriteArrayList<String> cal = new CopyOnWriteArrayList<>();

            public void addData(List<String> list) {
                cal.addAll(list);
            }

            public int getCacheSize() {
                return cal.size();
            }
        }

        //
        // Given that one thread calls the addData method on an instance of the above class with a List containing 10 Strings
        // and another thread calls the getCacheSize method on the same instance at the same time, which of the following options are correct?
        //
        // (Assume that no other calls have been made on the MyCache instance.)
        //
        // The getCacheSize call may return either 0 or 10.
        //
        // All modification operations of a CopyOnWriteArrayList are considered atomic.
        // So the thread that calls size() will either see no data in the list or will see all the elements added to the list.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Identify correct statements about the fork/join framework?

        // 1º It is well suited for computation intensive tasks that can be broken into smaller pieces recursively.

        // 2º A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
        // Note that the concurrent use of the same 'java.util.Random' instance across threads may encounter contention and consequent poor performance.
        // Consider instead using ThreadLocalRandom in multithreaded designs.
        // There are a few basic questions about generating random numbers in the exam.
        // Besides Math.random() method, you should know about ThreadLocalRandom class.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
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
