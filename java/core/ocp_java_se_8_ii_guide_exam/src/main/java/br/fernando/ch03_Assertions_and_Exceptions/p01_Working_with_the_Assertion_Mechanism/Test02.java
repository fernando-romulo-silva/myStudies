package br.fernando.ch03_Assertions_and_Exceptions.p01_Working_with_the_Assertion_Mechanism;

// Using Assertions
public class Test02 {

    // =========================================================================================================================================
    // Running with Assertions
    static void test01() {
        // You enable assertions at runtime with a command like this:
        // java -ea com.geeksanonymous.TestClass
        //
        // or
        //
        // java -enableassertions com.geeksanonymous.TestClass

        // Disabling Assertions at Runtime
        // java -da com.geeksanonymous.TestClass
        //
        // or
        //
        // java -disableassertions com.geeksanonymous.TestClass
    }

    // =========================================================================================================================================
    // Selective Enabling and Disabling
    static void test02() {

        // The command-line switches for assertions can be used in various ways:
        //
        // * With no arguments (as in the preceding examples) Enables or disables
        // assertions in all classes, except for the system classes.
        //
        // * With a package name Enables or disables assertions in the package specified
        // and in any packages below this package in the same directory hierarchy (more on that in a moment).
        //
        // * With a class name Enables or disables assertions in the class specified
        //
        // You can combine switches to, say, disable assertions in a single class but keep them
        // enabled for all others as follows. The proceding command line tells the JVM to enable
        // assertions in general, but disable them in the class com.geeksanonymous.Foo
        //
        // java -ea -da:com.geeksanonymous.Foo
        //
        // or package
        //
        // java -ea -da:com.geeksanonymous...
    }

    // =========================================================================================================================================
    // Assertion Command-Line Switche
    static void test03() {
        // enable assertions
        // java -ea
        // java -enableassertions
        //
        // Disable assertions (the default behavior)
        // java -da
        // java -disableassertions
        //
        // Enable assertions in class com.foo.Bar
        // java -ea:com.foo.Bar
        //
        // Enable assertions in package com.foo and nay of its subpackages
        // java -ea:com.foo...
        //
        // Enable assertions in general, but disable assertions in system classes.
        // java -ea -dsa
        //
        // Enable assertions in general, but disable assertions in package com.foo and any of its subpackages
        // java -ea -da:com.foo...
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
