package br.fernando.ch02_object_orientation.par02_inheritance_and_polymorphism;

public class Test02 {

    // =========================================================================================================================================
    // IS-A
    // In OO, the concept of IS-A is based on inheritance (or interface implementation).
    static void test01() {

        // A Car is a type of Vehicle, so the inheritance tree might start from the Vehicle class
        //
        // In OO terms, you can say the following:
        //
        // Vehicle is a superclass of Car.
        // Car is a subclass of Vehicle.
        // Car is a superclass of Subaru.
        // Subaru is a subclass of Vehicle.
        // Car inherits from Vehicle.
        // Subaru inherits from both Vehicle and Car.
        // Subaru is derived from Car.
        // Car is derived from Vehicle.
        // Subaru is derived from Vehicle.
        // Subaru is a subtype of both Vehicle and Car.
        //
        // Returning to our IS-A relationship, the following statements are true:
        // "Car extends Vehicle" means "Car IS-A Vehicle."
        // "Subaru extends Car" means "Subaru IS-A Car."
        //
        // And we can also say: “Subaru IS-A Vehicle“
    }

    static class Vehicle {

    }

    static class Car extends Vehicle {
        // cool car code goes here
    }

    static class Subaru extends Car {

        // importan Subaru-specific stuff goes here
        // don't forget subaru inherits accessible Car members which
        // can include both methods and variables
    }

    // =========================================================================================================================================
    // HAS-A
    // HAS-A relationships are based on use, rather than inheritance. In other words, class A
    // HAS-A B if code in class A has a reference to an instance of class B
    static void test02() {
        // “Horse HAS-A Halter.”
        // In other words, Horse has a reference to a Halter . Horse code can use that
        // Halter reference to invoke methods on the Halter and get Halter behavior without
        // having Halter -related code (methods) in the Horse class itself
    }

    static class Animal {
    }

    static class Horse extends Animal {

        private Halter myHalter;

        void tie(LeadRope rope) {
            // Delegate tie behavior to he Halter object
            myHalter.tie(rope);
        }
    }

    static class Halter {
        void tie(LeadRope aRope) {

        }
    }

    static class LeadRope {

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }

}
