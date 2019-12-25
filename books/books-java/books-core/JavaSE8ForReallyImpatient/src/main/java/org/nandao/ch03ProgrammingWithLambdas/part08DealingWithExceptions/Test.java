package org.nandao.ch03ProgrammingWithLambdas.part08DealingWithExceptions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

// When an exception is thrown in a lambda expression, it is propagated to the caller.
// They are simply method calls on some object that implements a functional
// interface. Often it is appropriate to let the expression bubble up to the caller

public class Test {

    public static void main(String[] args) {

        try {

            doInOrder(() -> System.out.println(args[0]), // exception
                      () -> System.out.println("Goodbye") // Text
            );

        } catch (final Exception e1) {
            e1.printStackTrace();
        }

        try {

            doInOrderAsync(() -> System.out.println(args[0]), // exception
                           () -> System.out.println("Goodbye") // Text
            );

        } catch (final Exception e2) {
            e2.printStackTrace();
        }

        doInOrderAsync(() -> System.out.println(args[0]), // exception
                       () -> System.out.println("Goodbye"), // Text
                       (e) -> System.out.println("Yikes: " + e) // Return Exception

        );

        final Supplier<String> s = unchecked(() -> new String(Files.readAllBytes(Paths.get("/etc/passwd")), StandardCharsets.UTF_8));
        System.out.println(s.get());

    }

    // If first.run() throws an exception, then the doInOrder method is terminated, second
    // is never run, and the caller gets to deal with the exception.
    public static void doInOrder(Runnable first, Runnable second) {
        first.run();
        second.run();
    }

    // f first.run() throws an exception, the thread is terminated, and second is never
    // run. However, the doInOrderAsync returns right away and does the work in a separate
    // thread, so it is not possible to have the method rethrow the exception.
    public static void doInOrderAsync(Runnable first, Runnable second) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                first.run();
                second.run();
            }
        };
        t.start();
    }

    // The doInOrderAsync returns right away and does the work in a separate
    // thread, so it is not possible to have the method rethrow the exception. In
    // this situation, it is a good idea to supply a handler:
    public static void doInOrderAsync(Runnable first, Runnable second, Consumer<Throwable> handler) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    first.run();
                    second.run();
                } catch (final Throwable t) {
                    handler.accept(t);
                }
            }
        };
        t.start();
    }

    // Now suppose that first produces a result that is consumed by second. We can still use the handler.
    public static <T> void doInOrderAsync(Supplier<T> first, Consumer<T> second, Consumer<Throwable> handler) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    final T result = first.get();
                    second.accept(result);
                } catch (final Throwable t) {
                    handler.accept(t);
                }
            }
        };
        t.start();
    }

    // It is often inconvenient that methods in functional interfaces don’t allow checked
    // exceptions. Of course, your methods can accept functional interfaces whose
    // methods allow checked exceptions, such as Callable<T> instead of Supplier<T>. A
    // Callable<T> has a method that is declared as T call() throws Exception. If you want
    // an equivalent for a Consumer or a Function, you have to create it yourself.
    // You sometimes see suggestions to “fix” this problem with a generic wrapper,
    // like this:
    public static <T> Supplier<T> unchecked(Callable<T> f) {
        return () -> {
            try {
                return f.call();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            } catch (final Throwable t) {
                throw t;
            }
        };
    }

}
