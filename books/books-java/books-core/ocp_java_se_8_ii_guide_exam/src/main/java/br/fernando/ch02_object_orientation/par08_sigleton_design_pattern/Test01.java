package br.fernando.ch02_object_orientation.par08_sigleton_design_pattern;

import java.util.HashSet;
import java.util.Set;

class Test01 {

    // =========================================================================================================================================
    //
    static void test01() {

        // 1º Version
        Show01 show01 = Show01.getInstance();

        System.out.println(show01.bookSeat("1A"));
        System.out.println(show01.bookSeat("1A"));

        // The key parts of the singleton pattern are:
        //
        // 1º A private static variable to store the single instance called the singleton.
        // This variable is usually final to keep developers from accidentally changing it.
        //
        // 2º A public static method for callers to get a reference to the instance.
        //
        // 3º A private constructor so no callers can instantiate the object directly.

        Show03 show03 = Show03.INSTANCE;

        System.out.println(show03.bookSeat("1A"));
        System.out.println(show03.bookSeat("1A"));
    }

    // ----------------------------------------------------------
    static class Show01 {

        // store one instance (this is the singleton)
        private static final Show01 INSTANCE = new Show01();

        private Set<String> availableSeats;

        // CALLSERS CAN GET TO THE INSTANCE
        static Show01 getInstance() {
            return INSTANCE;
        }

        // callers can't create directly anymore.
        // Must use getInstance()
        private Show01() {
            availableSeats = new HashSet<>();
            availableSeats.add("1A");
            availableSeats.add("1B");
        }

        public boolean bookSeat(String seat) {
            return availableSeats.remove(seat);
        }
    }

    // ----------------------------------------------------------
    static class Show02 {

        private static Show02 INSTANCE;

        private Set<String> availableSeats;

        static Show02 getInstance() {

            // This is called eager initialization
            // In this example, INSTANCE isn’t final because that would prevent the code from compiling.
            if (INSTANCE == null) {
                INSTANCE = new Show02();
            }

            return INSTANCE;
        }

        private Show02() {
            availableSeats = new HashSet<>();
            availableSeats.add("1A");
            availableSeats.add("1B");
        }

        public boolean bookSeat(String seat) {
            return availableSeats.remove(seat);
        }
    }

    // ------------------------------------------------------------
    // You might have noticed that the code for getInstance() can get a bit complicated.
    // Java 5 gave us a much shorter way of creating a singleton:

    static enum Show03 {
        // This is na enum instead of a class
        INSTANCE;

        private Set<String> availableSeats;

        private Show03() {
            availableSeats = new HashSet<>();
            availableSeats.add("1A");
            availableSeats.add("1B");
        }

        public boolean bookSeat(String seat) {
            return availableSeats.remove(seat);
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
