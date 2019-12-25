package br.fernando.ch03_Assertions_and_Exceptions.p01_Working_with_the_Assertion_Mechanism;

// 249
public class Test01 {

    // =========================================================================================================================================
    // Assertion overviews
    // Assertions let you test your assumptions during development, but the assertion code
    // basically evaporates when the program is deployed, leaving behind no overhead or
    // debugging code to track down and remove.
    static void methodA(int num) {
        assert (num > 0); // throws an AssertionError if this test isn't true

        int x = 10;
        int y = 24;

        useNume(num + 10);

        // Assertions come in two flavors: really simple and simple, as follows:
        assert (y > x);

        // or
        assert (y > x) : "y is " + y + " x is " + x; // with message for debug
    }

    static void useNume(int i) {
    }

    static void test01() {
        methodA(10);
    }

    // =========================================================================================================================================
    // Assertion Expression Rules
    static void test02() {

        int x = 1;
        boolean b = true;

        // the following six are legal assert statements
        assert (x == 1);

        assert (b);

        assert true;

        assert (x == 1) : x;

        assert (x == 1) : aReturn();

        assert (x == 1) : new ValidAssert();

        // The following six are ILLEGAL assert statements
        // none of these are booleans
        // assert(x = 1);
        // assert(x);
        // assert 0;
        // none of these return a value
        // assert(x == 1) : ;
        // assert(x == 1) : noReturn();
        // assert (x == 1) : Test01 va;

    }

    static String aReturn() {
        return null;
    }

    static void noReturn() {

    }

    static class ValidAssert {

    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test02();
    }

}
