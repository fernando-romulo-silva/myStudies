package org.nandao.ch02TheStreamAPI.part09CollectingResults;

import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// When you are done with a stream, you often just want to look at the results instead
// of reducing them to a value. You can call the iterator method, which yields
// an old-fashioned iterator that you can use to visit the elements. Or you can call
// toArray and get an array of the stream elements.
// Since it is not possible to create a generic array at runtime, the expression
// stream.toArray() returns an Object[] array

public class Test {

    public static void main(final String[] args) {

        final String contents = "This a example test to use stream API and quite a lot";

        final Stream<String> words1 = Stream.of(contents.split("[\\P{L}]+"));

        final String[] result1 = words1.toArray(String[]::new);

        System.out.println("result 1: " + result1);

        // Now suppose you want to collect the results in a HashSet. If the collection is parallelized,
        // you can’t put the elements directly into a single HashSet because a HashSet
        // object is not threadsafe. For that reason, you can’t use reduce. Each segment needs
        // to start out with its own empty hash set, and reduce only lets you supply one
        // identity value. Instead, use collect. It takes three arguments:
        //
        // 1. A supplier to make new instances of the target object, for example, a constructor for a hash set
        // 2. An accumulator that adds an element to the target, for example, an add method
        // 3. An combiner that merges two objects into one, such as addAll        

        final Stream<String> words2 = Stream.of(contents.split("[\\P{L}]+"));
        final HashSet<String> result2 = words2.collect(HashSet::new, HashSet::add, HashSet::addAll);
        System.out.println("result 2: " + result2);

        // In practice, you don’t have to do that because there is a convenient Collector interface
        // for these three functions, and a Collectors class with factory methods for
        // common collectors. To collect a stream into a list or set, you can simply call
        final Stream<String> words3 = Stream.of(contents.split("[\\P{L}]+"));
        final List<String> result3 = words3.collect(Collectors.toList());
        System.out.println("result 3: " + result3);
        // or
        final Stream<String> words4 = Stream.of(contents.split("[\\P{L}]+"));
        final Set<String> result4 = words4.collect(Collectors.toSet());
        System.out.println("result 4: " + result4);

        // If you want to control which kind of set you get, use the following call instead:
        final Stream<String> words5 = Stream.of(contents.split("[\\P{L}]+"));
        final TreeSet<String> result5 = words5.collect(Collectors.toCollection(TreeSet::new));
        System.out.println("result 5: " + result5);

        // Suppose you want to collect all strings in a stream by concatenating them. You can call
        final Stream<String> words6 = Stream.of(contents.split("[\\P{L}]+"));
        final String result6 = words6.collect(Collectors.joining());
        System.out.println("result 6: " + result6);

        // If you want a delimiter between elements, pass it to the joining method:
        final Stream<String> words7 = Stream.of(contents.split("[\\P{L}]+"));
        final String result7 = words7.collect(Collectors.joining(", "));
        System.out.println("result 7: " + result7);

        // If your stream contains objects other than strings, you need to first convert them to strings, like this:
        final Stream<String> words8 = Stream.of(contents.split("[\\P{L}]+"));
        final String result8 = words8.map(Object::toString).collect(Collectors.joining(", "));
        System.out.println("result 8: " + result8);

        // If you want to reduce the stream results to a sum, average, maximum, or minimum,
        // then use one of the methods summarizing(Int|Long|Double). These methods
        // take a function that maps the stream objects to a number and yield a result of type
        // (Int|Long|Double)SummaryStatistics, with methods for obtaining the sum, average,
        // maximum, and minumum
        final Stream<String> words9 = Stream.of(contents.split("[\\P{L}]+"));
        final IntSummaryStatistics summary = words9.collect(Collectors.summarizingInt(String::length));
        final double averageWordLength = summary.getAverage();
        final double maxWordLength = summary.getMax();
        System.out.println("averageWordLength : " + averageWordLength);
        System.out.println("maxWordLength : " + maxWordLength);
        
        // So far, you have seen how to reduce or collect stream values. But
        // perhaps you just want to print them or put them in a database.Then you can
        // use the forEach method:
        final Stream<String> words10 = Stream.of(contents.split("[\\P{L}]+"));
        words10.forEach(System.out::print);
        
    }
}
