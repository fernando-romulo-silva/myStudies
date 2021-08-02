package org.nandao.ch02TheStreamAPI.part12PrimitiveTypeStreams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// So far, we have collected integers in a Stream<Integer>, even though it is clearly inefficient
// to wrap each integer into a wrapper object. The same is true for the
// other primitive types double, float, long, short, char, byte, and boolean. The stream library
// has specialized types IntStream, LongStream, and DoubleStream that store primitive
// values directly, without using wrappers. If you want to store short, char, byte, and
// boolean, use an IntStream, and for float, use a DoubleStream.
public class Test {

    public static void main(final String[] args) {

        final IntStream stream1 = IntStream.of(1, 1, 2, 3, 5);
        for (final int intResult : stream1.toArray()) {
            System.out.println(" " + intResult);
        }

        final int[] intArray = new int[]{ 1, 2, 3, 4, 5 };
        final IntStream stream2 = Arrays.stream(intArray, 1, 4); // values is an int[] array
        for (final int intResult : stream2.toArray()) {
            System.out.println(" " + intResult);
        }

        // As with object streams, you can also use the static generate and iterate methods.
        // In addition, IntStream and LongStream have static methods range and rangeClosed that
        // generate integer ranges with step size one:
        final IntStream zeroToNinetyNine = IntStream.range(0, 100); // Upper bound is excluded
        for (final int intResult : zeroToNinetyNine.toArray()) {
            System.out.println(" " + intResult);
        }
        //
        final IntStream zeroToHundred = IntStream.rangeClosed(0, 100); // Upper bound is included
        for (final int intResult : zeroToHundred.toArray()) {
            System.out.println(" " + intResult);
        }

        // The CharSequence interface has methods codePoints and chars that yield an IntStream
        // of the Unicode codes of the characters or of the code units in the UTF-16 encoding.
        final String sentence = "\uD835\uDD46 is the set of octonions.";
        // \uD835\uDD46 is the UTF-16 encoding of the letter , unicode U+1D546
        final IntStream codes = sentence.codePoints();
        for (final int intResult : codes.toArray()) {
            System.out.println(" " + intResult);
        }

        // When you have a stream of objects, you can transform it to a primitive type
        // stream with the mapToInt, mapToLong, or mapToDouble methods.
        final List<String> wordList = Arrays.asList("Now", "you", "can", "see", "the", "transformation", "and", "my", "SUPERTRANSFORMATION");
        final Stream<String> words = wordList.stream();
        final IntStream lengths = words.mapToInt(String::length);
        for (final int intResult : lengths.toArray()) {
            System.out.println(" " + intResult);
        }

        // To convert a primitive type stream to an object stream, use the boxed method:
        final IntStream rangeInts = IntStream.rangeClosed(0, 100); // Upper bound is included
        final Stream<Integer> integers = rangeInts.boxed();
        for (final Object intResult : integers.toArray()) {
            System.out.println(" " + intResult);
        }
    }
}
