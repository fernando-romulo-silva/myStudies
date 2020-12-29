package br.fernando.ch02_object_orientation.par05_implementing_an_interface;

public class Test01 {

    // =========================================================================================================================================
    // Implementing an Interface
    static void test01() {
        // When you implement an interface, you’re agreeing to adhere to the contract defined in the interface.
        // The interface contract guarantees that a class will have the method (in other words, others
        // can call the method subject to access control), but it never guaranteed a good implementation
        //
        // To be a legal implementation class, a nonabstract implementation class must do the following:
        //
        // * Provide concrete (nonabstract) implementations for all abstract methods from the declared interface.
        //
        // Follow all the rules for legal overrides, such as the following:
        //
        // * Declare no checked exceptions on implementation methods other than those
        // declared by the interface method, or subclasses of those declared by the
        // interface method.
        //
        // * Maintain the signature of the interface method, and maintain the same return
        // type (or a subtype). (But it does not have to declare the exceptions declared
        // in the interface method declaration.)
        //
        // A class can implement more than one interface.
        //
        // You can extend only one class, but you can implement many interfaces (which,
        // as of Java 8, means a form of multiple inheritance, which we’ll discuss shortly).
        /// In other words, subclassing defines who and what you are
        //
        // An interface can itself extend another interface.
        //
        // Hold on, though, because here’s where it gets strange. An interface can extend more than one interface! Only interfaces okay?
        //
        //
    }

    static abstract class Ball implements Bounceable { // keyword 'implements'

        @Override
        public void bounce() {
            // interesting Beach bounce code
        }

        @Override
        public void setBounceFactor(int f) {
            // Clever BeachBall-specific code for setting a bounce factor
        }

        // Don't implement the rest; leave it for a subclass
    }

    static class BeachBall extends Ball {

        @Override
        public void moveIt() {
        }

        @Override
        public void doSphericalThing() {
        }
    }

    static interface Bounceable extends Moveable, Spherical {

        void bounce();

        void setBounceFactor(int f);
    }

    static interface Moveable {

        void moveIt();
    }

    static interface Spherical {

        void doSphericalThing();
    }

    // =========================================================================================================================================
    public static void main(String[] args) {

    }
}
