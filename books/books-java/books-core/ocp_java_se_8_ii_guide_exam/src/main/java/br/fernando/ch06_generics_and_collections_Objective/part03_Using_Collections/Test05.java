package br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test05 {

    // =========================================================================================================================================
    // Navigating (Searching) TreeSets and TreeMaps
    //
    // Java 6 introduced (among other things) two interfaces: java.util.NavigableSet and java.util.NavigableMap.
    // For the purposes of the exam, you’re interested in how TreeSet and TreeMap implement these interfaces.

    static void test01() {
        TreeSet<Integer> times = new TreeSet<>();

        // add some departure times
        times.add(1205);
        times.add(1505);
        times.add(1545);
        times.add(1830);
        times.add(2010);
        times.add(2100);

        // java 5 version
        TreeSet<Integer> subset = new TreeSet<Integer>();
        subset = (TreeSet<Integer>) times.headSet(1600);
        System.out.println("J5 - last before 4pm is: " + subset.last());

        TreeSet<Integer> sub2 = new TreeSet<>();
        sub2 = (TreeSet<Integer>) times.tailSet(2000);
        System.out.println("J5 - first after 8pm is: " + sub2.first());

        // java 6 version using the new lower() and higher() methods
        System.out.println("J6 - last before 4pm is: " + times.lower(1600));
        System.out.println("J6 - first after 8pm is: " + times.higher(2000));

        // The difference between lower() and floor() is that lower() returns the element less than the given element,
        // and floor() returns the element less than or equal to the given element
        //
        // Similarly, higher() returns the element greater than the given element, and ceiling() returns the element
        // greater than or equal to the given element
    }

    // =========================================================================================================================================
    // Other Navigation Methods
    // Polling
    // The idea of polling is that we want both to retrieve and remove an element from either the beginning or the end of a collection.
    // In the case of TreeSet , pollFirst() returns and removes the first entry in the set, and pollLast() returns and removes the last.
    // Similarly, TreeMap now provides pollFirstEntry() and pollLastEntry() to retrieve and remove key/value pairs
    //
    // Descending Order
    // Also added in Java 6 for TreeSet and TreeMap were methods that returned a collection in the reverse order of the collection on
    // which the method was invoked. The important methods for the exam are TreeSet.descendingSet() and TreeMap.descendingMap()
    //
    // =========================================================================================================================================
    // Backed Collections
    //
    // Some of the classes in the java.util package support the concept of “backed collections.” We’ll use a little code to help explain the idea:
    static void test02() {
        final TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "ant");
        map.put("d", "dog");
        map.put("h", "horse");

        // create a backed collection
        final SortedMap<String, String> submap = map.subMap("b", "g"); // subMap(fromKey, true, toKey, false);

        // show contents
        System.out.println(map + " " + submap);

        // We can add new entries to either collection within the range of the copy, and the new entries will show up in both collections
        //
        // add to original
        map.put("b", "bat");

        // add to copy
        submap.put("f", "fish");

        // In addition, we can add a new entry to the original collection, even if it’s outside the range of the copy. In this case, the new entry will
        // show up only in the original—it won’t be added to the copy because it’s outside the copy’s range.

        // add to original - out of range
        map.put("r", "raccoon");

        // If you attempt to add an out-of-range entry to the copied collection, an exception will be thrown.
        // add to copy - out of range
        // submap.put("p", "pig");

        // show final contents
        System.out.println(map + " " + submap);

        // The subMap() method is making a copy of a portion of the TreeMap named map.
        //
        // When we add key/value pairs to either the original TreeMap or the partial-copy SortedMap, the new entries were automatically
        // added to the other collection—sometimes.
        //
        // When submap was created, we provided a value range for the new collection. This range defines not only what should be
        // included when the partial copy is created, but also defines the range of values that can be added to the copy
        //
        // For the exam, you’ll need to understand the basics just explained, plus a few more details about three methods: 
        // from TreeSet: headSet(), subSet() , and tailSet() 
        // from TreeMap: headMap(), subMap() , and tailMap()
        // 
        // headSet() / headMap() : Create a subset that starts at the beginning of the original collection and ends at the point
        // specified by the method’s argument. 
        //
        // tailSet() / tailMap() : Create a subset that starts at the point specified by the method’s argument and goes to the end
        // of the original collection
        //
        // subSet() / subMap() : Allow you to specify both the start and end points for the subset collection you’re creating.
        //
        // For the exam you have to remember only that when these methods are invoked with end point and boolean arguments, 
        // the boolean always means “is inclusive.”  Unless specifically indicated by a boolean argument, a subset’s starting point will always be inclusive
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test02();
    }

    //
}
