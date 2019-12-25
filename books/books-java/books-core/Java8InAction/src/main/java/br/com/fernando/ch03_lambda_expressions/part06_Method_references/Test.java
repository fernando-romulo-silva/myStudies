package br.com.fernando.ch03_lambda_expressions.part06_Method_references;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import br.com.fernando.Apple;

// Method references
public class Test {

    // In a nutshell
    public static void test1() {
        // your code can gain better readability. How does it work? When you need a method reference,
        // the target reference is placed before the delimiter :: and the name of the method is provided after it.

        final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        inventory.sort(comparing(Apple::getWeight)); // your first method reference!

        // == Lambda ================================================== Method reference equivalent
        //
        // (Apple a) -> a.getWeight() ================================ Apple::getWeight
        //
        // () -> Thread.currentThread().dumpStack() ================== Thread.currentThread()::dumpStack
        //
        // (str, i) -> str.substring(i) ============================== String::substring
        //
        // (String s) -> System.out.println(s) ======================= System.out::println
    }

    // Recipe for constructing method references
    public static void test2() {
        final List<String> str = Arrays.asList("a", "b", "A", "B");

        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));

        // The lambda expression has a signature compatible with the function descriptor of Comparator.
        // Using the recipes described previously, the example can also be written using a method reference as follows:
        str.sort(String::compareToIgnoreCase);
    }

    // Constructor references
    public static void test3() {

        // You can create a reference to an existing constructor using its name and the keyword new as
        // follows: ClassName::new.

        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();

        // which is equivalent to
        Supplier<Apple> c2 = () -> new Apple();
        Apple a2 = c2.get();

        // If you have a constructor with signature Apple(Integer weight), it fits the signature of the
        // Function interface, so you can do this,

        Function<Integer, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(100);

        // which is equivalent to
        Function<Integer, Apple> c4 = (weight) -> new Apple(weight);
        Apple a4 = c4.apply(100);

        // In the following code, each element of a List of Integers is passed to the constructor of Apple
        // using a similar map method we defined earlier, resulting in a List of apples with different
        // weights:
        final List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        final List<Apple> apples = map(weights, Apple::new);

        // If you have a two-argument constructor, Apple(String color, Integer weight), it fits the signature
        // of the BiFunction interface, so you can do this,

        BiFunction<Integer, String, Apple> c5 = Apple::new;
        Apple a5 = c5.apply(100, "white");
    }

    public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for (Integer e : list) {
            result.add(f.apply(e));
        }

        return result;
    }

}
