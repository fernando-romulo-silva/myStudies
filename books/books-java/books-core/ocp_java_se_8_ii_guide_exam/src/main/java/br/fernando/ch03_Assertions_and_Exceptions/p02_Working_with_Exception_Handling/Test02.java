package br.fernando.ch03_Assertions_and_Exceptions.p02_Working_with_Exception_Handling;

public class Test02 {

    // ======================================================================================================
    // To make sure you understand what is going on here, think about what happens in this example:

    static class A extends Exception {

    }

    static class B extends Exception {

    }

    public static void rain() throws A, B {

    }

    static void test01() throws A, B {

        // java 6 style
        // What happens if rain() adds a new checked exception?
        // Add another catch block to handle the new exception.
        //
        // What happens if rain() removes a checked exception from signature?
        // Remove a cathc block to avoid compiler error about unreachable code.
        try {
            rain();
        } catch (A e) {
            throw e;
        } catch (B e) {
            throw e;
        }

        // Java 7 and 8, with duplication
        // What happens if rain() adds a new checked exception?
        // Add another exception to multi-catch block to handle the new exception
        //
        // What happens if rain() removes a checked exception from signature?
        // Remove an expression from the multi-catch block to avoid compiler error about unreachable code.
        try {
            rain();
        } catch (A | B e) {
            throw e;
        }

        // java 7 and 8, without duplication
        // What happens if rain() adds a new checked exception?
        // Add another exception to the mehtod signature to handle the new exception that can be thrown.
        //
        // What happens if rain() removes a checked exception from signature?
        // No code changes needed.
        try {
            rain();
        } catch (Exception e) {
            throw e;
        }

        // For multi-catch , the compiler error occurs on the line where we attempt to assign a new value 
        // to the parameter, whereas here, the compiler error occurs on the line where we throw e . 
        // It is different because code written prior to Java 7 still needs to compile.
        try {
            rain();
        } catch (Exception e) {
            e = new A();
            // throw e; // 
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01();
    }

}
