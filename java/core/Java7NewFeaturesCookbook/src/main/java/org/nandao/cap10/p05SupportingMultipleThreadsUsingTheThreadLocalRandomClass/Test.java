package org.nandao.cap10.p05SupportingMultipleThreadsUsingTheThreadLocalRandomClass;

import java.util.concurrent.ThreadLocalRandom;

// The java.util.concurrent package has a new class, ThreadLocalRandom, which
// supports functionality similar to the Random class. However, the use of this new class, with
// multiple threads, will result in less contention and better performance as compared to
// their use with the Random class. When multiple threads need to use random numbers, the
// ThreadLocalRandom class should be used. The random number generator is local to the
// current thread. This recipe examines how to use this class.
public class Test {

    public static void main(String[] args) {

        System.out.println("Five random integers");
        
        for (int i = 0; i < 5; i++) {
            System.out.println(ThreadLocalRandom.current().nextInt());
        }

        System.out.println();
        System.out.println("Random double number between 0.0 and 35.0");
        System.out.println(ThreadLocalRandom.current().nextDouble(35.0));
        System.out.println();
        System.out.println("Five random Long numbers between 1234567 and 7654321");

        for (int i = 0; i < 5; i++) {
            System.out.println(ThreadLocalRandom.current().nextLong(1234567L, 7654321L));
        }
    }
}
