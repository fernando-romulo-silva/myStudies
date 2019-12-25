package br.fernando.ch03_Assertions_and_Exceptions.p02_Working_with_Exception_Handling;

import java.io.IOException;

public class Test04 {

    // ======================================================================================================
    // Suppressed Exceptions
    static void test01() {
        // Java also adds any exceptions thrown by the close() methods to a
        // suppressed array in that main exception. 

        //  Each close() method can throw an exception in addition to the try block itself
        try (One one = new One()) {

            //  If we remove this line, the code just prints Closing .
            throw new Exception("Try");

        } catch (Exception e) {
            System.out.println(e.getMessage());

            for (Throwable ex : e.getSuppressed()) {
                System.out.println("supperssed: " + ex);
            }
        }

        System.out.println("");
        System.out.println("");

        // As one more example, think about what the following prints:

        try (Bad b1 = new Bad("1"); Bad b2 = new Bad("2")) {

            // do stuffS
        } catch (Exception e) {
            System.out.println(e.getMessage());

            for (Throwable ex : e.getSuppressed()) {
                System.out.println("supperssed: " + ex);
            }
        }
        // When Automatic Resource Management calls b2.close() , we get our first exception. This
        // becomes the main exception. Then, Automatic Resource Management calls
        // b1.close() and throws another exception. Since there was already an exception
        // thrown, this second exception gets added as a second exception.
        // If the catch or finally block throws an exception, no suppressions happen
        //
        // Closing - 2
        // suppressed:java.io.IOException: Closing – 1

    }

    static class One implements AutoCloseable {

        public void close() throws IOException {
            throw new IOException("Closing");
        }
    }

    static class Bad implements AutoCloseable {

        String name;

        public Bad(String name) {
            this.name = name;
        }

        public void close() throws IOException {
            throw new IOException("Closing - " + name);
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01();
    }
}
