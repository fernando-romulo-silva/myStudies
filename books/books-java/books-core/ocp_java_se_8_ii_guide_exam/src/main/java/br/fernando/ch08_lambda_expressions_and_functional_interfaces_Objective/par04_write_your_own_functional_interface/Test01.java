package br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par04_write_your_own_functional_interface;

public class Test01 {

    // =========================================================================================================================================
    // Write Your Own Functional Interface
    static void test01_01() {

        final TriPredicate<String, Integer, Integer> theTest = (s, n, w) -> {

            if (s.equals("There is no spoon") && n > 2 && w < n) {
                return true;
            } else {
                return false;
            }
        };

        System.out.println("Pass the test? " + theTest.test("Follow the White Rabbit", 2, 3));

        System.out.println("Pass the test? " + theTest.test("There is no spoon", 101, 3));

        // The trick to writing your own generic functional interfaces is having a good handle
        // on generics. As long as you understand what a functional interface is and how to use
        // generics to specify parameter and return types, you’re good to go.
    }

    @FunctionalInterface
    static interface TriPredicate<T, U, V> {

        boolean test(T t, U u, V v);
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}
