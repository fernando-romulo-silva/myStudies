package br.fernando.ch02_object_orientation.par07_constructors_and_instantiation;

public class Test01 {

    // =========================================================================================================================================
    // Constructor Basics
    static void test01() {
        // Two key points to remember about constructors are that they have no return type and their names must exactly match the class name.

        // In the preceding code example, the Foo class does not have a no-arg constructor. That means the following will fail to compile
        // Foo f = new Foo(); // Won't compile, no matching constructor
        //
        // but the following wil compile:
        Foo f = new Foo("Fred", 43); // No problem. Arguments match the Foo constructor.

    }

    static class Foo {

        int size;

        String name;

        // Foo() { } // The constructor for the Foo class

        Foo(String name, int size) {
            this.name = name;
            this.size = size;
        }
    }

    // =========================================================================================================================================
    // Constructor Chaining
    static void test02() {

        // But what really happens when you say new Horse() ?
        Horse h = new Horse();

        // 1. The Horse constructor is invoked. Every constructor invokes the constructor of its superclass with an (implicit) call to super(),
        // unless the constructor invokes an overloaded constructor of the same class.
        //
        // 2. The Animal constructor is invoked ( Animal is the superclass of Horse ).
        //
        // 3. The Object constructor is invoked.
        //
        // 4. By explicit values, we mean values that are assigned at the time the variables are declared, such as int x = 27 ,
        // where 27 is the explicit value (as opposed to the default value) of the instance variable.
        //
        // 5. The Object constructor completes.
        //
        // 6. The Animal instance variables are given their explicit values (if any).
        //
        // 7. The Animal constructor completes.
        //
        // 8. The Horse instance variables are given their explicit values (if any).
        //
        // 9. The Horse constructor completes.
    }

    static class Animal {

    }

    static class Horse extends Animal {

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
