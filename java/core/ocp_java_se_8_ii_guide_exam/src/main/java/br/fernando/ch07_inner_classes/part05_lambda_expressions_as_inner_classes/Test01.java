package br.fernando.ch07_inner_classes.part05_lambda_expressions_as_inner_classes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Test01 {

    // =========================================================================================================================================
    //
    static void test01() {
        Bar b = new Bar();

        // Notice one important thing about Foo: it’s an interface with one abstract method.
        // Does that sound familiar (from the previous chapter)? Yes, Foo is a functional interface.

        b.doStuff(new Foo() {

            @Override
            public void foof() {
                System.out.println("foofy");
            }

        }, 10);

        b.doStuff(() -> System.out.println("foofy"), 10); // lambda magic!

        // more explicitly obvious version below
        Foo f = () -> System.out.println("foofy");

        // pass to doStuff
        b.doStuff(f, 10);

        // The trick is to remember that the type of a lambda expression is a functional interface. And
        // when used to simplify inner classes, the important thing to know for the exam is that
        // you can substitute a lambda expression for an anonymous inner class whenever that
        // class is implementing a functional interface.

    }

    // =========================================================================================================================================
    // Comparator Is a Functional Interface
    static void test02() {
        List<String> names = Arrays.asList("John", "Mary", "Sarah");

        Comparator<String> nameSort = new Comparator<String>() {

            @Override
            public int compare(String name1, String name2) {
                return name1.compareTo(name2);
            }
        };

        // This is an example of flavor two of anonymous inner classes: that is, we’re creating
        // an instance of a class that implements the Comparator interface.
        names.sort(nameSort);

        // This code is no different from what you saw in the previous chapter, only now
        // instead of replacing the outer class nameSort,

        names.sort((name1, name2) -> name1.compareTo(name2));

        // we’re replacing the anonymous inner class. It’s exactly the same idea.
        nameSort = (name1, name2) -> name1.compareTo(name2);

        names.sort(nameSort);
    }

    static interface Foo {

        void foof();
    }

    static class Bar {

        void doStuff(Foo f, int x) {
            f.foof();
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
