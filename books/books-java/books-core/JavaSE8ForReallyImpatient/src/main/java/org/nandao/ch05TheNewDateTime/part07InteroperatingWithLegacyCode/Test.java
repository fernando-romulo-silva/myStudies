package org.nandao.ch05TheNewDateTime.part07InteroperatingWithLegacyCode;

import java.time.Instant;
import java.util.Date;

// The Instant class is a close analog to java.util.Date. In Java 8, that class has two
// added methods: the toInstant method that converts a Date to an Instant, and the
// static from method that converts in the other direction.
// Similarly, ZonedDateTime is a close analog to java.util.GregorianCalendar, and that class
// has gained conversion methods in Java 8. The toZonedDateTime method converts a
// GregorianCalendar to a ZonedDateTime, and the static from method does the opposite conversion.

public class Test {

    public static void main(final String[] args) {
     
        // Ex:
        final Instant apollo11launch = Instant.now();
        
        Date.from(apollo11launch);

        new Date().toInstant();
        
    }
}
