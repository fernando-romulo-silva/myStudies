package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p04_localization_tests;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Consider the following piece of code:
        Locale.setDefault(new Locale("fr", "CA")); // Set default to French Canada
        Locale l = new Locale("jp", "JP");
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", l);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // You have created two resource bundle files with the following contents:

        // #In appmessages.properties:
        // greetings=Hello

        // #In appmessages_fr_FR.properties:
        // greetings=bonjour

        // Given that this code is run on machines all over the world. Which of the following statements are correct?

        // It will run without any exception all over the world.

        // Explanation
        // While retrieving a message bundle, you are passing a locale explicitly (jp/JP). Therefore, it will first try to load
        // appmessages_jp_JP.properties.
        //
        // Since this file is not present, it will look for a resource bundle for default locale. Since you are changing the default locale to
        // "fr", "CA", it will look for appmessages_fr_CA.properties, which is also not present.
        //
        // Remember that when a resource bundle is not found for a given locale, the default locale is used to load the resource bundle.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // A Locale object represents...

        // a specific geographical, political, or cultural region.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // java.util.Locale allows you to do which of the following?
        //
        // Provide country and language specific formatting for Dates.
        //
        // Provide country specific formatting for Currencies.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Consider the following code that is a part of a big application:
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", Locale.US);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // This application is developed by multiple teams in multiple locations and runs in many regions but you want the above code to always
        // print the message in US English. What should be the name of the resource bundle file that contains the actual English text to be
        // displayed for "greetings" and where should that file be placed?
        //
        // appmessages_en_US.properties in CLASSPATH.
        // A resource bundle file should always be present somewhere in the CLASSPATH.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // You are developing an application that will be run in many countries. You are trying to provide as many language specific resource
        // bundles as you can but you can't do that for all the countries. What is the best strategy to mitigate this issue?
        //
        // Create a default resource bundle by removing any locale specific appenders from the file name (for example, appmessages.properties).
        // If a resource bundle for a particular region is not found, the default will be used and even though messages will not be in the
        // right language, there won't be any error.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code fragment print when compiled and run?
        Locale myloc = new Locale.Builder().setLanguage("en").setRegion("UK").build(); // L1
        ResourceBundle msgs = ResourceBundle.getBundle("mymsgs", myloc);

        Enumeration<String> en = msgs.getKeys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String val = msgs.getString(key);
            System.out.println(key + " : " + val);
        }

        // Assume that only the following two properties files (contents of the file is shown below the name of the file)
        // are accessible to the code.
        //
        // 1. mymsgs.properties
        // okLabel=OK
        // cancelLabel=Cancel
        //
        // 2. mymsgs_en_UK.properties
        //
        // okLabel=YES
        // noLabel=NO

        // Result:
        //
        // noLabel : NO
        // okLabel : YES
        // cancelLabel : Cancel
        //
        // mymsgs.properties is the base file for this resource bundle. Therefore, it will be loaded first. Since the language and region specific
        // file is also present (_en_UK), it will also be loaded and the values in this file will be superimposed on the values of the base file.
        //
        // Remember that if there were another properties file named mymsgs_en.properties also present, then that file would have been
        // loaded before mymsgs_en_UK.properties
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which of the following texts can occur in a valid resource bundle file?

        // greetings=bonjour
        //
        // greetings1=bonjour
        // greetings2=no bonjour
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // This is a Drag and Drop type question. Please click on 'Show DnD Screen' to see the question.
        Date d = Calendar.getInstance(Locale.FRANCE).getTime();

        DateFormat mydf = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

        System.out.println(mydf.format(d));
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
        // How will you initialize a SimpleDateFormat object so that the following code will print the full text time zone of the given date?

        SimpleDateFormat sdf = new SimpleDateFormat("zzzz", Locale.FRANCE);
        // Remember that if the number of pattern letters is 4 or more, the full form is used; otherwise a short or abbreviated form 
        // is used if available. For parsing, both forms are accepted, independent of the number of pattern letters.

        System.out.println(sdf.format(new Date()));
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Which of the following are required to construct a Locale?

        // language
        new Locale("fr"); // language is French
        new Locale("fr", "FR"); // language is French, Country is France.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Identify the correct statements regarding DateFormat and NumberFormat classes.
        // The following line of code will work on a machine in any locale :
        double x = 12345.123;
        String str = NumberFormat.getInstance().format(x);

        // If you don't pass a Locale in getInstance() methods of NumberFormat and DateFormat, they are set to default Locale of the machine.
        // If you run this code on a French machine, it will format the number in French format ( 12 345,123 ) and if you run it on a US machine,
        // it is format the number in US format ( 12,345.123 ).
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {

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
    static void test01_14() throws Exception {

        // Consider the following code.

        Date d = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        // df.setLocale(new Locale("fr", "FR")); // error here

        String s = null; // 1 insert code here.          

        System.out.println(s);

        // What should be inserted at //1 above so that it will print the date in French format?

        // None of these.

        // Observe that the code is doing df.setLocale(...). There is no such setLocale method in DateFormat or NumberFormat.
        // You cannot change the Locale of these objects after they are created. So this code will not compile.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
