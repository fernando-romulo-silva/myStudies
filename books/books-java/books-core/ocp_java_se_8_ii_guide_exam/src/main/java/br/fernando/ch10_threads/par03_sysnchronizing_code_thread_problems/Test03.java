package br.fernando.ch10_threads.par03_sysnchronizing_code_thread_problems;

import java.util.HashSet;
import java.util.Set;

public class Test03 {

    // =========================================================================================================================================
    // Thread Deadlock
    static void test01_01() {
        // Deadlock occurs when two threads are blocked, with each waiting for the other’s lock. Neither
        // can run until the other gives up its lock, so they’ll sit there forever.
    }

    private static class DeadlockRisk {

        private Resource resourceA = new Resource();

        private Resource resourceB = new Resource();

        public int read() {
            synchronized (resourceA) { // May deadlock here
                synchronized (resourceB) {
                    return resourceB.value + resourceA.value;
                }
            }
        }

        public void write(int a, int b) {
            synchronized (resourceB) { // May deadlock here
                synchronized (resourceA) {
                    resourceA.value = a;
                    resourceB.value = b;
                }
            }
        }
    }

    private static class Resource {
        public int value;
    }

    // =========================================================================================================================================
    // Thread Livelock
    static void test01_02() {
        // Livelock is similar to deadlock, except that the threads aren’t officially dead; they’re just too busy to make progress.
        //
        // Livelock occurs if thread 1 tries to get lock 2 when thread 2 has lock 2, and thread 2
        // tries to get lock 1 when thread 1 has lock 1; they both free up the locks they have, so
        // then thread 1 and thread 2 are both waiting on the other thread to get the lock each
        // wants and then both get their locks again and go back to trying to get the lock the other
        // thread has…over and over and over again.
    }

    // =========================================================================================================================================
    // Thread Starvation
    static void test01_03() {
        // Starvation is when a thread is unable to make progress because it cannot get access to a shared resource that other threads are hogging. 
        // This could happen when another thread gets access to a synchronized resource and then goes into an infinite loop or takes a really long 
        // time to use the resource.
    }

    // =========================================================================================================================================
    // Race Conditions
    static void test01_04() throws Exception {

        // create Thread 1, which will try to book seats 1A and 1B
        Thread getSeats101 = new Thread(() -> {
            ticketAgentBooks01("1A");
            ticketAgentBooks01("1B");
        });

        // create Thread 2, which will try to go book seats 1A and 1B too
        Thread getSeats102 = new Thread(() -> {
            ticketAgentBooks01("1A");
            ticketAgentBooks01("1B");
        });

        // starts both threads
        getSeats101.start();
        getSeats102.start();

        // It will show:
        // Thread-1 true
        // Thread-1 true
        // Thread-0 true
        // Thread-0 false

        // Uh oh. It looks like we’ve sold seat 1A twice! That means two people will show up
        // for the concert and expect to get the same seat

        // This issue is caused by a race condition. A race condition is when two or more
        // threads try to access and change a shared resource at the same time, and the result is
        // dependent on the order in which the code is executed by the threads.

        Thread.sleep(3000);

        System.out.println("--------------------------------------------------------------------------------------");

        Thread getSeats201 = new Thread(() -> {
            ticketAgentBooks02("1A");
            ticketAgentBooks02("1B");
        });

        Thread getSeats202 = new Thread(() -> {
            ticketAgentBooks02("1A");
            ticketAgentBooks02("1B");
        });

        getSeats201.start();
        getSeats202.start();

        // In a multithreaded environment, we need to make sure our code is designed so it’s
        // not dependent on the ordering of the threads. In situations where our code is dependent
        // on ordering or dependent on certain operations not being interrupted, we can get race
        // conditions.
    }

    static void ticketAgentBooks01(String seat) {
        // get the one instance of the Show Singleton
        Show01 show01 = Show01.getInstance();

        // book a seat and print
        System.out.println(Thread.currentThread().getName() + " " + show01.bookSeat(seat));
    }

    static void ticketAgentBooks02(String seat) {
        // get the one instance of the Show Singleton
        Show02 show02 = Show02.getInstance();

        // book a seat and print
        System.out.println(Thread.currentThread().getName() + " " + show02.bookSeat(seat));
    }

    static class Show01 {
        private static Show01 INSTANCE;

        private Set<String> availableSeats;

        public static Show01 getInstance() { // create a singleton instance
            if (INSTANCE == null) {
                INSTANCE = new Show01(); // should be only one Show
            }

            return INSTANCE;
        }

        private Show01() {
            availableSeats = new HashSet<>();
            availableSeats.add("1A");
            availableSeats.add("1B");
        }

        public boolean bookSeat(String seat) {
            return availableSeats.remove(seat);
        }
    }

    static class Show02 {

        // The volatile keyword makes sure that the variable INSTANCE is atomic; that is, a write to the variable 
        // happens all at once. Nothing can interrupt this process: it either happens completely, or it doesn’t happen at all.
        private static volatile Show02 INSTANCE;

        private Set<String> availableSeats;

        // The synchronized keyword makes sure only one thread at a time can access the
        // getInstance() method. That ensures we won’t get a race condition: in other words,
        // we can’t check the value of INSTANCE in one thread, then stop, and do the same in
        // another thread
        public static synchronized Show02 getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new Show02(); // 
            }

            return INSTANCE;
        }

        private Show02() {
            availableSeats = new HashSet<>();
            availableSeats.add("1A");
            availableSeats.add("1B");
        }

        // When we book a seat by calling this method from the ticketAgentBooks() method, 
        // we are changing another shared resource, the availableSeats Set . 
        // However, the Set is not thread-safe! That means a thread could be interrupted in 
        // the middle of removing seat “1A” from the Set , and another thread could come along and access the Set .
        public synchronized boolean bookSeat(String seat) {
            return availableSeats.remove(seat);
        }
    }

    // =========================================================================================================================================
    // 
    static void test01_05() {

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_04();
    }
}
