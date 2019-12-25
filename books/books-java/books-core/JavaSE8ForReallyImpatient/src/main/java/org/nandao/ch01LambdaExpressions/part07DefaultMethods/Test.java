package org.nandao.ch01LambdaExpressions.part07DefaultMethods;

import java.util.ArrayList;
import java.util.List;

// Many programming languages integrate function expressions with their collections library.
// This often leads to code that is shorter and easier to understand than the loop equivalent.
public class Test {

    public static void main(String[] args) {

        final List<String> list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // There is a better way. The library designers can supply a forEach method that
        // applies a function to each element. Then you can simply call

        list.forEach(System.out::println);

        // If the Collection interface gets new methods, such as forEach, then
        // every program that defines its own class implementing Collection will break until
        // it, too, implements that method. That is simply unacceptable in Java.
        // The Java designers decided to solve this problem once and for all by allowing
        // interface methods with concrete implementations (called default methods)
    }

    // The interface has two methods: getId, which is an abstract method, and the default
    // method getName. A concrete class that implements the Person interface must, of
    // course, provide an implementation of getId, but it can choose to keep the
    // implementation of getName or to override it.
    static interface Person {

        long getId();

        default String getName() {
            return "John Q. Public";
        }
    }

    // What happens if the exact same method is defined as a default method in one
    // interface and then again as a method of a superclass or another interface?

    static interface Named {
        default String getName() {
            return getClass().getName() + "_" + hashCode();
        }
    }

    // 1. Superclasses win. If a superclass provides a concrete method, default methods
    // with the same name and parameter types are simply ignored.
    //
    // 2. Interfaces clash. If a superinterface provides a default method, and another
    // interface supplies a method with the same name and parameter types (default
    // or not), then you must resolve the conflict by overriding that method.

    static class Student implements Person, Named {

        @Override
        public long getId() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public String getName() {
            return Person.super.getName();
        }
    }
}
