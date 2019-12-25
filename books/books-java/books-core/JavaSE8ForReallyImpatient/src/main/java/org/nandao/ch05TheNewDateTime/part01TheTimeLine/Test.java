package org.nandao.ch05TheNewDateTime.part01TheTimeLine;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

// That gives Java the flexibility to adjust to future changes in the official time.
// In Java, an Instant represents a point on the time line. An origin, called the epoch,
// is arbitrarily set at midnight of January 1, 1970 at the prime meridian that passes
// through the Greenwich Royal Observatory in London. This is the same convention
// used in the Unix/POSIX time
public class Test {

    public static void main(final String[] args) throws Exception {
        final Instant start = Instant.now();

        // runAlgorithm();

        TimeUnit.SECONDS.sleep(1);

        final Instant end = Instant.now();

        // A Duration is the amount of time between two instants. You can get the length of
        // a Duration in conventional units by calling toNanos, toMillis, toSeconds, toMinutes, toHours,  or toDays.        
        final Duration timeElapsed = Duration.between(start, end);
        //
        final long millis = timeElapsed.toMillis();
        //
        System.out.println("Test: " + millis);
        //
        final Duration timeElapsed2 = Duration.between(start, end);
        final boolean overTenTimesFaster = timeElapsed.multipliedBy(10).minus(timeElapsed2).isNegative();
        // Or timeElapsed.toNanos() * 10 < timeElapsed2.toNanos()
        System.out.println("Test: " + overTenTimesFaster);
    }
}
