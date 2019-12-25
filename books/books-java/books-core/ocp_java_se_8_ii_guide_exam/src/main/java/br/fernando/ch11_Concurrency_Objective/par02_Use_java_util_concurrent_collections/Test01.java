package br.fernando.ch11_Concurrency_Objective.par02_Use_java_util_concurrent_collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.PriorityBlockingQueue;

public class Test01 {

    // =========================================================================================================================================
    // Use java.util.concurrent Collections
    static void test01_01() {
        // ArrayListRunnable01 alr01 = new ArrayListRunnable01(); // Exception will be throwed

        ArrayListRunnable02 alr02 = new ArrayListRunnable02();

        Thread td01 = new Thread(alr02);
        Thread td02 = new Thread(alr02);

        td01.start();
        td02.start();

        // Using synchronization to safeguard a collection creates a performance bottleneck and reduces the liveness of
        // your application. The java.util.concurrent package provides several types of collections that are thread-safe
        // but do not use coarse-grained synchronization.
    }

    static class ArrayListRunnable01 implements Runnable {

        // shared by all threads
        private List<Integer> list = new ArrayList<>();

        public ArrayListRunnable01() {
            // add some elements
            for (int i = 0; i < 10_000; i++) {
                list.add(i);
            }
        }

        // might run concurrently, you cannot be sure to be safe you must assume it does
        public void run() {
            String tName = Thread.currentThread().getName();

            while (!list.isEmpty()) {
                System.out.println(tName + " removed " + list.remove(0));
            }
        }
    }

    static class ArrayListRunnable02 implements Runnable {

        // shared by all threads
        private List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        public ArrayListRunnable02() {
            // add some elements
            for (int i = 0; i < 10_000; i++) {
                list.add(i);
            }
        }

        // might run concurrently, you cannot be sure to be safe you must assume it does
        public void run() {
            String tName = Thread.currentThread().getName();

            while (!list.isEmpty()) {
                System.out.println(tName + " removed " + list.remove(0));
            }
        }
    }

    // =========================================================================================================================================
    // Copy-on-Write Collections
    static void test01_02() {
        // As its name implies, a CopyOnWriteArrayList will never modify its internal array of data. Any mutating
        // operations on the List (add, set, remove, etc.) will cause a new modified copy of the array to be created,
        // which will replace the original read-only array.
        //
        // Remember that read-only (immutable) objects are always thread-safe.

        // A for-each loop uses an Iterator when executing, so it is safe to use with a copy-on-write collection,
        // unlike a traditional for loop.
        //
        //
        List<String> list01 = new ArrayList<>();

        for (String string : list01) { // use this

        }

        for (int i = 0; i < list01.size(); i++) { // not this

        }

        // The java.util.concurrent package provides two copy-on-write-based collections: CopyOnWriteArrayList and CopyOnWriteArraySet .
        // Use the copy-on-write collections when your data sets remain relatively small and the number of read operations and traversals
        // greatly out number modifications to the collections.

        List<String> list02 = new CopyOnWriteArrayList<>();
    }

    // =========================================================================================================================================
    // Concurrent Collections
    static void test01_03() {
        // The java.util.concurrent package also contains several concurrent collections:
        //
        // ConcurrentHashMap, ConcurrentLinkedDeque, ConcurrentLinkedQueue, ConcurrentSkipListMap, ConcurrentSkipListSet
        //
        // Be aware that an Iterator for a concurrent collection is weakly consistent; it can return elements from the point in
        // time the Iterator was created or later.
        //
        // This means that while you are looping through a concurrent collection, you might observe elements that are being inserted by other threads.
        // In addition, you may observe only some of the elements that another thread is inserting with methods such as addAll
        // when concurrently reading from the collection.
    }

    // =========================================================================================================================================
    // Blocking Queues
    static void test01_04() {
        // A BlockingQueue is a type of shared collection that is used to exchange data between two or more threads while causing one or more of the
        // threads to wait until the point in time when the data can be exchanged. One use case of a BlockingQueue is called the producer-consumer problem.
        // In a producer-consumer scenario, one thread produces data, then adds it to a queue, and another thread must consume the data from the queue.
        //
        // ArrayBlockingQueue
        // LinkedBlockingDeque
        // LinkedBlockingQueue
        // PriorityBlockingQueue
        // DelayQueue
        // LinkedTransferQueue
        // SynchronousQueue

    }

    // =========================================================================================================================================
    // Bounded Queues
    static void test01_05() {
        // ArrayBlockingQueue, LinkedBlockingDeque, and LinkedBlockingQueue support a bounded capacity and will block on put(e) and similar
        // operations if the collection is full. LinkedBlockingQueue is optionally bounded, depending on the constructor you use.

        BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(1);
        try {
            bq.put(42);
            bq.put(43); // blcoks until previous value is removed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // =========================================================================================================================================
    // Summary
    static void summary() {
        // ****************************************************************************
        // Synchronized Collections, List, Sets
        // ****************************************************************************
        // All methods are synchronized
        List<Integer> list = Collections.synchronizedList(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
        Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
        Map<Integer, String> map = Collections.synchronizedMap(new HashMap<>());

        // ****************************************************************************
        // Copy-on-Write Collections (Only list and set)
        // ****************************************************************************
        // CopyOnWriteArray Collections will never modify its internal array of data. Any mutating operations on the List (add, set, remove, etc.)
        // will cause a new modified copy of the array to be created, which will replace the original read-only array.
        // Another important thing to remember is that ITERATORS of this collection class doesn't support remove() operation, trying to remove an
        // element while iterating will result in UnSupportedOperationException.

        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4));
        copyOnWriteArrayList.remove(0); // it works

        CopyOnWriteArraySet<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>(Arrays.asList(1, 2, 3, 4));
        copyOnWriteArraySet.remove(1);

        // ************************************************************************************************
        // Concurrent Collections - Collections with thread-safety and memory-consistent atomic operations.
        // ************************************************************************************************
        // This collections providees a better scalability than there synchronized counter part.
        // Iterator of ConcurrentHashMap are fail-safe iterators which doesn’t throw ConcurrentModificationException
        // thus eliminates another requirement of locking during iteration which result in further scalability and performance.

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        concurrentHashMap.putIfAbsent("", "Empty key");

        concurrentHashMap.compute("", (k, v) -> "now, the value is this");

        // ***************************************************************************************************************************************
        // Blocking Queues - Consider using BlockingQueue to solve Producer/Consumer problem in Java instead of writing your won wait-notify code.
        // ***************************************************************************************************************************************
        PriorityBlockingQueue<String> priorityBlockingQueue = new PriorityBlockingQueue<>(10, (v1, v2) -> v1.length() - v2.length());

        priorityBlockingQueue.add("one");
        priorityBlockingQueue.add("two");

        // ****************************************************************************
        // Bounded Queues
        // ****************************************************************************
        // ArrayBlockingQueue, LinkedBlockingDeque, and LinkedBlockingQueue support a bounded capacity and will block on put(e) and similar
        // operations if the collection is full. LinkedBlockingQueue is optionally bounded, depending on the constructor you use.

        BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(1);
        try {
            bq.put(42);
            bq.put(43); // blcoks until previous value "42" is removed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        summary();
    }
}
