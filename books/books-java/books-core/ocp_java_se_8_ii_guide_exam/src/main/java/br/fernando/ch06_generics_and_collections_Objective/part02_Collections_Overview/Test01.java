package br.fernando.ch06_generics_and_collections_Objective.part02_Collections_Overview;

class Test01 {

    // =========================================================================================================================================
    // So What Do You Do with a Collection?
    //
    // * Add objects to the collection.
    // * Remove objects from the collection.
    // * Find out if an object (or group of objects) is in the collection.
    // * Retrieve an object from the collection without removing it.
    // * Iterate through the collection, looking at each element (object) one after another.
    //
    // Definitely on the exam: ArrayList , ArrayDeque , TreeMap , TreeSet , and Arrays
    //
    // Somewhat likely to be on the exam: Collections , HashMap , Hashtable , and PriorityQueue
    //
    // Unlikely to be on the exam: HashSet , LinkedHashMap , LinkedHashSet , LinkedList , and Vector
    //
    // Collections come in four basic flavors:
    //
    // * Lists Lists of things (classes that implement List )
    // * Sets Unique things (classes that implement Set )
    // * Maps Things with a unique ID (classes that implement Map )
    // * Queues Things arranged in the order in which they are to be processed
    //
    // But there are subflavors within those four flavors of collections
    //
    // * Sorted : A sorted collection means that the order in the collection is determined
    // according to some rule or rules, known as the “sort order.” A sort order has nothing to
    // do with when an object was added to the collection or when it was last accessed or at
    // what “position” it was added.
    // Most commonly, the sort order used is something called the “natural order.”
    //
    // Unsorted
    //
    // * Ordered : When a collection is ordered, it means you can iterate through the collection in a specific (not random) order.
    //
    // unordered
    //
    // =========================================================================================================================================
    // List Interface
    //
    // A List cares about the index. The one thing that List has that nonlists don’t is a set of methods related to the index.
    // All three List implementations are ordered by index position
    //
    // ArrayList : Think of this as a growable array. It gives you fast iteration and fast random access. To state the obvious: It is an ordered
    // collection (by index), but not sorted.
    //
    // Vector : A Vector is basically the same as an ArrayList , but Vector methods are synchronized for thread safety.
    //
    // LinkedList : A LinkedList is ordered by index position, like ArrayList , except that the elements are doubly linked to one another.
    // It an easy choice for implementing a stack or queue. Keep in mind that a LinkedList may iterate more slowly than an ArrayList , but it’s a
    // good choice when you need fast insertion and deletion.
    //
    //
    public static void test01() {

    }

    // =========================================================================================================================================
    // List Interface
    //
    // A Set cares about uniqueness—it doesn’t allow duplicates. Your good friend the equals() method determines whether two objects are identical
    //
    // HashSet : A HashSet is an unsorted, unordered Set . It uses the hashcode of the object being inserted, so the more efficient your hashCode()
    // implementation, the better access performance you’ll get. Use this class when you want a collection with no duplicates and you don’t care about
    // order when you iterate through it.
    //
    // LinkedHashSet : A LinkedHashSet is an ordered version of HashSet that maintains a doubly linked List across all elements. Use this
    // class instead of HashSet when you care about the iteration order.
    //
    // TreeSet : It uses a Red-Black tree structure (but you knew that) and guarantees that the elements will be in ascending order, according
    // to natural order.
    // =========================================================================================================================================
    //
    // Map Interface
    //
    // A Map cares about unique identifiers. You map a unique key (the ID) to a specific value, where both the key and the value are, of course, objects.
    // The Map implementations let you do things like search for a value based on the key, ask for a collection of just the values, or ask for a collection
    // of just the keys. Like Set s, Map s rely on the equals() method to determine whether two keys are the same or different.
    //
    // HashMap : The HashMap gives you an unsorted, unordered Map . When you need a Map and you don’t care about the order
    // when you iterate through it, then HashMap is the way to go
    //
    // Hashtable : Like Vector , Hashtable has existed from prehistoric Java times. For fun, don’t forget to note the naming
    // inconsistency: HashMap vs. Hashtable . Where’s the capitalization of t? Oh well, you won’t be expected to spell it. 
    // Anyway, just as Vector is a synchronized counterpart to the sleeker, more modern ArrayList , 
    // Hashtable is the synchronized counterpart to  HashMap .
    //
    // LinkedHashMap :  Like its Set counterpart, LinkedHashSet , the LinkedHashMap collection maintains insertion order.
    // Although it will be somewhat slower than HashMap for adding and removing elements, you can expect faster iteration with a LinkedHashMap .
    //
    // TreeMap : Like TreeSet , TreeMap lets you define a custom sort order (via a Comparator ) when you construct a TreeMap that specifies 
    // how the elements should be compared to one another when they’re being ordered. As of Java 6, TreeMap implements NavigableMap . 
    public static void test02() {

    }
    // =========================================================================================================================================
    // 
    // Queue Interface
    //
    // Queues are typically thought of as FIFO (first-in, first-out). Queue s support all of the standard Collection methods and they also 
    // havemethods to add and subtract elements and review queue elements.
    //
    // PriorityQueue : Since the LinkedList class has been enhanced to implement the Queue interface, basic queues can be handled with
    // a LinkedList . The purpose of a PriorityQueue is to create a “priority-in, priority-out” queue as opposed to a typical FIFO queue.
    //
    // ArrayDeque : The Deque interface was added in Java 6. Deque (pronounced “deck“) is a double-ended queue, meaning you can add and remove items from both ends of the
    // queue. ArrayDeque is one of the Collections that implements this interface, and it is a good choice for implementing either a queue or a stack because it is resizable with no
    // capacity restrictions and it is designed to be high performance
    // =========================================================================================================================================

    public static void main(String[] args) {

    }
}
