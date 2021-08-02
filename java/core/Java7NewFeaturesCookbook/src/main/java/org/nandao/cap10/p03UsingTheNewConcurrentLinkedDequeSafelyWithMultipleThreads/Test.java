package org.nandao.cap10.p03UsingTheNewConcurrentLinkedDequeSafelyWithMultipleThreads;

import java.util.concurrent.ConcurrentLinkedDeque;

// The java.util.concurrent.ConcurrentLinkedDeque class, which is a member of
// the Java Collections Framework, offers the ability for multiple threads to safely access the
// same data collection concurrently. The class implements a double-ended queue, known as
// a deque, and allows for the insertion and removal of elements from both ends of the deque.
// It is also known as a head-tail linked list and, like other concurrent collections, does not allow
// the usage of null elements.
public class Test {

    private static ConcurrentLinkedDeque<Item> deque = new ConcurrentLinkedDeque<>();

    static class Item {

        private final String description;

        private final int itemId;

        public Item() {
            this("Default Item", 0);
        }

        public Item(String description, int itemId) {
            this.description = description;
            this.itemId = itemId;
        }

        public String getDescription() {
            return description;
        }

        public int getItemId() {
            return itemId;
        }
    }

    static class ItemProducer implements Runnable {
        @Override
        public void run() {
            String itemName = "";
            int itemId = 0;
            try {
                for (int x = 1; x < 8; x++) {
                    itemName = "Item" + x;
                    itemId = x;
                    deque.add(new Item(itemName, itemId));
                    System.out.println("New Item Added:" + itemName + " " + itemId);
                    Thread.currentThread().sleep(250);
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
            Item item;
            while ((item = deque.pollFirst()) != null) {
                generateOrder(item);
            }
        }

        private void generateOrder(Item item) {
            System.out.println("Part Order");
            System.out.println("Item description: " + item.getDescription());
            System.out.println("Item ID # " + item.getItemId());
            System.out.println();
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ItemProducer()).start();
        new Thread(new ItemConsumer()).start();
    }
}
