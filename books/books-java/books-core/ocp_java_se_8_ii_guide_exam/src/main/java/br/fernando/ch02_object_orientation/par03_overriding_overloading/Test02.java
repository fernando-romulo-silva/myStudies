package br.fernando.ch02_object_orientation.par03_overriding_overloading;

public class Test02 {

    // =========================================================================================================================================
    // Invoking a Supertype Version of an Overridden Method
    static void test01() {

    }

    static class Animal {

        void eat() {
            System.out.println("Generic Animal Eating Genercially");
        }

        void printYourself() {
            System.out.println("Animal printYourself");
        }
    }

    static class Horse extends Animal {

        void eat() {
            System.out.println("Horse eating hay, oats, and horse treats");
        }

        // Java 5 introduced the @Override annotation, which you can use to help catch errors
        // with overriding or implementing at compile time. 
        @Override
        void printYourself() {

            System.out.println("Horse printYourself");

            // take advantage of Animal code, then add some more Invoke the superclass (Animal) code
            // Then do Horse-specific print work here
            super.printYourself();
        }
    }

    static class Mustang extends Horse {

        // And you can use super only to access a method in a type’s supertype, not the supertype 
        // of the supertype—that is, you cannot say super.super.doStuff() and you cannot say
        // InterfaceX. super.super.doStuff() 
        void printYourself() {
            super.printYourself();
        }
    }

    // =========================================================================================================================================
    // Invoking a Supertype Version of an Overridden Method
    static void test02() {

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
