package org.nandao.cap11.p01Introduction;

import java.util.logging.Logger;

public class Test {

    public static void main(String[] args) {
        test01();
    }

    // Unicode 6.0
    // Unicode 6.0 is the newest revision of the Unicode standard. Java 7 supports this release with
    // the addition of thousands of more characters and numerous new methods. In addition, regular
    // expression pattern matching supports Unicode 6.0 using either \\u or \x escape sequences.
    // Numerous new character blocks were added to the Character.UnicodeBlock class. The
    // Character.UnicodeScript enumeration was added in Java 7 to represent the character
    // scripts defined in the Unicode Standard Annex #24: Script Names.
    protected static void test01() {
        int codePoint = Character.codePointAt("朝鲜圆", 0);
        System.out.println("isBmpCodePoint: " + Character.isBmpCodePoint(codePoint));
        System.out.println("isSurrogate: " + Character.isSurrogate('朝'));
        System.out.println("highSurrogate: " + (int) Character.highSurrogate(codePoint));
        System.out.println("lowSurrogate: " + (int) Character.lowSurrogate(codePoint));
        System.out.println("isAlphabetic: " + Character.isAlphabetic(codePoint));
        System.out.println("isIdeographic: " + Character.isIdeographic(codePoint));
        System.out.println("getName: " + Character.getName(codePoint));
    }

    // Primitive types and the compare method
    // Java 7 introduced new static methods for comparing primitive data types Boolean, byte,
    // long, short, and int. Each wrapper class now has a compare method, which takes two
    // instances of the data type as arguments and returns an integer representing the result of the
    // comparison.
    protected static void test02() {

        boolean x = false;
        boolean y = false;

        // before
        Boolean.valueOf(x).compareTo(Boolean.valueOf(y));

        // Java 7 
        Boolean.compare(x, y);
    }

    // Global logger
    // The java.util.logging.Logger class has a new method, getGlobal, used for
    // retrieving the global logger object named GLOBAL_LOGGER_NAME. The static field global of
    // the Logger class is prone to deadlocks when the Logger class is used in conjunction with
    // the LogManager class, as both classes will wait on each other to complete initialization. The
    // getGlobal method is the preferred way to access the global logger object, in order to
    // prevent such deadlock.
    protected static void test03() {
        java.util.logging.Logger logger = Logger.getGlobal();
    }
}
