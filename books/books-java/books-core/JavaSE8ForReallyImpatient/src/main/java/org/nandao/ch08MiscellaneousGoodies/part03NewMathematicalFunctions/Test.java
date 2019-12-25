package org.nandao.ch08MiscellaneousGoodies.part03NewMathematicalFunctions;

public class Test {

    // The Math class provides several methods for “exact” arithmetic that throw an exception
    // when a result overflows. For example, 100000 * 100000 quietly gives the
    // wrong result 1410065408, whereas multiplyExact(100000, 100000) throws an exception.
    // The provided methods are (add|subtract|multiply|increment|decrement|negate)Exact,
    // with int and long parameters. The toIntExact method converts a long to the
    // equivalent int
    public static void test1() {

        final int result1 = 100000 * 100000;

        // wrong value
        System.out.println("Resul1: " + result1);

        try {
            // trow a exception
            final int result2 = Math.multiplyExact(100000, 100000);

            System.out.println("Resul2: " + result2);

        } catch (final Exception e) {
            System.out.println("No result");
        }


    }

    // The floorMod and floorDiv methods aim to solve a long-standing problem with integer
    // remainders. Consider the expression n % 2. Everyone knows that this is 0 if
    // n is even and 1 if n is odd. Except, of course, when n is negative. Then it is –1.
    // Why? When the first computers were built, someone had to make rules for how
    // integer division and remainder should work for negative operands. Mathematicians
    // had known the optimal (or “Euclidean”) rule for a few hundred
    // years: always leave the remainder ≥ 0. 
    //
    // Use Math.floorMod(x, n) instead of x % n if x might be negative.
    public static void test2() {

        System.out.println(Math.floorMod(11, 2));

        System.out.println(Math.floorDiv(11, 2));

        System.out.println(Math.floorDiv(-11, 2));
    }

    public static void main(final String[] args) {
        test2();
    }

}
