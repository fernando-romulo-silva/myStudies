package org.nandao.cap10.p02UsingTheReusableSynchronizationBarrierPhaser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

// The java.util.concurrent.Phaser class is concerned with the synchronization of
// threads that work together in cyclic type phases. The threads will execute and then wait for
// the completion of the other threads in the group. When all of the threads are completed, one
// phase is done. The Phaser can then be used to coordinate the execution of the same set of
// threads again.
//
// The java.util.concurrent.CountdownLatch class provided a way of doing this, but
// required a fixed number of threads, and is executed once by default. The java.util.
// concurrent.CyclicBarrier, which was introduced in Java 5, also used a fixed number
// of threads, but is reusable. However, it is not possible to advance to the next phase. This is
// useful when a problem is characterized by a series of steps/phases that advance from one
// phase to the next based on some criteria.

// 322
public class Test {

    private static abstract class Entity implements Runnable {
        public abstract void run();
    }

    private static class Player extends Entity {
        private final static AtomicInteger idSource = new AtomicInteger();
        private final int id = idSource.incrementAndGet();

        @SuppressWarnings("static-access")
        public void run() {
            System.out.println(toString() + " started");
            try {
                Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(200, 600));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(toString() + " stopped");
        }

        @Override
        public String toString() {
            return "Player #" + id;
        }
    }

    private static class Zombie extends Entity {
        private final static AtomicInteger idSource = new AtomicInteger();
        private final int id = idSource.incrementAndGet();

        public void run() {
            System.out.println(toString() + " started");
            try {
                Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(400, 800));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(toString() + " stopped");
        }

        @Override
        public String toString() {
            return "Zombie #" + id;
        }
    }

    public static void main(String[] args) {
        List<Entity> entities = new ArrayList<>();
        entities = new ArrayList<>();
        entities.add(new Player());
        entities.add(new Zombie());
        entities.add(new Zombie());
        entities.add(new Zombie());
        gameEngine(entities);
    }

    private static void gameEngine(List<Entity> entities) {
        final Phaser phaser = new Phaser(1);

        for (final Entity entity : entities) {

            final String member = entity.toString();
            System.out.println(member + " joined the game");
            phaser.register();

            new Thread() {
                @Override
                public void run() {
                    System.out.println(member + " waiting for the remaining participants");
                    phaser.arriveAndAwaitAdvance();
                    // wait for remaining entities
                    System.out.println(member + " starting run");
                    entity.run();
                }
            }.start();
        }

        phaser.arriveAndDeregister();
        //Deregister and continue
        System.out.println("Phaser continuing");
    }

}
