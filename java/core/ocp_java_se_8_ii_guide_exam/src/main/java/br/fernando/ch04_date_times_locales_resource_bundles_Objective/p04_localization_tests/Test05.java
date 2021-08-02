package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p04_localization_tests;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // This is a Drag and Drop type question. Please click on 'Show DnD Screen' to see the question.
        Date d = Calendar.getInstance(Locale.FRANCE).getTime();

        DateFormat mydf = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

        System.out.println(mydf.format(d));
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // What will the following code print
        SimpleDateFormat sdf = new SimpleDateFormat("zzzz");
        System.out.println(sdf.format(new Date())); // Horário de Verão de Brasília

        // Full text time zone name.
        // For example, Eastern Standard Time (If the Locale is US, UK, or any other English based Locale)
        // Heure normale de l'Est (If the Locale is France)

        // Letter---------Date or Time Component------Presentation---------------Examples
        // G--------------Era designator--------------Text-----------------------AD

        // y--------------Year------------------------Year-----------------------1996; 96
        // Y--------------Week year-------------------Year-----------------------2009; 09

        // M--------------Month in year---------------Number---------------------July; Jul; 07

        // w--------------Week in year----------------Number---------------------27
        // W--------------Week in month---------------Number---------------------2
        // D--------------Day in year-----------------Number---------------------189
        // d--------------Day in Month----------------Number---------------------10
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given:
        Locale locale = new Locale("en", "US");
        ResourceBundle rb = ResourceBundle.getBundle("test.MyBundle", locale);

        // Which of the following are valid lines of code? (Assume that the ResourceBundle has the values for the given keys.)

        Object obj = rb.getObject("key1");

        String[] vals = rb.getStringArray("key2");

        // Keys are always Strings. So you cannot use an int to get value for a key.
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
        // Consider the following code:
        Locale myLoc = new Locale("fr", "FR");
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", myLoc);

        // Which of the following lines of code will assign a ResourceBundle for a different Locale to rb than the one currently assigned?

        rb = ResourceBundle.getBundle("appmessages", new Locale("ch", "CH"));

        // and

        rb = ResourceBundle.getBundle("appmessages", Locale.CHINA);

        // Explanation
        // Note that once a ResourceBundle is retrieved, changing the Locale will not affect the ResourceBundle. 
        // You have to retrieve a new ResourceBundle by passing in the new Locale and then assign it to the variable.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
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
    static void test01_08() throws Exception {
        // You are developing an application that will be run in many countries. You are trying to provide as many language specific resource
        // bundles as you can but you can't do that for all the countries. What is the best strategy to mitigate this issue?
        //
        // Create a default resource bundle by removing any locale specific appenders from the file name (for example, appmessages.properties).
        // If a resource bundle for a particular region is not found, the default will be used and even though messages will not be in the
        // right language, there won't be any error.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // A Locale object represents...

        // a specific geographical, political, or cultural region.
        //
        // Explanation
        //
        // A Locale object just represents a region. Based on a given Locale, you can do various things such as display text in that 
        // region's language and/or style.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Consider the following code.

        Date d = new Date();
        DateFormat df = null; // 1 INSERT CODE HERE
        String s = null; // 2 INSERT CODE HERE
        System.out.println(s);

        // What should be inserted at //1 and //2 above so that it will print the date in default date format for the UK Locale?

        df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.UK);
        // and
        df.format(d);

        // Explanation
        // There are several getXXXInstance() methods in DateFormat class. For the purpose of the exam, you should remember the following:
        //
        // static DateFormat getDateInstance()
        // Gets the date formatter with the default formatting style for the default locale.
        //
        // static DateFormat getDateInstance(int style)
        // Gets the date formatter with the given formatting style for the default locale.
        //
        // static DateFormat getDateInstance(int style, Locale aLocale)
        // Gets the date formatter with the given formatting style for the given locale.
        //
        // The formatting styles include DEFAULT, FULL, LONG, MEDIUM, and SHORT.
        //
        // Errors
        //
        // The getInstance() method doesn't take any parameter. static getInstance() - Get a default date/time formatter that uses the 
        // SHORT style for both the date and the time.
        DateFormat.getInstance();
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_03();
    }
}
