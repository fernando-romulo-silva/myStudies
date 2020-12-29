package br.fernando.ch09_Streams_Objective.par03_the_stream_pipeline;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Test01 {

    // =========================================================================================================================================
    // Describe Stream interface and Stream pipeline
    static void test01_01() {
        final List<String> names = Arrays.asList("Boi", "Charis", "Zooey", "Bokeh", "Clover", "Aiko");

        // The source describes where the data is coming from;
        // the intermediate operations operate on the stream and produce another, perhaps modified, stream;
        // and the terminal operation ends the stream and typically produces a value or some output.

        names.stream() // the source
                .filter(s -> s.startsWith("B") || s.startsWith("C")) // intermediate op
                .filter(s -> s.length() > 3) // intermediate op
                .forEach(System.out::println); // terminal op

        // we can parallelize streams to take advantage of the underlying architecture of the system and do parallel computations very easily.
        // Not all streams can be parallelized, but some can. We’ll talk more about parallel streams later in “A Taste of Parallel Streams.”
    }

    // =========================================================================================================================================
    // Streams Are Lazy
    static void test01_02() {
        final List<String> names = Arrays.asList("Boi", "Charis", "Zooey", "Bokeh", "Clover", "Aiko");

        // Nothing has happened. Going back to the assembly-line analogy, the worker at station 1 (filter one) is still sitting idle,
        // as is the worker at station 2 (filter two).

        // That’s because no terminal operation has been executed yet. We’ve got everything set up, but nothing to kick-start the data processing.

        // Not until the forEach() is executed does anything happen. As soon as the terminal operation is executed, then the assembly line kicks
        // into gear, and the data starts flowing from the source through the stream and into the operations

        final Stream<String> stream = names.stream() // the source
                .filter(s -> s.startsWith("B") || s.startsWith("C")) // intermediate op
                .filter(s -> s.length() > 3); // intermediate op

        // This laziness makes streams more efficient because the JDK can perform
        // optimizations to combine the operations efficiently, to operate on the data in a single
        // pass, and to reduce operations on data whenever possible. If it’s not necessary to run
        // an operation on a piece of data, then we can avoid even getting the data element from the source.
    }

    // =========================================================================================================================================
    static void summary() {

        final List<String> names = Arrays.asList("Boi", "Charis", "Zooey", "Bokeh", "Clover", "Aiko");

        // Stream Pipeline
        names.stream() // the source
                .filter(s -> s.startsWith("B") || s.startsWith("C")) // intermediate op
                .filter(s -> s.length() > 3) // intermediate op
                .forEach(System.out::println); // terminal op

        // The intermediate op. create another stream
        // 
        // The terminal op. create value(s)

        // The stream are Lazy, only execute when have a terminal operation or state full operation

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
