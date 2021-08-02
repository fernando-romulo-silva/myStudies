package org.nandao.cap11.p06HandlingLocalesAndTheLocale_BuilderClassInJava7;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Locale.Builder;

// The java.util.Locale.Builder class has been added to Java 7 and provides an easy
// way of creating a locale. The Locale.Category enumeration is also new and makes using
// different locales for display and formatting purposes easy. We will first look at the use of the
// Locale.Builder class and then examine other locale improvements and the use of the
// Locale.Category enumeration in the There's more... section.

public class Test {

    public static void main(String[] args) {
        test02();
    }

    public static void test01() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setWeekDate(2012, 16, 3);

        final Builder builder = new Builder();
        builder.setLanguage("hy");
        builder.setScript("Latn");
        builder.setRegion("IT");
        builder.setVariant("arevela");

        Locale locale = builder.build();
        Locale.setDefault(locale);

        System.out.println(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(calendar.getTime()));
        System.out.println("" + locale.getDisplayLanguage());

        System.out.println("");
        System.out.println("");

        builder.setLanguage("zh");
        builder.setScript("Hans");
        builder.setRegion("CN");

        locale = builder.build();
        Locale.setDefault(locale);

        System.out.println(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(calendar.getTime()));
        System.out.println("" + locale.getDisplayLanguage());
    }

    // Using the Locale.Category enumeration to display information using two different locales
    public static void test02() {
        Locale locale = Locale.getDefault();
        Calendar calendar = Calendar.getInstance();
        calendar.setWeekDate(2012, 16, 3);

        System.out.println(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(calendar.getTime()));
        System.out.println(locale.getDisplayLanguage());

        Locale.setDefault(Locale.Category.FORMAT, Locale.JAPANESE);
        Locale.setDefault(Locale.Category.DISPLAY, Locale.GERMAN);
        
        System.out.println(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(calendar.getTime()));
        System.out.println(locale.getDisplayLanguage());
    }
}
