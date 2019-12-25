package br.fernando.ch06_generics_and_collections_Objective.part01_override_hashCode_hashEquals_and_toString;

// Override hashCode(), equals(), and toString() (OCP Objective 1.4)
public class Test01 {

    // =========================================================================================================================================
    // Methods of Class Object Covered on the Exam
    //
    // boolean equals(Object object) : Decides whether two objects are meaningfully equivalent
    //
    // void finalize() : Called by the garbage collector when the garbage collector sees that the object cannot be referenced
    // (rarely used, and deprecated in Java 9)
    //
    // int hasCode() : Resturns a hascode int value for an object so that the object can be used in Collection classes that use hasing,
    // including Hashtable, HashMap, and HashSet
    //
    // final void notify() : Wakes up a thread that is waiting for this object's lock
    //
    // final void notifyAll() : Wakes up all threads that are waiting for this object's lock
    //
    // final void wait() : Causes the current thread to wait until another thread calls notify() or notifyAll() on this object
    //
    // String toString() : Returns a "text resentation" of the object
    //
    // =========================================================================================================================================
    //
    // The toString() Method
    //
    static class HardToRead {

    }

    static class Bob {

        int shoeSize;

        String nickName;

        public Bob(String nickName, int shoeSize) {
            this.nickName = nickName;
            this.shoeSize = shoeSize;
        }

        @Override
        public String toString() {
            return ("I am a Bob, but you can call me " + nickName + ". My shoe size is " + shoeSize);
        }
    }

    public static void test01() {
        // Code can call toString() on your object when it wants to read useful details about your object

        final HardToRead h = new HardToRead();

        System.out.println(h); // br.fernando.ch06_generics_and_collections.Test01$HardToRead@15db9742
        //
        // The preceding output is what you get when you don’t override the toString() method
        // of class Object . It gives you the class name (at least that’s meaningful) followed by
        // the @ symbol, followed by the unsigned hexadecimal representation of the object’s hashcode

        final Bob b = new Bob("GoBobGo", 19);
        // This ought to be a bit more readable:
        System.out.println(b); // I am a Bob, but you can call me GoBobGo. My shoe size is 19

    }

    // =========================================================================================================================================
    // Overriding equals()
    //
    // Comparing two object references using the == operator evaluates to true only when both references refer to the same object because ==
    // simply looks at the bits in the variable, and they’re either identical or they’re not.
    //
    // You saw that the String class has overridden the equals() method (inherited from the class Object ), so you could
    // compare two different String objects to see if their contents are meaningfully equivalent.
    //
    // When you really need to know if two references are identical, use == . But when you need to know if the objects themselves
    // (not the references) are equal, use the equals() method
    //
    // Implementing an equals() Method
    static class Moof {

        private int moofValue;

        public Moof(int val) {
            this.moofValue = val;
        }

        public int getMoofValue() {
            return moofValue;
        }

        // First of all, you must observe all the rules of overriding, and in line 1 we are,
        // indeed, declaring a valid override of the equals() method we inherited from Object
        @Override
        public boolean equals(Object obj) {

            // First, be sure that the object being tested is of the correct type! It comes in
            // polymorphically as type Object , so you need to do an instanceof test on it.
            if ((obj instanceof Moof) && //
            // compare the values
                    (((Moof) obj).getMoofValue() == this.getMoofValue())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void test02() {
        final Moof one = new Moof(8);
        final Moof two = new Moof(8);

        if (one.equals(two)) {
            System.out.println("on and two are equal");
        }
    }
    // The equals() Contract
    // Pulled straight from the Java docs, the equals() contract says
    //
    // * It is reflexive. For any reference value x , x.equals(x) should return true
    //
    // * It is symmetric. For any reference values x and y , x.equals(y) should return true if and only if y.equals(x) returns true .
    //
    // * It is transitive. For any reference values x , y , and z , if x.equals(y) returns true and y.equals(z) returns true ,
    // then x.equals(z) must return true
    //
    // * It is consistent. For any reference values x and y , multiple invocations of x.equals(y) consistently return true or consistently return false ,
    // provided no information used in equals() comparisons on the object is modified.
    //
    // * For any non- null reference value x , x.equals(null) should return false

    //465
    // =========================================================================================================================================

    public static void main(String[] args) {
        test01();
    }

}
