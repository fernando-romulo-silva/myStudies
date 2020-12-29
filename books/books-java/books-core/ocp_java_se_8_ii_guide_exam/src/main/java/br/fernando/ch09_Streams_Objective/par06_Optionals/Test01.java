package br.fernando.ch09_Streams_Objective.par06_Optionals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Test01 {

    static final List<Dog> dogsList = Arrays.asList(new Dog("Boi", 5), new Dog("Bingo", 5));

    // =========================================================================================================================================
    // Optional
    static void test01_01() {
        // What is an optional? It’s a container that may or may not contain a value.
        // You can think of an optional as a wrapper around a value and that wrapper provides various
        // methods to determine whether a value is there and, if it is, to get that value.

        Stream<Double> doubleStream = Stream.of(1.0, 2.0, 3.0, 4.0); // stram of doubles

        Optional<Double> aNum = doubleStream.findFirst(); // first the double

        if (aNum.isPresent()) {
            System.out.println("First number from the doubles stream: " + aNum.get());
        } else {
            System.out.println("Double stream is empty");
        }

        // That element is a Double object, so the type we use for the return value from
        // findFirst() is Optional<Double> . Note that OptionalDouble will not work here!
        //
        // Why? Because findFirst() is operating on a stream of Double s, not a stream of
        // double s. Again, pay very close attention to the types here.

        // You can create your own optionals, too. For instance, here’s how to create an
        // Optional<Dog> (using our previous class for Dog )

        Dog boi = new Dog("boi", 6);

        Optional<Dog> optionalDog01 = Optional.of(boi);

        optionalDog01.ifPresent(System.out::println);

        boi = null; // boi is null!

        Optional<Dog> optionalDog02 = Optional.of(boi); // potential problem here
        optionalDog02.ifPresent(System.out::println); // potential problem here

        // --------------------------------------------------------------

        Optional<Dog> optionalDog03 = Optional.ofNullable(boi); // check for null
        optionalDog03.ifPresent(System.out::println); //

        if (!optionalDog03.isPresent()) {
            System.out.println("Boi must be null!");
        }

        // You can also create empty optionals directly:
        Optional<Dog> emptyDog = Optional.empty(); // make an empty dog optional

        if (!emptyDog.isPresent()) {
            System.out.println("Empty Dog must be empty");
        }

        emptyDog = dogsList.stream().findFirst(); // find the first dog in the list, assign it to empty Dog;

        emptyDog.ifPresent(d -> System.out.println("Empty dog is no longer empty"));

        // Another way to handle an empty optional is the orElse() method. With this method you get the value in the optional,
        // or, if that optional is empty, you get the value you specify with the orElse() method:

        Dog aDog = emptyDog.orElse(new Dog("Default Dog", 10));
        System.out.println("A Dog: " + aDog);
    }

    static class Dog {

        final String name;

        final int age;

        public Dog(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }

        int getAge() {
            return age;
        }

        String getName() {
            return name;
        }
    }

    // =========================================================================================================================================
    static void summary() {
        Stream<Double> doubleStream = Stream.of(1.0, 2.0, 3.0, 4.0); // stram of doubles

        // Optional
        // It's a container that may or may not contain a value.
        Optional<Double> aNum = doubleStream.findFirst(); // first the double

        doubleStream.count(); // don't return  Optional

        // Constructors
        Optional<Double> aNumTemp = Optional.of(1D); // Throw a NullPointerException if Null

        aNumTemp = Optional.ofNullable(null); // if value is null, return a empty optional

        aNumTemp = Optional.empty(); // Return a empty Optional

        // Methods
        aNum.get(); // get The element, but if don't have, throw the NoSuchElementException

        aNum.isPresent(); // check if it has element

        aNum.ifPresent(System.out::println); // if present, execute the consumer function

        aNum.orElse(34D); // if not present, return a value

        aNum.orElseGet(() -> 0D); // if not present, return a function supplier result

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
