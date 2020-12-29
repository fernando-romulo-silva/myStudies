package br.fernando.ch07_inner_classes.par03_anonymous_inner_classes;

public class Test01 {

    // =========================================================================================================================================
    // Plain-Old Anonymous Inner Classes, Flavor One
    static void test01() {

        // Normal reference
        Popcorn p01 = new Popcorn(); // notice the semcolon at the end;

        // The Popcorn reference variable refers, not to an instance of Popcorn,
        // but to an instance of an anonymous (unnamed) subclass of Popcorn.
        Popcorn p02 = new Popcorn() { // a curly brace, not a semicolon

            // statement within the overriding pop() method. Nothing special there.
            void pop() {
                System.out.println("anonymous popcorn!");
            }

            void sizzle() {
                System.out.println("anonymous sizzling popcorn");
            }
        }; // curly brace closing off the anonymous class definition (it’s the companion brace to the one on line 2)
           // Don't forget the semicolon

        p02.pop(); // Ok, Popcorn has a pop() method
        // p02.sizzle(); // Not legal! Popcorn does not have sizzle()
    }

    static class Popcorn {

        void pop() {
            System.out.println("Popcorn");
        }
    }

    // =========================================================================================================================================
    // Plain-Old Anonymous Inner Classes, Flavor Two
    static void test02() {

        // The only difference between flavor one and flavor two is that flavor one creates an
        // anonymous subclass of the specified class type, whereas flavor two creates an
        // anonymous implementer of the specified interface type

        Cookable c01 = new Cookable() {

            @Override
            public void cook() {
                System.out.println("anonymous cookable implementer");
            }
        };

        c01.cook();

    }

    static interface Cookable {

        void cook();
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
