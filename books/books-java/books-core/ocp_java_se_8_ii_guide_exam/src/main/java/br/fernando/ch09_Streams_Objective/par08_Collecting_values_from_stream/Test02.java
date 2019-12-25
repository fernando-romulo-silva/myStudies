package br.fernando.ch09_Streams_Objective.par08_Collecting_values_from_stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
class Test02 {

    static List<Person> people = Arrays.asList( //
            new Person("Beth", 30), //
            new Person("Eric", 31), //
            new Person("Deb", 31), //
            new Person("Liz", 30), //
            new Person("Wendi", 34), //
            new Person("Kathy", 35), //
            new Person("Bert", 32), //
            new Person("Bill", 34), //
            new Person("Robert", 38), //
            // ------------------------------ Names repeated
            new Person("Bill", 40), //
            new Person("Beth", 45), //
            new Person("Bert", 38) //
    );

    // =========================================================================================================================================
    // Summing and Averaging
    static void test01_01() {

        // The code took the ages of the two Bills and added them together. It does the same
        // thing with the two Beths and the two Berts, and groups the results by name in a Map

        final Map<String, Integer> sumOfBAges = people.stream() // stream people
                .filter(p -> p.getName().startsWith("B")) // filter "B" Names
                .collect( // collect
                        Collectors.groupingBy( // groupBy
                                Person::getName, // ... name
                                Collectors.summingInt(Person::getAge) // and sum of Ages
                        ));

        System.out.println("People by sum of age: " + sumOfBAges);

        // What about the average age of the people whose names begin with “B”? Yep, that’s
        // what averagingInt() is for:

        final Map<String, Double> avgOfBAges = people.stream() // note we need double not Integer for the values!
                .filter(p -> p.getName().startsWith("B")) //
                .collect( //
                        Collectors.groupingBy( //
                                Person::getName, //
                                Collectors.averagingDouble(Person::getAge) //
                        ));

        System.out.println("People by avg of age: " + avgOfBAges);
    }

    // =========================================================================================================================================
    // Counting, joining, maxBy, and minBy
    static void test01_02() {
        // We used the Collectors.counting() method above to create a Collector that counts elements being collected.
        // More typically, you’ll find Collectors.counting() used like we did above, as part of a groupingBy() operation.
        Long n = people.stream().collect(Collectors.counting());
        System.out.println("Count: " + n);

        // The Collectors.joining() method returns a Collector that takes stream
        // elements and concatenates them into a String by order in which they are encountered
        // (which may or may not be the order of the original Collection you’re streaming!).
        // For instance, we can get the name of every Person who’s older than 34 and join
        // those names together into one String like this:

        final String older34 = people.stream() // stream people
                .filter(p -> p.getAge() < 34) //
                .map(Person::getName) // map Person to name
                .collect(Collectors.joining(",")); // join names into one string

        System.out.println("Names of people older than 34: " + older34);

        // The methods maxBy() and minBy() do what you expect: they collect (reduce) to the
        // max and min of the input elements, respectively.
        final Optional<Person> oldest = people.stream() //
                .collect(Collectors.maxBy((p1, p2) -> p1.getAge() - p2.getAge()));

        oldest.ifPresent(p -> System.out.println("Oldes person: " + p));

        // The advantage to finding the max of our Person stream using collect() with the
        // maxBy() collector, rather than just using the max() terminal operation on the stream, like this:

        people.stream().mapToInt(p -> p.getAge()).max();

        // is that our result is a Person object. Remember, IntStream.max() works only on
        // numbers, so we when we mapped our Person stream to a stream of Integer s in order
        // to use max() to find the oldest person, we got a number back.
    }

    // =========================================================================================================================================
    static void summarize() {
        // **********************************************************************************************************
        // Summing - Collect that grouping by key and SUM of the values, like count. Use Collectors.summingInt method
        // **********************************************************************************************************
        final Map<String, Integer> sumOfBAges = people.stream() // stream people
                .filter(p -> p.getName().startsWith("B")) // filter "B" Names
                .collect( // collect
                        Collectors.groupingBy( // groupBy
                                Person::getName, // ... name, the key
                                Collectors.summingInt(Person::getAge) // and sum of Ages (the values)
                        ));

        // **********************************************************************************************************
        // Average - Collect that grouping by key and AVERAGE of the values. Use Collectors.averagingDouble method
        // **********************************************************************************************************
        final Map<String, Double> avgOfBAges = people.stream() // note we need double not Integer for the values!
                .filter(p -> p.getName().startsWith("B")) //
                .collect( //
                        Collectors.groupingBy( //
                                Person::getName, //
                                Collectors.averagingDouble(Person::getAge) //
                        ));

        // **********************************************************************************************************
        // Counting - It's more use with groupingBy method, see Test01 at same package
        // **********************************************************************************************************
        Long n = people.stream() //
                .collect(Collectors.counting()); // count

        people.stream().count(); // you can use it, more simple

        // **********************************************************************************************************
        // Joining - Collect values on a single value, with Strings
        // **********************************************************************************************************
        String value = people.stream() // stream people
                .filter(p -> p.getAge() < 34) //
                .map(Person::getName) // map Person to name
                .collect(Collectors.joining(",")); // join names into one string

        // **********************************************************************************************************
        // maxBy or minBy - reduce to the max or min of the input elements
        // **********************************************************************************************************
        final Optional<Person> oldest = people.stream() // return a Optional
                .collect( //
                        Collectors.maxBy( //
                                (p1, p2) -> p1.getAge() - p2.getAge() // it's a Comparator!
                        ));

        // So, in this case you can use it...
        OptionalInt max = people.stream() //
                .mapToInt(p -> p.getAge()) // you can map it to int, more efficiently
                .max();

        // but for groupinBy methods, not.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
