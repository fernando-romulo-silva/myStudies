package br.com.fernando.ch05_working_with_streams.part02_Mapping;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import br.com.fernando.Dish;

// Mapping
public class Test {

    // Applying a function to each element of a stream
    public static void test1() {

	// Streams support the method map, which takes a function as argument. The function is applied
	// to each element, mapping it into a new element (the word mapping is used because it has a
	// meaning similar to transforming but with the nuance of “creating a new version of” rather than “modifying”).
	//
	final List<String> dishNames = Dish.menu.stream() //
	    .map(Dish::getName) //
	    .collect(toList());

	// Given a list of words, you’d like to return a list of the number of characters for each word. How would you do it?
	// You’d need to apply a function to each element of the list. This sounds like a job for the map
	// method! The function to apply should take a word and return its length. You can solve this
	// problem as follows by passing a method reference String::length to map:
	//
	final List<String> words = Arrays.asList("Java8", "Lambdas", "In", "Action");

	final List<Integer> wordLengths = words.stream() //
	    .map(String::length) //
	    .collect(toList());

	// Let’s now return to the example where you extracted the name of each dish. What if you wanted
	// to find out the length of the name of each dish? You could do this by chaining another map as follows:
	//
	final List<Integer> dishNameLengths = Dish.menu.stream() //
	    .map(Dish::getName) //
	    .map(String::length) //
	    .collect(toList()); //
    }

    // Flattening streams
    public static void test2() {
	// You saw how to return the length for each word in a list using the method map. Let’s extend this
	// idea a bit further: how could you return a list of all the unique characters for a list of words? For
	// example, given the list of words ["Hello", "World"] you’d like to return the list ["H", "e", "l", "o", "W", "r", "d"].
	//
	// You might think that this is easy, that you can just map each word into a list of characters and
	// then call distinct to filter duplicate characters. A first go could be like this:

	final List<String> words = Arrays.asList("Hello", "World");

	words.stream() //
	    .map(word -> word.split("")) //
	    .distinct() //
	    .collect(toList());

	// The problem with this approach is that the lambda passed to the map method returns a String[]
	// (an array of String) for each word. So the stream returned by the map method is actually of type
	// Stream<String[]>. What you really want is Stream<String> to represent a stream of characters.
	//
    }

    // Attempt using map and Arrays.stream
    public static void test3() {

	// First, you need a stream of characters instead of a stream of arrays. There’s a method called
	// Arrays.stream()that takes an array and produces a stream, for example:
	//
	final String[] arrayOfWords = { "Goodbye", "World" };
	final Stream<String> streamOfwords = Arrays.stream(arrayOfWords);

	final List<String> words = Arrays.asList("Hello", "World");

	final List<Stream<String>> result = words.stream() //
	    .map(word -> word.split("")) // Covnverts each word into an array of its individual letters
	    .map(Arrays::stream) // Makes each array into a separate stream
	    .distinct() //
	    .collect(toList());

	System.out.println(result);
    }

    // Using flatMap
    public static void test4() {
	final List<String> words = Arrays.asList("Hello", "World");

	// Using the flatMap method has the effect of mapping each array not with a stream but with the
	// contents of that stream. All the separate streams that were generated when using
	// map(Arrays::stream) get amalgamated—flattened into a single stream. 
	//
	final List<String> result = words.stream() //
	    .map(word -> word.split("")) // Covnverts each word into an array of its individual letters
	    .flatMap(Arrays::stream) // Flatterns each generated stream into a single stream
	    .distinct() //
	    .collect(toList());
	
	// In a nutshell, the flatMap method lets you replace each value of a stream with another stream
	// and then concatenates all the generated streams into a single stream.

    }

    public static void main(String[] args) {

    }
}
