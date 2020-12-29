package br.fernando.ch06_generics_and_collections_Objective.part03_Using_Collections;

// Method Overview for Arrays and Collections
public class Test07 {

    // =========================================================================================================================================
    static void test01() {

        // ----------------------------------------------------------------------------------------------------------------
        //
        // java.util.Arrays
        //
        // static List asList(T[]) - Convert an array to a List (and bind them).
        //
        // static int binarySearch(object[], key) - Search a sorted array for a given value; return an index or insertion point.
        // static int binarySearch(primitive[], key)
        //
        // static int binarySearch(T[], key Comparator) - Search a Comparator-sorted array for a value.
        //
        // static boolean equals(Object[], Object[]) - Compare two arrays to determine if their contents are equal.
        // static boolean equals(primitive[], pritimite[])
        //
        // static void sort(Object[]) - Sort the elements of an array by natural order.
        // static void sort(primitive[])
        //
        // static void sort(T[], Comparator) - Sort the elements of an array using a Comparator.
        //
        // static String toString(Object[]) - Create a String containing the contents of an array.
        // static String toString(primitive[])
        //
        // ----------------------------------------------------------------------------------------------------------------
        //
        // java.util.Collections
        //
        // static int binarySearch(List, key) - Search a "sorted" List for a given value; return an index or insertion point.
        // static int binaySearch(List, key, Comparator)
        //
        // static void reverse(List) - Reverse the order of elements in a List.
        //
        // static Comparator reverseOrder() - Return a Comparator that sorts the reverse of the collection's current sort sequence.
        // static Compartor reverseOrder(Comparator)
        //
        // static void sor(List) - Sort a List eigher by natural order or by a Comparator.
        //
        // ----------------------------------------------------------------------------------------------------------------
        //
        // Method Overview for List, Set, Map, and Queue
        //
        // boolean add(element) - List, Set - add elemnt. For lists, optionally add element at an index point.
        // boolean add(element, index) - List
        //
        // boolean contains(object) - List, Set - Search a collection for an object (or, optionally for Maps, a key); return th result as a boolean.
        // boolean containsKey(object) - Map
        // boolean containsValue(object) - Map
        //
        // Object get(index) - List - Get an object from a collection via an index or a key.
        // Object get(key) - Map
        // 
        // int indexOf(object) - List - Get the location of an object in List.
        // 
        // Iterator iterator() - List, Set - Get an Iterator for a List or Set.
        //
        // Set keySet() - Map - Return a Set containing a Map's.
        // 
        // void put(key, value) - Map - Add a key/value pair to a Map.
        //
        // element remove(index) - List - Remove an element via an index, or via the elements value, or via a key.
        // element remove(object) - List, Set
        // element remove(key) - Map
        //
        // int size() - List, Set, Map - Return the number of elements in a collection.
        // 
        // Object[] toArray() - List, Set - Return an array containing the elements of the collection.
        // T[] toArray()   - List, Set
        // 
        //
        // For the exam, the PriorityQueue methods that are important to understand are
        // offer() (which is similar to add() ), peek() (which retrieves the element at the head
        // of the queue but doesn’t delete it), and poll() (which retrieves the head element and removes it from the queue).
        //
        // 
    }

    public static void main(String[] args) {
        test01();
    }
}
