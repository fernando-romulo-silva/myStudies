package br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Test04 {

    // =========================================================================================================================================
    // Searching Arrays and Collections
    //
    // When searching through collections or arrays, the following rules apply:
    //
    // * Searches are performed using the binarySearch() method.
    //
    // * Successful searches return the int index of the element being searched
    //
    // * Unsuccessful searches return an int index that represents the insertion point. The insertion point is the place in the
    // collection/array where the element would be inserted to keep the collection/array properly sorted. Because positive return
    // values and 0 indicate successful searches, the binarySearch() method uses negative numbers to indicate insertion points.
    //
    // * The collection/array being searched must be sorted before you can search it.
    //
    // * If the collection/array you want to search was sorted in natural order, it must be
    // searched in natural order. (Usually, this is accomplished by NOT sending a Comparator as an
    // argument to the binarySearch() method.)
    //
    // If the collection/array you want to search was sorted using a Comparator , it must be searched using the same Comparator,
    // which is passed as the third argument to the binarySearch() method. Remember that Comparator s cannot
    // be used when searching arrays of primitives

    static class ReSortComparator implements Comparator<String> {

        @Override
        public int compare(final String a, final String b) {
            // By switching the use of the arguments in the invocation of compareTo() , we get an inverted sort.
            return b.compareTo(a);
        }
    }

    static void test01() {

        String[] sa = { "one", "two", "three", "four" };

        // Sort the sa array alphabetically (the natural order).
        Arrays.sort(sa);

        for (final String s : sa) {
            System.out.print(s + " ");
        }

        // Search for the location of element "one" , which is 1.
        System.out.println("\none = " + Arrays.binarySearch(sa, "one"));

        // Make a Comparator instance. On the next line, we re-sort the array using the Comparator .
        final ReSortComparator rs = new ReSortComparator();

        System.out.println("Now reverse sort");
        Arrays.sort(sa, rs);

        for (final String s : sa) {
            System.out.print(s + " ");
        }

        // Attempt to search the array. We didn’t pass the binarySearch() method the
        // Comparator we used to sort the array, so we got an incorrect (undefined) answer.
        System.out.println("\none = " + Arrays.binarySearch(sa, "one"));

        // Search again, passing the Comparator to binarySearch() . This time, we get
        // the correct answer, 2.
        System.out.println("one = " + Arrays.binarySearch(sa, "one", rs));
    }

    // =========================================================================================================================================
    // Converting Arrays to Lists to Arrays
    //
    // The Arrays.asList() method copies an array into a List . The API says, “Returns a fixed-size list backed by the specified array.
    // (Changes to the returned list ‘write through’ to the array.)”
    // When you use the asList() method, the array and the List become joined at the hip. When you update one of them, the other is updated
    // automatically.
    static void test02() {
        String[] sa = { "one", "two", "three", "four" };

        final List<String> sList = Arrays.asList(sa);

        System.out.println("size " + sList.size());
        System.out.println("idx2e " + sList.get(2));

        sList.set(3, "six");
        sa[1] = "five";

        for (String s : sa) {
            System.out.print(s + " ");
        }

        System.out.println("\ns1[1] " + sList.get(1));

        // Now let’s take a look at the toArray() method. There’s nothing too fancy going on
        // with the toArray() method; it comes in two flavors: one that returns a new Object
        // array, and one that uses the array you send it as the destination array:

        final List<Integer> iL = new ArrayList<>();

        for (int x = 0; x < 3; x++) {
            iL.add(x);
        }

        // create an Object array
        Object[] oa = iL.toArray();

        Integer[] ia2 = new Integer[3];

        // create an Integer array
        ia2 = iL.toArray(ia2);

        // 509
    }

    static class Dog {

        public String name;

        Dog(String n) {
            name = n;
        }

        @Override
        public int hashCode() {
            return name.length();
        }

        @Override
        public boolean equals(Object obj) {

            if (obj instanceof Dog && ((Dog) obj).name == name) {
                return true;
            }

            return false;
        }
    }

    static class Cat {
    }

    static enum Pets {
        DOG,
        CAT,
        HORSE
    }

    // =========================================================================================================================================
    // Using Lists
    //
    // Notice that in both of these examples, it’s perfectly reasonable to assume that duplicates might occur.
    // In addition, List s allow you to manually override the ordering of elements by adding or removing elements via the element’s index
    // The two Iterator methods you need to understand for the exam are :
    //
    // * boolean hasNext() : Returns true if there is at least one more element in the collection being traversed.
    // Invoking hasNext() does NOT move you to the next element of the collection.
    //
    // * Object next() : This method returns the next object in the collection AND moves you forward to the element after the element just returned.
    static void test03() {
        final List<Dog> d = new ArrayList<>();

        final Dog dog = new Dog("aiko");

        d.add(dog);

        d.add(new Dog("clover"));
        d.add(new Dog("magnolia"));

        // we used generics syntax to create the Iterator, cast not required
        final Iterator<Dog> i3 = d.iterator(); // make an iterator

        while (i3.hasNext()) {
            Dog d2 = i3.next(); // cast not required
            System.out.println(d2.name);
        }

        System.out.println("size: " + d.size());

        System.out.println("get1 " + d.get(1).name);

        System.out.println("aiko " + d.indexOf(dog));

        d.remove(2);

        Object[] oa = d.toArray();

        for (Object o : oa) {
            Dog d2 = (Dog) o;
            System.out.println("oa " + d2.name);
        }
    }

    // =========================================================================================================================================
    // Using SetS
    //
    // Remember that Set s are used when you don’t want any duplicates in your collection.
    // If you attempt to add an element to a set that already exists in the set, the duplicate
    // element will not be added, and the add() method will return false .
    static void test04() {
        // Set s = new HashSet<>();
        // HashSet s do not guarantee any ordering. Also, notice that the fourth
        // invocation of add() failed because it attempted to insert a duplicate entry
        // (a String with the value a ) into the Set .

        Set s = new TreeSet<>();
        // The issue is that whenever you want a collection to be sorted, its elements must be mutually comparable

        boolean[] ba = new boolean[5];

        ba[0] = s.add("a");
        ba[1] = s.add(new Integer(42));
        ba[2] = s.add("b");
        ba[3] = s.add("a");
        ba[4] = s.add(new Object());

        for (int x = 0; x < ba.length; x++) {
            System.out.print(ba[x] + " ");
        }

        System.out.println();

        for (Object o : s) {
            System.out.println(o + " ");
        }
    }

    // =========================================================================================================================================
    // Using Maps
    //
    // Remember that when you use a class that implements Map , any classes that you use as a part of the keys for that map must override
    // the hashCode() and equals() methods.

    // 515
    static void test05() {
        final Map<Object, Object> m = new HashMap<>();

        // The first value retrieved is a Dog object (your value will vary)
        m.put("k1", new Dog("aiko")); // add some key/value pairs
        // The second value retrieved is an enum value ( DOG ).
        m.put("k2", Pets.DOG);
        // The third value retrieved is a String; Enums override equals() and hashCode() .
        m.put(Pets.CAT, "CAT key");

        Dog d1 = new Dog("Clover"); // let's keep this reference
        // The fourth output is a String . The important point about this output is that the key used to retrieve the String was made of a Dog object
        m.put(d1, "Dog key");

        // The fifth output is null . The important point here is that the get() method failed to find the Cat object that was inserted earlier
        // It’s easy to see that Dog overrode equals() and hashCode() while Cat didn’t.
        m.put(new Cat(), "Cat Key");

        System.out.println(m.get("k1"));

        String k2 = "k2";

        System.out.println(m.get(k2));

        Pets p = Pets.CAT;

        System.out.println(m.get(p));
        System.out.println(m.get(d1));
        System.out.println(m.get(new Cat()));
        System.out.println(m.size());

        // What happens when an object used as a key has its values changed?
        d1.name = "magnolia";
        // Print null, The Dog that was previously found now cannot be found. Because the Dog.name
        // variable is used to create the hashcode, changing the name changed the value of the hashcode
        System.out.println(m.get(d1));

        // As a final quiz for hashcodes, determine the output for the following lines of code if they’re added to the end of MapTest.main()

        // Remember that the hashcode is equal to the length of the name variable. When you
        // study a problem like this, it can be useful to think of the two stages of retrieval:
        // 1. Use the hashCode() method to find the correct bucket.
        // 2. Use the equals() method to find the object in the bucket.

        // This hashcodes are both 6 , so step 1 succeeds. Once in the correct bucket (the “length of name = 6” bucket), 
        // the equals() method is invoked, and because Dog ’s equals() method compares names, equals() succeeds, and the output is Dog key         
        d1.name = "clover";
        System.out.println(m.get(new Dog("clover")));

        // This hashcode test succeeds, but the equals() test fails because arthur is NOT equal to clover 
        d1.name = "arthur";
        System.out.println(m.get(new Dog("clover")));
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test05();
    }

}
