package br.fernando.ch02_object_orientation.par07_constructors_and_instantiation;

public class Test03 {

    // =========================================================================================================================================
    // Overloaded Constructors
    static void test01() {

        // Overloading a constructor means typing in multiple versions of the constructor, each having a different argument list
        //
        // The preceding Foo class has two overloaded constructors: one that takes a string and one with no arguments.
        // Because there’s no code in the no-arg version, it’s actually identical to the default constructor the compiler
        // supplies—but remember, since there’s already a constructor in this class (the one that takes a string), the compiler won’t
        // supply a default constructor.

        Animal a = new Animal();
        System.out.println(a.name);

        Animal b = new Animal("Zeus");
        System.out.println(b.name);

        // Key Rule: The first line in a constructor must be a call to super()or a call to this().
        //
        // The preceding rule means a constructor can never have both a call to super() and a call to this().
    }

    static class Foo {

        Foo() {
        }

        Foo(String s) {
        }
    }

    static class Animal {
        String name;

        Animal(String name) {
            this.name = name;
        }

        Animal() {
            // we’re calling this(), and this() always means a call to another constructor in the same class.
            this(makeRandomName());
        }

        // That’s because you cannot invoke an instance (in other words, nonstatic) 
        // method (or access an instance variable) until after the super constructor has run.
        static String makeRandomName() {
            int x = (int) (Math.random() * 5);

            String name = new String[]{ "Fluffy", "Fido", "Rover", "Spike", "Gigi" }[x];

            return name;
        }

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
