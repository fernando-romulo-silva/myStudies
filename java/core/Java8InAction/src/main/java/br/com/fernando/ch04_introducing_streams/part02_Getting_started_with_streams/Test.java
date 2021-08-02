package br.com.fernando.ch04_introducing_streams.part02_Getting_started_with_streams;

import static java.util.stream.Collectors.toList;

import java.util.List;

import br.com.fernando.Dish;

// Getting started with streams
public class Test {

    public static void test1() {
        // What exactly is a Stream?
        // Is a sequence of elements from a source that supports data processing operations
        //
        // Sequence of elements — Like a collection, a stream provides an interface to a sequenced set of values of a specific element type
        //
        // Source — Streams consume from a data-providing source such as collections, arrays, or I/O resources.
        //
        // Data processing operations — Streams support database-like operations and common operations
        // from functional programming languages to manipulate data, such as filter, map, reduce, find, match, sort, and so on.
        // Stream operations can be executed either sequentially or in parallel.
        //
        // In addition, stream operations have two important characteristics:
        //
        // Pipelining — Many stream operations return a stream themselves, allowing operations to be chained and form a larger pipeline.
        //
        // Internal iteration — In contrast to collections, which are iterated explicitly using an iterator, stream operations do the
        // iteration behind the scenes for you.
        //
        //
        List<String> threeHighCaloricDishName = Dish.menu.stream() // Get stream from menu
            .filter(d -> d.getCalories() > 300)// Create a pipeline of operations: first filter high-calorie dishes.
            .map(Dish::getName) // Get the names of the dishes
            .limit(3) // Select only the first three.
            .collect(toList()); // Store the results in another List

        System.out.println(threeHighCaloricDishName); // The result is pork, beef, chicken

        //  filter—Takes a lambda to exclude certain elements from the stream. In this case, you select dishes that have more than 300 calories by passing the lambda d -> d.getCalories() > 300.
        //
        //  map—Takes a lambda to transform an element into another one or to extract information. In this case,you extract the name for each dish by passing the method reference Dish::getName, which is
        // equivalent to the lambda d -> d.getName(). 
        //
        //  limit—Truncates a stream to contain no more than a given number of elements.
        //
        //  collect—Converts a stream into another form. In this case you convert the stream into a list. It looks
        // like a bit of magic; we describe how collect works in more detail in chapter 6. At the moment, you can
        // see collect as an operation that takes as an argument various recipes for accumulating the elements of
        // a stream into a summary result. Here, toList() describes a recipe for converting a stream into a list.
    }

    public static void main(String[] args) {

    }
}
