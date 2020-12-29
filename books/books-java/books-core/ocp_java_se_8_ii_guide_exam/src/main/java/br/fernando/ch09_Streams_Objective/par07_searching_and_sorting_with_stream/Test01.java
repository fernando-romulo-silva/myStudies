package br.fernando.ch09_Streams_Objective.par07_searching_and_sorting_with_stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("unused")
class Test01 {

    static final Dog boi = new Dog("boi", 30, 6);

    static final Dog clover = new Dog("clover", 35, 12);

    static final Dog aiko = new Dog("aiko", 50, 10);

    static final Dog zooey = new Dog("zooey", 45, 8);

    static final Dog charis = new Dog("charis", 20, 7);

    static final List<Dog> dogs = Arrays.asList(boi, clover, aiko, zooey, charis);

    static final List<Duck> ducks = Arrays.asList( //
            new Duck("Jerry", "yellow", 3), //
            new Duck("George", "brown", 4), //
            new Duck("krammer", "mottled", 6), //
            new Duck("Elaine", "white", 2), //
            new Duck("Huey", "mottled", 2), //
            new Duck("Louie", "white", 4), //
            new Duck("Dwey", "brown", 6)

    );

    // =========================================================================================================================================
    // Searching to See Whether an Element Exists
    static void test01_01() {
        // The methods allMatch(), anyMatch(), and noneMatch() all take a Predicate to do a matching test and return a boolean.

        boolean cNames = dogs.stream() // stream the dogs
                .filter(d -> d.getWeight() > 50) // keep dogs whose weight > 50
                .anyMatch(d -> d.getName().startsWith("c")); // do any dog names start with c?

        System.out.println("Are there any dogs > 50 pounds whose name starts with 'c'? " + cNames);

        // allMatch() to find whether ALL the dogs in the list have an age greater than 5.

        boolean isOlder = dogs.stream() //
                .mapToInt(d -> d.getAge()) // map from Dog to the Dog's age (integer)
                .allMatch(a -> a > 5); // do all dogs have an age > 5?

        System.out.println("Are all the dogs age older than 5? " + isOlder);

        // use noneMatch() to make sure that none of the dogs in the stream are named "red".
        boolean notRed = dogs.stream() //
                .map(d -> d.getName()) // map from Dog to Dog's name (String)
                .noneMatch(n -> n.equals("red")); // are any of the dogs named "red"?

        System.out.println("None of th dogs are red: " + notRed);

    }

    // =========================================================================================================================================
    // Searching to Find and Return an Object
    static void test01_02() {
        // If you want to actually get back a result of a match, then you can use findFirst() or findAny().

        // Use findAny() to find any Dog in our list of dogs that weighs more than 50 pounds and whose name begins with "c".
        Optional<Dog> c50 = dogs.stream() // stream the dogs
                .filter(d -> d.getWeight() > 50) // keep dogs with weight > 50
                .filter(d -> d.getName().startsWith("c")) // keep dogs with name "c"
                .findAny(); // pick any dog from the stream and return it

        c50.ifPresent(System.out::println);

        // Of course, boi happens to be the first dog in the stream, but technically findAny()
        // could return any of the dogs in the stream. There’s no guarantee it will be the first one
        // (particularly when you parallelize the stream, which we’ll get to later on).
    }

    // =========================================================================================================================================
    // Sorting
    static void test01_03() {
        // The Stream interface includes a sorted() method that you can use to sort a stream of elements by natural order or by
        // providing a Comparator to determine the order.

        Stream.of("Jerry", "George", "Kramer", "Elaine")//
                .sorted() //
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------------------");

        // We’ve gone ahead and made the Duck a Comparable and implemented the
        // compareTo() method that sorts by name so we can sort these Ducks

        ducks.stream() //
                .sorted() // sort ducks by name
                .forEach(System.out::println); // print them

        // What if you want to change the sort when you stream the ducks? You can use sorted() with a Comparator:

        final Comparator<Duck> byAgeLmabda = (d1, d2) -> d1.getAge() - d2.getAge();

        ducks.stream() //
                .sorted(byAgeLmabda) // sort ducks by age
                .forEach(System.out::println);

        // Comparator also has some handy static methods that you’re likely to see when defining comparators for use
        // with streams: comparing(), reversed(), and thenComparing().

        // The comparing() method takes a Function whose functional method expects a property to sort by, like Duck.age, as
        // an argument and returns a Comparator that compares objects by that property.
        Comparator<Duck> byColor = Comparator.comparing(Duck::getColor);
        Comparator<Duck> byName = Comparator.comparing(Duck::getName);
        Comparator<Duck> byAge = Comparator.comparing(Duck::getAge);

        // We can sort by age:
        ducks.stream() //
                .sorted(byAge) //
                .forEach(System.out::println);

        // Or sort by age reversed:
        ducks.stream() //
                .sorted(byAge.reversed()) //
                .forEach(System.out::println);

        // Note that 'reversed()' is a method of the Comparator, that returns a new Comparator that has the reverse ordering.
        ducks.stream() //
                .sorted(byName.thenComparing(byAge))//
                .forEach(System.out::println);

        // To get the list of distinct colors of ducks, we’ll first map each Duck to its color, then
        // use distinct() to make sure we get a stream of distinct color Strings, and then print those:

        ducks.stream() //
                .map(Duck::getColor) // get the duck colors
                .distinct() // make sure there are no repeates!
                .forEach(System.out::println);// print the colors
    }

    // =========================================================================================================================================
    static void summary() {

        // **********************************************************************************************************
        // Search elements on stream and return true or false if match - Use a Predicate function 
        // All operations is short circuit - All methods return boolean
        // **********************************************************************************************************
        // anyMatch
        boolean cNames = dogs.stream() //
                .filter(d -> d.getWeight() > 50) //
                .anyMatch(d -> d.getName().startsWith("c")); // do any dog names start with c?

        // allMatch
        boolean isOlder = dogs.stream() //
                .mapToInt(d -> d.getAge())//
                .allMatch(a -> a > 5); // do ALL dogs have an age > 5?

        // noneMatch()
        boolean notRed = dogs.stream() //
                .map(d -> d.getName()) // map from Dog to Dog's name (String)
                .noneMatch(n -> n.equals("red")); // are any of the dogs named "red"?

        // **********************************************************************************************************
        // Searching to Find and Return an Object - All methods return a Optional
        // **********************************************************************************************************
        // Use findAny() to find any Dog in our list of dogs that weighs more than 50 pounds and whose name begins with "c".
        Optional<Dog> c50 = dogs.stream() // stream the dogs
                .filter(d -> d.getWeight() > 50) // keep dogs with weight > 50
                .filter(d -> d.getName().startsWith("c")) // keep dogs with name "c"
                .findAny(); // pick ANY dog from the stream and return it

        c50.ifPresent(System.out::println);

        // Use findFirst() to find the first Dog in our list of dogs, There’s no guarantee it will be the first one (parallel).
        Optional<Dog> c60 = dogs.stream() // stream the dogs
                .filter(d -> d.getWeight() > 50) // keep dogs with weight > 50
                .filter(d -> d.getName().startsWith("c")) // keep dogs with name "c"
                .findFirst(); // pick the FIRST dog from the stream and return it

        c60.ifPresent(System.out::println);

        // **********************************************************************************************************
        // Sorting elements
        // **********************************************************************************************************
        Stream.of("Jerry", "George", "Kramer", "Elaine")//
                .sorted() // sort elements, natural order
                .forEach(System.out::println);

        // We can use Comparator ( comparator p1 < p2 => negative; p1 == p2 => zero; p1 > p2 => positive
        final Comparator<String> byNameSize = (p1, p2) -> p1.length() - p2.length();

        Stream.of("Jerry", "George", "Kramer", "Elaine")//
                .sorted(byNameSize) // sort elements with comparator
                .forEach(System.out::println);

        // You can create comparators objects by comparing methods
        Comparator<Duck> byColor = Comparator.comparing(Duck::getColor);
        Comparator<Duck> byName = Comparator.comparing(Duck::getName);
        Comparator<Duck> byAge = Comparator.comparing(Duck::getAge);

        // you can reverse the comparators
        Stream.of("Jerry", "George", "Kramer", "Elaine")//
                .sorted(byNameSize.reversed()) // reverse sort elements with comparator, reversed method return another comparator
                .forEach(System.out::println);

        // You can make a "stream" of comparator, when the first comparator is equal, then execute the second
        ducks.stream() //
                .sorted(byName.thenComparing(byAge))//
                .forEach(System.out::println);

        // **********************************************************************************************************
        // Distinct Elements
        // **********************************************************************************************************
        // To get the list of distinct colors of ducks, we’ll first map each Duck to its color, then
        // use distinct() to make sure we get a stream of distinct color Strings, and then print those:

        ducks.stream() //
                .map(Duck::getColor) // get the duck colors
                .distinct() // make sure there are no repeates!
                .forEach(System.out::println);// print the colors

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_02();
    }
}
