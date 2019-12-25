package br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Sorting Collections and Arrays
public class Test02 {

    // =========================================================================================================================================
    // Sorting Collections
    //
    // Let’s start with something simple, like sorting an ArrayList of strings alphabetically.
    // What could be easier? There are a couple of ways to sort an ArrayList ; for now we’ll use the java.util.Collections class to sort, and
    // return later to the ArrayList ’s sort() method
    //
    public static void test01_01() {
        final ArrayList<String> stuff = new ArrayList<>();

        stuff.add("Denver");
        stuff.add("Boulder");
        stuff.add("Vail");
        stuff.add("Aspen");
        stuff.add("Telluride");

        System.out.println("unsorted " + stuff);
        Collections.sort(stuff);
        System.out.println("sorted " + stuff);
    }

    static class DVDInfo01 {

        private String title;

        private String genre;

        private String leadActor;

        public DVDInfo01(final String title, final String genre, final String leadActor) {
            super();
            this.title = title;
            this.genre = genre;
            this.leadActor = leadActor;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getLeadActor() {
            return leadActor;
        }

        public void setLeadActor(String leadActor) {
            this.leadActor = leadActor;
        }

        public String toString() {
            return title + " " + genre + " " + leadActor + "\n";
        }
    }

    // The Comparable Interface
    // The Comparable interface is used by the Collections.sort() method and the
    // java.util.Arrays.sort() method to sort List s and arrays of objects, respectively.
    /// To implement Comparable , a class must implement a single method, compareTo()
    public static void test01_02() throws Exception {
        final ArrayList<DVDInfo01> dvdList = new ArrayList<>();

        populateList01(dvdList);

        // Collections.sort(dvdList); // compile error, DvdInfo don't extends Comparable
    }

    // In line 1, we declare that class DVDInfo implements Comparable in such a way that
    // DVDInfo objects can be compared to other DVDInfo objects.
    static class DVDInfo02 implements Comparable<DVDInfo02> {

        String title;

        String genre;

        String leadActor;

        public DVDInfo02(final String title, final String genre, final String leadActor) {
            super();
            this.title = title;
            this.genre = genre;
            this.leadActor = leadActor;
        }

        public String toString() {
            return title + " " + genre + " " + leadActor + "\n";
        }

        // we implement compareTo() by comparing the two DVDInfo object’s titles.
        @Override
        public int compareTo(DVDInfo02 o) {
            return title.compareTo(o.title);
        }
    }

    public static void test01_03() throws Exception {
        final ArrayList<DVDInfo02> dvdList = new ArrayList<>();

        populateList02(dvdList);

        // The sort() method uses compareTo() to determine how the List or object array should be sorted.
        Collections.sort(dvdList); // Now works
    }

    // =========================================================================================================================================

    public static void populateList01(List<DVDInfo01> info) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(Test02.class.getResourceAsStream("/dvdInfo.txt")));
        // read each line of dvdInfo.txt below
        String line = null;
        while ((line = br.readLine()) != null) { // check line in loop
            String[] tokens = line.split("/");
            DVDInfo01 infoItem = new DVDInfo01(tokens[0], tokens[1], tokens[2]);
            info.add(infoItem);
        }
    }

    public static void populateList02(List<DVDInfo02> info) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(Test02.class.getResourceAsStream("/dvdInfo.txt")));
        // read each line of dvdInfo.txt below
        String line = null;
        while ((line = br.readLine()) != null) { // check line in loop
            String[] tokens = line.split("/");
            DVDInfo02 infoItem = new DVDInfo02(tokens[0], tokens[1], tokens[2]);
            info.add(infoItem);
        }
    }

    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
