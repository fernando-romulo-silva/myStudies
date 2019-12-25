package br.fernando.ch05_IO_and_NIO_Objective.p01_file_navigation_and_IO;

import java.io.Console;

// The java.io.Console Class
//
// The Console class makes it easy to accept input from the command line, both echoed and nonechoed
// (such as a password), and makes it easy to write formattedoutput to the command line.
// It’s a handy way to write test engines for unit testing or if you want to support a simple but
// secure user interaction and you don’t need a GUI.
public class Test03 {

    static class MyUtility {

        String doStuff(final String arg1) {
            // stub code
            return "result is " + arg1;
        }
    }

    // =========================================================================================================================================
    // Using Console
    static void test01() {
        String name = "";

        Console c = System.console(); // #1: get a Console

        // we invoke readPassword , which returns a char[] , not a string.
        // You’ll notice when you test this code that the password you enter isn’t echoed
        // on the screen.

        char[] pw = c.readPassword("%s", "pw: "); // #2: return a char[]

        for (char ch : pw) {
            c.format("%c ", ch); // #3: format output
        }

        c.format("\n");

        MyUtility mu = new MyUtility();

        while (true) {
            name = c.readLine("%s", "input?: "); // #4: return a String
            c.format("output: %s \n", mu.doStuff(name));
        }

    }

    // =========================================================================================================================================
    static void summary() {
        Console c = System.console();

        char[] pw = c.readPassword("%s", "pw: ");

        for (char ch : pw) {
            c.format("%c ", ch); // #3: format output
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
