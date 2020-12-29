package br.fernando.ch02_object_orientation.par06_legal_return_types;

import java.awt.Button;

// Returning a Value
public class Test02 {

    // =========================================================================================================================================
    // You have to remember only six rules for returning a value:
    //
    static void test01() {
    }

    // 1. You can return null in a method with an object reference return type
    static Button doStuff() {
        return null;
    }

    // 2. An array is a perfectly legal return type.
    static String[] go() {
        return new String[]{ "Fred", "Barney", "Wilma" };
    }

    // 3. In a method with a primitive return type, you can return any value or variable
    // that can be implicitly converted to the declared return type.
    static int foo() {
        char c = 'c';
        return c; // char is compatible with int;
    }

    // 4. In a method with a primitive return type, you can return any value or variable
    // that can be explicitly cast to the declared return type.
    static int beta() {
        float f = 32.5f;
        return (int) f;
    }

    // 5. You must not return naything from a method with a void return type.
    // static void bar() { return "this is it"; } // not legal!!
    // (Although you can say return;)

    // 6. In a method with an object reference return type, you can return any object
    // type that can be implicitly cast to the declared return type.
    static Animal getAnimal() {
        return new Horse(); // assume Horse extends Animal
    }

    static Object getObject() {
        int[] nums = { 1, 2, 3 };
        return nums; // Return an int array, which is still an object
    }

    // Method with an interface return type
    static Chewable getChewable() {
        return new Gum(); // Return interface implmenter
    }

    // ------------------

    static class Animal {
    }

    static class Horse extends Animal {
    }

    // -----------------

    static interface Chewable {
    }

    static class Gum implements Chewable {
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }

}
