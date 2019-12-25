package br.fernando.ch02_object_orientation.par06_legal_return_types;

class Test01 {

    // =========================================================================================================================================
    // Return Types on Overloaded Methods
    static void test01() {

        // Notice that the Bar version of the method uses a different return type. That’s
        // perfectly fine. As long as you’ve changed the argument list, you’re overloading the
        // method, so the return type doesn’t have to match that of the supertype version.
        // What you’re NOT allowed to do is this:
    }

    static class Foo {

        void go() {
            System.out.println("Go Foo!");
        }
    }

    static class Bar extends Foo {

        // The following code shows an overloaded method
        String go(int x) {
            return null;
        }

        // String go() { return null; } // Not legal! Can't change only the return type
    }

    // =========================================================================================================================================
    // Overriding and Return Types and Covariant Returns
    static void test02() {
        // When a subtype wants to change the method implementation of an inherited method
        // (an override), the subtype must define a method that matches the inherited version
        // exactly. Or, since Java 5, you’re allowed to change the return type in the overriding
        // method as long as the new return type is a subtype of the declared return type of the
        // overridden (superclass) method.

    }

    static class Alpha {

        Alpha doStuff(char c) {
            return new Alpha();
        }
    }

    static class Beta extends Alpha {

        Beta doStuff(char c) { // legal override since Java 1.5
            return new Beta();
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
