package org.nandao.ch06ConcurrencyEnhancements.part02ConcurrentHashMapImprovements;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class Test {

    private static final String WORD = "Test";
    private static final ConcurrentHashMap<String, Long> map1 = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        map1.put("Test00", 13L);
        map1.put("Test1", 10L);
        map1.put("Test2", 15L);
        map1.put("Test3", 8L);

        test2();
    }

    // Updating Values
    public static void test1() {
        // The original version of ConcurrentHashMap only had a few methods for atomic updates,
        // which made for somewhat awkward programming. Suppose we want to
        // count how often certain features are observed. As a simple example, suppose
        // multiple threads encounter words, and we want to count their frequencies.

        // Can we use a ConcurrentHashMap<String, Long>? Consider the code for incrementing
        // a count. Obviously, the following is not threadsafe:

        final ConcurrentHashMap<String, Long> map1 = new ConcurrentHashMap<>();

        Long oldValue = map1.get(WORD);

        Long newValue = oldValue == null ? 1 : oldValue + 1;

        map1.put(WORD, newValue); // Error—might not replace oldValue

        // One remedy is to use the replace operation, replacing a known old value with
        // a new one, just as you have seen in the preceding section:

        do {
            oldValue = map1.get(WORD);
            newValue = oldValue == null ? 1 : oldValue + 1;
        } while (!map1.replace(WORD, oldValue, newValue));

        map1.forEach((k, v) -> System.out.println("key: " + k + "value:" + v));

        // Alternatively, you can use a ConcurrentHashMap<String, AtomicLong> or, with Java 8, a
        // ConcurrentHashMap<String, LongAdder>. Then the update code is:
        final ConcurrentHashMap<String, LongAdder> map2 = new ConcurrentHashMap<>();

        map2.putIfAbsent(WORD, new LongAdder());

        map2.get(WORD).increment();

        // The first statement ensures that there is a LongAdder present that we can increment
        // atomically. Since putIfAbsent returns the mapped value (either the existing one or
        // the newly put one), you can combine the two statements:
        map2.putIfAbsent(WORD, new LongAdder()).increment();
        map2.forEach((k, v) -> System.out.println("key: " + k + "value:" + v));

        // Java 8 provides methods that make atomic updates more convenient. The compute
        // method is called with a key and a function to compute the new value. That
        // function receives the key and the associated value, or null if there is none, and it
        // computes the new value
        final ConcurrentHashMap<String, Integer> map3 = new ConcurrentHashMap<>();

        map3.compute(WORD, (k, v) -> v == null ? 1 : v + 1);

        map3.forEach((k, v) -> System.out.println("key: " + k + "value:" + v));

        // There are also variants computeIfPresent and computeIfAbsent that only compute a new
        // value when there is already an old one, or when there isn’t yet one. A map of
        // LongAdder counters can be updated with
        final ConcurrentHashMap<String, LongAdder> map4 = new ConcurrentHashMap<>();
        // The LongAdder constructor is only called when a new counter is actually needed.
        map4.computeIfAbsent(WORD, k -> new LongAdder()).increment();
        map4.forEach((k, v) -> System.out.println("key: " + k + "value:" + v));

        // The merge method makes this particularly convenient. It has a parameter for the
        // initial value that is used when the key is not yet present. Otherwise, the function
        // that you supplied is called, combining the existing value and the initial value.
        // (Unlike compute, the function does not process the key.)

        final ConcurrentHashMap<String, Long> map5 = new ConcurrentHashMap<>();

        map5.merge(WORD, 1L, (existingValue, pNewValue) -> existingValue + pNewValue);

        // or, more simply,

        map5.merge(WORD, 1L, Long::sum);
    }

    // Bulk Operations
    public static void test2() {

        // Java 8 provides bulk operations on concurrent hash maps that can safely execute
        // even while other threads operate on the map. The bulk operations traverse
        // the map and operate on the elements they find as they go along. No effort is
        // made to freeze a snapshot of the map in time. Unless you happen to know that
        // the map is not being modified while a bulk operation runs, you should treat its
        // result as an approximation of the map’s state.
        //
        // There are three kinds of operations:
        // • search applies a function to each key and/or value, until the function yields
        // a non-null result. Then the search terminates and the function’s result is returned.
        //
        // • reduce combines all keys and/or values, using a provided accumulation function.
        //
        // • forEach applies a function to all keys and/or values.
        //
        // Each operation has four versions:
        // • operationKeys: operates on keys.
        // • operationValues: operates on values.
        // • operation: operates on keys and values.
        // • operationEntries: operates on Map.Entry objects

        final long threshold = 2;
        final String result1 = map1.search(threshold, (k, v) -> v > 10 ? k : null);
        System.out.println("Result1 :" + result1);

        // The forEach methods have two variants. The first one simply applies a consumer
        // function for each map entry, for example

        map1.forEach(threshold, (k, v) -> System.out.println(k + " -> " + v));

        // The second variant takes an additional transformer function, which is applied
        // first, and its result is passed to the consumer:
        map1.forEach(threshold, //
                     (k, v) -> k + " -> " + v, // Transformer
                     System.out::println); // Consumer

        // The transformer can be used as a filter. Whenever the transformer returns
        // null, the value is silently skipped. For example, here we only print the entries
        // with large values:
        map1.forEach(threshold, //
                     (k, v) -> v > 1000 ? k + " -> " + v : null, // Filter and transformer
                     System.out::println); // The nulls are not passed to the consumer

        // The reduce operations combine their inputs with an accumulation function. For
        // example, here is how you can compute the sum of all values.
        final Long sum1 = map1.reduceValues(threshold, Long::sum);
        System.out.println("Sum1 :" + sum1);

        // As with forEach, you can also supply a transformer function. Here we compute
        // the length of the longest key:
        final Integer maxlength = map1.reduceKeys(threshold, //
                                                  String::length, // Transformer
                                                  Integer::max); // Accumulator

        System.out.println("Max Lenght: " + maxlength);

        // The transformer can act as a filter, by returning null to exclude unwanted inputs.
        // Here, we count how many entries have value > 1000:
        final Long count = map1.reduceValues(threshold, //
                                             v -> v > 10 ? 1L : null, // Transformer
                                             Long::sum); // Accumulator

        System.out.println("Count value > 10: " + count);

        // There are specializations for int, long, and double outputs with suffix ToInt, ToLong,
        // and ToDouble. You need to transform the input to a primitive value and specify a
        // default value and an accumulator function. The default value is returned when
        // the map is empty.

        final long sum2 = map1.reduceValuesToLong(threshold, //
                                                  Long::longValue, // Transformer to primitive type
                                                  0, // Default value for empty map
                                                  Long::sum); // Primitive type accumulator

        System.out.println("Sum2 :" + sum2);
    }

    // Set Views
    public static void test3() {

        // Suppose you want a large, threadsafe set instead of a map. There is no
        // ConcurrentHashSet class, and you know better than trying to create your own. Of
        // course, you can use a ConcurrentHashMap with bogus values, but then you get a map,
        // not a set, and you can’t apply operations of the Set interface.
        //
        // The static newKeySet method yields a Set<K> that is actually a wrapper around a
        // ConcurrentHashMap<K, Boolean>. (All map values are Boolean.TRUE, but you don’t actually
        // care since you just use it as a set.)

        final Set<String> words1 = ConcurrentHashMap.<String> newKeySet();
        words1.forEach((x) -> System.out.println(x));
        
        // Of course, if you have an existing map, the keySet method yields the set of keys.
        // That set is mutable. If you remove the set’s elements, the keys (and their values)
        // are removed from the map. But it doesn’t make sense to add elements to the key
        // set, because there would be no corresponding values to add. Java 8 adds a second
        // keySet method to ConcurrentHashMap, with a default value, to be used when adding
        // elements to the set:
        
        final Set<String> words2 = map1.keySet(1L);
        words2.add("Java");
        
        words2.forEach((x) -> System.out.println(x));

        // If "Java" wasn’t already present in words, it now has a value of one

    }

}
