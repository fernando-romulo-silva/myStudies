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
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
        // Give
        final ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String, Object>();

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

        // It may print any combination except: a, or b, or a, b, or b, a,
        // This is correct because the order of iteration is not known and so the thread that removes "a" and "b", may remove them in any order.
        // Thus, the iterator thread may or may not see "a" and/or "b" through its Iterator. However, "c" is never removed from the
        // map and so c, will always be printed.

        // Explanation
        // An important thing to know about the Iterators retrieved from a ConcurrentHashMap is that they are backed by that ConcurrentHashMap,
        // which means any operations done on the ConcurrentHashMap instance may be reflected in the Iterator.

        // The view's iterator is a "weakly consistent" iterator that will never throw ConcurrentModificationException, and guarantees to traverse
        // elements as they existed upon construction of the iterator, and may (but is not guaranteed to) reflect any modifications subsequent to construction.

        // only for wait the end
        Thread.sleep(10000);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given Counter

        // This class is supposed to keep an accurate count for the number of times the increment method is called.
        // Several classes share an instance of this class and call its increment method.
        // What should be inserted at //1 and //2?

        // AtomicInteger count = new AtomicInteger(0); at //1
        // count.incrementAndGet(); at //2
    }

    static class Counter {

        // 1
        AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            // 2
            count.incrementAndGet();
        }
        // other valid code
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_09() throws Exception {
        // Consider the following code,

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
    static void test01_10() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_04();
    }
}
