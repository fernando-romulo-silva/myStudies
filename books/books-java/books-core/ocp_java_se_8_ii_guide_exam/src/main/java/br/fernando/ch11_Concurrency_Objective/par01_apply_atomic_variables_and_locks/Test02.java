package br.fernando.ch11_Concurrency_Objective.par01_apply_atomic_variables_and_locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test02 {

    // =========================================================================================================================================
    // Locks
    static void test01_01() {
        // You use java.util.concurrent.locks classes and traditional monitor locking (the synchronized keyword) for roughly the same
        // purpose: creating segments of code that require exclusive execution (one thread at a time).

        // So why would you use these newer locking classes? The java.util.concurrent.locks package provides :

        // 1 - The ability to duplicate traditional synchronized blocks.
        //
        // 2 - Nonblock scoped locking—obtain a lock in one method and release it in another (this can be dangerous, though).
        //
        // 3 - Multiple wait/notify/notifyAll pools per lock—threads can select which pool (Condition) they wait on.
        //
        // 4 - The ability to attempt to acquire a lock and take an alternative action if locking fails.
        //
        // 5 - An implementation of a multiple-reader, single-writer lock
    }

    // =========================================================================================================================================
    // ReentrantLock
    static void test01_02() {
        // To demonstrate the use of Lock, we will first duplicate the functionality of a basic traditional synchronized block.
        Object obj = new Object();

        synchronized (obj) { // traditional locking, blocks until acquired work releases lock automatically
            // do work here
            System.out.println(obj);
        }

        // Here is an equivalent piece of code using the java.util.concurrent.locks package.
        Lock lock01 = new ReentrantLock();
        lock01.lock(); // blocks until acquired, the current thread will sleep 

        try {
            // do work here
            System.out.println(obj);
        } finally { // to ensure we unlock
            lock01.unlock(); // must manually release
        }

        // ---------------------------------------------------------------------------------------------
        // One of the very powerful features is the ability to attempt (and fail) to
        // acquire a lock. With traditional synchronization, once you hit a synchronized block,
        // your thread either immediately acquires the lock or blocks until it can.

        Lock lock02 = new ReentrantLock();
        boolean locked02 = lock02.tryLock(); // try without waiting

        if (locked02) {
            try {
                // do work here
            } finally { // to ensure we unlock
                lock01.unlock(); // must manually release
            }
        }

        // There is also a variation of the tryLock method that allows you to specify
        // an amount of time you are willing to wait to acquire the lock:

        Lock lock03 = new ReentrantLock();

        try {
            boolean locked03 = lock03.tryLock(3, TimeUnit.SECONDS);

            if (locked03) {
                try {
                    // work
                } finally { // to ensure we unlock
                    lock03.unlock();
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // =========================================================================================================================================
    // ReentrantLock 02
    static void test01_03() throws Exception {
        // Another benefit of the tryLock method is deadlock avoidance. With traditional
        // synchronization, you must acquire locks in the same order across all threads.
        // For example, if you have two objects to lock against:

        Object o1 = new Object();
        Object o2 = new Object();

        // and synchronize using the internal lock flags o both objects:

        synchronized (o1) {
            // thread A could pause here
            synchronized (o2) {
                // work
            }
        }

        // Looking at a similar example using a ReentrantLock, start by creating two locks:
        Lock l01 = new ReentrantLock();
        Lock l02 = new ReentrantLock();

        boolean aq01 = l01.tryLock();
        boolean aq02 = l02.tryLock();

        try {

            if (aq01 && aq02) {
                // work
            } else {
                // You can avoid livelock in this scenario by introducing a short random delay
                // with Thread.sleep(int) any time you fail to acquire both locks.
                Thread.sleep(1000);
            }

        } finally {
            if (aq02) {
                l02.unlock(); // don't unlock if not locked!
            }

            // If a thread attempts to release a lock that it does not own,
            // an IllegalMonitorStateException will be thrown.
            if (aq01) {
                l01.unlock();
            }
        }
    }

    // =========================================================================================================================================
    static void summary() {
        // **********************************************************************
        // Lock
        // **********************************************************************
        // 1 - The ability to duplicate traditional synchronized blocks.
        //
        // 2 - Nonblock scoped locking—obtain a lock in one method and release it in another (this can be dangerous, though).
        //
        // 3 - Multiple wait/notify/notifyAll pools per lock—threads can select which pool (Condition) they wait on.
        //
        // 4 - The ability to attempt to acquire a lock and take an alternative action if locking fails.
        //
        // 5 - An implementation of a multiple-reader, single-writer lock

        // **********************************************************************
        // ReentrantLock
        // **********************************************************************
        // -------------------------------
        // To demonstrate the use of Lock, we will first duplicate the functionality of a basic traditional synchronized block.
        Object obj = new Object();

        synchronized (obj) { // traditional locking, blocks until acquired work releases lock automatically
            // do work here
            System.out.println(obj);
        }

        // Here is an equivalent piece of code using the java.util.concurrent.locks package.
        Lock lock01 = new ReentrantLock();
        lock01.lock(); // blocks until acquired

        try {
            // do work here
            System.out.println(obj);
        } finally { // to ensure we unlock
            lock01.unlock(); // must manually release
        }

        // -------------------------------
        // try to acquire a lock
        Lock lock03 = new ReentrantLock();

        try {
            boolean locked03 = lock03.tryLock(3, TimeUnit.SECONDS); // wait for 3 seconds!

            if (locked03) {
                try {
                    // work
                } finally { // to ensure we unlock
                    lock03.unlock();
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        //---------------------------------
        // Multiple locks

        // Another benefit of the tryLock method is deadlock avoidance. With traditional
        // synchronization, you must acquire locks in the same order across all threads.
        // For example, if you have two objects to lock against:

        Object o1 = new Object();
        Object o2 = new Object();

        // and synchronize using the internal lock flags o both objects:

        synchronized (o1) {
            // thread A could pause here
            synchronized (o2) {
                // work
            }
        }

        // Looking at a similar example using a ReentrantLock, start by creating two locks:
        Lock l01 = new ReentrantLock();
        Lock l02 = new ReentrantLock();

        boolean aq01 = l01.tryLock();
        boolean aq02 = l02.tryLock();

        try {

            if (aq01 && aq02) {
                // work
            } else {
                // You can avoid livelock in this scenario by introducing a short random delay
                // with Thread.sleep(int) any time you fail to acquire both locks.
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (aq02) {
                l02.unlock(); // don't unlock if not locked!
            }

            // If a thread attempts to release a lock that it does not own,
            // an IllegalMonitorStateException will be thrown.
            if (aq01) {
                l01.unlock();
            }
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
