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
public class Test04 {

    // =========================================================================================================================================
    static void test01_01(String[] args) throws Exception {
        // What will the following code print when run with command line:
        // java TestSIS a A

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
    }

    // =========================================================================================================================================
    static void test01_02(String records1, String records2) throws Exception {
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
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {

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
    static void test01_06() throws Exception {
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
    // Consider the following method code:
    static void test01_07(String records1, String records2) throws Exception {
        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (Exception e) { // LINE 100
        }

        // What can be inserted at //LINE 100 to make the method compile?
        //
        // IOException | RuntimeException
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code print when compiled and run?

        String[] sa = new String[]{ "a", "b", null };

        for (String s : sa) {
            switch (s) {
            case "a":
                System.out.println("Got a");
            }
        }

        // Got a 
        // a NullPointerException stack trace
    }

    // =========================================================================================================================================
    static void test01_09(String records1, String records2) throws Exception {
        // Consider the following code:
        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            // e = new FileNotFoundException();
            e.printStackTrace();
        }

        // Assuming appropriate import statements and the existence of both the files, what will happen when the program is compiled and run?

        // The program will not compile because the line e = new FileNotFoundException(); in catch block is invalid.

        // The exception parameter in a multi-catch clause is implicitly final. Thus, it cannot be reassigned. Had there been only one exception
        // in the catch clause (of type that is compatible with FileNotFoundException such as IOException or Exception, but not RuntimeException),
        // it would have been valid.
    }

    // =========================================================================================================================================
    //
    static void test01_10() throws Exception {
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
    //
    static void test01_11(String records1, String records2) throws Exception {

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
    public static void main(String[] args) throws Exception {
        test01_01(args);
    }
}
