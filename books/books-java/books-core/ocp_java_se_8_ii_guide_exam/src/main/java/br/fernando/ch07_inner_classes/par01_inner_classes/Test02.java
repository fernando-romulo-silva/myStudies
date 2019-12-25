package br.fernando.ch07_inner_classes.par01_inner_classes;

import java.io.IOException;

public class Test02 {

    // =========================================================================================================================================
    // Referencing the Inner or Outer Instance from Within the Inner Class
    static void test01() {
        MyClass mc = new MyClass();
        mc.MyMethod();

        // Member Modifiers Applied to Inner Classes
        //
        // A regular inner class is a member of the outer class just as instance variables and methods are, so the following modifiers
        // can be applied to an inner class:
        //
        // final
        // abstract
        // public
        // private
        // protected
        // static — but static turns it into a static nested class, not an inner class
        // strictfp

    }

    static class MyClass {

        // How does an object refer to itself normally?
        //
        // * The keyword "this" can be used only from within instance code. In other words, not within static code.
        //
        // * The this reference is a reference to the currently executing object. In other words, the object whose
        // reference was used to invoke the currently running method.
        //
        // * The this reference is the way an object can pass a reference to itself to some other code as a method argument:
        void MyMethod() {
            MyClass mc = new MyClass();
            mc.doStuff(this);
        }

        void doStuff(MyClass obj) {

        }
    }

    static class MyOuter {

        private int x = 7;

        public void makeInner() {
            MyInner in = new MyInner();
            in.seeOuter();
        }

        class MyInner {

            // But what if the inner class code wants an explicit reference to the outer class
            // instance that the inner instance is tied to? In other words, how do you
            // reference the “outer this“?

            public void seeOuter() {
                System.out.println("Outer x is " + x);

                System.out.println("Inner class ref is " + this); // inner instance

                System.out.println("Outer class ref is " + MyOuter.this); // outer instance
            }
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test01();
    }
}
