package br.fernando.ch13_enhancements_Objective;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {

        class Device implements AutoCloseable {

            String header = null;

            public void open() {
                header = "OPENED";
                System.out.println("Device Opened");
            }

            public String read() throws IOException {
                throw new IOException("Unknown");
            }

            public void writeHeader(String str) throws IOException {
                System.out.println("Writing : " + str);
                header = str;
            }

            public void close() {
                header = null;
                System.out.println("Device closed");
            }
        }

        // What will be printed when the following code is compiled and run?
        try (Device d = new Device()) {
            d.open();
            d.writeHeader("TEST");
            d.close();
        } catch (IOException e) {
            System.out.println("Got Exception");
        }

        // Device Opened
        // Writing : TEST
        // Device closed
        // Device closed

        // Explanation
        //
        // 1. d.open() will print Device Opened
        // 2. d.writeHeader("TEST") will print Writing : TEST
        // 3. d.close() will print Device Closed
        //
        // Since Device is instantiated in try-with-resources block, d.close() will be called automatically at the end of the try block.
        // Thus, Device Closed will be printed again. The fact that the code explicitly calls d.close() is irrelevant.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following code print when compiled and run?

        String[] sa = new String[]{ "a", "b", "c" };

        for (String s : sa) {
            switch (s) {
            case "a":
                System.out.println("Got a");
                break;
            case "b":
                System.out.println("Got b");
                break;
            }
        }

        // Got a
        // Got b
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will be printed when the following code is compiled and run?

        class Device {

            String header = null;

            public void open() {
                header = "OPENED";
                System.out.println("Device Opened");
            }

            public String read() throws IOException {
                throw new IOException("Unknown");
            }

            public void writeHeader(String str) throws IOException {
                System.out.println("Writing : " + str);
                header = str;
            }

            public void close() {
                header = null;
                System.out.println("Device closed");
            }
        }

        /**
         * <pre>
         * try (Device d = new Device()) {
         *     d.open();
         *     d.read();
         *     d.writeHeader("TEST");
         *     d.close();
         * } catch (IOException e) {
         *     System.out.println("Got Exception");
         * }
         * </pre>
         **/

        // The code will not compile.
        // Remember that when you use a resource in try-with-resources, the resource must implement java.lang.AutoCloseable interface.
        // In this case, although Device class contains the close() method required by this interface but it does not say that it
        // implements AutoCloseable in its declaration. Therefore, compilation will fail.
    }

    // =========================================================================================================================================
    public static void test01_04(String records1, String records2) throws IOException {
        // Consider the following code:
        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            // e = new FileNotFoundException(); // Error here
            e.printStackTrace();
        }

        // Assuming appropriate import statements and the existence of both the files, what will happen when the program is compiled and run?

        // The program will not compile because the line e = new FileNotFoundException(); in catch block is invalid.
        // The exception parameter in a multi-catch clause is implicitly final. Thus, it cannot be reassigned.
        // Had there been only one exception in the catch clause (of type that is compatible with FileNotFoundException such as IOException or Exception,
        // but not RuntimeException), it would have been valid.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print?
        int x = 1____3; // 1
        long y = 1_3; // 2
        float z = 3.234_567f; // 3

        System.out.println(x + " " + y + " " + z);

        // 13 13 3.234567

        // The number at //1 and //2 are actually the same. Although confusing, it is legal to have multiple underscores between two digits.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print?

        /**
         * <pre>
         *  
        int value = 1,000,000; //1         
        
        switch(value){             
            case 1_000_000 : System.out.println("A million 1"); //2                 
            break;             
            case 1000000 : System.out.println("A million 2"); //3                 
            break;         
        }
         * </pre>
         **/

        // Compilation error because of //1 and //3

        // Explanation
        //
        // 1. You may use underscores (but not commas) to format a number for better code readability. So //1 is invalid.
        //
        // 2. Adding underscores doesn't actually change the number. The compiler ignores the underscores.
        // So 1_000_000 and 1000000 are actually same and you cannot have two case blocks with the same value. Therefore, the second case at //3 is invalid.
    }

    // =========================================================================================================================================
    static void test01_07(String records1, String records2) throws Exception {
        // Consider the following method code:

        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (IOException | RuntimeException e) { // LINE 100
        }

        // What can be inserted at //LINE 100 to make the method compile?

        // IOException | RuntimeException
        // Remember, You cannot include classes that are related by inheritance in the same multi-catch block.
    }

    // =========================================================================================================================================
    static int test01_08(String a, int b) throws Exception {
        // Consider the following code fragment appearing in a class:

        final String aa = " ";

        String c = "c";

        switch (a) {
        case "":
            return 1;

        case aa:
            return 1;

        case " z ": // only const
            return 1;

        // case c : return 3; // must be a const    
        }
        return 0;

        // What should be the type of XXXX to make the code compile?

        // java.lang.String

        // Explanation
        // Rule 1 : The type of the switch expression and the type of the case labels must be compatible. In this case, the switch expression
        // is of type String, so all the case labels must be of type String.

        // FYI : case labels must be compile time constants. Thus, you cannot use non-final variable names as labels. final variables can be used.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Identify the correct statements regarding the following program?

        class Device implements AutoCloseable {

            String header = null;

            public Device(String name) throws IOException {
                header = name;
                if ("D2".equals(name))
                    throw new IOException("Unknown");

                System.out.println(header + " Opened");
            }

            public String read() throws IOException {
                return "";
            }

            public void close() {
                System.out.println("Closing device " + header);
                throw new RuntimeException("RTE while closing " + header);
            }
        }

        try (Device d1 = new Device("D1"); Device d2 = new Device("D2")) {
            throw new Exception("test");
        }

        // It will end up with an IOException containing message "Unknown" and a suppressed RuntimeException containing message "RTE while closing D1".

        // Explanation
        // The following output obtained after running the program explains what happens: D1 Opened Closing device D1
        /**
         * <pre>
         * Exception in thread "main" java.io.IOException: Unknown     
         *     at trywithresources.Device.<init>(Device.java:9)     
         *     at trywithresources.Device.main(Device.java:24)     
         *     Suppressed: java.lang.RuntimeException: RTE while closing D1         
         *         at trywithresources.Device.close(Device.java:19)         
         *         at trywithresources.Device.main(Device.java:26) Java Result: 1
         * </pre>
         */

        // Device D1 is created successfully but an IOException is thrown while creating Device D2. Thus, the control never enters the try
        // block and throw new Exception("test") is never executed.
        // Since one resource was created, its close method will be called (which prints Closing device D1). Any exception that is thrown
        // while closing a resource is added to the list of suppressed exceptions of the exception thrown while
        // opening a resource (or thrown from the try block.)
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code print when run?

        try {
            m2();
        } catch (Exception e) {
            Throwable[] ta = e.getSuppressed(); // Returns an array containing all of the exceptions that were suppressed, typically by the try-with-resources statement
            for (Throwable t : ta) {
                System.out.println(t.getMessage());
            }

            // the Exception e is java.lang.RuntimeException: Exception from finally
        }

        // It will not print any thing.

        // Explanation
        // In the given code, method m2() throws an exception explicitly from the catch block as well as from the finally block. Since this is an explicit finally
        // block (and not an implicit finally block that is created when you use a try-with-resources statement), the exception thrown by the finally block
        // is the one that is thrown from the method. The exception thrown from the catch block is lost. It is not added to the suppressed exceptions list of
        // the exception thrown from the finally block.
        //
        // Therefore, e.getSuppressed() returns an array with 0 elements and nothing is printed.
        //
        // Had the code for m3() been something like this:
        // Now, if m1() throws an exception and r.close() also throws an exception, the exception thrown by m1 would have been the one thrown by the method m3
        // and the exception thrown by r.close() would have been added to the list of suppressed exception of the exception thrown from the try block.
    }

    public static void m1() throws Exception {
        throw new Exception("Exception from m1");
    }

    @SuppressWarnings("finally")
    public static void m2() throws Exception {
        try {
            m1();
        } catch (Exception e) { // Can't do much about this exception so rethrow it
            throw e;
        } finally {
            throw new RuntimeException("Exception from finally");
        }
    }

    public static void m3() throws Exception {
        try (Connection c = DriverManager.getConnection("url", "user", "password");) {
            m1();
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_10();
    }
}
