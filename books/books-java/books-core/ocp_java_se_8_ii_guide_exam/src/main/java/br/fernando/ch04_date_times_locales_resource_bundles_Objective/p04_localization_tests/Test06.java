package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p04_localization_tests;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Consider the following code:
        double amount = 53000.35;
        Locale jp = new Locale("jp", "JP");

        // How will you create formatter using a factory at //1 so that the output is in Japanese Currency format?
        // 1 create formatter here.

        Format formatter = NumberFormat.getCurrencyInstance(jp);
        // This is valid because java.text.NumberFormat extends from java.text.Format.
        // The return type of the method getCurrencyInstance() is NumberFormat.
        //
        // or
        //
        // getCurrencyInstance is actually defined in NumberFormat. However, since DecimalFormat extends NumberFormat, this is valid.
        //
        // To format a number in currency format, you should use getCurrencyInstance() instead of getInstance() or getNumberInstance().
        formatter = DecimalFormat.getCurrencyInstance(jp);

        System.out.println(formatter.format(amount));
        //
        //
        // Wrongs
        // NumberFormat formatter =  NumberFormat.getInstance(jp);
        // getInstance(Locale ) is a valid factory method in NumberFormat class but it will not not format the given number as per the currency.
        //
        // NumberFormat formatter =  new DecimalFormat("#.00");
        // While it is a valid way to create a DecimalFormat object, it is not valid for two reasons:
        // 1. We need a currency formatter and not just a simple numeric formatter.
        // 2. This is not using a factory to create the formatter object
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You are developing an application that will be run in many countries. You are trying to provide as many language specific resource
        // bundles as you can but you can't do that for all the countries. What is the best strategy to mitigate this issue?
        //
        // Create a default resource bundle by removing any locale specific appenders from the file name (for example, appmessages.properties).
        // If a resource bundle for a particular region is not found, the default will be used and even though messages will not be in the
        // right language, there won't be any error.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Identify valid statements.

        // 1º Option
        Locale myLocale01 = Locale.getDefault();
        //
        // 2º Option
        Locale myLocale02 = Locale.US;
        // Locale class has several static constants for standard country locales.
        //
        // 3º Option
        Locale myLocale03 = new Locale("ru", "RU");
        // You don't have to worry about the actual values of the language and country codes. Just remember that both are two lettered codes
        // and country codes are always upper case.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Which import statements are required to compile the following code?

        Date d = new Date();
        DateFormat df = DateFormat.getInstance();

        // import java.util.*;
        //
        // import java.text.*;
        //
        // Explanation
        // Date class is in java.util package. (There is one in java.sql package as well but you need not worry about it for the exam.) 
        // DateFormat (and other Formatters as well) is in java.text package.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // How will you initialize a SimpleDateFormat object so that the following code will print the number of the month
        // (i.e. 02 for Feb, 12 for Dec, and so on) and a two digit calendar year of any given date?

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy", Locale.US);
        // Upper case M is for Month and lower case y is for Calendar Year.

        System.out.println(sdf.format(new Date()));

        // Explanation
        // For the purpose of the exam, you need to know the basic codes for printing out a date. The important ones are m, M, d, D, y, s, S, h, H, and z.
        // The following table shows all the codes (Please check http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html for details)
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Which of the following are required to construct a Locale?

        // language
        new Locale("fr"); // language is French
        new Locale("fr", "FR"); // language is French, Country is France.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Consider the following piece of code, which is run in an environment where the default locale is English - US:

        Locale.setDefault(new Locale("fr", "CA")); // Set default to French Canada
        Locale l = new Locale("jp", "JP");
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", l);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // You have created two resource bundles for appmessages, with the following contents:
        //
        // #In English US resource bundle file
        // greetings=Hello
        //
        // #In French CA resource bundle file
        // greetings=bonjour
        //
        // What will be the output?
        //
        // bonjour
        //
        // Explanation
        //
        // While retrieving a message bundle, you are passing a locale explicitly (jp JP). Therefore, it will first try to load 
        // appmessages_jp_JP.properties. Since this file is not present, it will look for a resource bundle for default locale. 
        // Since you are changing the default locale to "fr", "CA", it will look for appmessages_fr_CA.properties, which is present. 
        // This file contains "bonjour" for "greetings", which is what is printed.
        //
        // Observe that when a resource bundle is not found for a given locale, the default locale is used to load the resource bundle.  
        // Every effort is made to load a resource bundle if one is not found and there are several fall back options. As a last resort, 
        // it will try to load a resource bundle with no locale information i.e. appmessages.properties in this case. 
        // An exception is thrown when even this resource bundle is not found. 
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code print when run?

        double amount = 123456.789;
        Locale fr = new Locale("fr", "FR");
        NumberFormat formatter = NumberFormat.getInstance(fr);
        String s = formatter.format(amount);
        formatter = NumberFormat.getInstance();

        // Number amount2 = formatter.parse(s); // parse method throw a exception!

        // System.out.println(amount + " " + amount2);

        // It will not compile.

        // Remember that parse() method of DateFormat and NumberFormat throws java.text.ParseException. So it must either be declared in
        // the throws clause of the main() method or the call to parse() must be wrapped in a try/catch block.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given:
        Locale locale = new Locale("en", "US");
        ResourceBundle rb = ResourceBundle.getBundle("test.MyBundle", locale);

        // Which of the following are valid lines of code? (Assume that the ResourceBundle has the values for the given keys.)

        Object obj = rb.getObject("key1");

        String[] vals = rb.getStringArray("key2");

        // Keys are always Strings. So you cannot use an int to get value for a key.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Consider the following piece of code, which is run in an environment where the default locale is English - US:

        Locale.setDefault(new Locale("fr", "CA")); // Set default to French Canada
        Locale l = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", l);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // You have created two resource bundles for appmessages, with the following contents:
        //
        // #In English US resource bundle file
        // greetings=Hello
        //
        // #In French CA resource bundle file
        // greetings=bonjour

        // What will be the output?
        //
        // bonjour
        //
        // Explanation
        // While retrieving a message bundle, you are passing a locale explicitly (fr CA). Therefore, appmessages_fr_CA.properties will be loaded.
        // This file contains "bonjour" for "greetings", which is what is printed.
        //
        // Observe that the setting of default locale makes no difference in this case because the new default locale and the locale that you are
        // passing are same and a resource bundle is available for that locale.
        //
        // 
        // However, if you do ResourceBundle.getBundle("appmessages", new Locale("es", "ES")); and if there is no appmessages_es_ES.properties 
        // file, the role of default locale becomes crucial. In this case, appmessages_fr_CA.properties will be loaded instead of 
        // appmessages_en_US.properties because you've changed the default locale and when a resource bundle is not found for a given locale, 
        // the default locale is used to load the resource bundle.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
