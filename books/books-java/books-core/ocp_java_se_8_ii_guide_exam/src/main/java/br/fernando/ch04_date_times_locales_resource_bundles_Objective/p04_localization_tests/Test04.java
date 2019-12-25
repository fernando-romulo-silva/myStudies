package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p04_localization_tests;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given:
        Locale locale = new Locale("en", "US");
        ResourceBundle rb = ResourceBundle.getBundle("test.MyBundle", locale);

        // Which of the following are valid lines of code? (Assume that the ResourceBundle has the values for the given keys.)

        Object obj = rb.getObject("key1");

        String[] vals = rb.getStringArray("key2");

        // Keys are always Strings. So you cannot use an int to get value for a key.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // Which of the following texts can occur in a valid resource bundle file?

        // greetings=bonjour
        //
        // and
        //
        // greetings1=bonjour
        // greetings2=no bonjour
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Drag and Drop

        Date d = Calendar.getInstance(Locale.FRANCE).getTime();

        DateFormat mydf = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);

        System.out.println(mydf.format(d));
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Consider the following code:
        double amount = 53000.35;
        Locale jp = new Locale("jp", "JP");
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

        // How will you create formatter using a factory at //1 so that the output is in Japanese Currency format?
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
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
        // A Locale object represents...

        // a specific geographical, political, or cultural region.
        //
        // Explanation
        //
        // A Locale object just represents a region. Based on a given Locale, you can do various things such as display text in that 
        // region's language and/or style.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Identify valid statements.

        // Default locale
        Locale myLocale01 = Locale.getDefault();

        // Locale class has several static constants for standard country locales.
        Locale myLocale02 = Locale.US;

        Locale myLocale03 = new Locale("ru", "RU");
        // You don't have to worry about the actual values of the language and country codes. Just remember that both are two lettered 
        // codes and country codes are always upper case.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
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
