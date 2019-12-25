package br.fernando.ch11_Concurrency_Objective.par01_apply_atomic_variables_and_locks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test04 {

    // =========================================================================================================================================
    // ReentrantReadWriteLock
    static void test01_01() throws Exception {
        // A ReentrantReadWriteLock is not actually a Lock; it implements the ReadWriteLock interface.
        // What a ReentrantReadWriteLock does is produce two specialized Lock instances, one to a read lock and the other to a write lock.

        ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();

        Lock readLock = rw1.readLock();

        Lock writeLock = rw1.writeLock();

        // These two locks are a matched set—one cannot be held at the same time as the other (by different threads).
        // What makes these locks unique is that multiple threads can hold the read lock at the same time, but only one thread can hold
        // the write lock at a time.

    }

    static class MaxValueCollection {

        private List<Integer> integers = new ArrayList<>();

        private ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();

        public void add(Integer i) {
            Lock writeLock = rw1.writeLock(); // one thread can WRITE at a time

            writeLock.lock();

            try {
                integers.add(i);
            } finally {
                writeLock.unlock();
            }
        }

        public int findMax() {
            Lock readLock = rw1.readLock(); // one thread can READ at a time

            readLock.lock();

            try {
                return Collections.max(integers);
            } finally {
                readLock.unlock();
            }
        }

        // Instead of wrapping a collection with Lock objects to ensure thread safety, you can
        // use one of the thread-safe collections you’ll learn about in the next section.
    }

    // =========================================================================================================================================
    // Buffer Example
    static void test02() {
        final Buffer buffer = new Buffer(10);

        Runnable writer1 = () -> {
            System.out.println("writer1 starts");

            sleepSeconds(6);

            buffer.putItem("item1");
            buffer.putItem("item2");

            System.out.println("writer1 ends");
        };

        Runnable writer2 = () -> {

            System.out.println("writer2 starts");

            sleepSeconds(6);

            buffer.putItem("item3");
            buffer.putItem("item4");

            System.out.println("writer2 ends");
        };

        Runnable read1 = () -> {
            System.out.println(buffer.getRecent());
        };

        Runnable read2 = () -> {
            System.out.println(buffer.getRecent());
        };

        final ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.execute(writer1);
        executor.execute(writer2);
        executor.execute(read1);
        executor.execute(read2);
    }

    public static class Buffer {

        private final int capacity;

        private final Deque<String> recent;

        private int discarded;

        private final Lock readLock;

        private final Lock writeLock;

        public Buffer(int capacity) {
            this.capacity = capacity;
            recent = new ArrayDeque<>(capacity);

            // ------
            final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
            readLock = rwLock.readLock();
            writeLock = rwLock.writeLock();
        }

        public void putItem(String item) {
            writeLock.lock();

            try {
                while (recent.size() >= capacity) {
                    recent.removeFirst();
                    ++discarded;
                }
                recent.addLast(item);
            } finally {
                writeLock.unlock();
            }
        }

        public List<String> getRecent() {
            readLock.lock();

            try {
                final ArrayList<String> result = new ArrayList<>();
                result.addAll(recent);
                return result;
            } finally {
                readLock.unlock();
            }
        }

        public int getDiscardedCount() {
            readLock.lock();
            try {
                return discarded;
            } finally {
                readLock.unlock();
            }
        }

        public int getTotal() {
            readLock.lock();
            try {
                return discarded + recent.size();
            } finally {
                readLock.unlock();
            }
        }

        public void flush() {
            writeLock.lock();
            try {
                discarded += recent.size();
                recent.clear();
            } finally {
                writeLock.unlock();
            }
        }
    }

    static void sleepSeconds(int value) {
        try {
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
        }
    }

    // =========================================================================================================================================
    static void summary() {
        // ****************************************************************************
        // ReentrantReadWriteLock
        // ****************************************************************************
        // A ReentrantReadWriteLock is not actually a Lock; it implements the ReadWriteLock interface.
        // What a ReentrantReadWriteLock does is produce two specialized Lock instances, one to a read lock and the other to a write lock.

        ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();

        Lock readLock = rw1.readLock();

        Lock writeLock = rw1.writeLock();

        // lock at Buffer class

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test02();
    }
}
