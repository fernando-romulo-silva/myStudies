package br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections.Test02.DVDInfo01;

public class Test03 {

    // =========================================================================================================================================
    // Sorting with Comparator
    // The Comparator interface gives you the capability to sort a given collection any number of different ways. The other handy thing about the Comparator
    // interface is that you can use it to sort instances of any class—even classes you can’t modify—unlike the Comparable interface, which forces you to change
    // the class whose instances you want to sort.
    //

    static class GenreSort implements Comparator<DVDInfo01> {

        // The Comparator.compare() method returns an int whose meaning is the same as
        // the Comparable.compareTo() method’s return value.
        @Override
        public int compare(DVDInfo01 o1, DVDInfo01 o2) {
            return o1.getGenre().compareTo(o2.getGenre());
        }

    }

    public static void test01_01() throws Exception {

        List<DVDInfo01> dvds = new ArrayList<>();

        Test02.populateList01(dvds);

        Collections.sort(dvds, new GenreSort());

        System.out.println(dvds);
    }

    // Because GenreSort is implementing a functional interface, we can actually replace the entire class with a lambda expression.
    // To turn that Comparator into a lambda expression, here’s what we do:
    //
    // 1. First we get the two parameters from the compare() method; those become the
    // parameters for the lambda:
    // (one, two)
    // Because there are two parameters, we need to put them in parentheses.
    //
    // 2. Then,we write an arrow:
    // (one, two) ->
    //
    // 3. Then we write the body of the lambda expression. What should the body be?
    // Almost exactly what the body of the compare() method in GenreSort is:
    // (one, two) -> one.getGenre().compareTo(two.getGenre())
    public static void test01_02() throws Exception {

        List<DVDInfo01> dvds = new ArrayList<>();

        Test02.populateList01(dvds);

        Collections.sort(dvds, (one, two) -> one.getGenre().compareTo(two.getGenre()));

        System.out.println(dvds);
    }

    // =========================================================================================================================================
    // Sorting ArrayLists Using the sort() Method
    //
    //
    public static void test01_03() throws Exception {
        List<DVDInfo01> dvds = new ArrayList<>();

        Test02.populateList01(dvds);

        dvds.sort(new GenreSort());
        // or
        dvds.sort((one, two) -> one.getGenre().compareTo(two.getGenre()));

        System.out.println(dvds);
    }

    // The last rule you’ll need to burn into your mind is that whenever you want
    // to sort an array or a collection, the elements inside must all be mutually comparable

    // 504 OCA Java SE 8 Programmer 1 Exam Guide ?

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
