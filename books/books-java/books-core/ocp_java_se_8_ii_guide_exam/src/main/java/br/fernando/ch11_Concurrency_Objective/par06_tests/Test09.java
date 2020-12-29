package br.fernando.ch11_Concurrency_Objective.par06_tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test09 {

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
        // You have a collection (say, an ArrayList) which is read by multiple reader threads and which is modified by a single writer thread.
        // The collection allows multiple concurrent reads but does not tolerate concurrent read and write.
        // Which of the following strategies will you use to obtain best performance?

        //
        // Encapsulate the collection into another class and use ReadWriteLock to manage read and write access.
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
    static void test01_05() throws Exception {
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
        // Note that ReentrantLock implements Lock
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {

        // Consider the following program that computes the sum of all integers in a given array of integers:

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

        // What changes must be done together so that it will work as expected?
        //
        // The compute method must be changed to return a value.
        //
        // The values returned by the newtask1 and newtask2 should be added and returned.

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };

        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedTask st = new ComplicatedTask(ia, 0, 6);

        int sum = fjp.invoke(st);
        System.out.println("sum = " + sum);
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
