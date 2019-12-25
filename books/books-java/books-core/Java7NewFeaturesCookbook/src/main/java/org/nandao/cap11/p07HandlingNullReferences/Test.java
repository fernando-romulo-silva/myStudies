package org.nandao.cap11.p07HandlingNullReferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Objects;

// A fairly common exception is the java.lang.NullPointerException. This occurs when
// an attempt is made to execute a method against a reference variable, which contains a value
// of null. In this recipe we will examine various techniques that are available to address this
// type of exception.
//
// The java.util.Objects class has been introduced and provides a number of static
// methods that address situations where null values need to be handled. The use of this
// class simplifies the testing for null values.

public class Test {

    public static class Item {
        private String name;
        private int partNumber;

        public Item() {
            this("Widget", 0);
        }

        public Item(String name, int partNumber) {
            this.name = Objects.requireNonNull(name);
            this.partNumber = partNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            // this.name = Objects.requireNonNull(name);
            this.name = Objects.requireNonNull(name, "The name field requires a non-null value");
        }

        public int getPartNumber() {
            return partNumber;
        }

        public void setPartNumber(int partNumber) {
            this.partNumber = partNumber;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Item other = (Item) obj;
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            if (this.partNumber != other.partNumber) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;

            Objects.hash(name, partNumber);

            hash = 47 * hash + Objects.hashCode(this.name);
            hash = 47 * hash + this.partNumber;
            return hash;
        }

        @Override
        public String toString() {
            return name + " - " + partNumber;
        }

    }

    public static void main(String[] args) {
        Item item1 = new Item("Eraser", 2200);
        Item item2 = new Item("Eraser", 2200);
        Item item3 = new Item("Pencil", 1100);
        Item item4 = null;

        System.out.println("item1 equals item1: " + item1.equals(item1));
        System.out.println("item1 equals item2: " + item1.equals(item2));
        System.out.println("item1 equals item3: " + item1.equals(item3));
        System.out.println("item1 equals item4: " + item1.equals(item4));

        item2.setName(null);
        System.out.println("item1 equals item2: " + item1.equals(item2));

        System.out.println("toString: " + Objects.toString(item4));
        System.out.println("toString: " + Objects.toString(item4, "Item is null"));

        // Using empty iterators to avoid null pointer exceptions
        // One approach to avoid a NullPointerException is to return a non-null value, when the list
        // could not be created. It could be beneficial to return an empty Iterator instead.
        // In Java 7, the Collections class has added three new methods that return an Iterator, a
        // ListIterator, or an Enumeration, all of which are empty. By returning empty, they can be
        // used without incurring a null pointer exception.
        ListIterator<String> list = returnEmptyListIterator();

        while (list.hasNext()) {
            System.out.println(list.next());
        }

    }

    public static ListIterator<String> returnEmptyListIterator() {
        boolean someConditionMet = false;
        if (someConditionMet) {
            ArrayList<String> list = new ArrayList<>();
            // Add elements
            ListIterator<String> listIterator = list.listIterator();
            return listIterator;
        } else {
            return Collections.emptyListIterator();
        }
    }
}
