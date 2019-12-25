package br.fernando.ch10_threads.par03_sysnchronizing_code_thread_problems;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Test02 {

    // =========================================================================================================================================
    // Synchronization and Locks
    static void test01_01() {
        // Remember the following key points about locking and synchronization:
        //
        // * Only methods (or blocks) can be synchronized, not variables or classes.
        //
        // * Each object has just one lock.
        //
        // * Not all methods in a class need to be synchronized. A class can have both
        // synchronized and non-synchronized methods.
        //
        // * If two threads are about to execute a synchronized method in a class and both
        // threads are using the same instance of the class to invoke the method, only one
        // thread at a time will be able to execute the method
        //
        // If a class has both synchronized and non-synchronized methods, multiple
        // threads can still access the class’s non-synchronized methods!
        //
        // Synchronization can cause a hit in some cases (or even deadlock if used incorrectly),
        // so you should be careful not to overuse it.
        //
        // If a thread goes to sleep, it holds any locks it has—it doesn’t release them
        //
        // A thread can acquire more than one lock. For example, a thread can enter a
        // synchronized method, thus acquiring a lock, and then immediately invoke a
        // synchronized method on a different object, thus acquiring that lock as well.
        //
        // You can synchronize a block of code rather than a method.
    }

    // =========================================================================================================================================
    static void test01_02() {

        // Because synchronization does hurt concurrency, you don’t want to synchronize any more code than is necessary
        // to protect your data. So if the scope of a method is more than needed, you can reduce the scope of the synchronized part to something less than
        // a full method—to just a block. We call this, strangely, a synchronized block, and it looks like this:

        Test02 t = new Test02();

        System.out.println("Not synchronized");

        synchronized (t) { // this for isntance
            System.out.println("synchronized");
        }

    }

    // =========================================================================================================================================
    // Can Static Methods Be Synchronized?
    static synchronized void test01_03() { // static methods can be synchronized.

        // Again, this could be replaced with code that uses a synchronized block. If the
        // method is defined in a class called MyClass, the equivalent code is as follows:
        synchronized (Test02.class) {
            System.out.println("Synchronized!");
        }
    }

    // =========================================================================================================================================
    // What Happens If a Thread Can’t Get the Lock?
    static void test01_04() {
        // If a thread tries to enter a synchronized method and the lock is already taken, the thread is said to be blocked
        // on the object’s lock. Essentially, the thread goes into a kind of pool for that particular object and has to sit
        // there until the lock is released and the thread can again become runnable/running.
        //
        // When thinking about blocking, it’s important to pay attention to which objects are being used for locking:
        //
        // * Threads calling non-static synchronized methods in the same class will only block each other if they’re invoked
        // using the same instance.
        //
        // * Threads calling static synchronized methods in the same class will always block each other—they all lock on the same Class instance.
        //
        // * A static synchronized method and a non-static synchronized method will not block each other, ever.
        //
        // * For synchronized blocks, you have to look at exactly what object has been used for locking.
        // Threads that synchronize on the same object will block each other. Threads that synchronize on different objects will not.
    }

    // =========================================================================================================================================
    // So When Do I Need to Synchronize?
    static void test01_05() {
        // Synchronization can get pretty complicated, and you may be wondering why you
        // would want to do this at all if you can help it. But remember the earlier “race conditions” 
        // example with Lucy and Fred making withdrawals from their account.
    }

    static class Thing {

        private static int staticFiled;

        private int nonstaticField;

        public static synchronized int getStaticField() {
            return staticFiled;
        }

        public synchronized int getNonstaticField() {
            return nonstaticField;
        }
    }

    // =========================================================================================================================================
    // Thread-Safe Classes
    static void test01_06() {
        // When a class has been carefully synchronized to protect its data (using the rules just given or using more complicated alternatives), 
        // we say the class is “thread-safe.”

        StringBuilder sb01 = new StringBuilder();

        StringBuffer sb02 = new StringBuffer();

        // However, even when a class is “thread-safe,” it is often dangerous to rely on these classes to provide the thread protection you need.
        //
        // It’s tempting to think that yes, because the data in names is in a synchronized collection, the NameList class
        // is “safe” too. However, that’s not the case—the removeFirst() may sometimes throw an IndexOutOfBoundsException .

        final NameList nl = new NameList();
        nl.add("Ozymandias");

        class NameDropper extends Thread {
            @Override
            public void run() {
                String name = nl.removeFirst();
                System.out.println(name);
            }
        }

        Thread t01 = new NameDropper();
        Thread t02 = new NameDropper();

        t01.start();
        t02.start();

        // What might happen here is that one of the threads will remove the one name and print it, and then the other will try to remove a 
        // name and get null . If we think just about the calls to names.size() and names.remove(0);
        //
        // The thing to realize here is that in a “thread-safe” class like the one returned by synchronizedList() , each individual method is 
        // synchronized. So names.size() is synchronized , and names.remove(0) is synchronized . But nothing prevents another thread from doing 
        // something else to the list in between those two calls. And that’s where problems can happen.
    }

    static class NameList {
        final List<String> names = Collections.synchronizedList(new LinkedList<>());

        public void add(String name) {
            names.add(name);
        }

        public String removeFirst() {
            if (names.size() > 0) {
                return (String) names.remove(0);
            } else {
                return null;
            }
        }

        // There’s a solution here: Don’t rely on Collections.synchronizedList() . 
        // Instead, synchronize the code yourself:
        public synchronized String removeFirstSync() {
            return removeFirst();
        }

        // The moral here is that just because a class is described as “thread-safe” doesn’t
        // mean it is always thread-safe.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_06();
    }
}
