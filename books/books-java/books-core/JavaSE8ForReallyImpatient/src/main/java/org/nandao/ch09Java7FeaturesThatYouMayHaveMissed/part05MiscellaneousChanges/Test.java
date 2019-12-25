package org.nandao.ch09Java7FeaturesThatYouMayHaveMissed.part05MiscellaneousChanges;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Test {

    // Converting Strings to Numbers
    public static void test1() {

        final double x = Double.parseDouble("+1.0");
        final int n = Integer.parseInt("+1");

        // Pat yourself on the back if you knew the answer: +1.0 has always been a valid
        // floating-point number, but until Java 7, +1 was not a valid integer.
        // This has now been fixed for all the various methods that construct int, long, short,
        // byte, and BigInteger values from strings. There are more of them than you may
        // think. In addition to parse(Int|Long|Short|Byte), there are decode methods that work
        // with hexadecimal and octal inputs, and valueOf methods that yield wrapper
        // objects. The BigInteger(String) constructor is also updated.

    }

    // The global Logger
    public static void test2() {
        final double x = Double.MAX_VALUE;
        // In order to encourage the use of logging even in simple cases, the Logger class has
        // a global logger instance. It was meant to be as easy as possible to use, so that you
        // could always add trace statements as Logger.global.finest("x=" + x); instead of

        System.out.println("x=" + x);

        // Unfortunately, that instance variable has to be initialized somewhere, and if
        // other logging happens in the static initialization code, it was possible to cause
        // deadlocks. Therefore, Logger.global was deprecated in Java 6. Instead, you were
        // supposed to call 

        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // which wasn’t anyone’s idea of quick and easy logging.
        // In Java 7, you can call Logger.getGlobal() instead, which isn’t too bad.
    }

    // Null Checks
    public static void test3() {
        // The Objects class has methods requireNonNull for convenient null checks of
        // parameters. Here is the simplest one:
        Object directions = null;
        Objects.requireNonNull(directions);

        // But consider working back from a stack trace. When you see a call to requireNonNull 
        // as the culprit, you know right away what you did wrong.
        // You can also supply a message string for the exception:
        directions = Objects.requireNonNull(directions, "directions must not be null");
    }

    // ProcessBuilder
    public static void test4() throws IOException, InterruptedException {

        // Java 7 adds convenience methods to hook the standard input, output, and error
        // streams of the process to files.
        final ProcessBuilder builder1 = new ProcessBuilder("grep", "-o", "[A-Za-z_][A-Za-z_0-9]*");
        builder1.redirectInput(Paths.get("Hello.java").toFile());
        builder1.redirectOutput(Paths.get("identifiers.txt").toFile());
        final Process process = builder1.start();
        process.waitFor();

        // NOTE: Since Java 8, the Process class has a waitFor method with timeout:
        final boolean completed = process.waitFor(1, TimeUnit.MINUTES);

        // Also new in Java 7 is the inheritIO method of ProcessBuilder. It sets the standard
        // input, output, and error streams of the process to those of the Java program. For
        // example, when you run
        final ProcessBuilder builder2 = new ProcessBuilder("ls", "-al");
        builder2.inheritIO();
        builder2.start().waitFor();

    }

    // URLClassLoader
    public static void test5() throws Exception {

        final URL[] urls = { //
                new URL(" file:junit-4.11.jar"), //
                new URL("file:hamcrest-core-1.3.jar") //
        };

        final URLClassLoader loader1 = new URLClassLoader(urls);
        final Class<?> klass1 = loader1.loadClass("org.junit.runner.JUnitCore");

        // Before Java 7, code such as this could lead to resource leaks. Java 7 simply adds
        // a close method to close the classloader. URLClassLoader now implements AutoCloseable,
        // so you can use a try-with-resources statement:

        try (URLClassLoader loader2 = new URLClassLoader(urls)) {
            final Class<?> klass2 = loader2.loadClass("org.junit.runner.JUnitCore");
        }

    }

    // BitSet
    public static void test6() {

        // A BitSet is a set of integers that is implemented as a sequence of bits. The ith bit
        // is set if the set contains the integer i. That makes for very efficient set operations.
        // Union/intersection/complement are simple bitwise or/and/not.
        //
        // Java 7 adds methods to construct bitsets.
        final byte[] bytes1 = { (byte) 0b10101100, (byte) 0b00101000 };
        final BitSet primes = BitSet.valueOf(bytes1);
        // {2, 3, 5, 7, 11, 13}
        final long[] longs = { 0x100010116L, 0x1L, 0x1L, 0L, 0x1L };
        final BitSet powersOfTwo = BitSet.valueOf(longs);
        // {1, 2, 4, 8, 16, 32, 64, 128, 256}

        // The inverse methods are toByteArray and toLongArray.
        final byte[] bytes2 = powersOfTwo.toByteArray();
        // [0b00010110, 1, 1, 0, 1, 0, 0, 0, 1, ...] 
    }

    public static void main(final String[] args) throws Exception {

    }

}
