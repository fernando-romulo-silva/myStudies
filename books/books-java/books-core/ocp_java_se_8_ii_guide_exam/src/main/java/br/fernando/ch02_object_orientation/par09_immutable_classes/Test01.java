package br.fernando.ch02_object_orientation.par09_immutable_classes;

// Immutable Classes
// Immutable classes are particularly useful in applications that include concurrency and/or
// parallelism.
// In this world of big data and fast data, the need for concurrency and parallelism is on the rise,
// so
// adding immutable classes to the exam is a timely choice.
class Test01 {

    // =========================================================================================================================================
    //
    static void test01() {
        // The code above allows the following:
        //
        // * You can build your own immutable ADSR objects with secret values
        //
        // * You can share your ADSR objects.
        //
        // * Your users can read the name field of your objects, but they can’t mutate your objects

        StringBuilder sb01 = new StringBuilder("a1 ");
        ADSR01 a1v01 = new ADSR01(sb01, 5, 7);

        ADSR01 a2v01 = a1v01.getADSR();

        System.out.println(a1v01.getName());
        sb01.append("alterthe name ");
        System.out.println(a2v01.getName());

        // Oops! Our constructor is using the same StringBuilder object as the one used by
        // the method that invoked the constructor. (Note: If you’re thinking “Don’t use a
        // StringBuilder, use a String” to give yourself extra points. We used a
        // StringBuilder to demonstrate how to deal with instance variables that are of a
        // mutable, nonprimitive type.)

        System.out.println("--------------------------------------------------------------");

        StringBuilder sb02 = new StringBuilder("a1 ");
        ADSR02 a1v02 = new ADSR02(sb02, 5, 7);

        ADSR02 a2v02 = a1v02.getADSR();

        System.out.println(a1v02.getName());
        sb01.append("alterthe name ");
        System.out.println(a2v02.getName());

        // Much better! Here’s a list of things to do to create an immutable class:
        // 1. Mark the class final so that it cannot be extended.
        // 2. Mark its variables private and final.
        // 3. If the constructor takes any mutable objects as arguments, make new copies of those objects in the constructor.
        // 4. Do NOT provide any setter methods!
        // 5. If any of the getter methods return a mutable object reference, make a copy of the actual object, and return a reference to the copy.
    }

    static final class ADSR01 { // final class, can't be extended

        private final StringBuilder name;

        private final int attack;

        private final int decay;

        public ADSR01(StringBuilder name, int attack, int decay) {
            super();
            this.name = name;
            this.attack = attack;
            this.decay = decay;
        }

        // return a new object not the original
        public StringBuilder getName() {

            final StringBuilder nameCopy = new StringBuilder(name);

            return nameCopy;
        }

        public int getAttack() {
            return attack;
        }

        public int getDecay() {
            return decay;
        }

        public ADSR01 getADSR() {
            return this;
        }
    }

    static final class ADSR02 { // final class, can't be extended

        private final StringBuilder name;

        private final int attack;

        private final int decay;

        public ADSR02(StringBuilder name, int attack, int decay) {
            super();
            this.name = new StringBuilder(name); // make a new object for the name!
            this.attack = attack;
            this.decay = decay;
        }

        // return a new object not the original
        public StringBuilder getName() {

            final StringBuilder nameCopy = new StringBuilder(name);

            if (nameCopy != name) {
                System.out.println("different objects");
            }

            return nameCopy;
        }

        public int getAttack() {
            return attack;
        }

        public int getDecay() {
            return decay;
        }

        public ADSR02 getADSR() {
            return this;
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
