package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // You want to execute your tasks after a given delay. Which ExecutorService would you use?

        // ScheduledExecutorService

        // Explanation
        // Following is a usage example that sets up a ScheduledExecutorService to beep every ten seconds for an hour:

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(() -> System.out.println("beep"), 10, 10, TimeUnit.SECONDS);
        scheduler.schedule(() -> beeperHandle.cancel(true), 60 * 60, TimeUnit.SECONDS);
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following classes will you use to generate random int values from multiple threads without degrading performance?

        // java.util.concurrent.ThreadLocalRandom
        //
        // Returns a pseudorandom, uniformly distributed value between the given least value (inclusive) and bound (exclusive).

        // This will return a random int from 1 to 10.
        int r = ThreadLocalRandom.current().nextInt(1, 11);
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
        // You have a collection (say, an ArrayList) which is read by multiple reader threads and which is modified by a single writer thread.
        // The collection allows multiple concurrent reads but does not tolerate concurrent read and write.
        // Which of the following strategies will you use to obtain best performance?

        //
        // Encapsulate the collection into another class and use ReadWriteLock to manage read and write access.

    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {

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
    static void test01_07() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following methods are available in java.util.concurrent.ConcurrentMap in addition to the methods provided by java.util.Map?
        //
        // None of the above.

        // from Map not from ConcurrentMap
        Map<String, Object> map01 = new HashMap<>();
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
    static void test01_09() throws Exception {
        // Consider the following code:
        class MyCache {

            private CopyOnWriteArrayList<String> cal = new CopyOnWriteArrayList<>();

            public void addData(List<String> list) {
                cal.addAll(list);
            }

            public Iterator getIterator() {
                return cal.iterator();
            }
        }

        // Given that one thread calls the addData method on an instance of the above class and another thread calls the getIterator method on
        // the same instance at the same time and starts iterating through its values, which of the following options are correct?

        // Both the threads will complete their operations successfully without getting any exception
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    static void test01_11() throws Exception {
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

            private static final long serialVersionUID = 1L;

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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
