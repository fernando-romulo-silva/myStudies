package org.nandao.cap10.p04UsingTheNewLinkedTransferQueueClass;

import java.util.concurrent.LinkedTransferQueue;

// The java.util.concurrent.LinkedTransferQueue class implements the java.
// util.concurrent.TransferQueue interface and is an unbounded queue that follows a
// First In First Out model for the queue elements. This class provides blocking methods and
// non-blocking methods for retrieving elements and is an appropriate choice for concurrent
// access by multiple threads. In this recipe we will create a simple implementation of a
// LinkedTransferQueue and explore some of the methods available in this class.

public class Test {

    private static LinkedTransferQueue<Item> linkTransQ = new LinkedTransferQueue<>();

    static class Item {
        public final String description;
        private final int itemId;

        public Item(String description, int itemId) {
            this.description = description;
            this.itemId = itemId;
        }

        public int getItemId() {
            return itemId;
        }

        public String getDescription() {
            return description;
        }

        public Item() {
            this("Default Item", 0);
        }
    }

    static class ItemProducer implements Runnable {
        @Override
        public void run() {
            try {
                for (int x = 1; x < 8; x++) {
                    String itemName = "Item" + x;
                    int itemId = x;
                    linkTransQ.offer(new Item(itemName, itemId));
                    System.out.println("New Item Added:" + itemName + " " + itemId);
                    Thread.currentThread().sleep(250);
                    if (linkTransQ.hasWaitingConsumer()) {
                        System.out.println("Hurry up!");
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    static class ItemConsumer implements Runnable {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            while (true) {
                try {
                    generateOrder(linkTransQ.take());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void generateOrder(Item item) {
            System.out.println();
            System.out.println("Part Order");
            System.out.println("Item description: " + item.getDescription());
            System.out.println("Item ID # " + item.getItemId());
        }
    }

    public static void main(String[] args) {
        new Thread(new ItemProducer()).start();
        new Thread(new ItemConsumer()).start();
    }
}
