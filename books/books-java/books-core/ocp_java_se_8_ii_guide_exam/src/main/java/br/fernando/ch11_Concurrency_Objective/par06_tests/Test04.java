package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04 {

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
        // (Assume that no other calls have been made on the MyCache instance.)
        //
        // Both the threads will complete their operations successfully without getting any exception.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Given Counter class

        // This class is supposed to keep an accurate count for the number of times the increment method is called.
        // Several classes share this class and call its increment method. What should be inserted at //1 ?
    }

    static class Counter {

        static AtomicInteger ai = new AtomicInteger(0);

        public static void increment() {
            // 1
            ai.incrementAndGet();
            // or
            ai.addAndGet(1);
        }
        // other valid code
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
        // Note that the concurrent use of the same 'java.util.Random' instance across threads may encounter contention and consequent poor performance.
        // Consider instead using ThreadLocalRandom in multithreaded designs.
        // There are a few basic questions about generating random numbers in the exam.
        // Besides Math.random() method, you should know about ThreadLocalRandom class.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
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
    // Given
    static ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String, Object>();

    static void test01_09() throws Exception {
        chm.put("a", "aaa");
        chm.put("b", "bbb");
        chm.put("c", "ccc");

        new Thread() {

            public void run() {
                Iterator<Entry<String, Object>> it = Test04.chm.entrySet().iterator();
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
                Iterator<Entry<String, Object>> it = Test04.chm.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> en = it.next();
                    System.out.print(en.getKey() + ", ");
                }
            }
        }.start();

        // Which of the following are possible outputs when the above program is run?

        // It may print any combination except: a, or b, or a, b, or b, a,
        // This is correct because the order of iteration is not known and so the thread that removes "a" and "b", may remove them in any order.
        // Thus, the iterator thread may or may not see "a" and/or "b" through its Iterator.
        // However, "c" is never removed from the map and so c, will always be printed.

    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // You have been given an instance of an Executor and you use that instance to execute tasks. How many threads will be created 
        // for executing these tasks by the Executor?

        // Number of threads created by the Executor depends on how the Executor instance was created.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
