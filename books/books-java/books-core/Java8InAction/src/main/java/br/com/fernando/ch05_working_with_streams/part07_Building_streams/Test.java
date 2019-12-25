package br.com.fernando.ch05_working_with_streams.part07_Building_streams;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

// Building streams
public class Test {

    // This section shows how you can create a stream from a sequence of values, from an array,
    // from a file, and even from a generative function to create infinite streams!
    //
    // Streams from values
    public static void test1() {
        // You can create a stream with explicit values by using the static method Stream.of, which can
        // take any number of parameters.
        //
        final Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // You can get an empty stream using the empty method as follows:
        final Stream<String> emptyStream = Stream.empty();

        System.out.println(emptyStream);
    }

    // Streams from arrays
    public static void test2() {

        // You can create a stream from an array using the static method Arrays.stream, which takes an
        // array as parameter.
        final int[] numbers = { 2, 3, 5, 7, 13 };

        final int sum = Arrays.stream(numbers).sum(); // The sum is 41

        System.out.println(sum);
    }

    // Streams from files
    public static void test3() {

        // Java’s NIO API (non-blocking I/O), which is used for I/O operations such as processing a file,
        // has been updated to take advantage of the Streams API. Many static methods in
        // java.nio.file.Files return a stream. For example, a useful method is Files.lines, which returns a
        // stream of lines as strings from a given file

        long uniqueWords = 0;

        try (final Stream<String> lines = Files.lines(Paths.get("data"), Charset.defaultCharset())) { // Streams are autoclosable.

            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))) // Generate a stream of words
                .distinct() // Remove duplicates.
                .count(); // Count the number of unique words.

        } catch (final Exception e) {
            throw new IllegalStateException(e); // Deal with the exception if one occurs when opening the file
        }

        // You use Files.lines to return a stream where each element is a line in the given file. You then
        // split each line into words by calling the split method on line. Notice how you use flatMap to
        // produce one flattened stream of words instead of multiple streams of words for each line. Finally,
        // you count each distinct word in the stream by chaining the methods distinct and count.

        System.out.println(uniqueWords);
    }

    // Streams from functions: creating infinite streams!
    public static void test4() {
        // The Streams API provides two static methods to generate a stream from a function: Stream.iterate and Stream.generate.

        // Iterate
        // The iterate method takes an initial value, here 0, and a lambda (of type Unary-Operator<T>) to
        // apply successively on each new value produced. Here you return the previous element added with 2 using the lambda n -> n + 2. A
        Stream.iterate(0, n -> n + 2) //
            .limit(10) //
            .forEach(System.out::println);
        // Note that this operation produces an infinite stream—the stream doesn’t have an end because values are
        // computed on demand and can be computed forever

        // Fibonacci tuples series
        // How does it work? iterate needs a lambda to specify the successor element. In the case of the
        // tuple (3, 5) the successor is (5, 3+5) = (5, 8).
        Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] }) //
            .limit(10) //
            .map(t -> t[0]) //
            .forEach(System.out::println);
        //
        // Generate
        // Similarly to the method iterate, the method generate lets you produce an infinite stream of
        // values computed on demand. But generate doesn’t apply successively a function on each new
        // produced value. It takes a lambda of type Supplier<T> to provide new values. Let’s look at an
        // example of how to use it:

        Stream.generate(Math::random) //
            .limit(5) //
            .forEach(System.out::println);
        
        // Using generate so you can compare it with the approach using the iterate method! 
        // But it’s important to note that a supplier that’s stateful isn’t safe to use in parallel code.
        // So what follows is shown just for completeness but should be avoided!
        //
        // The generate method will use the given supplier and repeatedly call the getAsInt method, which
        // always returns 2. But the difference between the anonymous class used here and a lambda is
        // that the anonymous class can define state via fields, which the getAsInt method can modify. 
        // This is an example of a side effect. All lambdas you’ve seen so far were side-effect free; they didn’t
        // change any state.
    }

    public static void main(String[] args) {

    }
}
