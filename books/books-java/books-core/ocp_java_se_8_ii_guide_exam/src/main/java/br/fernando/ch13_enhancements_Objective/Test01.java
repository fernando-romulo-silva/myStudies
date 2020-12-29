package br.fernando.ch13_enhancements_Objective;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
         */

        // The code will not compile.
        // Remember that when you use a resource in try-with-resources, the resource must implement java.lang.AutoCloseable interface.
        // In this case, although Device class contains the close() method required by this interface but it does not say that it implements
        // AutoCloseable in its declaration. Therefore, compilation will fail.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
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

    static String[] sa = new String[]{ "a", "b", null };

    static void test01_04() throws Exception {
        // What will the following code print when compiled and run?
        for (String s : sa) {
            switch (s) {
            case "a":
                System.out.println("Got a");
            }
        }

        // Got a
        // a NullPointerException stack trace

        // The first String in the loop matches with case "a" and so Got a will be printed. Second String does not match with any of the case
        // labels, so nothing is printed. The third String is null and so a NullPointerException will be thrown.
    }

    // =========================================================================================================================================
    static void test01_05(String[] args) throws Exception {
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
    static void test01_06(String records1, String records2) throws Exception {

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
    static int test01_07(String a, int b) throws Exception {
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
    static void test01_08() throws Exception {
        // Given that the bit pattern for the amount $20,000 is 100111000100000, which of the following is/are valid declarations of an int
        // variable that contains this value?

        int b = 0b01001110_00100000;

        // Beginning with Java 7, you can include underscores in between the digits. This helps in writing long numbers.
        // For example, if you want to write 1 million, you can write: 1_000_000, which is easier to interpret for humans than 1000000.

        // Note that you cannot start or end a value with an underscore though. Thus, 100_ or _100 would be invalid values.
        // The value _100 would actually be a valid variable name!

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
        //
        // Since one resource was created, its close method will be called (which prints Closing device D1). Any exception that is thrown
        // while closing a resource is added to the list of suppressed exceptions of the exception thrown while
        // opening a resource (or thrown from the try block.)
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given Account and PremiumAccount,
        // Which of the following options can be inserted in PremiumAccount independent of each other?

        // String getId();
        //
        // or
        //
        // default String getId(){  return "1111"; };

        // Explanation
        // In case of classes, you cannot override a static method with a non-static method and vice-versa.
        //
        // However, in case of interfaces, it is possible for a sub interface to have a non-static (i.e. default) method with the same signature
        // as that of a static method of a super interface.
        //
        // This is because static methods are not inherited in any sense in the sub-interface
        // and that is why it is allowed to declare a default method with the same signature. The reverse is not true though.
        // That is, you cannot have a static method in a sub-interface with the same signature as that of a default method in a super interface.
    }

    interface Account {

        public default String getId() {
            return "0000";
        }
    }

    interface PremiumAccount extends Account {
        // INSERT CODE HERE
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
