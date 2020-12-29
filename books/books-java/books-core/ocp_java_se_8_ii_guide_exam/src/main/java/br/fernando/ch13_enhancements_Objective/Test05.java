package br.fernando.ch13_enhancements_Objective;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeSet;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Consider the follwing code snippet :

        // readData method

        // Given that getConnection method throws ClassNotFoundException and that an IOException is thrown while closing fr1, which exception
        // will be received by the caller of readData() method?

        // java.lang.ClassNotFoundException

        // Explanation
        //
        // If an exception is thrown within the try-with-resources block, then that is the exception that the caller gets.
        //
        // But before the try block returns, the resource's close() method is called and if the close() method throws an exception as well,
        // then this exception is added to the original exception as a supressed exception.
    }

    public static void readData(String fileName) throws Exception {
        try (FileReader fr1 = new FileReader(fileName)) {
            Connection c = getConnection(); // ...other valid code
        }
    }

    static class Connection {

    }

    static Connection getConnection() throws ClassNotFoundException {
        throw new ClassNotFoundException();
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
    static void test01_03(String records1, String records2) throws Exception {
        // Consider the following code:
        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) { // 1

            // if (os == null) os = new FileOutputStream("c:\\default.txt"); // 2 // don't compile here

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) { // 3
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (IOException e) { // 4 e.printStackTrace(); }
        }

        // The program will not compile because line //2 is invalid.
        // The auto-closeable variables defined in the try-with-resources statement are implicitly final. Thus, they cannot be reassigned.
    }

    // =========================================================================================================================================
    static int test01_04(String a, int b) throws Exception {
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
    static void test01_05() throws Exception {
        // Which of these statements about interfaces are true?

        // Interfaces are always abstract.
        //
        // An interface can have static methods.
        //
        // Interfaces cannot be final.
        //
        // In Java 8, interfaces allow multiple implementation inheritance through default methods.
    }

    // =========================================================================================================================================
    static void test01_06(String records1, String records2) throws Exception {
        // Consider the following method code:

        try (InputStream is = new FileInputStream(records1); //
                OutputStream os = new FileOutputStream(records2);) {

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (/* *INSERT CODE HERE* */ IOException | RuntimeException e) {
            // LINE 100
        }

        // Correct Answer: IOException | RuntimeException

        // My Answer: IOException|FileNotFountException
        // FileNotFountException is a subclass of IOException. You cannot include classes that are related by inheritance in the same multi-catch block.

    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which of the following is/are valid double values for 10 million? (A million has 6 zeros)

        double d = 10_000_000;

        // Explanation
        // Beginning with Java 7, you can include underscores in between the digits. This helps in writing long numbers. 
        // For example, if you want to write 1 million, you can write: 1_000_000, which is easier than 1000000 for humans to interpret.
        //
        // Note that you cannot start or end a value with an underscore though. Thus, 100_ or _100 are invalid values. 
        // The _100 is actually a valid variable name!
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code print when executed?
        Set<String> names = new TreeSet<String>();
        names.add("111");
        names.add("222");
        names.add("111");
        names.add("333");
        for (String name : names) {
            switch (name) {
            default:
                System.out.print("333 ");
                break;
            case "111":
                System.out.print("111 ");
            case "222":
                System.out.print("222 ");
            }
        }

        // 111 222 222 333

        // Explanation
        // There are a few things this question tries to test you on:
        //
        // 1. A Set contains unique elements. If you add an element that already exists, it is ignored.
        // Thus, in this case, the set will only contain 3 elements (and not 4) because 111 is being added twice.
        //
        // 2. A TreeSet keeps its elements sorted. So when you iterate through a TreeSet, you get elements in sorted order.
        //
        // 3. The order of case labels in a switch block doesn't matter. So even though default is the first statement in this switch block,
        // it will not be executed when the name is 111 or 222.
        //
        // 4. A break statement causes the execution control to come out of the switch block. If you do not have a break after a case block,
        // the control will fall through to the next case block. Thus, when name is 111, the control will go to case 111 block and, after
        // executing the case block, fall through to the next case block. Thus, it will print 111 222.
        //
        // Therefore, the given code will print 111 222 (when name is 111), 222 (when name is 222) and 333 (when name is 333).        
    }

    // =========================================================================================================================================
    static void test01_09(String[] args) throws Exception {
        // What will the following code print when run with command line: java TestSIS a A

        for (String arg : args) {
            switch (arg) {
            case "a":
                System.out.println("great!");
                break;
            default:
                System.out.println("unknown");
            }
        }

        // great!
        // unknown

        // Remember that String comparison is case sensitive in a switch statement.
        // Also, FYI, if the switch variable (i.e. arg, in this example) turns out to be null at run time, a NullPointerException is thrown
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
