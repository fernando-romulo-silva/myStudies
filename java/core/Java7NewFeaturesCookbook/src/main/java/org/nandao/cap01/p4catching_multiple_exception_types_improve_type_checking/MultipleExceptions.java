package org.nandao.cap01.p4catching_multiple_exception_types_improve_type_checking;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultipleExceptions {

    private static final Logger logger = Logger.getLogger("log.txt");

    public static void main(String[] args) {
        test2();
    }

    // When an exception was thrown, the catch block was entered. Notice that the two exceptions
    // of interest here, java.util.InputMismatchException and InvalidParameter, occur
    // within the same catch statement and are separated with a vertical bar. Also, notice that there
    // is only a single variable, e, used to represent the exception.
    public static void test0() {
        System.out.print("Enter a number: ");
        try {
            final Scanner scanner = new Scanner(System.in);
            final int number = scanner.nextInt();
            if (number < 0) {
                throw new InvalidParameter();
            }
            System.out.println("The number is: " + number);
        } catch (InputMismatchException | InvalidParameter e) {
            logger.log(Level.INFO, "Invalid input, try again");
        }
    }

    // As a general rule, it is better to catch the exception that is as specific
    // to the problem as possible. For example, it is better to catch a
    // NoSuchFileException as opposed to the more broad Exception, when
    // dealing with a missing file. This provides more detail about the exception.

    public static void test1() {
        try {
            Files.delete(Paths.get(new URI("file:///tmp.txt")));
        } catch (final URISyntaxException ex) {
            ex.printStackTrace();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    // Assertions are useful in building an application that is more robust. A good introduction to
    // this topic can be found at http://download.oracle.com/javase/1.4.2/docs/
    // guide/lang/assert.html. In Java 7, a new constructor was added that allows a message
    // to be attached to a user-generated assertion error. This constructor has two arguments.
    // The first is the message to be associated with the AssertionError and the second is a
    // Throwable clause.
    public static void test2() {

        System.out.print("Enter a number: ");
        try {
            final Scanner scanner = new Scanner(System.in);
            final int number = scanner.nextInt();
            if (number < 0) {
                throw new InvalidParameter();
            }

            if (number > 10) {
                throw new AssertionError("Number was too big", new Throwable("Throwable assertion message"));
            }
            System.out.println("The number is: " + number);
        } catch (InputMismatchException | InvalidParameter e) {
            logger.log(Level.INFO, "Invalid input, try again");
        }

    }

}
