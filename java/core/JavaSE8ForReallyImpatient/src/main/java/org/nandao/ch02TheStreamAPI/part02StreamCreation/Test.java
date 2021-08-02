package org.nandao.ch02TheStreamAPI.part02StreamCreation;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// You have already seen that you can turn any collection into a stream with the
// stream method that Java 8 added to the Collection interface. If you have an array,
// use the static Stream.of method instead.
public class Test {

    public static void main(String[] args) throws Exception {
        final String contents = "This a example test to use stream API";

        final Stream<String> words = Stream.of(contents.split("[\\P{L}]+"));
        // split returns a String[] array
        System.out.println(words.collect(Collectors.toList()));

        final Stream<String> silence = Stream.empty();
        // Generic type <String> is inferred; same as Stream.<String>empty()
        System.out.println(silence.collect(Collectors.toList()));

        //
        final Stream<String> echos = Stream.generate(() -> "Echo") // java.lang.OutOfMemoryError: Java heap space
            .limit(5);
        System.out.println(echos.collect(Collectors.toList()));
        // or a stream of random numbers as
        final Stream<Double> randoms = Stream.generate(Math::random) // java.lang.OutOfMemoryError: Java heap space
            .limit(10);
        System.out.println(randoms.collect(Collectors.toList()));

        // To produce infinite sequences such as 0 1 2 3 ..., use the iterate method instead.
        final Stream<BigInteger> integers = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)) // java.lang.OutOfMemoryError: Java heap space 
            .limit(5);
        System.out.println(integers.collect(Collectors.toList()));

        // A number of methods that yield streams have been added to the API
        // with the Java 8 release. For example, the Pattern class now has a method
        // splitAsStream that splits a CharSequence by a regular expression.You can use
        // the following statement to split a string into words
        final Stream<String> words2 = Pattern.compile("[\\P{L}]+").splitAsStream(contents);
        System.out.println(words2.collect(Collectors.toList()));
        
        // The stream, and the underlying file with it, will be closed when the try block
        // exits normally or through an exception.

        try (Stream<String> lines = Files.lines(Paths.get("alice.txt"))) {
            // Do something with lines
        }

    }

}
