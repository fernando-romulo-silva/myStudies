package br.fernando.ch09_Streams_Objective.par08_Collecting_values_from_stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
class Test01 {

    static List<Person> people = Arrays.asList( //
            new Person("Beth", 30), //
            new Person("Eric", 31), //
            new Person("Deb", 31), //
            new Person("Liz", 30), //
            new Person("Wendi", 34), //
            new Person("Kathy", 35), //
            new Person("Bert", 32), //
            new Person("Bill", 34), //
            new Person("Robert", 38));

    // =========================================================================================================================================
    // Collecting Values from Streams
    static void test01_01() {

        List<Person> peopleAge34 = people.stream() // stream the people
                .filter(d -> d.getAge() == 34) // find people age 34
                .collect(Collectors.toList()); // collect 34s into a new List

        System.out.println("People aged 34: " + peopleAge34);

        // There’s no guarantee what kind of List you get using Collectors.toList() .
        // If you specifically want an ArrayList, you can use the toCollection() method instead, like this:
        List<Person> peopleAge34_02 = people.stream() //
                .filter(p -> p.getAge() == 34) //
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("People aged 34: " + peopleAge34_02);
    }

    // =========================================================================================================================================
    // Using collect() with Files.lines()
    static void test01_02() {
        // Using collect() is the preferred way to do this, and, as you’ll see, along with
        // avoiding an unnecessary side effect, it also makes your code clearer.

        String fileName = "names.txt";

        try (final Stream<String> stream = Files.lines(Paths.get(fileName))) {

            final List<String> data = stream.collect(Collectors.toList()); // collect names

            data.forEach(System.out::println); // print names

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    // =========================================================================================================================================
    // Grouping and Partitioning
    static void test01_03() {
        // You can think of the Function as a “classification function.” If we want to group
        // people by age, then we need to pass a Function to groupingBy() that maps a Person to their age:

        Map<Integer, List<Person>> peopleByAge = people.stream() //
                .collect(Collectors.groupingBy(Person::getAge));

        System.out.println("People by age: " + peopleByAge);

        // Notice that the Map you get back uses an age ( Integer ) as a key and the value is a
        // List of the same type of object in the stream; in this case, that’s Person .

        // So we have two reductions going on here: we have a groupingBy() reduction to group people
        // by age and then we have a counting() reduction to count the people in the List associated
        // with a particular age:

        Map<Integer, Long> numPeopleWithAge = people.stream() //
                .collect(Collectors.groupingBy( // we're going to goup by ...
                        Person::getAge, // age
                        Collectors.counting() // and count rather than list
                ));

        //
        System.out.println("People by age: " + numPeopleWithAge);

        // What if we want to group people by age, but list only their name rather than the
        // entire Person object in the Map?

        Map<Integer, List<String>> namesByAge = people.stream() //
                .collect(Collectors.groupingBy( //
                        Person::getAge, // group by age
                        Collectors.mapping(// map from Person to ...
                                Person::getName, // .. name
                                Collectors.toList() // collect names in a list
                        ) //
                ) //
                );

        System.out.println("People by age: " + namesByAge);

        // The mapping() method maps each Person to another value. What we’d like to do is
        // map a Person to their name. Taking a look at the mapping() method, we see that its
        // first argument is a Function whose functional method takes an object of the type
        // we’re mapping from (a Person) and returns another object, in this case, the Person’s
        // name, a String.
        // The second argument to mapping() is a collector that tells us what to
        // do with the potentially multiple values we’re mapping. Remember that we can have
        // multiple people of the same age, so we’re mapping a List of Persons to a List of their names.

        // The partioningBy() method organizes the results into a Map like groupingBy() does,
        // but partioningBy() takes a Predicate rather than a Function, so the results are split
        // into two groups (partitions) based on whether the items in the stream pass the test in
        // the Predicate

        Map<Boolean, List<Person>> peopleOldThan34 = people.stream() //
                .collect(Collectors.partitioningBy( //
                        p -> p.getAge() > 34 //
                ));

        System.out.println("People > 34: " + peopleOldThan34);
    }

    static void summary() {
        // ************************************************************************************
        // Method Collect - a reduce that use a collector
        // ************************************************************************************
        List<Person> peopleAge34 = people.stream() // stream the people
                .filter(d -> d.getAge() == 34) // find people age 34
                .collect(Collectors.toList()); // collect 34s into a new List, but not guarantee that it's a ArrayList

        List<Person> peopleAge34_02 = people.stream() //
                .filter(p -> p.getAge() == 34) //
                .collect(Collectors.toCollection(ArrayList::new)); // you can use it to determine the kind of list

        Map<String, Integer> peopleAge34_03 = people.stream() //
                .filter(p -> p.getAge() == 34) //
                .collect(Collectors.toMap(b -> b.getName(), b -> b.getAge())); // you can convert to map too.

        // ************************************************************************************
        // Collect and Files
        // ************************************************************************************
        String fileName = "names.txt";

        try (final Stream<String> stream = Files.lines(Paths.get(fileName))) {

            final List<String> data = stream.collect(Collectors.toList()); // collect the lines of the file

            data.forEach(System.out::println); // print lines

        } catch (IOException ex) {
            System.out.println(ex);
        }

        // ************************************************************************************
        // Grouping - Collectors that grouping by something, method Collectors.counting()
        // ************************************************************************************
        Map<Integer, List<Person>> peopleByAge = people.stream() //
                .collect(Collectors.groupingBy(Person::getAge));
        // Age is a Integer, so that group will be people with same age. 
        // The key is the age, and values are Persons

        // Now, I'll group by age (key) but, I want the COUNT of the values, not the values
        Map<Integer, Long> numPeopleWithAge = people.stream() //
                .collect(Collectors.groupingBy( // we're going to group by ...
                        Person::getAge, // age, the key
                        Collectors.counting() // and count how much has that age
                ));

        // Now, I'll group by age (key) but, I want only a attribute of the values, so I'll mapping it...
        Map<Integer, List<String>> namesByAge = people.stream() //
                .collect(Collectors.groupingBy( //
                        Person::getAge, // group by age, the key
                        Collectors.mapping(// map from Person to ...
                                Person::getName, // .. name ...
                                Collectors.toList() // in a list of names
                        ) //
                ) //
                );

        // ***************************************************************************************************
        // Partitioning - Collect grouping by two groups, that is ok with a Predicate or not ok with Predicate
        // ***************************************************************************************************
        Map<Boolean, List<Person>> peopleOldThan34 = people.stream() //
                .collect(Collectors.partitioningBy( //
                        p -> p.getAge() > 34 //
                ));

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_03();
    }
}
