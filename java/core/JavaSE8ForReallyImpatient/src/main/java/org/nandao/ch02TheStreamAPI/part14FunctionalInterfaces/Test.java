package org.nandao.ch02TheStreamAPI.part14FunctionalInterfaces;

import java.util.stream.Collectors;
import java.util.stream.Stream;

// In this chapter, you have seen many operations whose argument is a function.
// For example, the Streams.filter method takes a function argument:

public class Test {

    public static void main(String[] args) {
        final String[] wordArray = new String[]{ "Now", "you", "can", "see", "the", "transformation", "and", "my", "SUPERTRANSFORMATION" };
        
        final Stream<String> wordsStream = Stream.of(wordArray);
        final Stream<String> longWords = wordsStream.filter(s -> s.length() >= 12);
        System.out.println(longWords.collect(Collectors.toList()));
        
        // It use Predicate function, It is an interface with one nondefault method returning a boolean value:
    }
    
}
