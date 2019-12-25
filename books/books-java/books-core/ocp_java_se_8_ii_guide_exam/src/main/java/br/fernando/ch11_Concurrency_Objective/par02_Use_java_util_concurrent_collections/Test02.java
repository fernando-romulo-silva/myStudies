package br.fernando.ch11_Concurrency_Objective.par02_Use_java_util_concurrent_collections;

import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.primitives.Ints;

public class Test02 {

    // =========================================================================================================================================
    // Special-Purpose Queues
    static void test01_01() throws Exception {
        // A SynchronousQueue is a special type of bounded blocking queue; it has a capacity of zero. Having a zero capacity, the first thread to
        // attempt either an insert or remove operation on a SynchronousQueue will block until another thread performs the opposite operation.
        //
        // You use a SynchronousQueue when you need threads to meet up and exchange an object.
        //
        // A DelayQueue is useful when you have objects that should not be consumed until a specific time.
        // The elements added to a DelayQueue will implement the java.util.concurrent.Delayed interface, which defines a single method:
        //
        // public long getDelay(TimeUnit unit).
        //
        // The elements of a DelayQueue can only be taken once their delay has expired.
    }

    // =========================================================================================================================================
    // The LinkedTransferQueue
    static void test01_02() throws Exception {
        // A LinkedTransferQueue (added in Java 7) is a superset of ConcurrentLinkedQueue, SynchronousQueue, and LinkedBlockingQueue.
        // It can function as a concurrent Queue implementation similar to ConcurrentLinkedQueue.
        //
        // Like a SynchronousQueue, a LinkedTransferQueue can be used to make two threads rendezvous to exchange an object.
        // Unlike a SynchronousQueue, a LinkedTransferQueue has internal capacity, so the transfer(E) method is used to block until the inserted
        // object (and any previously inserted objects) is consumed by another thread.
    }

    // ========================================================================================================================================

    static void summary() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // *****************************************************************************************************************
        // SynchronousQueue - this implementation allows us to exchange information between threads in a thread-safe manner.
        // *****************************************************************************************************************
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

        Runnable producer01 = () -> {
            Integer producedElement = ThreadLocalRandom.current().nextInt();

            try {
                synchronousQueue.put(producedElement);
                System.out.println(producedElement);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        Runnable consumer01 = () -> {
            try {
                Integer consumedElement = synchronousQueue.take();
                System.out.println(consumedElement);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        executor.execute(producer01);
        executor.execute(consumer01);

        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        executor.shutdown();

        // ****************************************************************************************************************************************************
        // DelayQueue - when the consumer wants to take an element from the queue, they can take it only when the delay for that particular element has expired.
        // ****************************************************************************************************************************************************
        executor = Executors.newFixedThreadPool(2);

        // The DelayeObject
        class DelayObject implements Delayed {

            private String data;

            private long startTime;

            public DelayObject(String data, long delayInMilliseconds) {
                this.data = data;
                this.startTime = System.currentTimeMillis() + delayInMilliseconds;
            }

            @Override
            public long getDelay(TimeUnit unit) {
                long diff = startTime - System.currentTimeMillis();
                return unit.convert(diff, TimeUnit.MILLISECONDS);
            }

            @Override
            public int compareTo(Delayed o) {
                return Ints.saturatedCast(this.startTime - ((DelayObject) o).startTime);
            }
        }

        // The DelayQueue
        final DelayQueue<DelayObject> queue = new DelayQueue<>();

        Integer numberOfElements = 2;

        AtomicInteger numberOfConsumedElements = new AtomicInteger();
        Integer delayOfEachProducedMessageMilliseconds = 500;

        Runnable producer02 = () -> {
            for (int i = 0; i < numberOfElements; i++) {
                DelayObject object = new DelayObject(UUID.randomUUID().toString(), delayOfEachProducedMessageMilliseconds);

                System.out.println("Put object: " + object);
                try {
                    queue.put(object);
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        };

        Runnable consumer02 = () -> {
            for (int i = 0; i < numberOfElements; i++) {
                try {
                    DelayObject object = queue.take();
                    numberOfConsumedElements.incrementAndGet();
                    System.out.println("Consumer take: " + object);
                } catch (InterruptedException e) {

                }
            }
        };

        executor.execute(producer02);
        executor.execute(consumer02);

        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        executor.shutdown();

        // ***********************************************************************************************************************************************************
        // LinkedTransferQueue
        // ***********************************************************************************************************************************************************
        // Simply put, this queue allows us to create programs according to the producer-consumer pattern, and coordinate messages passing from producers to consumers.

        executor = Executors.newFixedThreadPool(2);

        AtomicInteger numberOfProducedMessages = new AtomicInteger();
        AtomicInteger numberOfConsumedMessages = new AtomicInteger();

        LinkedTransferQueue<String> transferQueue = new LinkedTransferQueue<>();

        Runnable producer03 = () -> {
            for (int i = 0; i < numberOfElements; i++) {
                try {
                    final String element = "A" + i;
                    boolean added = transferQueue.tryTransfer(element, 4000, TimeUnit.MILLISECONDS);
                    if (added) {
                        numberOfProducedMessages.incrementAndGet();
                        System.out.println("Element transfered: " + element);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumer03 = () -> {
            for (int i = 0; i < numberOfElements; i++) {
                try {
                    String element = transferQueue.take();
                    System.out.println("Element received: " + element);

                    numberOfConsumedMessages.incrementAndGet();
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        executor.execute(producer03);
        executor.execute(consumer03);

        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        summary();
    }
}
