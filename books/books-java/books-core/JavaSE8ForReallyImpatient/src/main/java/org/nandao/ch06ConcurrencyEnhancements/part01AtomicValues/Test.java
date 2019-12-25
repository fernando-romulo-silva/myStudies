package org.nandao.ch06ConcurrencyEnhancements.part01AtomicValues;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.StampedLock;

// The incrementAndGet method atomically increments the AtomicLong and returns the
// post-increment value. That is, the operations of getting the value, adding 1, setting
// it, and producing the new value cannot be interrupted. It is guaranteed that
// the correct value is computed and returned, even if multiple threads access the
// same instance concurrently.
//
// There are methods for atomically setting, adding, and subtracting values, but if
// you want to make a more complex update, you have to use the compareAndSet
// method. For example, suppose you want to keep track of the largest value
// that is observed by different threads.

public class Test {

    public static AtomicLong largest = new AtomicLong();

    public static void main(String[] args) throws Exception {

        final long observed = new Random().nextLong();

        // In some thread . . .
        // This update is not atomic.
        largest.set(Math.max(largest.get(), observed)); // Error—race condition!

        long oldValue = 0;
        long newValue = new Random().nextLong();

        // Instead, compute the new value and use compareAndSet in a loop:
        do {

            oldValue = largest.get();

            newValue = Math.max(oldValue, observed);

        } while (!largest.compareAndSet(oldValue, newValue));

        // In Java 8, you don’t have to write the loop boilerplate any more. Instead, you
        // provide a lambda expression for updating the variable, and the update is done
        // for you. In our example, we can call
        largest.updateAndGet(x -> Math.max(x, observed));
        // or
        largest.accumulateAndGet(observed, Math::max);
        //
        //
        final LongAdder adder1 = new LongAdder();

        final ExecutorService pool = Executors.newCachedThreadPool();

        for (int t = 0; t < 100; t++) {
            pool.submit(() -> adder1.increment());
        }

        final long total1 = adder1.sum();
        System.out.println("Total1:" + total1);

        final LongAccumulator adder2 = new LongAccumulator(Long::sum, 0);
        // In some thread . . .

        for (int t = 0; t < 100; t++) {
            pool.submit(() -> adder2.accumulate(1L));
        }

        final long total2 = adder2.get();
        System.out.println("Total2:" + total2);

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    // NOTE: Another addition to Java 8 is the StampedLock class that can be used
    // to implement optimistic reads. I don’t recommend that application programmers
    // use locks, but here is how it is done.You first call tryOptimisticRead, upon
    // which you get a “stamp.” Read your values and check whether the stamp
    // is still valid (i.e., no other thread has obtained a write lock). If so, you can use
    // the values. If not, get a read lock (which blocks any writers). Here is an
    // example.

    public class Vector {
        private int size;
        private Object[] elements;
        private final StampedLock lock = new StampedLock();

        public Object get(int n) {
            long stamp = lock.tryOptimisticRead();
            Object[] currentElements = elements;
            int currentSize = size;
            if (!lock.validate(stamp)) { // Someone else had a write lock
                stamp = lock.readLock(); // Get a pessimistic lock
                currentElements = elements;
                currentSize = size;
                lock.unlockRead(stamp);
            }
            return n < currentSize ? currentElements[n] : null;
        }
    }

}
