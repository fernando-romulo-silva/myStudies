package br.fernando.ch11_Concurrency_Objective.par01_apply_atomic_variables_and_locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test03 {

    // =========================================================================================================================================
    // Condition
    static void test01_01() throws Exception {

        // A Condition provides the equivalent of the traditional wait, notify, and notifyAll
        // methods. The traditional wait and notify methods allow developers to implement an
        // await/signal pattern.

        Lock lock = new ReentrantLock();

        Condition blockingPoolA = lock.newCondition();

        //--------------------------------------------------------
        // thread 1

        // Before the await method returns, the lock will be reacquired.
        lock.lock();

        try {
            // When your thread reaches a point where it must delay until another thread performs
            // an activity, you “await” the completion of that other activity.
            blockingPoolA.await(); // "wait" here! The lock associated with this Condition is atomically released!

            // work!

        } catch (InterruptedException ex) {
            // interrupted during await()
        } finally { // to ensure we unlock
            lock.unlock();
        }

        //--------------------------------------------------------
        // thread 2

        // In another thread, you perform the activity that the first thread was waiting on and
        // then signal that first thread to resume (return from the await method).
        // Part three of the Condition example is run in a different thread than part two.
        // This part causes the thread waiting in the second piece to wake up:

        lock.lock(); // same lock, okay?

        try {
            // work

            blockingPoolA.signalAll(); // wake all awaiting threads
            // You can also use the signal() method to wake up a single awaiting thread.
        } finally {
            lock.unlock(); // now an awoken thread can run
        }

        // One advantage of a Condition over the traditional wait/notify operations is that
        // multiple Conditions can exist for each Lock. A Condition is effectively a
        // waiting/blocking pool for threads.
    }

    // =========================================================================================================================================
    static void summary() {

        // ************************************************************************
        // Condition
        // ************************************************************************	
        //-------------------------------------
        // thread 1
        Lock lock = new ReentrantLock(); // all threads share the same lock

        Condition blockingPoolA = lock.newCondition(); // conditions too.

        lock.lock();

        try {
            blockingPoolA.await(); // "wait" here!

            // work!

        } catch (InterruptedException ex) {
            // interrupted during await()
        } finally { // to ensure we unlock
            lock.unlock();
        }

        //--------------------------------------------------------
        // thread 2

        lock.lock(); // same lock, okay?

        try {
            // work

            blockingPoolA.signalAll(); // wake all awaiting threads
            // You can also use the signal() method to wake up a single awaiting thread.
        } finally {
            lock.unlock(); // now an awoken thread can run
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
