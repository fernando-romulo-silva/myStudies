package org.nandao.ch03ProgrammingWithLambdas.part02ParametersLambdaExpressions;

import java.util.Arrays;
import java.util.function.IntConsumer;

// In general, you want to design your algorithm so that it passes
// any required information as arguments.
public class Test {

    public static void main(String[] args) {

        final String[] names = new String[]{ "Paul", "Jack", "Mary" };

        Arrays.sort(names, //
                    (s, t) -> Integer.compare(s.length(), t.length())); // Compare strings s and t

        // Why an IntConsumer and not a Runnable? We tell the action in which iteration it occurs,
        // which might be useful information. The action needs to capture that input in a parameter
        repeat(10, i -> System.out.println("Countdown: " + (9 - i)));
        
        
        repeat(10, () -> System.out.println("Hello, World!"));
        
    }

    // Now consider a different example. This method repeats an action multiple times:
    public static void repeat(int n, IntConsumer action) {
        for (int i = 0; i < n; i++) {
            action.accept(i);
        }
    }

    public static void repeat(int n, Runnable action) {
        for (int i = 0; i < n; i++) {
            action.run();
        }
    }
}
