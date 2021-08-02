package br.fernando.ch10_threads.par02_threads_states_and_transitions;

public class Test02 {

    // =========================================================================================================================================
    // Thread Priorities and yield( )
    static void test01_01() {
        // If a thread enters the runnable state and it has a higher priority than any of the threads in the pool and a higher priority than the
        // currently running thread, the lower-priority running thread usually will be bumped back to runnable and the highest-priority thread
        // will be chosen to run.
        //

        // A thread gets a default priority that is the priority of the thread of execution that creates it
        Thread t01 = new Thread(); // same priority
        t01.start();

        // ----------------------------------------------------------------
        // A thread gets a default priority that is the priority of the thread of execution that creates it
        Runnable r02 = () -> System.out.println("Doing something!");
        Thread t02 = new Thread(r02);

        t02.setPriority(8);
        t02.start();

        // Priorities are set using a positive integer, usually between 1 and 10, and the JVM will never change a thread’s priority.

        // Thread.MIN_PRIORITY;
        // Thread.NORM_PRIORITY;
        // Thread.MAX_PRIORITY;
    }

    // =========================================================================================================================================
    // The yield( ) Method
    static void test01_02() {
        // What yield() is supposed to do is make the currently running thread head
        // back to runnable to allow other threads of the same priority to get their turn.

        Runnable r = () -> {
            System.out.println("Doing something!");
            Thread.yield();
        };

        Thread t01 = new Thread(r);
        t01.start();
    }

    // =========================================================================================================================================
    // The join( ) Method
    static void test01_03() throws InterruptedException {
        // The non-static join() method of class Thread lets one thread “join onto the end” of another thread.
        // If you have a thread B that can’t do its work until another thread A has completed its work, then you want thread B to “join” thread A.
        Thread t = new Thread(() -> System.out.println("Do It!"));
        t.start();
        t.join();

        // The preceding code takes the currently running thread (if this were in the main() method, then that would be the main thread) and
        // joins it to the end of the thread referenced by thread.
    }

    static void test01_04() {
        // Besides those three, we also have the following scenarios in which a thread might leave the running state:

        // * The thread’s run() method completes. Duh.
        //
        // * A call to wait() on an object (we don’t call wait() on a thread, as we’ll see in a moment).
        //
        // * A thread can’t acquire the lock on the object whose method code it’s attempting to run.

        // * The thread scheduler can decide to move the current thread from running to runnable in order to give another thread a chance to run. No reason is needed—
        // the thread scheduler can trade threads in and out whenever it likes.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
