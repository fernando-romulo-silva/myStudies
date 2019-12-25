package br.fernando.ch03_Assertions_and_Exceptions.p02_Working_with_Exception_Handling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test01 {

    // =========================================================================================================================================
    // Use the try Statement with multi-catch and finally Clauses
    static void test01(int x) {
        // Prior to Java 7, the best we could do was
        try {
            Connection connection = DriverManager.getConnection("some error");
            FileOutputStream f = new FileOutputStream("");
        } catch (SQLException ex) {

        } catch (IOException ex) {

        }

        // That’s a lot of duplication. Luckily, Java 7 made handling this sort of situation nice and easy with a feature called multi-catch :

        try {
            Connection connection = DriverManager.getConnection("some error");
            FileOutputStream f = new FileOutputStream("");
            // With multi- catch , order doesn’t matter. The following two snippets are equivalent to each other
        } catch (SQLException | IOException ex) {

        }

        try {
            FileOutputStream f = new FileOutputStream("");
            // The exception FileNotFoundException is already caught by the alternative IOException
            // } catch (FileNotFoundException | IOException ex) {

            // Since FileNotFoundException is a subclass of IOException , we could have just
            // written that in the first place! There was no need to use multi- catch . The simplified
            // and working version simply says:
        } catch (IOException ex) {

        }

        // Remember, multi- catch is only for exceptions in different inheritance hierarchies.

        // The following LEGAL code demonstrates assigning a new value to the single catch parameter:
        try {
            // access the database and write to a file
            FileOutputStream f = new FileOutputStream("");
        } catch (IOException e) {
            e = new IOException();
        }

        // But, The following ILLEGAL code demonstrates trying to assign a value to the final multi- catch parameter:
        try {
            Connection connection = DriverManager.getConnection("some error");
            FileOutputStream f = new FileOutputStream("");
            // With multi- catch , order doesn’t matter. The following two snippets are equivalent to each other
        } catch (SQLException | IOException ex) {
            // ex = new IOException(); // The parameter e of a multi-catch block cannot be assigned
        }
    }

    // =========================================================================================================================================
    // Rethrowing Exceptions
    static void test02() throws IOException, SQLException {
        // This is a common pattern called “handle and declare.” We want to do something
        // with the exception—log it. We also want to acknowledge we couldn’t completely
        // handle it, so we declare it and let the caller deal with it.

        try {
            couldThrowAnException();
        } catch (SQLException | IOException e) {
            log(e);
            throw e;
        }

        // Lucky for us, Java helps us out here as well with a feature added in Java 7.
        // This example is a nicer way of writing the previous code:
        try {
            couldThrowAnException();
            //  In Java 7 and later, it means catch all Exception subclasses that would allow the method to compile.
        } catch (Exception e) { // watch out: this isn't really cathing all excpeitons subclasses
            log(e);
            throw e;
        }

        // Notice the multi-catch is gone and replaced with catch(Exception e). It’s not bad
        // practice here, though, because we aren’t really catching all exceptions. The compiler is
        // treating Exception as “any exceptions that the called methods happen to throw.”
    }

    static void couldThrowAnException() throws IOException, SQLException {
        // You may have noticed that couldThrowAnException() doesn’t actually throw an exception.
        // The compiler doesn’t know this. The method signature is key to the compiler.
        // It can’t assume that no exception gets thrown, as a subclass could override the method and throw an exception.
    }

    static void log(Throwable e) {

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws IOException, SQLException {
        test02();
    }
}
