package org.nandao.ch06ConcurrencyEnhancements.part03ParallelArrayOperations;

import java.util.Arrays;
import java.util.Comparator;

// The Arrays class now has a number of parallelized operations. The static
// Arrays.parallelSort method can sort an array of primitive values or objects

public class Test {

    public static void main(String[] args) throws Exception {

        final String contents = "This a example test to use stream API";
        final String[] words = contents.split("[\\P{L}]+");
        Arrays.parallelSort(words);

        // When you sort objects, you can supply a Comparator. With all methods, you can
        // supply the bounds of a range, such as
        Arrays.parallelSort(words, words.length / 2, words.length); // Sort the upper half

        final Integer[] values = new Integer[]{ 3, 1, 2, 5 };

        final Comparator<Integer> comparator = (obj1, obj2) -> obj1.compareTo(obj2);
        
        // You can use a comparator
        Arrays.parallelSort(values, comparator); // Sort the upper half

        Arrays.parallelSetAll(values, i -> i % 10);
        // Fills values with 0 1 2 3 4 5 6 7 8 9 0 1 2 . . .

        Arrays.parallelPrefix(values, (x, y) -> x * y);
        // Fils valus with [1, 1 × 2, 1 × 2 × 3, 1 × 2 × 3 × 4, ...]
    }

}
