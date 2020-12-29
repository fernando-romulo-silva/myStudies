package br.fernando.ch09_Streams_Objective.par09_Streams_of_Streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Test01 {

    // =========================================================================================================================================
    // Use flatMap() methods in the Stream API
    static void test01_01() {

        try (Stream<String> input = Files.lines(Paths.get("java.txt"))) {

            // You know the split() method on String creates an array of Strings, and you know map()
            // produces a stream, so what this line of code does is take the stream of lines coming
            // from the file, splits each line into a String[], and generates a Stream of String arrays,
            // which we write as Stream<String[]>:

            Stream<String[]> inputStream = input.map(line -> line.split(" "));

            // inputStream.map(array -> Arrays.stream(array)).forEach(System.out::println);

            // The flatMap() method is similar to map() in that it maps a stream of one type into
            // a stream of another type, but it does something extra; flatMap() “flattens” out the
            // streams, essentially concatenating them into one stream. It replaces each stream with
            // its contents, creating one stream from many.

            Stream<String> ss = inputStream.flatMap(array -> Arrays.stream(array));

            ss.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------------------------------------");

        // --------------------------------------------
        // Our goal is count the number of words equal to “Java.” Hopefully that task is easier now;
        // you just need to filter() and count(). Here’s the whole code, so you can run it yourself:

        try (final Stream<String> input = Files.lines(Paths.get("java.txt"))) {

            long n = Files.lines(Paths.get("java.txt")) // stream lines from the file
                    .map(line -> line.split(" ")) // split each line into String[]
                    .flatMap(array -> Arrays.stream(array)) // stream arrays, and flatten
                    .filter(w -> w.equals("Java")) // filter the words = "Java"
                    .count(); // count "Java"

            System.out.println("Number of times 'Java' appears: " + n);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // The Stream interface also includes flatMapToDouble(), flatMapToInt(), and
        // flatMapToLong(), to flat map to a DoubleStream, an IntStream, and a LongStream,
        // respectively, in case you need that. Each of the primitive stream types just mentioned
        // also include their own flatMap() methods that take a DoubleFunction, an
        // IntFunction, and a LongFunction, respectively.
    }

    // =========================================================================================================================================
    static void summary() {
        // ********************************************************************************************
        // flatMap - This method transform one type into another type, com
        // ********************************************************************************************

        List<String> texts = Arrays.asList("This is text, of course it is a text for learning.", "Don't worry about the text.", "Just study Java!"); // 19 words

        // How many words int the text list?
        //
        long count = texts.stream() //
                .map(t -> t.split(" ")) // split return a String[], map return a Stream<String[]>.
                .flatMap(array -> Arrays.stream(array)) // flatMap transform Stream<String[]> to Stream<String>, we can do something useful with it.
                .count(); // reduce operation

        System.out.println(count);

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        summary();
    }
}
