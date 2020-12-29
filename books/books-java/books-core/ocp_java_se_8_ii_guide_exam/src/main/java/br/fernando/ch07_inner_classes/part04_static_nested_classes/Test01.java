package br.fernando.ch07_inner_classes.part04_static_nested_classes;

public class Test01 {

    // =========================================================================================================================================
    // Static Nested Classes
    static void test01() {
        // Whereas an inner class (regardless of the flavor) enjoys that special relationship with the outer class (or
        // rather, the instances of the two classes share a relationship), a static nested class does not.
        // It is simply a non-inner (also called “top-level“) class scoped within another.
    }

    // =========================================================================================================================================
    // Instantiating and Using Static Nested Classes
    static void test02() {
        BigOuter.Nest n01 = new BigOuter.Nest(); // both class names

        n01.go();
    }

    static class BigOuter {

        private int x = 10;

        static class Nest {

            void go() {
                System.out.println("Hi");

                // System.out.println("Value of x is " + x); // don't compile
            }
        }

        void execute() {
            Nest n02 = new Nest(); // access the enclosed class
            n02.go();
        }

        // Just as a static method does not have access to the instance variables and
        // nonstatic methods of the class, a static nested class does not have access to the
        // instance variables and nonstatic methods of the outer class.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
