package org.nandao.cap11.p08UsingTheNewBitSetMethodsInJava7;

import java.util.BitSet;

// The java.util.BitSet class gained several new methods with the latest release of Java.
// These are designed to simplify the manipulation of large sets of bits and provide easier
// access to information about bit location. Bit sets can be used for priority queues
// or compressed data structures. This recipe demonstrates some of the new methods
public class Test {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        long[] array = { 1, 21, 3 };

        bitSet = BitSet.valueOf(array);

        System.out.println(bitSet);

        long[] tmp = bitSet.toLongArray();
        for (long number : tmp) {
            System.out.println(number);
        }

        System.out.println(bitSet.previousSetBit(1));
        
        System.out.println(bitSet.previousClearBit(66));
    }

}
