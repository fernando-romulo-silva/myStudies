package org.nandao.ch08MiscellaneousGoodies.part04Collections;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Test {

    static class Person {
        private final Long id;

        private final String name;

        private final Integer age;

        public Person(final Long id, final String name, final Integer age) {
            super();
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;

            final Person other = (Person) obj;

            return Objects.equals(id, other.id);
        }

        @Override
        public String toString() {
            return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
        }
    }

    // Methods Added to Collection Classes
    public static void test1() {
        final LinkedList<String> list1 = new LinkedList<>(Arrays.asList("T", "S", "R", "Q", "R"));

        list1.forEach(x -> System.out.println(x));

        list1.removeIf(x -> x.equals("S"));

        list1.replaceAll(x -> x.equals("R") ? "W" : x);

        System.out.println("");

        list1.forEach(x -> System.out.println(x));

        System.out.println("");

        list1.sort((c1, c2) -> c1.compareTo(c2));

        list1.forEach(x -> System.out.println(x));

        System.out.println("");

        final Iterator<String> iterator = list1.iterator();

        iterator.forEachRemaining( // 
                e -> {
                    if (e.equals("W")) {
                        iterator.remove();
                    } else {
                        System.out.print(e + " ");
                    }
                }//
        );

        System.out.println("");
        System.out.println("");

        list1.forEach(x -> System.out.println(x));

    }

    // Comparators
    // The Comparator interface has a number of useful new methods, taking advantage
    // of the fact that interfaces can now have concrete methods.
    public static void test2() {

        // Here is how you can sort them by name:
        final Person[] people1 = new Person[]{ new Person(1L, "Peter", 25), new Person(2L, "Paul", 20), new Person(3L, "Mary", 35), new Person(4L, "Peter", 45) };
        Arrays.sort(people1, Comparator.comparing(Person::getName));
        System.out.println(people1);

        // You can chain comparators with the thenComparing method for breaking ties 
        // If two people have the same last name, then the second comparator is used.
        final Person[] people2 = new Person[]{ new Person(1L, "Peter", 25), new Person(2L, "Paul", 20), new Person(3L, "Mary", 35), new Person(4L, "Peter", 45) };
        Arrays.sort(people2, Comparator.comparing(Person::getName).thenComparing(Person::getId));
        System.out.println(people2);

        // There are a few variations of these methods. You can specify a comparator to
        // be used for the keys that the comparing and thenComparing methods extract. For
        // example, here we sort people by the length of their names:
        final Person[] people3 = new Person[]{ new Person(1L, "Peter", 25), new Person(2L, "Paul", 20), new Person(3L, "Mary", 35), new Person(4L, "Peter", 45) };
        Arrays.sort(people3, //
                Comparator.comparing(Person::getName, // 
                        (s, t) -> Integer.compare(s.length(), t.length())));
        System.out.println(people3);

        // Moreover, both the comparing and thenComparing methods have variants that avoid
        // boxing of int, long, or double values. An easier way of producing the preceding
        // operation would be
        final Person[] people4 = new Person[]{ new Person(1L, "Peter", 25), new Person(2L, "Paul", 20), new Person(3L, "Mary", 35), new Person(4L, "Peter", 45) };
        Arrays.sort(people4, Comparator.comparingInt(p -> p.getName().length()));
        System.out.println(people4);

        // If your key function can return null, you will like the nullsFirst and nullsLast adapters. 
        // These static methods take an existing comparator and modify it so that
        // it doesn’t throw an exception when encountering null values but ranks them
        // as smaller or larger than regular values. For example, suppose getMiddleName
        // returns a null when a person has no middle name. Then you can use :
        // Comparator.comparing(Person::getName(), Comparator.nullsFirst(...)).
        final Person[] people5 = new Person[]{ new Person(1L, "Peter", 25), new Person(2L, "Paul", 20), new Person(3L, "Mary", 35), new Person(4L, "Peter", 45) };
        Arrays.sort(people5, comparing(Person::getName, nullsFirst(naturalOrder())));
        System.out.println(people5);
    }

    public static void test3() {

        // Java 6 introduced NavigableSet and NavigableMap classes that take advantage of the
        // ordering of the elements or keys, providing efficient methods to locate, for any
        // given value v, the smallest element ≥ or > v, or the largest element ≤ or < v. 
        final NavigableSet<String> original = new TreeSet<>();
        original.add("1");
        original.add("2");
        original.add("3");
        original.forEach(x -> System.out.println(x));
        
        System.out.println();

        //this headset will contain "1" and "2"
        final SortedSet<String> headset = original.headSet("3");
        headset.forEach(x -> System.out.println(x));
        
        // Now the Collections class supports these classes as it does other collections, with
        // methods (unmodifiable|synchronized|checked|empty)Navigable(Set|Map).
        Collections.unmodifiableNavigableSet(original);

    }

    public static void main(final String[] args) {
        test3();
    }

}
