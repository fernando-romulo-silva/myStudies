package br.fernando.ch02_object_orientation.par05_implementing_an_interface;

// Java 8—Now with Multiple Inheritance!
public class Test02 {

    // =========================================================================================================================================
    // Implementing an Interface
    static void test01() {

    }

    static interface I1 {
        default int doStuff() {
            return 1;
        }
    }

    static interface I2 {
        default int doStuff() {
            return 2;
        }
    }

    static class MultiInt implements I1, I2 {

        void go() {
            System.out.println(doStuff());
        }

        // it WILL NOT COMPILE (if you comment this method) because 
        // it’s not clear which version of doStuff() should be used. 
        public int doStuff() {
            return 3;
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
