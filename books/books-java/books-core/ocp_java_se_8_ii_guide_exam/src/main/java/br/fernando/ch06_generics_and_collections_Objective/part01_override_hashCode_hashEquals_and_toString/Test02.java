package br.fernando.ch06_generics_and_collections_Objective.part01_override_hashCode_hashEquals_and_toString;

public class Test02 {

    // =========================================================================================================================================
    // Overriding hashCode()
    //
    // Hashcodes are typically used to increase the performance of large collections of data.
    // When you put an object in a collection that uses hashcodes, the collection uses the hashcode of the object to decide in which bucket/slot the object
    // should land. Then when you want to fetch that object (or, for a hashtable, retrieve the associated value for that object), you have to give the collection
    // a reference to an object, which it then compares to the objects it holds in the collection.
    //
    // If two objects are equal, their hashcodes must be equal as well.

    static class Moof {

        private int x;

        public Moof(int xVal) {
            this.x = xVal;
        }

        @Override
        public int hashCode() {
            return (x * 17);
        }

        public boolean equals(Object obj) {
            final Moof m = (Moof) obj; // Don't try at home without instanceof test

            if (m.x == this.x) {
                return true;
            } else {
                return false;
            }
        }

    }

    // The hashCode() Contract
    // Now coming to you straight from the fabulous Java API documentation for class Object , may we present (drumroll) the hashCode() contract:
    //
    // * The hashCode() method must consistently return the same integer, provided that no information used in equals() comparisons on the
    // object is modified. This integer need not remain consistent from one execution of an application to another execution of the same application
    //
    // * If two objects are equal according to the equals(Object) method, then calling the hashCode() method on each of the two objects must produce
    // the same integer result.
    //
    // * It is NOT required that if two objects are unequal according to the equals(java.lang.Object) method, then calling the hashCode() method on
    // each of the two objects must produce distinct integer results. However, the programmer should be aware that producing distinct integer results for unequal
    // objects may improve the performance of hashtables.
    //
    // Transient variables are not saved when an object is serialized, avoid use transient variables on hash
    public static void test01() {
        final Moof m = new Moof(10);
        System.out.println(m.hashCode());
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
