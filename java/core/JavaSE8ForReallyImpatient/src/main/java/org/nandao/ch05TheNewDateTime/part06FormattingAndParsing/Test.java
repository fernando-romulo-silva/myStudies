package org.nandao.ch05TheNewDateTime.part06FormattingAndParsing;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

// The DateTimeFormatter class provides three kinds of formatters to print a date/time value:
// • Predefined standard formatters (see Table 5–6)
// • Locale-specific formatters
// • Formatters with custom patterns
public class Test {

    public static void main(final String[] args) {

        final ZonedDateTime apollo11launch = ZonedDateTime.of(1969, 7, 16, 9, 32, 0, 0, ZoneId.of("America/New_York"));

        final String formatted1 = DateTimeFormatter.ISO_DATE_TIME.format(apollo11launch);
        // 1969-07-16T09:32:00-05:00[America/New_York]
        System.out.println("Default formatter: " + formatted1);

        final DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);

        final String formatted2 = formatter1.format(apollo11launch);
        System.out.println("Default locale formatter: " + formatted2);

        final String formatted3 = formatter1.withLocale(Locale.FRENCH).format(apollo11launch);
        System.out.println("With French locale formatter: " + formatted3);

        // NOTE: The java.time.format.DateTimeFormatter class is intended as a replacement
        // for java.util.DateFormat. If you need an instance of the latter for
        // backwards compatibility, call formatter.toFormat().

        final String formatted4 = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm").format(apollo11launch);
        System.out.println("With French locale formatter: " + formatted4);

        // To parse a date/time value from a string, use one of the static parse methods. For example,

        final LocalDate churchsBirthday = LocalDate.parse("1903-06-14");
        System.out.println("churchsBirthday: " + churchsBirthday);
        
        final ZonedDateTime apollo11launch2 = ZonedDateTime.parse("1969-07-16 03:32:00-0400", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxx"));
        System.out.println("With French locale formatter: " + apollo11launch2);
    }

}
