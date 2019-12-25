package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par02_functional_interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Test04 {

    // =========================================================================================================================================
    // andThen
    static void test01_01() {
        // andThen() is actually a default method of the Consumer interface that you can use to chain consumers together.

        Dog boi = new Dog("boi", 30, 6);
        Dog clover = new Dog("clover", 35, 12);
        Dog zooey = new Dog("zooey", 45, 8);

        List<Dog> dogs = Arrays.asList(boi, clover, zooey);

        Consumer<Dog> displayName01 = d -> System.out.println(d + " ");

        dogs.forEach(displayName01);

        // Now let’s say we want to display the dog’s name andThen we want to have the dog bark. Can we do it with consumers? Yes! Here’s how:

        dogs.forEach(displayName01.andThen(d -> d.bark())); // Note that we’ve written the second Consumer as an inline lambda

        // boi Woof!
        // clover Woof!
        // Zooey Woof!

        // We’re passing a “composed Consumer ” to the forEach() method of the dogs ArrayLis.
        // When the andThen() method of the Consumer is called, it says, okay, now use that
        // same dog object, d , that we just used in the first Consumer for the accept() method of
        // the second Consumer

        // You might think that you could write both lambdas inline, like this:
        // dogs.forEach((d -> System.out.println(d+"")).andThen(d -> d.bark())); // Compile error!

        // But you can’t. You’ll get a compile error. You can, however, use named consumers for both:

        Consumer<Dog> displayName02 = d -> System.out.println(d);
        Consumer<Dog> doBark02 = d -> d.bark();

        dogs.forEach(displayName02.andThen(doBark02));

        // Most (but not all!) of the consumers in java.util.function have an andThen()
        // method, and notice that the type of the consumer you pass to the andThen() method
        // must match the type of the consumer used as the first operation.

        Consumer<Dog> displayName02AndDoBark02 = displayName02.andThen(doBark02);
        dogs.forEach(displayName02AndDoBark02);
    }

    static class Dog {

        String name;

        int age;

        int weight;

        public Dog(String name, int age, int weight) {
            super();
            this.name = name;
            this.age = age;
            this.weight = weight;
        }

        public void bark() {
            System.out.println("Woof!");
        }

        @Override
        public String toString() {
            return this.name;
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
