package org.nandao.ch09Java7FeaturesThatYouMayHaveMissed.part01ExceptionHandlingChanges;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Test {

    // The try-with-resources Statement
    //
    // Java 7 provides a useful shortcut to the code pattern open a resource
    //
    // try {
    // // work with the resource
    // }finally {
    // // close the resource
    // }
    //
    // provided the resource belongs to a class that implements the AutoCloseable
    // interface. That interface has a single method
    //
    // In its simplest variant, the try-with-resources statement has the form
    // try (Resource res = ...) {
    // // work with res
    // }
    public static void test1() throws Exception {
        // When the try block exits, res.close() is called automatically. Here is a typical example—reading all words of a file:

        try (Scanner in = new Scanner(Paths.get("/usr/share/dict/words"))) {

            while (in.hasNext()) {
                System.out.println(in.next().toLowerCase());
            }
        }

        // No matter how the block exits, both in and out are closed if they were constructed.
        // final This was surprisingly final difficult to implement final correctly prior final to Java 7
    }

    // Suppressed Exceptions
    //
    // Whenever you work with input or output, there is an awkward problem with
    // closing the resource after an exception. Suppose an IOException occurs and then,
    // when closing the resource, the call to close throws another exception.
    // Which exception will actually be caught? In Java, an exception thrown in a finally
    // clause discards the previous exception. This sounds inconvenient, and it is. 
    // After all, the user is likely to be much more interested in the original exception.
    public static void test2() throws Exception {

        try (final Scanner in = new Scanner(Paths.get("/usr/share/dict/words"))) {

            System.out.println("some errors!");

        } catch (final IOException ex) {

            // When you catch the primary exception, you can retrieve those secondary
            // exceptions by calling the getSuppressed method:

            final Throwable[] secondaryExceptions = ex.getSuppressed();

            for (final Throwable throwable : secondaryExceptions) {
                ex.addSuppressed(throwable);
            }
        }
    }

    // Catching Multiple Exceptions
    //
    // As of Java SE 7, you can catch multiple exception types in the same catch clause.
    // For example, suppose that the action for missing files and unknown hosts is
    // the same. Then you can combine the catch clauses:
    public static void test3() {

        try (Scanner in = new Scanner(Paths.get("/usr/share/dict/words")); PrintWriter out = new PrintWriter("/tmp/out.txt")) {
            while (in.hasNext()) {
                out.println(in.next().toLowerCase());
            }
        } catch (final FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IOException e1) {
            e1.printStackTrace();
        }

        // This feature is only needed when catching exception types that are not subclasses of one another
    }

    // Easier Exception Handling for Reflective methods
    // In the past, when you called a reflective method, you had to catch multiple
    // unrelated checked exceptions. For example, suppose you construct a class and
    // invoke its main method:
    public static void test4() {
        try {

            Class.forName(Test.class.getName()).getMethod("main").invoke(null, new String[]{});
            // This statement can cause a ClassNotFoundException, NoSuchMethodException, IllegalAccessException, or InvocationTargetException.
        } catch (final ReflectiveOperationException e) {
        }

        // Of course, you can use the feature described in the preceding section and combine them in a single clause:
        // }catch (ClassNotFoundException | NoSuchMethodException  | IllegalAccessException | InvocationTargetException ex) {
        //     
        // }
        //
        // However, that is still very tedious. Plainly, it is bad design not to provide a
        // common superclass for related exceptions. That design flaw has been remedied
        // in Java 7. A new superclass ReflectiveOperationException has been introduced so that
        // you can catch all of these exceptions in a single handler:
    }

    public static void main(final String[] args) throws Exception {

    }

}
