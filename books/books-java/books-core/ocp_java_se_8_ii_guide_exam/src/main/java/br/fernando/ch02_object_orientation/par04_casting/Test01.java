package br.fernando.ch02_object_orientation.par04_casting;

public class Test01 {

    // =========================================================================================================================================
    // Overloaded Methods
    static void test01() {

        Animal[] a = { new Animal(), new Dog(), new Animal() };

        for (Animal animal : a) {
            animal.makeNoise();

            if (animal instanceof Dog) {
                // animal.playDead(); // try to do a Dog behavior?
                Dog d = (Dog) animal; // casting the ref. var.
                d.playDead();
            }
        }

        // It’s important to know that the compiler is forced to trust us when we do a
        // downcast, even when we screw up:

        Animal animal = new Animal();
        Dog d = (Dog) animal; // complies but fails later, java.lang.ClassCastException

        // However, if the compiler knows with certainty that the cast could not possibly work,
        // compilation will fail. The following replacement code block will NOT compile:
        //
        // String s = (String) animal; // animal can't EVER be a String
        //
        //
        // Unlike downcasting, upcasting (casting up the inheritance tree to a more general type)
        // works implicitly (that is, you don’t have to type in the cast) because when you upcast you’re
        // implicitly restricting the number of methods you can invoke, as opposed to downcasting,
        // which implies that later on you might want to invoke a more specific method.
        // Here’s an example:
        // 
        Dog d03 = new Dog();
        Animal a03 = d; // upcast ok with no explicit casSt

        Animal a04 = (Animal) d; // upcast ok with an explicit cast

        // Both of the previous upcasts will compile and run without exception because a Dog IS-A(n) Animal 

    }

    static class Animal {

        void makeNoise() {
            System.out.println("Generic noise");
        }
    }

    static class Dog extends Animal {

        void makeNoise() {
            System.out.println("bark");
        }

        void playDead() {
            System.out.println("roll over");
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }

}
