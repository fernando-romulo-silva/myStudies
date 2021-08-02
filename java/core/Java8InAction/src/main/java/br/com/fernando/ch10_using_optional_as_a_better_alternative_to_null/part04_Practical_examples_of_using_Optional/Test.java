package br.com.fernando.ch10_using_optional_as_a_better_alternative_to_null.part04_Practical_examples_of_using_Optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.*;

// Practical examples of using Optional
public class Test {

    // Wrapping a potentially null value in an optional
    public static void test01() {

        // An existing Java API almost always returns a null to signal the absence of the required value or
        // that the computation to obtain it failed for some reason.

        final Map<String, Object> map = new HashMap<>();

        // it'll return null
        final Object value = map.get("key");

        // You can improve this by wreapping in an optional the value returned by the map.
        final Optional<Object> optValue = Optional.ofNullable(map.get("key"));
    }

    // Exceptions vs. Optional
    public static void test02() {
        // Throwing an exception is another common alternative in the Java API to returning a null when,
        // for any reason, a value can’t be provided
    }

    public static Optional<Integer> stringToInt(final String s) {
        try {
            return Optional.of(Integer.parseInt(s)); // If the String can be converted into an Integer, return an optional containing it.
        } catch (final NumberFormatException e) {
            return Optional.empty(); // Otherwise return an empty optional.
        }
    }

    // Putting it all together
    public static void test03() {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        System.out.println(readDurationImperative(props, "a")); // 5
        System.out.println(readDurationImperative(props, "b")); // 0
        System.out.println(readDurationImperative(props, "c")); // 0
        System.out.println(readDurationImperative(props, "d")); // 0

        System.out.println(readDurationWithOptional(props, "a")); // 5
        System.out.println(readDurationWithOptional(props, "b")); // 0
        System.out.println(readDurationWithOptional(props, "c")); // 0
        System.out.println(readDurationWithOptional(props, "d")); // 0
    }

    // As you might expect, the resulting implementation is quite convoluted and not very readable,
    // presenting multiple nested conditions coded both as if statements and as a try/catch block.
    public static int readDurationImperative(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) { // Make sure a property exists with the required name
            try {
                int i = Integer.parseInt(value); // Try to convert the String property
                if (i > 0) { // Check if the resulting number is positive
                    return i;
                }
            } catch (NumberFormatException nfe) {
            }
        }
        return 0; // Return 0 if any of the conditions fail.
    }

    public static int readDurationWithOptional(Properties props, String name) {
        return ofNullable(props.getProperty(name)) //
            .flatMap(Test::s2i) //
            .filter(i -> i > 0) //
            .orElse(0);
    }

    public static Optional<Integer> s2i(String s) {
        try {
            return of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return empty();
        }
    }
}
