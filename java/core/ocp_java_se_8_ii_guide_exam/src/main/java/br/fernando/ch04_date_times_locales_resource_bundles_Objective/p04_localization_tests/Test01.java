package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p04_localization_tests;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test01 {

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
        // Drag and Drop

        Date d = Calendar.getInstance(Locale.FRANCE).getTime();

        DateFormat mydf = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

        System.out.println(mydf.format(d));

    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Identify the correct statements regarding DateFormat and NumberFormat classes.

        // The following line of code will work on a machine in any locale :
        double x = 12345.123;
        String str = NumberFormat.getInstance().format(x);

        // If you don't pass a Locale in getInstance() methods of NumberFormat and DateFormat, they are set to default Locale of the machine.
        // If you run this code on a French machine, it will format the number in French format ( 12 345,123 ) and if you run it on a US machine,
        // it is format the number in US format ( 12,345.123 ).
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // A Locale object represents...

        // a specific geographical, political, or cultural region.

        // Explanation
        // A Locale object just represents a region. Based on a given Locale, you can do various things such as display text in that region's
        // language and/or style. Every Java runtime has a default Locale, which represents the region set by the operating system,
        // but it can be changed.

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
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
        // java.util.Locale allows you to do which of the following?

        // 1º Provide country and language specific formatting for Dates.

        // 2º Provide country specific formatting for Currencies.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Consider the following code that is a part of a big application:

        ResourceBundle rb = ResourceBundle.getBundle("appmessages", Locale.US);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // This application is developed by multiple teams in multiple locations and runs in many regions but you want the above code to always
        // print the message in US English. What should be the name of the resource bundle file that contains the actual English text to be displayed
        // for "greetings" and where should that file be placed?
        //
        // appmessages_en_US.properties in CLASSPATH.
        // A resource bundle file should always be present somewhere in the CLASSPATH.
        //
        // Explanation
        // The a program to load a resource bundle, the resource bundle properties file must be in the CLASSPATH.
        //
        // The JVM attempts to find a "resource" with this name using ClassLoader.getResource.
        // (Note that a "resource" in the sense of getResource has nothing to do with the contents of a resource bundle, it is just a container of data,
        // such as a file.)
        // If it finds a "resource", it attempts to create a new PropertyResourceBundle instance from its contents.
        // If successful, this instance becomes the result resource bundle.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
        // Observe that when a resource bundle is not found for a given locale, the default locale is used to load the resource bundle.  
        // Every effort is made to load a resource bundle if one is not found and there are several fall back options. 
        // As a last resort, it will try to load a resource bundle with no locale information i.e. appmessages.properties in this case. 
        // An exception is thrown when even this resource
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_10();
    }
}
