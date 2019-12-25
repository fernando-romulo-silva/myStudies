package br.fernando.ch11_Concurrency_Objective.par05_Parallel_Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test04 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Stream operations that are not necessarily stateful include map() , filter() , and reduce() (among others), so there’s a lot we can do
        // with streams that is stateless, although, as with all things related to concurrency, we need to be careful.
        // As you saw in the example above, when we tried to count numbers in the stream using a filter() , we can make filter() stateful by
        // creating a side effect.
        //

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        long sum = nums.stream() //
                .parallel() // make the stream parallel
                .mapToInt(n -> n) // map from Integer to int
                .filter(i -> i % 2 == 0 ? true : false) //
                .sum(); // sum the evens

        System.out.println("Sum of evens is: " + sum);

        // None of these stream operations requires any state in order to operate on the values in the stream pipeline. The map simply converts the type of
        // the stream value from Integer to int ; the filter simply determines if the value is even or odd, and the reduction ( sum() ) sums the values,
        // which can happen in any order because sum() is associative, so this operation requires no state either.
        //
        // This is an example of a stateless stream pipeline
    }

    // =========================================================================================================================================
    // Unordered Streams
    static void test01_02() throws Exception {
        // By default, many (but not all!) streams are ordered. That means there is an inherent ordering to the items in the stream.
        // A stream of int s created by range() and a stream of Integer s created from a List of Integer s are both ordered streams.
        // Intuitively that makes sense; technically, ordering is determined by whether the stream has an ORDERED characteristic

        final List<Integer> nums01 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Stream<Integer> s = nums01.stream();
        System.out.println("Stream from List ordered? " + s.spliterator().hasCharacteristics(Spliterator.ORDERED));

        // If a stream is ordered and we process it in parallel, the stream will remain ordered, but at the price of some efficiency.
        // If we don’t care about the ordering, as is the case when we are summing numbers, we might as well remove the ordering on the stream
        // in scenarios where we are using a parallel stream pipeline;

        long sum = s.unordered() // make the stream unordered
                .parallel() //
                .mapToInt(n -> n) //
                .filter(i -> i % 2 == 0 ? true : false) //
                .sum(); //

        // Calling unordered() doesn’t change the ordering of the stream; it just unsets that ORDERED bit so the stream doesn’t have to
        // maintain the ordering state.
        //
        // Depending on how the stream is processed in the parallel pipeline, you may find that the final stream (before the reduction) is in the
        // same order as the source, or not.
        //
        // Note that an ordered stream is not the same thing as a sorted stream. Once you’ve made a stream unordered, there is no way to order it
        // again, except by calling sorted(), which makes it sorted and ordered but not necessarily in the same order as it was in the original source.
        //
        // To summarize: to make the most efficient parallel stream, you should:
        //
        // * Make sure your reductions are associative and stateless.
        //
        // * Avoid side effects.
        //
        // * Make sure your pipeline is stateless by avoiding contextual operations such as limit(), skip(), distinct(), and sorted()
    }

    // =========================================================================================================================================
    // forEach() and forEachOrdered()
    static void test01_03() throws Exception {

        final Dog aiko = new Dog("aiko", 10, 10);
        final Dog boi = new Dog("boi", 6, 6);
        final Dog charis = new Dog("charis", 7, 7);
        final Dog clover = new Dog("clover", 12, 12);
        final Dog zooey = new Dog("zooey", 8, 8);

        final List<Dog> dogs = new ArrayList<>();

        // Notice that we’ve added the dogs in alphabetical order on purpose, so we can watch the ordering of our stream when we use a sequential
        // stream and when we use a parallel stream.
        dogs.add(aiko);
        dogs.add(boi);
        dogs.add(charis);
        dogs.add(clover);
        dogs.add(zooey);

        dogs.stream() //
                .filter(d -> d.getAge() > 7) //
                .forEach(System.out::println);

        System.out.println("-----------------------------------------------------------------------------");

        // aiko is 10 years old
        // clover is 12 years old
        // zooey is 8 years old
        //
        // That is, we see all dogs whose age is > 7 in the order in which they are encountered in the stream (which is the order in which they are added to the List ).
        // This is because, by default, when we stream the List , we get a sequential, ordered stream.
        dogs.stream() //
                .parallel() //
                .filter(d -> d.getAge() > 7) //
                .forEach(System.out::println);
        //
        // The output is unpredictable; if you run it again, you may get different results. Even though the stream is ordered (since we’re streaming a List ),
        // we see the output in a random order because the stream is parallel, so the dogs can be processed by different threads that may finish at different
        // times, and the forEach() Consumer is stateful
        //
        //
        // We can make sure we see the dogs in the order in which they appear in the original source by using the method forEachOrdered() instead:
        dogs.stream() //
                .parallel() //
                .filter(d -> d.getAge() > 7) //
                .forEachOrdered(System.out::println); // enforce ordering

        // Of course, this takes a bit of overhead to perform, right? As we discussed earlier, an ordered stream is going to be less efficient when processed
        // in parallel than an unordered stream

    }

    static class Dog {

        private final String name;

        private final int age;

        private final int weight;

        public Dog(String name, int age, int weight) {
            super();
            this.name = name;
            this.age = age;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return name + " is " + age + " years old";
        }
    }

    // =========================================================================================================================================
    // A Quick Word About findAny()
    static void test01_04() throws Exception {
        IntStream nums01 = IntStream.range(0, 20);

        OptionalInt any01 = nums01 //
                .filter(i -> i % 2 == 0 ? true : false) // filter the evens
                .findAny(); // find any even int

        // With a sequential stream, findAny() will likely return the first value in the stream, 0, every time, even though it’s not guaranteed.
        any01.ifPresent(i -> System.out.println("Any even is: "));

        // Now, let’s parallelize this stream and see what happens. We’ll add a peek() , so we can see the thread workers as they work on the problem:

        IntStream nums02 = IntStream.range(0, 20);
        OptionalInt any02 = nums02 //
                .parallel() // make thre stream parallel
                .peek(i -> System.out.println(i + ": " + Thread.currentThread().getName())) //
                .filter(i -> i % 2 == 0 ? true : false) // filter the evens
                .findAny(); // find any even int

        // Remember, our computer has eight cores, so this stream pipeline has been split up into eight workers, each tackling part of the stream
        //
        // The findAny() method is short circuiting, so even though we still have 12 more values in the stream to process
        // (since the stream has 20 values), we stop as soon as we find the first even number
        //
        // Run the code again, and you’ll likely get a different answer.
    }

    static void summary() throws Exception {
        // ***********************************************************************
        // Unordered Streams
        // ***********************************************************************
        // By default, many (but not all!) streams are ordered

        final List<Integer> nums01 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Stream<Integer> stream = nums01.stream();
        System.out.println("Stream from List ordered? " + stream.spliterator().hasCharacteristics(Spliterator.ORDERED));

        long sum = stream.unordered() // make the stream unordered
                .parallel() //
                .mapToInt(n -> n) //
                .filter(i -> i % 2 == 0 ? true : false) //
                .sum(); //

        // Calling unordered() doesn’t change the ordering of the stream; it just unsets that ORDERED bit so the stream doesn’t have to
        // maintain the ordering state.
        //
        // * Make sure your reductions are associative and stateless.
        // * Avoid side effects.
        // * Make sure your pipeline is stateless by avoiding contextual operations such as limit(), skip(), distinct(), and sorted()

        //
        // ***********************************************************************
        // forEach() and forEachOrdered()
        // ***********************************************************************
        nums01.stream() //
                .unordered() //
                .mapToInt(n -> n) //
                .filter(i -> i % 2 == 0 ? true : false) //
                .forEach(System.out::println);

        // The output is unpredictable; if you run it again, you may get different results. Even though the stream is ordered (since we’re streaming a List ),
        // we see the output in a random order because the stream is parallel, so the numbers can be processed by different threads that may finish at different
        // times, and the forEach() Consumer is stateful

        nums01.stream() //
                .unordered() //
                .mapToInt(n -> n) //
                .filter(i -> i % 2 == 0 ? true : false) //
                .forEachOrdered(System.out::println); // enforce ordering

        // ***********************************************************************
        // A Quick Word About findAny()
        // ***********************************************************************

        IntStream nums02 = IntStream.range(0, 20);

        OptionalInt any01 = nums02 //
                .filter(i -> i % 2 == 0 ? true : false) // filter the evens
                .findAny(); // find any even int

        // With a sequential stream, findAny() will likely return the first value in the stream, 0, every time, even though it’s not guaranteed.
        any01.ifPresent(i -> System.out.println("Any even is: "));

        // Now, let’s parallelize this stream and see what happens. We’ll add a peek() , so we can see the thread workers as they work on the problem:

        IntStream nums03 = IntStream.range(0, 20);

        OptionalInt any02 = nums03 //
                .parallel() // make thre stream parallel
                .peek(i -> System.out.println(i + ": " + Thread.currentThread().getName())) //
                .filter(i -> i % 2 == 0 ? true : false) // filter the evens
                .findAny(); // find any even int

        any02.ifPresent(i -> System.out.println("Any even is: "));
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_03();
    }
}
