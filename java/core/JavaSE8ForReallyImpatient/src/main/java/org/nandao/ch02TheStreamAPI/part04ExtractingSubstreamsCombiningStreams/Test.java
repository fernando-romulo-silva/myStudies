package org.nandao.ch02TheStreamAPI.part04ExtractingSubstreamsCombiningStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {
        // The call stream.limit(n) returns a new stream that ends after n elements (or when
        // the original stream ends if it is shorter). This method is particularly useful for
        // cutting infinite streams down to size.
        final Stream<Double> randoms = Stream.generate(Math::random).limit(5);
        System.out.println(randoms.collect(Collectors.toList()));

        // The call stream.skip(n) does the exact opposite. It discards the first n elements.
        final Stream<Double> randoms2 = Stream.generate(Math::random).limit(5).skip(4);
        System.out.println(randoms2.collect(Collectors.toList()));

        final String contents = "This a example test to use stream API";
        final Stream<String> words = Stream.of(contents.split("[\\P{L}]+")) //
            .skip(1);
        System.out.println(words.collect(Collectors.toList()));

        // You can concatenate two streams with the static concat method of the Stream class:
        final Stream<Character> combined = Stream.concat(characterStream("Hello"), characterStream("World"));
        System.out.println(combined.collect(Collectors.toList()));

        
        // The peek method yields another stream with the same elements as the
        // original, but a function is invoked every time an element is retrieved.
        final Object[] powers = Stream.iterate(1.0, p -> p * 2) //
            .peek(e -> System.out.println("Fetching " + e)) //
            .limit(20) //
            .toArray();

        System.out.println(powers);
    }

    public static Stream<Character> characterStream(final String s) {
        final List<Character> result = new ArrayList<>();

        for (final char c : s.toCharArray()) {
            result.add(c);
        }

        return result.stream();
    }
}
