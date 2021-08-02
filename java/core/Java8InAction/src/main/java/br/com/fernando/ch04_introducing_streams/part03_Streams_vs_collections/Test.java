package br.com.fernando.ch04_introducing_streams.part03_Streams_vs_collections;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import br.com.fernando.Dish;

// Streams vs. collections
public class Test {

    // In coarsest terms, the difference between collections and streams has to do with when things are
    // computed. A collection is an in-memory data structure that holds all the values the data
    // structure currently has—every element in the collection has to be computed before it can be
    // added to the collection.
    //
    // Traversable only once
    public static void test1() {
        // Note that, similarly to iterators, a stream can be traversed only once. After that a stream is said
        // to be consumed. You can get a new stream from the initial data source to traverse it again just
        // like for an iterator
        //

        final List<String> title = Arrays.asList("Java 8", "In", "Action");

        final Stream<String> stream = title.stream();

        stream.forEach(System.out::println); // Prints each word in the title

        stream.forEach(System.out::println); // java.lang.IllegalStateException: stream has already been operated upon or closed.
    }

    // External vs. internal iteration
    public static void test2() {

        // Collections: external iteration with a for-each loop
        final List<String> names1 = new ArrayList<>();
        for (Dish d : Dish.menu) { // Explicitly iterate the list of menu sequentially
            names1.add(d.getName()); // Extract the name and add i to an accumulator.
        }

        // Collections: external iteration using an iterator behind the scenes
        List<String> names2 = new ArrayList<>();
        Iterator<Dish> iterator = Dish.menu.iterator();
        while (iterator.hasNext()) { // Iterating explicitly
            Dish d = iterator.next();
            names2.add(d.getName());
        }

        // Streams: internal iteration
        Dish.menu.stream() //
            .map(Dish::getName) // Parameterize map with the getName method to extrac the name of a dish
            .collect(toList()); // Start executing the pipeline of operations; no iteration!
    }

    // Internal vs. external iteration
    public static void test3() {

        // We’ve described the conceptual differences between collections and streams. Specifically,
        // streams make use of internal iteration: iteration is taken care of for you. But this is useful only if
        // you have a list of predefined operations to work with (for example, filter or map) that hide the
        // iteration.
    }

    public static void main(String[] args) {

    }

}
