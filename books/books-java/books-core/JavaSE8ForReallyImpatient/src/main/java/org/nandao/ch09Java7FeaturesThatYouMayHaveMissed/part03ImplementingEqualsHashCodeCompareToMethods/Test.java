package org.nandao.ch09Java7FeaturesThatYouMayHaveMissed.part03ImplementingEqualsHashCodeCompareToMethods;

import java.util.Objects;

public class Test {

    public class Person implements Comparable<Person> {
        private String first;

        private String last;

        private int age;

        // Null-safe Equality Testing
        @Override
        public boolean equals(final Object otherObject) {

            if (this == otherObject)
                return true;
            if (otherObject == null)
                return false;
            if (getClass() != otherObject.getClass())
                return false;
            final Person other = (Person) otherObject;

            // Unfortunately, that drudgery isn’t simplified yet. But now it gets better. Instead
            // of worrying that first or last might be null, just call

            return Objects.equals(first, other.first) && Objects.equals(last, other.last);
        }

        // Computing Hash Codes
        @Override
        public int hashCode() {
            // The Objects.hashCode method returns a code of 0 for a null argument, so you can implement the body
            // of your hashCode method like this:
            // return 31 * Objects.hashCode(first) + Objects.hashCode(last);
            //
            // But it gets better than that. The varargs method Objects.hash, introduced in Java 7,
            // lets you specify any sequence of values, and their hash codes get combined:
            return Objects.hash(first, last);
        }

        @Override
        public int compareTo(final Person other) {

            // In the past, some people used new Integer(x).compareTo(other.x), but that creates two
            // boxed integers. The static method has int parameters.

            return Integer.compare(age, other.age); // No risk of overflow
        }
    }

    public Test() {
        // TODO Auto-generated constructor stub
    }

}
