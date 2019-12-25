package org.nandao.ch01LambdaExpressions.part03FunctionalInterfaces;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

// As we discussed, there are many existing interfaces in Java that encapsulate
// blocks of code, such as Runnable or Comparator.
// Lambdas are backwards compatible with these interfaces.
// You can supply a lambda expression whenever an object of an interface with a
// single abstract method is expected. Such an interface is called a functional interface.
public class Test {

    // To demonstrate the conversion to a functional interface, consider the Arrays.sort
    // method. Its second parameter requires an instance of Comparator, an interface with
    // a single method. Simply supply a lambda:
    public static void test1() {
        final String[] words = { "Test1", "Test2" };

        Arrays.sort(words, (first, second) -> Integer.compare(first.length(), second.length()));

        // One of the interfaces, BiFunction<T, U, R>, describes functions
        // with parameter types T and U and return type R. You can save our string
        // comparison lambda in a variable of that type
        final BiFunction<String, String, Integer> comp = (first, second) -> Integer.compare(first.length(), second.length());

        // Finally, note that checked exceptions matter when a lambda is converted to an
        // instance of a functional interface. If the body of a lambda expression may throw
        // a checked exception, that exception needs to be declared in the abstract method
        // of the target interface. For example, the following would be an error:
        final Runnable sleeper = () -> {
            System.out.println("Zzz");
            // Thread.sleep(1000); // error here
        };

        // You can catch the exception in the body of the lambda expression. Or assign the lambda to an interface whose single abstract
        // method can throw the exception.

        final Callable<Integer> call = () -> {

            Thread.sleep(1000);
            return 1;
        };

    }

    public static void main(String[] args) {

    }

}
