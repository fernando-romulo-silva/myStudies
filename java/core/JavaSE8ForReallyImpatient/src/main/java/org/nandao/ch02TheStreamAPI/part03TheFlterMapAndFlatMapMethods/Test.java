package org.nandao.ch02TheStreamAPI.part03TheFlterMapAndFlatMapMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A stream transformation reads data from a stream and puts the transformed
// data into another stream. You have already seen the filter transformation that
// yields a new stream with all elements that match a certain condition. Here, we
// transform a stream of strings into another stream containing only long words:
public class Test {

    public static void main(String[] args) {
        final List<String> wordList = Arrays.asList("Now", "you", "can", "see", "the", "transformation", "and", "my", "SUPERTRANSFORMATION");

        final Stream<String> words = wordList.stream();
        final Stream<String> longWords = words.filter(w -> w.length() > 12);
        System.out.println(longWords.count()); // two words with length > 12, // stream already used

        // Often, you want to transform the values in a stream in some way. Use the map
        // method and pass the function that carries out the transformation.
        final Stream<String> words2 = wordList.stream();
        final Stream<String> lowercaseWords = words2.map(String::toLowerCase);
        System.out.println(lowercaseWords.collect(Collectors.toList()));

        // Here, we used map with a method expression. Often, you will use a lambda expression instead:
        // The resulting stream contains the first character of each word
        // When you use map, a function is applied to each element, and the return values are collected in a new stream.
        final Stream<String> words3 = wordList.stream();
        final Stream<Character> firstChars = words3.map(s -> s.charAt(0));
        System.out.println(firstChars.collect(Collectors.toList()));

        // For example, characterStream("boat") is the stream ['b', 'o', 'a', 't'].
        final Stream<Character> characterStream = characterStream("boat");
        System.out.println(characterStream.collect(Collectors.toList()));

        final Stream<String> words4 = Arrays.asList("your", "boat").stream();
        
        // normal
        // final Stream<Stream<Character>> result = words4.map(w -> characterStream(w));
        // 
        // To flatten it out to a stream of characters
        final Stream<Character> result = words4.flatMap(w -> characterStream(w));
        
        System.out.println(result.collect(Collectors.toList()));
    }

    public static Stream<Character> characterStream(final String s) {
        final List<Character> result = new ArrayList<>();

        for (final char c : s.toCharArray()) {
            result.add(c);
        }

        return result.stream();
    }
}
