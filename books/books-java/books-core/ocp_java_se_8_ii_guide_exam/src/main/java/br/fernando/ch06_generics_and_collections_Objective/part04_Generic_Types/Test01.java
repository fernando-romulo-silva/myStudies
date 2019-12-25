package br.fernando.ch06_generics_and_collections_Objective.part04_Generic_Types;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Test01 {

    // =========================================================================================================================================
    // Generics and Legacy Code
    // The easiest thing about generics you’ll need to know for the exam is how to update nongeneric code to make it generic.
    static void test01() {

        // A nongeneric collection can hold any kind of object! A nongeneric collection is
        // quite happy to hold anything that is NOT a primitive.

        // it
        final List myList = new ArrayList();

        myList.add("Fred"); // OK, it will hold Strings

        myList.add(new BigDecimal(1));

        myList.add(new Character('c'));

        // That meant getting a String back out of our only- String s-intended list required a cast:
        final String s1 = (String) myList.get(2);

        // becomes
        final List<Integer> myList2 = new ArrayList<Integer>(); // or hte J7 Diamond! <>
    }

    // =========================================================================================================================================
    // Mixing Generic and Nongeneric Collections
    public static void test02() {

        final List<Integer> myList = new ArrayList<>(); // type safe collection
        myList.add(4);
        myList.add(6);

        final Adder adder = new Adder();

        int total = adder.addAll(myList); // pass it to an utyped argument

        // Yes, this works just fine. You can mix correct generic code with older nongeneric code, and everyone is happy.

        System.out.println(total);

        // In the previous example, the addAll() legacy method assumed (trusted? hoped?) that the list passed in was, 
        // indeed, restricted to Integers , even though when the code was written, there was no guarantee

        final Inserter inserter = new Inserter();

        // Remember, the older legacy code was allowed to put anything at all (except
        // primitives) into a collection. And in order to support legacy code, Java 5 and later
        // allowed your newer type-safe code to make use of older code
        inserter.insert(myList);

        System.out.println(myList);

        // That compiles (even with warnings) will run! No type violations will be caught at runtime by the JVM, until those type
        // violations mess with your code in some other way. In other words, the act of adding a String to an <Integer> list won’t fail at runtime 
        // until you try to treat that String-you-think-is-an-Integer as an Integer
        //
        // Keep in mind, then, that the problem of putting the wrong thing into a typed (generic) collection does not show up at the time 
        // you actually do the add() to the collection. It only shows up later, when you try to use something in the list and it
        // doesn’t match what you were expecting.
    }

    static class Adder {

        int addAll(List list) {
            // method with a non-generic List argument, but assumes (with no guarantee) that will be Integers

            Iterator it = list.iterator();

            int total = 0;

            while (it.hasNext()) {
                int i = ((Integer) it.next()).intValue();
                total += i;
            }

            return total;
        }
    }

    static class Inserter {
        // method with a non-generic List argument
        void insert(List list) {
            // put a Stringin the list passed in
            list.add(new String("My Test"));
        }
    }

    // =========================================================================================================================================
    // Polymorphism and Generics
    static class Parent {
    }

    static class Child extends Parent {
    }

    // 547
    public static void test03() {

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test02();
    }

}
