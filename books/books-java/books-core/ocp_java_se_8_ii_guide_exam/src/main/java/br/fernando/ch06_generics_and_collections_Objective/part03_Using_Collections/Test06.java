package br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// Using the PriorityQueue Class and the Deque Interface
public class Test06 {

    // =========================================================================================================================================
    // PriorityQueue
    //
    // Unlike basic queue structures that are first-in, first-out by default, a PriorityQueue
    // orders its elements using a user-defined priority. The priority can be as simple as natural ordering.
    // In addition, a PriorityQueue can be ordered using a Comparator, which lets you define any ordering you want. Queue s have a few methods not found in
    // other collection interfaces: peek() , poll() , and offer()

    static class PQSort implements Comparator<Integer> { // inverse sort

        @Override
        public int compare(Integer one, Integer two) {
            return two - one; // uboxing
        }
    }

    static void test01() {
        final int[] i1 = { 1, 5, 3, 7, 6, 9, 8 }; // unordered data

        final PriorityQueue<Integer> pq1 = new PriorityQueue<>(); // use natural order

        for (int i : i1) {
            pq1.offer(i);
        }

        for (int x : i1)
            System.out.print(pq1.poll() + " ");

        System.out.println("");

        final PQSort pqs = new PQSort(); // get a Comparator

        PriorityQueue<Integer> pq2 = new PriorityQueue<>(10, pqs); // Use comparator

        for (int i : i1) { // load queue
            pq2.offer(i); // insert element ordered
        }

        System.out.println("size " + pq2.size());

        // returns the highest-priority element in the queue without removing it
        System.out.println("peek " + pq2.peek());
        System.out.println("size " + pq2.size());

        // returns the highest-priority element AND removes it from the queue.
        System.out.println("poll " + pq2.poll());
        System.out.println("size " + pq2.size());

        for (int x : i1)
            System.out.print(pq2.poll() + " ");

    }

    // =========================================================================================================================================
    // ArrayDeque
    //
    // Deque is an interface for double-ended queues, with methods for adding and removing elements to and from the queue at either end.
    // Whereas the Deque interface allows for capacity-limited implementations, ArrayDeque has no capacity restrictions so
    // adding elements will not fail.
    //
    // The main advantage of ArrayDeque over, say, a List (like ArrayList or LinkedList ) is performance.
    //
    // Unlike PriorityQueue , there is no natural ordering in ArrayDeque ; it is simply a collection of elements that are stored in the
    // order in which you add them.
    static void test02() {

        List<Integer> num = Arrays.asList(10, 9, 8, 7, 6, 5); // create several arrayDeques, each with space for 2 items

        ArrayDeque<Integer> a = new ArrayDeque<>(2);
        ArrayDeque<Integer> b = new ArrayDeque<>(2);
        ArrayDeque<Integer> c = new ArrayDeque<>(2);
        ArrayDeque<Integer> d = new ArrayDeque<>(2);
        ArrayDeque<Integer> e = new ArrayDeque<>(2);

        for (Integer n : num) {
            a.offer(n); // add on the end
            b.offerFirst(n); // add on the front
            c.push(n); // add on the front
            d.add(n); // add on the end
            e.addFirst(n); // add on the front
        }

        // Remenber:
        // * The methods offerFirst() , push() , and addFirst() add elements to the front of the deque
        // * The methods offer() and add() add elements to the end of the deque

        // display the deques
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println("d: " + d);
        System.out.println("e: " + e);

        System.out.println("");

        // Method peek(), which returns the first element (sometimes called the head of the queue)
        // without removing it from the deque:
        System.out.println("First element of e: " + e.peek());
        System.out.println("e has been modifed: " + e);

        System.out.println("");

        // Then we poll() , which removes the first element from the deque and returns it:
        System.out.println("First element of e: " + e.poll());
        System.out.println("e has been modifed: " + e);

        System.out.println("");

        // Then we pop() , which ALSO removes the first element from the deque and returns it:
        System.out.println("First element of e: " + e.pop());
        System.out.println("e has been modifed: " + e);

        System.out.println("");

        // Then we pollLast() , which removes the last element from the deque (sometimes called the tail of the queue) and returns it:
        System.out.println("Last element of e: " + e.pollLast());
        System.out.println("e has been modifed: " + e);

        System.out.println("");
        // Then we call removeLast() three times to remove the three remaining elements from the end of the deque,
        // returning each one, so we see the elements in reverse order from the end of the deque:

        System.out.println("Remove all remaining elements of e: " + e.removeLast() + " " + e.removeLast() + " " + e.removeLast());
        System.out.println("e has been modified: " + e);

        System.out.println("");

        // calling pop() throws a java.util.NoSuchElementException
        // System.out.println("Try to pop one more item: " + e.pop());

        // calling remove() throws a java.util.NoSuchElementException
        // System.out.println("Try to remove one more item: " + e.remove());

        // But if we poll() , we get null :
        System.out.println("Try to poll one more item: " + e.poll());
        //
        // ArrayDeque is not thread safe, so you have to synchronize on classes that access a shared ArrayDeque in a multithreaded environment.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test02();
    }
}
