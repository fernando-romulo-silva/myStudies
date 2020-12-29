package br.fernando.ch10_threads.par01_defining_instantianting_and_starting_threads;

public class Test01 {

    // =========================================================================================================================================
    // Making a Thread
    static void test01_01() {
        // You’ll find methods in the Thread class for managing threads, including creating, starting, and pausing them.
        // For the exam, you’ll need to know, at a minimum, the following methods:
        //
        // start()
        // yield()
        // sleep()
        // run()

        // You can define and instantiate a thread in one of two ways:
        // 
        // Extend the java.lang.Thread class.
        // Implement the Runnable interface.
    }

    // =========================================================================================================================================
    // Defining a Thread
    static void test01_02() {
        // The limitation with this approach (besides being a poor design choice in most cases) is that if you extend Thread, 
        // you can’t extend anything else.
        MyThread myThread = new MyThread();

        // Notice that the Runnable interface is a functional interface, that is, an interface with
        // one abstract method, run() . That means we can also write a Runnable like this:

        Runnable r = () -> System.out.println("Important job in a Runnable");
    }

    // The simplest way to define code to run in a separate thread is to
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Imortant job running in MyThread");
        }

        // The overloaded run(String s) method will be ignored by the Thread class unless you call it yourself.
        public void run(String s) {
            System.out.println("String in run is " + s);
        }
    }

    // Implementing the Runnable interface gives you a way to extend any class you like but
    // still define behavior that will be run by a separate thread. It looks like this:
    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("Important job running in a Runnable");
        }
    }

    // =========================================================================================================================================
    // Instantiating a Thread
    static void test01_03() {
        // Remember, every thread of execution begins as an instance of class Thread.
        //
        // Common way to think about this is that the Thread is the “worker,” and 
        // the Runnable is the “job” to be done.

        // Job
        MyRunnable myRunnable01 = new MyRunnable();

        // Worker
        Thread thread01 = new Thread(myRunnable01); // Pass your Runnable to the Thread

        // Or, if you want to use a lambda expression, you can eliminate MyRunnable and write:

        Thread thread02 = new Thread(() -> System.out.println("Important job running in a Runnable"));

        // If you create a thread using the no-arg constructor, the thread will call its own
        // run() method when it’s time to start working.

        // You can pass a single Runnable instance to multiple Thread objects so that the
        // same Runnable becomes the target of multiple threads, as follows:
        Runnable r = () -> System.out.println("Importan job running in a Runnable");

        Thread foo = new Thread(r);
        Thread bar = new Thread(r);
        Thread bat = new Thread(r);

        // The Thread class itself implements Runnable . 
        // This means that you could pass a Thread to another Thread’s constructor:

        Thread thread03 = new Thread(new MyThread());

        // At this point, all we’ve got is a plain-old Java object of type Thread . It is not yet a thread of execution.
    }

    // =========================================================================================================================================
    // Starting a Thread
    static void test01_04() {
        // Prior to calling start() on a Thread instance, the thread (when we use lowercase t , we’re referring to the thread of execution rather 
        // than the Thread class) is said to be in the new state, as we said. The new state means you have a Thread object but you don’t yet have a 
        // true thread. So what happens after you call start() ? The good stuff: 
        //
        // * A new thread of execution starts (with a new call stack).
        // * The thread moves from the new state to the runnable state.
        // * When the thread gets a chance to execute, its target run() method will run.

        final Runnable r = () -> {
            for (int x = 1; x < 6; x++) {
                System.out.println("Runnable running " + x);
            }
        };

        Thread thread01 = new Thread(r);
        thread01.start();

        // Calling a run() method directly just means you’re invoking a method from whatever thread is currently executing, 
        // and the run() method goes onto the current call stack rather than at the beginning of a new call stack.
        Thread thread02 = new Thread(r);
        thread02.run(); // Legal, but does not start a new thread

        //  The following example instantiates a thread and gives it a name, and then the name is printed out 
        // from the run() method:

        NameRunnable nr = new NameRunnable();

        Thread thread03 = new Thread(nr);
        thread03.setName("Fred");

        thread03.start();

        // But the target Runnable instance doesn’t even have a reference to the Thread instance, so we first invoked 
        // the static Thread.currentThread() method, which returns a reference to the currently executing thread, and 
        // then we invoked getName() on that returned reference.
    }

    static class NameRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("NameRunnable running");
            System.out.println("Run by " + Thread.currentThread().getName());
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_04();
        System.out.println("Thread is " + Thread.currentThread().getName());
    }
}
