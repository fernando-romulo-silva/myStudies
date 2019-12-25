package br.fernando.ch10_threads.par04_thread_interaction;

public class Test02 {

    // =========================================================================================================================================
    // 
    static void test01_01() throws Exception {

        Operator operator = new Operator();

        synchronized (operator) { // The thread gets the lock on 'operator' 
            operator.wait(2000); //  thread releases the lock and waits for nofity only for maximum
            // of two seconds, then goes back to Runnable. The thread reacquies the lock 

            // More instrusctions here 
        }

        // When the wait() method is invoked on an object, the thread executing that code gives up its lock on the object immediately
        //
        // However, when notify() is called, that doesn’t mean the thread gives up its lock at that moment. If the
        // thread is still completing synchronized code, the lock is not released until the thread moves out of synchronized code. 
        // So just because notify() is called, this doesn’t mean the lock becomes available at that moment.
    }

    static class Operator extends Thread {
        public void run() {
            while (true) {
                // Get shape from user

                synchronized (this) {
                    // Calculate new machine steps from Shape

                    notify();
                }
            }
        }
    }

    static class Machine extends Thread {
        Operator operator; // assume this gets initialized

        public void run() {

            while (true) {
                synchronized (operator) {
                    try {
                        operator.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    // Send machine steps to hardware
                }
            }
        }
    }

    // =========================================================================================================================================
    // Using notifyAll( ) When Many Threads May Be Waiting
    static void test01_02() throws Exception {
        // In most scenarios, it’s preferable to notify all of the threads that are waiting on a
        // particular object. If so, you can use notifyAll() on the object to let all the threads
        // rush out of the waiting area and back to runnable
        //
        // All of the threads will be notified and start competing to get the lock. As the lock is
        // used and released by each thread, all of them will get into action without a need for
        // further notification.

        final Calculator calculator = new Calculator();

        new Reader(calculator).start();
        new Reader(calculator).start();
        new Reader(calculator).start();

        new Thread(calculator).start();
    }

    static class Reader extends Thread {

        Calculator c;

        Reader(Calculator calc) {
            this.c = calc;
        }

        public void run() {

            synchronized (c) {
                try {
                    System.out.println("Waiting for calculation...");
                    c.wait();
                } catch (final InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.out.println("Total is: " + c.total);
            }
        }
    }

    static class Calculator implements Runnable {
        int total;

        public void run() {
            synchronized (this) {
                for (int i = 0; i < 100; i++) {
                    total += i;
                }

                notifyAll();
            }
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_02();
    }
}
