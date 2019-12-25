package br.fernando.ch11_Concurrency_Objective.par01_apply_atomic_variables_and_locks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Test01 {

    // =========================================================================================================================================
    // Atomic Variables
    static void test01_01() throws Exception {
        Counter01 counter01 = new Counter01(); // The shared object

        IncrementerThread it101 = new IncrementerThread(counter01);
        IncrementerThread it102 = new IncrementerThread(counter01);

        it101.start();// thread 1 increments the count by 10.000
        it102.start();// thread 2 increments the count by 10.000

        it101.join(); // wait for thread 1 to finish
        it102.join(); // wait for thread 2 to finish

        System.out.println(counter01.getValue()); // rarely 20.000 lowest 11.972

        // The trap in this example is that count++ looks like a single action when, in fact, it is
        // not. When incrementing a field like this, what probably happens is the following sequence:
        //
        // 1. The value stored in count is copied to a temporary variable.
        // 2. The temporary variable is incremented.
        // 3. The value of the temporary variable is copied back to the count field.
        //
        // We say “probably” in this example because while the Java compiler will translate the
        // count++ statement into multiple Java bytecode instructions, you really have no control
        // over what native instructions are executed.
        //
        // While you could make this code thread-safe with synchronized blocks, the act of
        // obtaining and releasing a lock flag would probably be more time consuming than the
        // work being performed. This is where the classes in the java.util.concurrent.atomic
        // package can benefit you.
        //
        // --------------------------------------------------------------------
        // In reality, even a method such as getAndIncrement() still takes several steps to
        // execute. The reason this implementation is now thread-safe is something called CAS.
        // CAS stands for Compare And Swap. Most modern CPUs have a set of CAS
        // instructions. Following is a basic outline of what is happening now:
        //
        // 1. The value stored in count is copied to a temporary variable.
        // 2. The temporary variable is incremented.
        // 3. Compare the value currently in count with the original value. If it is
        // unchanged, then swap the old value for the new value.
        //
        // Step 3 happens atomically. If step 3 finds that some other thread has already modified
        // the value of count, then repeat steps 1–3 until we increment the field without interference.

        System.out.println("====================================================================================");

        Counter02 counter02 = new Counter02(); // The shared object

        IncrementerThread it201 = new IncrementerThread(counter02);
        IncrementerThread it202 = new IncrementerThread(counter02);

        it201.start();// thread 1 increments the count by 10.000
        it202.start();// thread 2 increments the count by 10.000

        it201.join(); // wait for thread 1 to finish
        it202.join(); // wait for thread 2 to finish

        System.out.println(counter02.getValue()); // always 20.000

    }

    static class IncrementerThread extends Thread {

        private Counter counter;

        // all instances are passed the same counter
        public IncrementerThread(Counter counter) {
            super();
            this.counter = counter;
        }

        @Override
        public void run() {
            // "i" is local and therefore thread-safe
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        }
    }

    static interface Counter {

        void increment();

        int getValue();
    }

    // --------------------------------------------------------------------------------------------
    static class Counter01 implements Counter {

        private int count;

        public void increment() {
            count++; // it's a trap! A single "line" is not atomic!
        }

        public int getValue() {
            return count;
        }
    }

    // --------------------------------------------------------------------------------------------
    // Here is a thread-safe replacement for the Counter class from the previous example:
    static class Counter02 implements Counter {
        // The java.util.concurrent.atomic package provides several classes for different data
        // types, such as AtomicInteger, AtomicLong, AtomicBoolean, and AtomicReference, to
        // name a few.

        private AtomicInteger count = new AtomicInteger();

        public void increment() {
            count.getAndIncrement(); // atomic operation
        }

        public int getValue() {
            return count.intValue();
        }
    }

    // =========================================================================================================================================
    static void summary() {
        // ***************************************************************************
        // Atomic Variables
        // ***************************************************************************
        // Atomic Integer
        AtomicInteger count = new AtomicInteger();
        // or
        count = new AtomicInteger(10);

        // methods

        // "get" first
        count.getAndIncrement(); // return and increase value;

        count.getAndDecrement(); // decrement the value

        count.getAndAdd(3); // return and add the value (sum)

        count.getAndSet(3);

        // just get
        count.get(); // return the value

        // "action" first
        count.incrementAndGet();

        count.decrementAndGet();

        count.addAndGet(3); //

        // Only action doesn't exists !
        // count.increment(); // doesn't exists !

        // Atomic References
        AtomicReference<String> atomicReference = new AtomicReference<String>("Test");

        atomicReference.getAndSet("Test1");

        atomicReference.compareAndSet("Test1", "Test2");

        atomicReference.getAndAccumulate("new value", (x, y) -> "new value");

        // The java.util.concurrent.atomic package provides several classes for different data
        // types, such as AtomicInteger, AtomicLong, AtomicBoolean, and AtomicReference, to
        // name a few.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
