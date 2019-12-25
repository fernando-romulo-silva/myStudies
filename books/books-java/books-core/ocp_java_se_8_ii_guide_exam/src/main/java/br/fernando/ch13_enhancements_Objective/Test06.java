package br.fernando.ch13_enhancements_Objective;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeSet;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given that the bit pattern for the amount $20,000 is 100111000100000, which of the following is/are valid declarations of an int
        // variable that contains this value?

        int b = 0b01001110_00100000;

        // Explanation
        // Beginning with Java 7, you can include underscores in between the digits. This helps in writing long numbers.
        // For example, if you want to write 1 million, you can write: 1_000_000, which is easier to interpret for humans than 1000000.
        // Note that you cannot start or end a value with an underscore though.
        // Thus, 100_ or _100 would be invalid values. _100 would actually be a valid variable name!
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
    static void test01_04() throws Exception {
        // Which statements about the following code are correct?

        House ci = new MyHouse(); // 1
        System.out.println(ci.getAddress()); // 2

        // The code will compile successfully.
        //
        // Explanation
        // There is no problem with the code. It is perfectly valid for a subinterface to override a default method of the base interface.
        // A class that implements an interface can also override a default method.
        //
        // It is valid for MyHouse to say that it implements Bungalow as well as House even though House is redundant because
        // Bungalow is a House anyway.
        //
        // It will actually print 101 Smart str.
    }

    static interface House {

        public default String getAddress() {
            return "101 Main Str";
        }
    }

    static interface Bungalow extends House {

        public default String getAddress() {
            return "101 Smart Str";
        }
    }

    static class MyHouse implements Bungalow, House {

    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print when compiled and run?

        String[] sa = new String[]{ "a", "b", null };

        for (String s : sa) {
            switch (s) {
            case "a":
                System.out.println("Got a");
            }
        }

        // Got a a NullPointerException stack trace
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Given Account and PremiumAccount, Which of the following options can be inserted in PremiumAccount independent of each other?

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

    interface PremiumAccount extends Account { // INSERT CODE HERE

        // An interface can redeclare a default method and also make it abstract.
        // String getId();

        // or (not the both)

        // An interface can redeclare a default method and provide a different implementation.
        default String getId() {
            return "1111";
        };
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08(String records1, String records2) throws Exception {
        // Consider the following method code:

        try {
            InputStream is = new FileInputStream(records1);
            OutputStream os = new FileOutputStream(records2);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }

            // } catch (FileNotFoundException | IndexOutOfBoundsException e) { // original
        } catch (IOException | IndexOutOfBoundsException e) {

        }

        // Assuming appropriate import statements and the existence of both the files, what will happen when the program is compiled and run?

        // The program will not compile because it does not handle exceptions correctly.
        // Remember that most of the I/O operations (such as opening a stream on a file, reading or writing from/to a file) throw IOException 
        // and this code does not handle IOException.
        //
        // FileNotFoundException is a subclass of IOException and IndexOutOfBoundsException is subclass of RuntimeException. 
        // The code can be fixed by replacing FileNotFoundException | IndexOutOfBoundsException with IOException or by adding another catch 
        // block that catches IOException.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // What will be printed when the following code is compiled and run?
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

        try (Device d = new Device()) {
            d.open();
            d.read();
            d.writeHeader("TEST");
            d.close();
        } catch (IOException e) {
            System.out.println("Got Exception");
        }

        // Device Opened
        // Device closed
        // Got Exception
        //
        // Catch and finally blocks are executed after the resource opened in try-with-resources is closed.
        // Therefore, Device Closed will be printed before Got Exception.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given that the user account under which the following code is run does not have the permission to access a.java, what will the the
        // following code print when run?
        try (BufferedReader bfr = new BufferedReader(new FileReader("c:\\works\\a.java"))) {
            String line = null;
            while ((line = bfr.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) { // } catch (NoSuchFileException | IOException | AccessDeniedException e) {
            e.printStackTrace();
        }

        // It will not compile because the catch clause is invalid.

        // NoSuchFileException is a subclass of IOException. In the same multi-catch block, you cannot include classes that are related
        // by inheritance.   
        // Remember that BufferedReader.close (which is called automatically at the end of the try-with-resources block)
        // and BufferedReader.readLine methods throw java.io.IOException.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_04();
    }
}
