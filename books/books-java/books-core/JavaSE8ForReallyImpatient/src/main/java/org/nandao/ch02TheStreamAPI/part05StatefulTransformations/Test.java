package org.nandao.ch02TheStreamAPI.part05StatefulTransformations;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// The stream transformations of the preceding sections were stateless. When an element
// is retrieved from a filtered or mapped stream, the answer does not depend
// on the previous elements. There are also a few stateful transformations. For example,
// the distinct method returns a stream that yields elements from the original
// stream, in the same order, except that duplicates are suppressed. The stream
// must obviously remember the elements that it has already seen

public class Test {

    public static void main(final String[] args) {

        // The sorted method must see the entire stream and sort it before it can give out
        // any elements—after all, the smallest one might be the last one. Clearly, you can’t
        // sort an infinite stream.
        final Stream<String> uniqueWords = Stream.of("merrily", "merrily", "merrily", "gently")//
            .distinct();
        System.out.println(uniqueWords.collect(Collectors.toList()));
        // Only one "merrily" is retained

        // There are several sorted methods. One works for streams of Comparable elements,
        // and another accepts a Comparator. Here, we sort strings so that the longest ones come first:
        final String contents = "This a example test to use stream API";
        
        final Stream<String> longestFirst = Stream.of(contents.split("[\\P{L}]+")) //
                .sorted(Comparator.comparing(String::length) //
                .reversed());
        System.out.println(longestFirst.collect(Collectors.toList()));        
    }

}
