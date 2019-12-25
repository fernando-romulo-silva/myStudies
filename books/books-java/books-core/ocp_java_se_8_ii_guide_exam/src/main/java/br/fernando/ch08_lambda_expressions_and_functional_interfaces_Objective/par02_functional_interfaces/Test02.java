package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par02_functional_interfaces;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test02 {

    // =========================================================================================================================================
    // Working with Suppliers
    static void test01() {
        // We’ve made a Supplier whose functional method, get() returns an Integer object, 42 .
        Supplier<Integer> answerSupplier01 = () -> 42;

        System.out.println("Answer to everything: " + answerSupplier01.get());

        // Let’s take a look at how we might create a Supplier without using a lambda expression
        Supplier<Integer> answerSupplier02 = new Supplier<Integer>() {

            @Override
            public Integer get() {
                return 42;
            }
        };

        Supplier<String> userSupplier01 = () -> {
            // get the system environment map
            Map<String, String> env = System.getenv();

            // get the value with the key "USER" from the map an return it
            return env.get("USER");
        };

        System.out.println("User is: " + userSupplier01.get());
    }

    // =========================================================================================================================================
    static void test01_01() {
        // The key thing to note about both of these suppliers is they take no arguments and return an object. That’s what suppliers do.
        // Looking at java.util.function , we can see that there are some variations on supplier, including IntSupplier , DoubleSupplier , and LongSupplier .
        // As you can guess, these supply an int , a double , and a long , respectively.

        Random random = new Random();

        IntSupplier randomIntSupplier = () -> random.nextInt(50);

        int myRandom = randomIntSupplier.getAsInt();

        System.out.println("Random number: " + myRandom);

        // Notice that IntSupplier doesn’t use a type parameter, because the functional method returns a primitive.
    }

    // =========================================================================================================================================
    // What’s the Point of Supplier? 
    // So using a Supplier here avoids that expensive operation when it’s unnecessary. Lazy operations
    static void test01_02() {

        String host = "coderanch.com";

        int port = 80;

        // set up logging
        Logger logger = Logger.getLogger("Status Logger");

        logger.setLevel(Level.SEVERE);

        // in case we need to check th status
        Supplier<String> status = () -> {

            int timeout = 1000;

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), timeout);
                return "up";
            } catch (IOException ex) {
                return "down"; // Error; can't reach the system!
            }
        };

        try {
            // Because we’ve set the log level to SEVERE on line 5, in the try block, where we call
            // the log() method with Level.INFO , the log() method won’t bother calling the get()
            // method of the status Supplier , and so we avoid making that expensive network call.

            // only calls the Supplier.get() method of the status Supplier if level is INFO or below
            logger.log(Level.INFO, status);

            // do stuff with coderanch.com
            // ....
            // 
        } catch (Exception ex) {
            // calls the get() method of the status Supplier if level is SEVERE or below
            logger.log(Level.SEVERE, status);
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
