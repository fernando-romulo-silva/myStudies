package org.nandao.cap01.p7using_safeVarargs_annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SafeVargExample {

    // The @SafeVarargs annotation is used with constructors and methods. To use the
    // @SafeVarargs annotation, the following steps need to be followed:
    // 1. Create a method or constructor that uses a variable number of generic parameters.
    // 2. Add the @SafeVarargs annotation before the method declaration.
    //
    // In Java 7, mandatory compiler warnings are generated with generic variable argument
    // methods or constructors. The use of the @SafeVarargs annotation suppresses warnings,
    // when these methods or constructors are deemed to be harmless.
    //
    // Cannot create a generic array like List<String>, it's a heap pollution
    public static void main(String[] args) {
        test0();
    }

    @SafeVarargs
    public static <T> void displayElements(T... array) {
        for (final T element : array) {
            System.out.println(element.getClass().getName() + ": " + element);
        }
    }

    public static void test0() {
        final ArrayList<Integer> a1 = new ArrayList<>();
        a1.add(new Integer(1));
        a1.add(2);
        final ArrayList<Float> a2 = new ArrayList<>();
        a2.add(new Float(3.0));
        a2.add(new Float(4.0));
        displayElements(a1, a2, 12);
    }

    // An example of heap pollution
    // Some methods should not be marked as safe, as illustrated with the following code adapted
    // from the javadoc description of the @SafeVarargs annotation (http://download.
    // oracle.com/javase/7/docs/api/index.html under the java.lang.SafeVarargs
    // annotation documentation).
    static void merge(List<String>... stringLists) {
        final Object[] array = stringLists;
        final List<Integer> tmpList = Arrays.asList(42);
        array[0] = tmpList; // Semantically invalid, but compiles without warnings
        final String element = stringLists[0].get(0); // runtime ClassCastException
    }
}
