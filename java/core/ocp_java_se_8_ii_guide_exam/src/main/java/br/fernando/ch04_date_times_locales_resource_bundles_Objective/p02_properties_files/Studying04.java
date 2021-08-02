package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p02_properties_files;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Studying04 {

    // =========================================================================================================================================
    static void studying01() {

        Locale locale = new Locale("da", "DK");

        // --------------------------------------------------------------
        // DateFormat
        // Used to format dates as strings according to a specific Locale. Different countries have different standards for how they format dates.

        // create a date instance
        Date d = Calendar.getInstance(Locale.FRANCE).getTime();

        // --------------------------------------------------------------
        // Creating a DateFormat

        DateFormat mydf = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
        // or
        mydf = DateFormat.getInstance(); // no parameters

        // * Pay Attention *
        // Don't exists a factory with only locale

        System.out.println(mydf.format(d));

        // The date format parameter can be chosen among the following constants in the DateFormat class:
        System.out.println(DateFormat.DEFAULT);
        System.out.println(DateFormat.SHORT);
        System.out.println(DateFormat.MEDIUM);
        System.out.println(DateFormat.LONG);
        System.out.println(DateFormat.FULL);

        // --------------------------------------------------------------
        // Formatting Dates
        DateFormat dateFormat01 = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date01 = dateFormat01.format(new Date());
        System.out.println(date01);

        // ---------------------------------------------------------------
        // Formatting Time
        // In order to format only time and not the date itself, you need a time instance of the DateFormat class.
        // You create such an instance using the getTimeInstance() method.
        DateFormat dateFormat02 = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        String date02 = dateFormat02.format(new Date());
        System.out.println(date02);

        // ---------------------------------------------------------------
        // Formatting Date and Time
        DateFormat dateFormat03 = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
        String date03 = dateFormat03.format(new Date());
        System.out.println(date03);
    }

    // =========================================================================================================================================
    static void studying02() {
        // ---------------------------------------------------------------
        // SimpleDateFormat
        // The java.text.SimpleDateFormat class is used to both parse and format dates according to a formatting pattern you specify yourself.
        // When parsing dates, the Java SimpleDateFormat typically parses the date from a Java String.
        // When formatting dates, the SimpleDateFormat typically formats a Date object into a String, although it can also format the
        // date into a StringBuffer.

        // ---------------------------------------------------------------
        // Creating a SimpleDateFormat

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat(pattern);

        String date = simpleDateFormat01.format(new Date());
        System.out.println(date);

        // ---------------------------------------------------------------
        // Format Date Into StringBuffer
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        simpleDateFormat02.format(new Date(), stringBuffer, new FieldPosition(0));

        // ---------------------------------------------------------------	
        // Parsing Dates
        SimpleDateFormat simpleDateFormat03 = new SimpleDateFormat(pattern);
        try {
            Date date03 = simpleDateFormat03.parse("2018-09-09");

            System.out.println(date03);
        } catch (ParseException e) {
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        studying01();
    }
}
