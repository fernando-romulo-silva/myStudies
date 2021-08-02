package org.nandao.ch08MiscellaneousGoodies.part01Strings;

import java.time.ZoneId;

// A common task is to combine several strings, separating them with a delimiter
// such as ", " or "/". This has now been added to Java 8. The strings can come from
// an array or an Iterable<? extends CharSequence>:

public class Test {

    public static void main(final String[] args) {
        final String joined = String.join("/", "usr", "local", "bin"); // "usr/local/bin"
        System.out.println(joined);

        //
        final String ids = String.join(", ", ZoneId.getAvailableZoneIds());
        System.out.println(ids);
        // All time zone identifiers, separated by commas

        // NOTE: As already mentioned in Chapter 2, the CharSequence interface provides
        // a useful instance method codePoints that returns a stream of Unicode values,
        // and a less useful method chars that returns a stream of UTF-16 code units.
    }
}
