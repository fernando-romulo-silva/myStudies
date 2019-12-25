package br.fernando.ch03_Assertions_and_Exceptions.p01_Working_with_the_Assertion_Mechanism;

public class Test03 {

    // =========================================================================================================================================
    // Using Assertions Appropriately
    static void test01(int x) {
        int y = 1;

        // Don’t Use Assertions to Validate Arguments to a public Method
        assert (x > 0); // inappropriate
        // If you need to validate public method arguments, you’ll probably use exceptions to
        // throw, say, an IllegalArgumentException if the values passed to the public method
        // are invalid.
        //
        // Do Use Assertions to Validate Arguments to a private Method
        // So, do enforce constraints on private methods’ arguments, but do not enforce constraints
        // on public methods. You’re certainly free to compile assertion code with an inappropriate
        // validation of public arguments, but for the exam (and real life), you need to know that you shouldn’t do it.
        //
        // Don’t Use Assertions to Validate Command-Line Arguments
        // This is really just a special case of the “Do not use assertions to validate arguments to
        // a public method” rule. If your program requires command-line arguments, you’ll
        // probably use the exception mechanism to enforce them.
        //
        // Do Use Assertions, Even in public Methods, to Check for Cases That You Know Are Never, Ever Supposed to Happen
        // This can include code blocks that should never be reached, including the default of a
        // switch statement as follows:
        switch (x) {
        case 1:
            y = 3;
            break;
        case 2:
            y = 9;
            break;

        default:
            assert false; // we're never supporsed to get here!
        }

        // Don’t Use assert Expressions That Can Cause Side Effects!
        // The rule is that an assert expression should leave the program in the same state it
        // was in before the expression! Think about it. assert expressions aren’t guaranteed to
        // always run, so you don’t want your code to behave differently depending on whether assertions are enabled
        //
        assert (modifyThings());
    }

    static boolean modifyThings() {
        return true;
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01(0);
    }
}
