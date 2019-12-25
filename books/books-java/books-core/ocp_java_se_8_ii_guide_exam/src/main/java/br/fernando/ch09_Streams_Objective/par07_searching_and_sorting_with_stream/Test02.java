package br.fernando.ch09_Streams_Objective.par07_searching_and_sorting_with_stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Test02 {

    static final Dog boi = new Dog("boi", 30, 6);

    static final Dog clover = new Dog("clover", 35, 12);

    static final Dog aiko = new Dog("aiko", 50, 10);

    static final Dog zooey = new Dog("zooey", 45, 8);

    static final Dog charis = new Dog("charis", 120, 7);

    static final List<Dog> dogs = Arrays.asList(boi, clover, aiko, zooey, charis);

    // =========================================================================================================================================
    // Don’t Modify the Source of a Stream
    static void test01_01() {
        // You might be tempted at times, but don’t. It just isn’t done. Java won’t give you a
        // compile-time error if you try and may not even give you a runtime error, although your
        // results will not be guaranteed. But just don’t do it. Ever.

        // Don't do that!
        dogs.stream() //
                .filter(d -> {

                    if (d.getWeight() < 50) {
                        dogs.remove(d);
                        return false;
                    }

                    return true;
                }).forEach(System.out::println);

        // The best way to do this is to collect the dogs at the end of the stream pipeline into a
        // new collection. And, of course, Java provides an easy way for you to do just that with
        // the Stream.collect() method.

        final List<Dog> heavyDogs = dogs.stream() // stream the dogs
                .filter(d -> d.getWeight() >= 50) // filter only dogs >= 50 pounds
                .collect(Collectors.toList()); // collect the dogs int a new List

        heavyDogs.forEach(System.out::println);

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
