package org.nandao.ch02TheStreamAPI.part08ReductionOperations;

import java.util.stream.Stream;

// If you want to compute a sum, or combine the elements of a stream to a result
// in another way, you can use one of the reduce methods. The simplest form takes
// a binary function and keeps applying it, starting with the first two elements.

public class Test {

    public static void main(String[] args) {
        final String contents = "This a example test to use stream API and quite a lot";

        final Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));

        final int result = words.reduce(0, //
                                        (total, word) -> total + word.length(), //
                                        (total1, total2) -> total1 + total2 //
        );

        System.out.println("Result: " + result);
    }

}
