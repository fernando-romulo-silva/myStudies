package br.fernando.ch02_object_orientation.par11_statics;

public class Test02 {

    // =========================================================================================================================================
    // Accessing Static Methods and Variables
    static void test01() {
        // Finally, remember that static methods can’t be overridden! This doesn’t mean they
        // can’t be redefined in a subclass, but redefining and overriding aren’t the same thing

        Animal[] a = { new Animal(), new Dog(), new Animal() };

        for (int x = 0; x < a.length; x++) {
            //The compiler is going to substitute something like Animal.doStuff() instead.
            a[x].doStuff(); // invoke the satic method
        }

        Dog.doStuff(); // invoke using the class name
    }

    static class Animal {

        static void doStuff() {
            System.out.println("a ");
        }
    }

    static class Dog extends Animal {

        static void doStuff() { // it's a redefinition, not an override
            System.out.println("d ");
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
