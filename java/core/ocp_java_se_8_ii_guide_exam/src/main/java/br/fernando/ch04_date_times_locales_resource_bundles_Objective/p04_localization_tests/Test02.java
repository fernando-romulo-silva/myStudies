package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p04_localization_tests;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following texts can occur in a valid resource bundle file?

        // greetings=bonjour
        //
        // and
        //
        // greetings1=bonjour
        // greetings2=no bonjour
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {

        // This is a Drag and Drop type question. Please click on 'Show DnD Screen' to see the question.
        Date d = Calendar.getInstance(Locale.FRANCE).getTime();

        DateFormat mydf = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

        System.out.println(mydf.format(d));
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code fragment print when compiled and run?

        Locale myloc = new Locale.Builder().setLanguage("en").setRegion("UK").build(); // L1
        ResourceBundle msgs = ResourceBundle.getBundle("mymsgs", myloc);

        Enumeration<String> en = msgs.getKeys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String val = msgs.getString(key);
            System.out.println(key + " : " + val);
        }

        // Assume that only the following two properties files (contents of the file is shown below the name of the file) are accessible to the code.
        //
        // 1. mymsgs.properties
        // okLabel=OK
        // cancelLabel=Cancel
        //
        // 2. mymsgs_en_UK.properties
        // okLabel=YES
        // noLabel=NO

        // noLabel : NO
        // okLabel : YES
        // cancelLabel : Cancel
        //
        //
        // mymsgs.properties is the base file for this resource bundle. Therefore, it will be loaded first. Since the language and region
        // specific file is also present (_en_UK), it will also be loaded and the values in this file will be superimposed on the values
        // of the base file.
        // Remember that if there were another properties file named mymsgs_en.properties also present, then that file would have been loaded
        // before mymsgs_en_UK.properties.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // java.util.Locale allows you to do which of the following?

        // Provide country and language specific formatting for Dates.
        // and
        // Provide country specific formatting for Currencies.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Locale object represents...

        // a specific geographical, political, or cultural region.
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
    static void test01_07() {
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

        // Create a default resource bundle by removing any locale specific appenders from the file name (for example, appmessages.properties).
        // If a resource bundle for a particular region is not found, the default will be used and even though messages will not be in the
        // right language, there won't be any error.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Assume that dt refers to a valid java.util.Date object and that df is a reference variable of class DateFormat.
        // Which of the following code fragments will print the country and the date in the correct local format?

        Date dt = new Date();

        Locale l = Locale.getDefault();
        DateFormat df = DateFormat.getDateInstance(); // watch out!
        System.out.println(l.getCountry() + " " + df.format(dt));
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
