package org.nandao.ch01LambdaExpressions.part04MethodReferences;

import java.util.Arrays;

import javafx.scene.control.Button;

// Sometimes, there is already a method that carries out exactly the action that you’d
// like to pass on to some other code.
public class Test {

    public static void main(String[] args) {

        final Button button = new Button();

        button.setOnAction(event -> System.out.println(event));

        // It would be nicer if you could just pass the println method to the setOnAction
        // method. Here is how you do that:

        // The expression System.out::println is a method reference that is equivalent to the
        // lambda expression x -> System.out.println(x).
        button.setOnAction(System.out::println);

        // As another example, suppose you want to sort strings regardless of letter case.
        // You can pass this method expression:
        final String[] strings = { "one", "two" };
        Arrays.sort(strings, String::compareToIgnoreCase);

        // As you can see from these examples, the :: operator separates the method name
        // from the name of an object or class. There are three principal cases:
        // • object::instanceMethod
        // • Class::staticMethod
        // • Class::instanceMethod

        // You can capture the this parameter in a method reference. For example,
        // this::equals is the same as x -> this.equals(x). It is also valid to use super. The method
        // expression
    }

    class Greeter {
        public void greet() {
            System.out.println("Hello, world!");
        }
    }

    class ConcurrentGreeter extends Greeter {
        // When the thread starts, its Runnable is invoked, and super::greet is executed, calling
        // the greet method of the superclass.
        @Override
        public void greet() {
            final Thread t = new Thread(super::greet);
            t.start();
        }
    }

}
