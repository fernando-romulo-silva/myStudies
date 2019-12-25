package br.fernando.ch13_enhancements_Objective;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;
import java.util.TreeSet;

public class Test02 {

    // =========================================================================================================================================
    static void test01_01(String records1, String records2) throws Exception {

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

        // What can be inserted at //LINE 100 to make the method compile?

        // Correct Answer: IOException | RuntimeException

        // My Answer: IOException|RuntimeException
        // FileNotFountException is a subclass of IOException. You cannot include classes that are related by inheritance in the same multi-catch block.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of these statements about interfaces are true?

        // 1. Interfaces are always abstract.

        // 2. An interface can have static methods.

        // 3. Interfaces cannot be final.

        // 4. In Java 8, interfaces allow multiple implementation inheritance through default methods.
    }

    static interface I1 {

        default void m1() {
            System.out.println("In I1.m1");
        }
    }

    static interface I2 {

        public default void m1() {
            System.out.println("In I2.m1");
        }
    }

    // This class will not compile.
    // static class C1 implements I1, I2{ }

    // This class will compile because it provides its own implementation of m1
    static class C2 implements I1, I2 {

        public void m1() {
            System.out.println("in C2.m1");
        }
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code print?
        /**
         * <pre>
        int value = 1,000,000; //1         
        
        switch(value){             
            case 1_000_000 : System.out.println("A million 1"); //2                 
            break;             
            case 1000000 : System.out.println("A million 2"); //3                 
            break;
        }
         * </pre>
         */

        // Compilation error because of //1 and //3

        // Explanation
        // 1. You may use underscores (but not commas) to format a number for better code readability. So //1 is invalid.
        //
        // 2. Adding underscores doesn't actually change the number. The compiler ignores the underscores. So 1_000_000 and 1000000 are actually
        // same and you cannot have two case blocks with the same value. Therefore, the second case at //3 is invalid.

    }

    // =========================================================================================================================================
    static String[] sa = new String[]{ "a", "b", "c" };

    static void test01_04() throws Exception {
        // What will the following code print when compiled and run?
        for (String s : sa) {
            switch (s) { // A NullPointerException will be thrown here if "s" is null.
            case "a":
                System.out.println("Got a");
                break;
            case "b":
                System.out.println("Got b");
                break;
            // case null : System.out.println("null"); //This line will not even compile
            }
        }

        // Got a
        // Got b

        // Explanation
        // There is only one important point that you need to know about String in a switch statement:
        //
        // The String in the switch expression is compared with the expressions associated with each case label as if the String.equals method were
        // being used. This means if the string in the switch expression turns out to be null, it will cause a NullPointerException to be thrown.
    }

    // =========================================================================================================================================
    static void test01_05(String records1, String records2) throws Exception {
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

        static String getName() {
            return "Account";
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

        // is not a inheritance, it's a redefinition
        static String getName() {
            return "PremiumAccount";
        }
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
        // Which of the following statements are true regarding the try-with-resources statement?

        // catch and finally blocks are executed after the resources opened in the try blocks are closed.

        // Explanation
        // You need to know the following points regarding try-with-resources statement for the exam:
        //
        // 1. The resource class must implement java.lang.AutoCloseable interface. Many standard JDK classes such as BufferedReader, BufferedWriter)
        // implement java.io.Closeable interface, which extends java.lang.AutoCloseable.
        //
        // 2. Resources are closed at the end of the try block and before any catch or finally block.
        //
        // 3. Resources are not even accessible in the catch or finally block. For example:
        /**
         * <pre>
        try(Device d = new Device()){ 
            d.read();         
        }finally{
            d.close(); //This will not compile because d is not accessible here.         
        }
         * </pre>
         */
        // 4. Resources are closed in the reverse order of their creation. 
        //
        // 5. Resources are closed even if the code in the try block throws an exception.
        //
        // 6. java.lang.AutoCloseable's close() throws Exception but java.io.Closeable's close() throws IOException.
        // 
        // 7. If code in try block throws exception and an exception also thrown while closing is resource, the exception thrown while 
        // closing the resource is suppressed. The caller gets the exception thrown in the try block.

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
    }
}
