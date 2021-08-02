package br.com.fernando.ch13_thinking_functionally.part02_what_is_functional_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// What’s functional programming?
public class Test {

    public static void test00() {
        // The context of functional programming a function corresponds to a mathematical
        // function: it takes zero or more arguments, gives one or more results, and has no side effects.
    }

    // Functional-style Java
    public static void test01() {
        // In practice, you can’t completely program in pure functional style in Java.
        // To be regarded as functional style, a function or method shouldn’t throw any exceptions.
        // There’s a simple overlegalistic explanation: you can’t throw an exception because this means a result is
        // being signaled other than being passed as a proper result via return as in the black-box model discussed previously

        // Referential transparency
        // A function is referentially transparent if it always returns the same result value when called with the same argument value.
        //
        // Object-oriented vs. functional-style programming
        // At one end of the spectrum is the extreme object-oriented view: everything is an object and
        // programs operate by updating fields and calling methods that update their associated object. At
        // the other end of the spectrum lies the referentially transparent functional-programming style of
        // no (visible) mutation. In practice, Java programmers have always mixed these styles.

        // Functional style in practice (non function programming)
        // 
        // Takeaway point: thinking of programming problems in terms of function-style methods that are
        // characterized only by their input arguments, and their output result (that is, what to do) is often
        // more productive than thinking how to do it and what to mutate too early in the design cycle

        final List<List<Integer>> subs = subsets(Arrays.asList(1, 4, 9));
        subs.forEach(System.out::println);
    }

    static List<List<Integer>> subsets(List<Integer> list) {

        if (list.isEmpty()) {
            // If the input list is empty, it has exactly one subset, the empty list itself!
            final List<List<Integer>> ans = new ArrayList<>();
            ans.add(Collections.emptyList());
            return ans;
        }

        final Integer first = list.get(0);

        final List<Integer> rest = list.subList(1, list.size());

        // Otherwise take one element out, first, and find all subsets of the rest to give subans;
        // subans forms half of the answer
        final List<List<Integer>> subans = subsets(rest);

        // The other half of the answer, subans2, cosists of all the lists in subans bubt
        // adjusted by prefixing each of these element lists with first.
        final List<List<Integer>> subans2 = insertAll(first, subans);

        // Then concatenate the two subanswers, Easy?
        return concat(subans, subans2);
    }

    private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
        
	final List<List<Integer>> result = new ArrayList<>();

        for (final List<Integer> l : lists) {
            // Copy the list to allow you to add to it.
            // You wouldn't copy the lower-level structure even if it were mutable (Integers are not).
            final List<Integer> copyList = new ArrayList<>();
            
            copyList.add(first);
            
            copyList.addAll(l);
            
            result.add(copyList);
        }

        return result;
    }

    private static List<List<Integer>> concat(List<List<Integer>> a, List<List<Integer>> b) {
        
	// Why I create a result list? Because a I don't want change parameter's method.
	
	final List<List<Integer>> resutl = new ArrayList<>(a);
       
        resutl.addAll(b);
        return resutl;
    }
}
