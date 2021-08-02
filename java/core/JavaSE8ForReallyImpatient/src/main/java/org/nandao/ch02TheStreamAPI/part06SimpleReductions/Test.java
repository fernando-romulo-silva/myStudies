package org.nandao.ch02TheStreamAPI.part06SimpleReductions;

import java.util.Optional;
import java.util.stream.Stream;

// Now that you have seen how to create and transform streams, we will finally get
// to the most important point—getting answers from the stream data. The methods
// that we cover in this section are called reductions. They reduce the stream to a
// value that can be used in your program. Reductions are terminal operations. After
// a terminal operation has been applied, the stream ceases to be usable.
public class Test {

    public static void main(String[] args) {

        final String contents = "This a example test to use stream API and quite a lot";

        final Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));
        final Optional<String> largest = words.max(String::compareToIgnoreCase);

        if (largest.isPresent()) {
            System.out.println("largest 1: " + largest.get());
        }

        final Stream<String> words2 = Stream.of(contents.split("[\\P{L}]+"));
        final Optional<String> startsWithQ = words2.filter(s -> s.startsWith("q")).findFirst();

        if (startsWithQ.isPresent()) {
            System.out.println("largest 2: " + startsWithQ.get());
        }

        final Stream<String> words3 = Stream.of(contents.split("[\\P{L}]+"));
        final Optional<String> startsWithQ2 = words3.parallel() //
            .filter(s -> s.startsWith("q")) //
            .findAny();

        if (startsWithQ2.isPresent()) {
            System.out.println("largest 3: " + startsWithQ2.get());
        }

        // If you just want to know there is a match, use anyMatch. That method takes a
        // predicate argument, so you won’t need to use filter.
        final Stream<String> words4 = Stream.of(contents.split("[\\P{L}]+"));
        final boolean aWordStartsWithQ = words4.parallel().anyMatch(s -> s.startsWith("q"));
        System.out.println("aWordStartsWithQ: " + aWordStartsWithQ);
        
        // There are also methods allMatch and noneMatch that return true if all or no elements
        // match a predicate. These methods always examine the entire stream, but they
        // still benefit from being run in parallel.
    }
}
