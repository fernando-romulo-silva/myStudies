package org.nandao.ch02TheStreamAPI.part01FromIterationToStreamOperations;

import java.util.Arrays;
import java.util.List;

// Streams are the key abstraction in Java 8 for processing collections of values and
// specifying what you want to have done, leaving the scheduling of operations to
// the implementation.
// When you process a collection, you usually iterate over its elements and do some work with each of them.
public class Test {

    public static void main(String[] args) throws Exception {
        final String contents = "This a example test to use stream API";
        final List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        long count = 0;
        for (final String w : words) {
            if (w.length() > 12) {
                count++;
            }
        }

        System.out.println("Count: "+count);
        
        // What’s wrong with it? Nothing really—except that it is hard to parallelize the
        // code. That’s where the Java 8 bulk operations come in. In Java 8, the same
        // operation looks like this:

        count = words.stream().filter(w -> w.length() > 12).count();

        count = words.parallelStream().filter(w -> w.length() > 12).count();
        // Simply changing stream into paralleStream allows the stream library to do the
        // filtering and counting in parallel.
    }

}
