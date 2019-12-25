package br.com.fernando.ch07_parallel_data_processing_and_performance.part03_Spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// Spliterator
public class Test {

    // The Spliterator is another new interface added to Java 8; its name stands for “splitable iterator.” Like Iterators, Spliterators are used to traverse the elements of a source, but they’re also
    // designed to do this in parallel. Although you may not have to develop your own Spliterator in
    // practice, understanding how to do so will give you a wider understanding about how parallel
    // streams work.

    // The splitting process
    public static void test01() {
	// The algorithm that splits a Stream into multiple parts is a recursive process and proceeds
	// In the first step trySplit is invoked on the first Spliterator and generates a
	// second one. Then in step 2 it’s called again on these two Spliterators, which results in a total of
	// four. The framework keeps invoking the method trySplit on a Spliterator until it returns null to
	// signal that the data structure that it’s processing is no longer divisible, as shown in step 3.
	// Finally, this recursive splitting process terminates in step 4 when all Spliterators have returned
	// null to a trySplit invocation.
    }

    // Implementing your own Spliterator

    public static final String SENTENCE = " Nel   mezzo del cammin  di nostra  vita mi  ritrovai in una  selva oscura che la  dritta via era   smarrita ";

    public static void test02() {
	System.out.println("Iteratively Found " + countWordsIteratively(SENTENCE) + " words");
	System.out.println("Parallel Stream Found " + countWordsWithParallelStream(SENTENCE) + " words");
	System.out.println("Custom Sliterator Found " + countWords(SENTENCE) + " words");
    }

    // An iterative word counter method
    //
    // Simple
    public static int countWordsIteratively(String s) {
	int counter = 0;
	boolean lastSpace = true;

	for (final char c : s.toCharArray()) { // Traverse all the characters in the String one by one.

	    if (Character.isWhitespace(c)) {
		lastSpace = true;
	    } else {

		if (lastSpace) {
		    counter++; // Increase the word counter when the last character is a space and the currently traversed one isn't
		}

		lastSpace = Character.isWhitespace(c);
	    }

	}
	return counter;
    }

    // Rewriting the WordCounter in functional style
    //
    // You’ve seen how a Spliterator can let you to gain control over the policy used to split a data
    // structure. One last notable feature of Spliterators is the possibility of binding the source of the
    // elements to be traversed at the point of first traversal, first split, or first query for estimated size,
    // rather than at the time of its creation.
    public static int countWords(String s) {

	final Spliterator<Character> spliterator = new WordCounterSpliterator(s);

	final Stream<Character> stream = StreamSupport.stream(spliterator, true);

	return countWords(stream);
    }

    // Making the WordCounter work in parallel
    //
    // Evidently something has gone wrong, but what? The problem isn’t hard to discover. Because the
    // original String is split at arbitrary positions, sometimes a word is divided in two and then
    // counted twice. In general, this demonstrates that going from a sequential stream to a parallel
    // one can lead to a wrong result if this result may be affected by the position where the stream is
    // split.
    public static int countWordsWithParallelStream(String s) {
	final Stream<Character> stream = IntStream //
	    .range(0, s.length()) //
	    .mapToObj(SENTENCE::charAt) //
	    .parallel();

	return countWords(stream);
    }

    private static int countWords(Stream<Character> stream) {

	final WordCounter wordCounter = stream //
	    .reduce(new WordCounter(0, true), //
	            WordCounter::accumulate, //
	            WordCounter::combine);

	return wordCounter.getCounter();
    }

    public static class WordCounterSpliterator implements Spliterator<Character> {

	private final String string;

	private int currentChar = 0;

	private WordCounterSpliterator(String string) {
	    this.string = string;
	}

	@Override
	public boolean tryAdvance(Consumer<? super Character> action) {
	    action.accept(string.charAt(currentChar++)); // Consume the current character
	    return currentChar < string.length(); // Return true if there are further characters to be consumed
	}

	@Override
	public Spliterator<Character> trySplit() {
	    final int currentSize = string.length() - currentChar;

	    // Return null to signal that the String to be parsed is small enough to be precessed sequentially
	    if (currentSize < 10) {
		return null;
	    }

	    // Set the candidate's split position to be half of the String to be parsed
	    for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
		
		// Advance the split position until the next space
		if (Character.isWhitespace(string.charAt(splitPos))) {
		
		    // Create a new WordCounter-Spliterator parsing the String from the start to the split position
		    final Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
		    
		    // Set the start position of this WordCounter-Spliterator to the split position
		    currentChar = splitPos;
		    return spliterator;
		}
	    }

	    return null;
	}

	@Override
	public long estimateSize() {
	    return string.length() - currentChar;
	}

	@Override
	public int characteristics() {
	    return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
	}
    }

    // A class to count words while traversing a stream of Characters
    public static class WordCounter {

	private final int counter;

	private final boolean lastSpace;

	public WordCounter(int counter, boolean lastSpace) {
	    this.counter = counter;
	    this.lastSpace = lastSpace;
	}

	public WordCounter accumulate(Character c) { // The accumulate method traverses the Characters one by one as done by the iterative algorithm
	    if (Character.isWhitespace(c)) {
		return lastSpace ? this : new WordCounter(counter, true);
	    } else {
		return lastSpace ? new WordCounter(counter + 1, false) : this; // Increase the word counter when the last character is a space and the currently traversed one isn't
	    }
	}

	public WordCounter combine(WordCounter wordCounter) { // combine two wordCounters by summing their counters
	    return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
	}

	public int getCounter() {
	    return counter;
	}
    }

    ///////////////////////////////
    public static void main(String[] args) {
	test02();
    }

}
