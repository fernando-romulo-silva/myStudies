package br.com.fernando.ch15_Blending_OOP_and_FP.part01_Introduction_to_Scala;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

// Introduction to Scala
public class Test {

    // Hello beer
    public static void test01() {
        IntStream.rangeClosed(2, 6).forEach(n -> System.out.println("Hello " + n + " bottles of beer"));

        // Creating collections
        Map<String, Integer> authorsToAge = new HashMap<>();
        authorsToAge.put("Raoul", 23);
        authorsToAge.put("Mario", 40);
        authorsToAge.put("Alan", 53);

        // Java provides several ways to create unmodifiable collections. In the following code the variable
        // newNumbers is a read-only view of the set numbers:
        Set<Integer> numbers = new HashSet<>();
        Set<Integer> newNumbers = Collections.unmodifiableSet(numbers);
        System.out.println(newNumbers);

        // Tuples
        // And of course you need to instantiate pairs explicitly:
        Pair<String, String> raoul = new Pair<>("Raoul", "+ 44 007007007");
        Pair<String, String> alan = new Pair<>("Alan", "+44 003133700");
    }

    // Tuples
    static class Pair<X, Y> {

        public final X x;

        public final Y y;

        public Pair(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    public String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge) //
            .flatMap(Person::getCar) //
            .flatMap(Car::getInsurance) //
            .map(Insurance::getName) //
            .orElse("Unknown");
    }

}
