package br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Using Collections (OCP Objectives 2.6, 3.2, and 3.3)
//
// * Create and use Lambda expressions.
// * Create and use ArrayList, TreeSet, TreeMap, and ArrayDeque objects.
// * Use java.util.Comparator and java.lang.Comparable interfaces.
// * Sort and search arrays and lists.
@SuppressWarnings({ "unchecked", "unused" })
public class Test01 {

    // =========================================================================================================================================
    // ArrayList Basics
    // Some of the advantages ArrayList has over arrays are
    // * It can grow dynamically
    // * It provides more powerful insertion and search mechanisms than arrays.
    public static void test01_01() {
        final List<String> test = new ArrayList<>();

        String s = "hi";

        test.add("string"); // add strings
        test.add(s);
        test.add(s + s);

        System.out.println(test.size()); // use ArrayList methods
        System.out.println(test.contains(42));
        System.out.println(test.contains("hihi"));

        test.remove("hi");

        System.out.println(test.size());
    }

    // =========================================================================================================================================
    // Autoboxing with Collections
    //
    // In general, collections can hold objects but not primitives. Prior to Java 5, a common
    // use for the so-called wrapper classes

    public static void test01_02() {
        List myInts = new ArrayList(); // pre Java 5 declaration

        myInts.add(new Integer(42)); // use Integer to wrap an int

        myInts.add(42); // autoboxing handles it!
        // In this last example, we are still adding an Integer object to myInts (not an int primitive);

        // wrapping and unwrapping
        //
        Integer y = new Integer(567); // make it

        y++; // unwrap it, increment it, rewrap it

        System.out.println("y = " + y);

        y = 568;

        // Earlier, we mentioned that wrapper objects are immutable...this example appears to contradict that statement.
        // It sure looks like y ’s value changed from 567 to 568 . What actually happened, however, is that a second wrapper object was created and its value was set
        // to 568 . If only we could access that first wrapper object, we could prove it

        Integer a = 567;
        Integer b = a;

        System.out.println(a == b); // very that they refer to the sameobject, print true

        a++; // unwrap, use, "rewrap"

        System.out.println(a + " " + b); // print values

        System.out.println(a == b); // print false!
    }

    // =========================================================================================================================================
    // Boxing, ==, and equals()
    public static void test01_03() {
        Integer i1 = 1000;
        Integer i2 = 1000;

        if (i1 != i2) {
            System.out.println("different objects");
        }

        if (i1.equals(i2)) {
            System.out.println("meaningfully equal");
        }

        Integer i3 = 10;

        Integer i4 = 10;

        if (i3 == i4) {
            System.out.println("same object");
        }

        if (i3.equals(i4)) {
            System.out.println("meaningfully equal");
        }

        // Yikes! The equals() method seems to be working, but what happened with '==' and '!=' ?
        // Why is '!=' telling us that i1 and i2 are different objects, when '==' is saying that i3
        // and i4 are the same object?
        //
        // In order to save memory, two instances of the following wrapper objects (created through boxing)
        // will always be == when their primitive values are the same:
        //
        // * Boolean
        // * Byte
        // * Character from \u0000\ to \y007f (127 decimal)
        // * Short and Integer from -128 to 127

        // Where Boxing Can Be Used
        // Any time you want your collection to hold objects and primitives, you’ll want to use
        // wrappers to make those primitives collection-compatible. The general rule is that
        // boxing and unboxing work wherever you can normally use a primitive or a wrapped object.
    }

    // =========================================================================================================================================
    // The Java 7 “Diamond” Syntax

    public static void test01_04() {
        // We’ve already seen several examples of declaring type-safe collections, and as we go deeper into collections, we’ll
        // see lots more like this:
        final ArrayList<String> stuff01 = new ArrayList<String>();

        final Map<String, Object> keyValueMap01 = new HashMap<String, Object>();

        // Notice that the type parameters are duplicated in these declarations. As of Java 7, these
        // declarations could be simplified to

        final ArrayList<String> stuff02 = new ArrayList<>();

        final Map<String, Object> keyValueMap02 = new HashMap<>();

        // You cannot swap these; for example, the following declaration is NOT legal:
        // List<> stuff = new ArrayList<String>(); // NOT a legal diamond syntax
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_03();
    }
}
