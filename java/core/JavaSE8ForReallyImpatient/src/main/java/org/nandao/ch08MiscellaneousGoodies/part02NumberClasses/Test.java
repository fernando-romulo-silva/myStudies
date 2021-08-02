package org.nandao.ch08MiscellaneousGoodies.part02NumberClasses;

import java.math.BigInteger;

public class Test {

    // An unsigned integer maps the values usually associated with negative numbers to positive numbers larger than MAX_VALUE

    //
    // Ever since Java 5, each of the seven numeric primitive type wrappers (i.e., not
    // Boolean) had a static SIZE field that gives the size of the type in bits. You will be
    // glad to know that there is now a BYTES field that reports the size in bytes, for those
    // who cannot divide by eight.
    // All eight primitive type wrappers now have static hashCode methods that return
    // the same hash code as the instance method, but without the need for boxing.
    // The five types Short, Integer, Long, Float, and Double now have static methods sum, max,
    // and min, which can be useful as reduction functions in stream operations. The
    // Boolean class has static methods logicalAnd, logicalOr, and logicalXor for the same purpose.
    // Integer types now support unsigned arithmetic. For example, instead of having
    // a Byte represent the range from –128 to 127, you can call the static method
    // Byte.toUnsignedInt(b) and get a value between 0 and 255. In general, with unsigned
    // numbers, you lose the negative values and get twice the range of positive values.
    // The Byte and Short classes have methods toUnsignedInt, and Byte, Short, and Integer
    //have methods toUnsignedLong.

    public static void main(final String[] args) {
        final int min = Integer.MIN_VALUE; // -2147483648
        final int max = Integer.MAX_VALUE; // 2147483647
        final int overByOne = Integer.MAX_VALUE + 1; // -2147483648 : same as Integer.MIN_VALUE
        final int underByOne = Integer.MIN_VALUE - 1; // 2147483647 : same as Integer.MAX_VALUE

        System.out.println("Over unsigned to Long:" + Integer.toUnsignedLong(overByOne));

        Integer.compareUnsigned(overByOne, Integer.MAX_VALUE + 1);

        System.out.println(Float.isFinite(Float.MAX_VALUE + 2));

        // The BigInteger class has instance methods (long|int|short|byte)ValueExact
        // that return the value as a long, int, short, or byte, throwing an ArithmeticException
        // if the value is not within the target range.

        final BigInteger bigInteger = BigInteger.TEN;

        System.out.println("BigInteger: " + bigInteger.byteValueExact());
    }

}
