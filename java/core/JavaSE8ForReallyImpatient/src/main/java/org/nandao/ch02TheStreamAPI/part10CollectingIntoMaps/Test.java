package org.nandao.ch02TheStreamAPI.part10CollectingIntoMaps;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Suppose you have a Stream<Person> and want to collect the elements into a map so
// that you can later look up people by their ID. The Collectors.toMap method has two
// function arguments that produce the map keys and values.

public class Test {

    public static class Person {

        private final Long id;

        private final String name;

        public Person(final Long id, final String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Person)) {
                return false;
            }
            final Person user = (Person) obj;
            return Objects.equals(id, user.id);
        }
    }

    public static void main(final String[] args) {

        final List<Person> peoples = Arrays.asList(new Person(1L, "Mary"), new Person(2L, "Rose"), new Person(3L, "Paul"));
        final Stream<Person> peopleStream1 = peoples.stream();

        final Map<Long, String> idToName = peopleStream1.collect(Collectors.toMap(Person::getId, Person::getName));

        idToName.forEach((x, y) -> System.out.println(x + " " + y));

        // In the common case that the values should be the actual elements, use Function.identity() for the second function.
        final Stream<Person> peopleStream2 = peoples.stream();
        final Map<Long, Person> idToPerson1 = peopleStream2.collect( //
                Collectors.toMap( //
                        Person::getId, // 
                        Function.identity()) //
        );

        idToPerson1.forEach((x, y) -> System.out.println(x + " " + y));

        // If there is more than one element with the same key, the collector will throw an
        // IllegalStateException. You can override that behavior by supplying a third function
        // argument that determines the value for the key, given the existing and the new value.
        final Stream<Locale> locales1 = Stream.of(Locale.getAvailableLocales());
        final Map<String, String> languageNames = locales1.collect(Collectors.toMap( //
                l -> l.getDisplayLanguage(), //
                l -> l.getDisplayLanguage(l), //
                (existingValue, newValue) -> existingValue) //
        );

        languageNames.forEach((x, y) -> System.out.println(x + " " + y));
        
        // We don’t care that the same language might occur twice—for example, German 
        // in Germany and in Switzerland, and we just keep the first entry.
        final Stream<Locale> locales2 = Stream.of(Locale.getAvailableLocales());
        final Map<String, Set<String>> countryLanguageSets = locales2.collect( //
                Collectors.toMap( //
                        l -> l.getDisplayCountry(), //
                        l -> Collections.singleton(l.getDisplayLanguage()), //
                        (a, b) -> { // Union of a and b
                            final Set<String> r = new HashSet<>(a); //
                            r.addAll(b); //
                            return r;
                        }));

        countryLanguageSets.forEach((x, y) -> System.out.println(x + " " + y));

        // If you want a TreeMap, then you supply the constructor as the fourth argument.
        // You must provide a merge function. Here is one of the examples from the
        // beginning of the section, now yielding a TreeMap:
        final Stream<Person> peopleStream3 = peoples.stream();
        final Map<Long, Person> idToPerson3 = peopleStream3.collect( //
                Collectors.toMap( //
                        Person::getId, //
                        Function.identity(), //
                        (existingValue, newValue) -> {
                            throw new IllegalStateException();
                        }, //
                        TreeMap::new));

        idToPerson3.forEach((x, y) -> System.out.println(x + " " + y));
        
        // For each of the toMap methods, there is an equivalent toConcurrentMap
        // method that yields a concurrent map.A single concurrent map is used in the
        // parallel collection process. When used with a parallel stream, a shared map
        // is more efficient than merging maps, but of course, you give up ordering.
    }

}
