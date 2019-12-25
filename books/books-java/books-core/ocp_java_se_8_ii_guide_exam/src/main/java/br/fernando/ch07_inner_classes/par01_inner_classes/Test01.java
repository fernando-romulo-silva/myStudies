package br.fernando.ch07_inner_classes.par01_inner_classes;

import java.io.IOException;

public class Test01 {

    // =========================================================================================================================================
    // Inner Classes
    static void test01() {
        // Sometimes, though, you find yourself designing a class where you discover you need behavior that not only belongs
        // in a separate specialized class, but also needs to be intimately tied to the class you’re designing.
        //
        // The inner class is a part of the outer class. Not just a “part,” but a full-fledged,
        // card-carrying member of the outer class. Yes, an inner class instance has access to all members of the outer class, even those marked private.
    }

    // =========================================================================================================================================
    // Coding a “Regular” Inner Class
    static void test02() {

        // Creating an Inner Class Object from Outside the Outer Class Instance Code
        // If we want to create an instance of the inner class, we must have an instance of the outer class.

        // gotta get an instance!
        MyOuter mo = new MyOuter();

        MyOuter.MyInner inner01 = mo.new MyInner();
        inner01.seeOuter();

        // or
        MyOuter.MyInner inner02 = new MyOuter().new MyInner();
        inner02.seeOuter();

    }

    static class MyOuter {

        private int x = 7;

        public void makeInner() {
            // Instantiating an Inner Class
            // To create an instance of an inner class, you must have an instance of the outer class to tie to the inner class.
            MyInner in = new MyInner();
            in.seeOuter();
        }

        // The inner class is still, in the end, a separate class, so a separate class file is
        // generated for it. But the inner class file isn’t accessible to you in the usual way
        class MyInner {

            // Regular inner class cannot have static declarations of any kind.
            // The only way you can access the inner class is through a live instance of the outer class!
            // static int w; // compiler error!

            public void seeOuter() {

                // Notice that the inner class is, indeed, accessing a private member of the outer class.
                // That’s fine, because the inner class is also a member of the outer class.
                System.out.println("Outer x is " + x);
            }

        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException {
        test01();
    }
}
