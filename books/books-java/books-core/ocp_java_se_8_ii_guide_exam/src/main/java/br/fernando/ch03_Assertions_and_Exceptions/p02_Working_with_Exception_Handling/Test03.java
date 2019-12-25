package br.fernando.ch03_Assertions_and_Exceptions.p02_Working_with_Exception_Handling;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test03 {

    // ======================================================================================================
    // AutoCloseable Resources with a try-with-resources Statement
    // The finally block is a good place for closing files and assorted other resources, but
    // real-world clean-up code is easy to get wrong. And when correct, it is verbose.
    // Let’s look at the code to close our one resource when closing a file:
    static void test01() throws IOException {
        Reader reader01 = null;

        try {
            // read from file
            reader01 = new FileReader(new File(""));

        } catch (IOException e) {
            log(e);
            throw e;
        } finally {
            if (reader01 != null) {
                try {
                    reader01.close();
                } catch (IOException e2) {
                    // ignore exceptions on closing file
                }
            }
        }

        // Lucky for us, we have Automatic Resource Management using “ try-with-resources”
        // to get rid of even these three lines. The following code is equivalent to the previous example:

        // We start out by declaring the reader inside the try declaration.
        // Think of the parentheses as a for loop in which we declare a loop index variable that is scoped to just the loop.
        // Here, the reader is scoped to just the try block. Not the catch block, just the try block.
        try (Reader reader02 = new FileReader(new File(""))) {

        } catch (IOException e) {
            log(e);
            throw e;
        }

        // Remember that a try must have catch or finally .
        // Time to learn something new about that rule.
        // This is ILLEGAL code because it demonstrates a try without a catch or finally :
        //
        // try {
        // do stuff
        // } // need a catch or finally here
        //
        // The following LEGAL code demonstrates a try-with-resources with no catch or finally:
        try (Reader reader02 = new FileReader(new File(""))) {
            // do stuff

            // What’s the difference? The legal example does have a finally block; you just
            // don’t see it. The try-with-resources statement is logically calling a finally block to
            // close the reader
        }

        try (Reader reader02 = new FileReader(new File(""))) {
            // You can add your own finally block to try -with-resources as well.
            // Both will get called. We’ll take a look at how this works shortly.
        } finally { //

        }

        // Since the syntax is inspired from the for loop, we get to use a semicolon when
        // declaring multiple resources in the try . For example:

        try (final Connection connection = DriverManager.getConnection("url", "login", "password"); // first, third to close
                final Statement stmt = connection.createStatement(); // second, second to close
                final ResultSet rs = stmt.executeQuery("SELECT * FROM Customer"); // third resource, first to close
        ) {

            // do stuff

        } catch (SQLException e) {

        }
    }

    // =========================================================================================================================================
    // AutoCloseable and Closeable
    // Because Java is a statically typed language, it doesn’t let you declare just any type in a
    // try -with-resources statement.
    static void test02() {
        // The following code will not compile:
        // try (String s = "hi"){ } // The resource type String does not implement java.lang.AutoCloseable

        // There’s also an interface called java.io.Closeable , which is similar to
        // AutoCloseable but with some key differences. Why are there two similar interfaces,
        // you may wonder? The Closeable interface was introduced in Java 5. When try -with-
        // resources was invented in Java 7, the language designers wanted to change some
        // things but needed backward compatibility with all existing code. So they created a
        // superinterface with the rules they wanted.
    }

    // Ok because AutoCloseable allows thorwing any Exception
    static class A implements AutoCloseable {

        public void close() throws Exception {
        }
    }

    // Ok because subclasses or implementing methods can throw a sublcass of exception or none at all
    static class B implements AutoCloseable {

        public void close() {
        }
    }

    // ILLEGAL - Closeable only allows IOExceptions or subClasses
    static class C implements Closeable { // Closeable extends AutoCloseable
        // don't compile
        // public void close() throws Exception {}

        public void close() throws IOException {
        }
    }

    // =========================================================================================================================================
    // A Complex try-with-resources Example
    static void test03() {

        // The try block “ends,” and Automatic Resource Management automatically cleans up
        // the resources before moving on to the catch or finally .
        //
        // The resources get cleaned up, “backward” printing Close – Two and then Close – One .
        //
        // The close() method gets called in the reverse order in which resources are declared
        // to allow for the fact that resources might depend on each other.

        try (One one = new One(); //
                Two two = new Two();) {

            System.out.println("try");
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("Catch");
        } finally {
            System.out.println("Finally");
        }

        // try
        // Close - Two
        // Close - One
        // Catch
        // Finally
    }

    static class One implements AutoCloseable {

        public void close() {
            System.out.println("Close - One");
        }
    }

    static class Two implements AutoCloseable {

        public void close() {
            System.out.println("Close - Two");
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test03();
    }

    static void log(Throwable e) {

    }

}
