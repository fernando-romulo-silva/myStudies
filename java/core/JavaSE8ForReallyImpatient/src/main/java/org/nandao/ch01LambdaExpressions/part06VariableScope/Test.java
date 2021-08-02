package org.nandao.ch01LambdaExpressions.part06VariableScope;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Often, you want to be able to access variables from an enclosing method or class
// in a lambda expression. Consider this example:
public class Test {

    public static void repeatMessage(String text, int count) {

        final Runnable r = () -> {

            for (int i = 0; i < count; i++) {
                System.out.println(text);
                Thread.yield();
            }

        };

        // System.out.println(i);

        new Thread(r).start();
    }

    //    To understand what is happening, we need to refine our understanding of a
    //    lambda expression. A lambda expression has three ingredients:
    //    1. A block of code
    //    2. Parameters
    //    3. Values for the free variables, that is, the variables that are not parameters and
    //    not defined inside the code    
    public static void repeatMessage2(String text, int count) {
        // In our example, the lambda expression has two free variables, text and count. The
        // data structure representing the lambda expression must store the values for these
        // variables, in our case, "Hello" and 1000.        
        final Runnable r = () -> {
            while (count > 0) {
                // There is a reason for this restriction. Mutating variables in a lambda expression
                // is not threadsafe. Consider a sequence of concurrent tasks, each updating a shared counter.
                // count--; // Error: Can’t mutate captured variable
                System.out.println(text);
                Thread.yield();
            }
        };
        new Thread(r).start();
    }

    public static void execute() {
        // If this code were legal, it would be very, very bad. The increment matches++ is not
        // atomic, and there is no way of knowing what would happen if multiple threads
        // execute that increment concurrently.
        final List<Path> matches = new ArrayList<>();

        for (final Path p : matches) {
            new Thread(() -> {
                if (p.isAbsolute()) {
                    matches.add(p);
                }
            }).start();
            // Legal to mutate matches, but unsafe
        }
    }

    // The body of a lambda expression has the same scope as a nested block. The same
    // rules for name conflicts and shadowing apply. It is illegal to declare a parameter
    // or a local variable in the lambda that has the same name as a local variable
    public static void execute2() {

        final Path first = Paths.get("/usr/bin");
        // Comparator<String> comp = (first, second) -> Integer.compare(first.length(), second.length());
        // Error: Variable first already defined

    }

    // When you use the this keyword in a lambda expression, you refer to the this
    // parameter of the method that creates the lambda. For example, consider
    class Application {

        public void doWork() {
            // The expression this.toString() calls the toString method of the Application object,
            // not the Runnable instance. 
            final Runnable runner = () -> {
                System.out.println(this.toString());
            };
        }
    }

    public static void main(String[] args) {
        repeatMessage("Hello", 1000); // Prints Hello 1,000 times in a separate thread
    }
}
