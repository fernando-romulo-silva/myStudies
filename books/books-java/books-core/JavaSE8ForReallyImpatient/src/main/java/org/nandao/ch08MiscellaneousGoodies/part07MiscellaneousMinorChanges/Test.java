package org.nandao.ch08MiscellaneousGoodies.part07MiscellaneousMinorChanges;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    // Null Checks
    public static void test1() {
        final String contents = "This a example test to use stream API";
        final List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        final Stream<String> stream = words.stream();

        // The Objects class has static predicate methods isNull and nonNull that can be useful
        // for streams. For example,
        stream.anyMatch(Objects::isNull);

        stream.anyMatch(x -> x == null);

        // checks whether a stream contains a null, and
        stream.filter(Objects::nonNull);

        stream.filter(x -> x != null);
        // gets rid of all of them.

    }

    // Lazy Messages
    public static void test2() {

        final Logger logger = Logger.getGlobal();

        final int x = 10;

        final int y = 11;

        // For example, consider the call

        logger.finest("x: " + x + ", y:" + y);

        // The message string is formatted even when the logging level is such that it would
        // never be used. Instead, use
        logger.finest(() -> "x: " + x + ", y:" + y);

        // Now the lambda expression is only evaluated at the FINEST logging level, when
        // the cost of the additional lambda invocation is presumably the least of one’s
        // problems.

        List<String> directions = null;

        directions = Objects.requireNonNull(directions, () -> "directions must not be null");

        // In the common case that directions is not null, this.directions is simply set to
        // directions. If directions is null, the lambda is invoked, and a NullPointerException is
        // thrown whose message is the returned string
    }

    // Regular Expressions
    public static void test3() {
        // Java 7 introduced named capturing groups. For example, a valid regular expression is
        //
        // (?<city>[\p{L} ]+),\s*(?<state>[A-Z]{2})
        //
        // In Java 8, you can use the names in the start, end, and group methods of Matcher:
        final Pattern pattern = Pattern.compile("(?<city>[\\p{L} ]+),\\s*(?<state>[A-Z]{2})");

        final Matcher matcher = pattern.matcher("city New York, state NY");
        if (matcher.matches()) {
            final String city = matcher.group("city");

        }
        // The Pattern class has a splitAsStream method that splits a CharSequence along a regular expression:
        final String contents = "This a example test to use stream API";
        final Stream<String> words = Pattern.compile("[\\P{L}]+").splitAsStream(contents);

        // All nonletter sequences are word separators.
        // The method asPredicate can be used to filter strings that match a regular
        // expression:
        final Stream<String> acronyms = words.filter(Pattern.compile("[A-Z]{2,}").asPredicate());

    }

    // Locales
    public static void test4() {

        // A locale specifies everything you need to know to present information to a user
        // with local preferences concerning language, date formats, and so on. For example,
        // an American user prefers to see a date formatted as December 24, 2013 or
        // 12/24/2013, whereas a German user expects 24. Dezember 2013 or 24.12.2013.

        // You can still construct a locale the old-fashioned way, such as
        new Locale("en", "US");
        // but since Java 7 you can simply call
        Locale.forLanguageTag("en-US");
        // Java 8 adds methods for finding locales that match user needs.

        // Given a list of weighted language ranges and a collection of locales, the filter
        // method produces a list of matching locales, in descending order of match quality:

        final List<Locale.LanguageRange> ranges = Stream.of("de", "*-CH") //
            .map(Locale.LanguageRange::new).collect(Collectors.toList());

        // A list containing the Locale.LanguageRange objects for the given strings
        final List<Locale> matches = Locale.filter(ranges, Arrays.asList(Locale.getAvailableLocales()));
        // The matching locales: de, de-CH, de-AT, de-LU, de-DE, de-GR, fr-CH, it_CH
        
        // The static lookup method just finds the best locale:
        final Locale bestMatch = Locale.lookup(ranges, matches);
    }
    
    // JDBC
    public static void test5() {
        // The Date, Time, and Timestamp classes in the java.sql package have methods to convert
        // from and to their java.time analogs LocalDate, LocalTime, and LocalDateTime.
        //
        // The Statement class has a method executeLargeUpdate for executing an update whose
        // row count exceeds Integer.MAX_VALUE.
        //
        // JDBC 4.1 (which was a part of Java 7) specified a generic method getObject(column,
        // type) for Statement and ResultSet, where type is a Class instance.
        //
        // For example, URL url = result.getObject("link", URL.class) retrieves a DATALINK as a URL. Now the
        // corresponding setObject method is provided as well.
    }

    public static void main(String[] args) {
        test3();

    }

}
