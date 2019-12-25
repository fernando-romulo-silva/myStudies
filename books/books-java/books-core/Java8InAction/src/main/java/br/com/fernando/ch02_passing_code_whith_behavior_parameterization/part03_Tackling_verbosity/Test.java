package br.com.fernando.ch02_passing_code_whith_behavior_parameterization.part03_Tackling_verbosity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import br.com.fernando.Apple;

public class Test {

    // Anonymous classes
    public static void test1() {
        final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        final ApplePredicate appleColorPredicate = new ApplePredicate() {

            @Override
            public boolean test(Apple apple) {
                return "green".equals(apple.getColor());
            }
        };

        final List<Apple> greenApples = filterApple(inventory, appleColorPredicate);
        System.out.println(greenApples);

        // [Apple{color='red', weight=120}]
        final ApplePredicate appleRedAndHeavyPredicate = new ApplePredicate() {

            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor()) && apple.getWeight() > 150;
            }
        };

        // But anonymous classes are still not good enough. First, they tend to be very bulky because they
        // take a lot of space, as shown in the highlighted code here using the same two examples used
        // previously:

        final List<Apple> redApples = filterApple(inventory, appleRedAndHeavyPredicate);
        System.out.println(redApples);
    }

    // Sixth attempt: using a lambda expression
    public static void test2() {
        // You have to admit this code looks a lot cleaner than our previous attempts! It’s great because it’s
        // starting to look a lot closer to the problem statement. We’ve now tackled the verbosity issue.

        final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
        final List<Apple> result = filterApple(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println(result);

        // You can now use the method filter with a List of bananas, oranges, Integers, or Strings! Here’s
        // an example, using lambda expressions:
        final List<Apple> redApples = filterFruit(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println(redApples);
    }

    public static void main(String[] args) {
        test1();
    }

    public interface ApplePredicate {

        boolean test(Apple apple);
    }

    public static List<Apple> filterApple(List<Apple> inventory, ApplePredicate p) {
        final List<Apple> result = new ArrayList<>();
        for (final Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // abstracting over List type
    public static <T> List<T> filterFruit(List<T> inventory, Predicate<T> p) {
        final List<T> result = new ArrayList<>();
        for (final T fruit : inventory) {
            if (p.test(fruit)) {
                result.add(fruit);
            }
        }
        return result;
    }

}
