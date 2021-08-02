package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p02_properties_files;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class Studying03 {
    // http://tutorials.jenkov.com/java-internationalization/decimalformat.html
    // =========================================================================================================================================
    // Formatters - provides support for layout justification and alignment, common formats for numeric, string, and date/time data,
    // and locale-specific output.
    static void studying01() {
        // ---------------------------------------------------------------------
        // Format - Main class

        // ---------------------------------------------------------------------
        // NumberFormatter - abstract class, you can't be intantiate!
        // Depending of the factory you use, you get a type of instance.
        double amount = 53000.35;
        Locale locale = new Locale("pt", "BR");

        // Currency Values
        NumberFormat numberFormat01 = NumberFormat.getCurrencyInstance(locale);
        NumberFormat.getCurrencyInstance(); // default locale

        System.out.println("NumberFormat.getCurrencyInstance: " + numberFormat01.format(amount)); // R$ 53.000,35

        ((NumberFormat) numberFormat01).setCurrency(Currency.getInstance("EUR"));

        System.out.println("NumberFormat.getCurrencyInstance (EUR): " + numberFormat01.format(amount)); // R$ 53.000,35

        // Number Values
        numberFormat01 = NumberFormat.getInstance(locale);
        NumberFormat.getInstance(); // default locale

        System.out.println("NumberFormat.getInstance: " + numberFormat01.format(amount)); // 53.000,35

        // or
        numberFormat01 = NumberFormat.getNumberInstance(locale);
        NumberFormat.getNumberInstance(); // default locale

        System.out.println("NumberFormat.getNumberInstance: " + numberFormat01.format(amount)); // 53.000,35

        // Percent Values
        numberFormat01 = NumberFormat.getPercentInstance(locale);
        NumberFormat.getPercentInstance(); // default locale

        System.out.println("NumberFormat.getPercentInstance: " + numberFormat01.format(amount)); // 5.300.035%

        // ---------------------------------------------------------------------
        // DecimalFormat
        DecimalFormat decimalFormat01 = new DecimalFormat("#,##0.00");
        System.out.println("new DecimalFormat(\"#,##0.00\"):" + decimalFormat01.format(amount)); // 53.000,35

        // ---------------------------------------------------------------------
        // Minimum and Maximum Number of Digits
        // You can set both the minimum and maximum number of digits to use both for the integer part and
        // the fractional part of the number. You do so using these methods:

        numberFormat01.setMinimumIntegerDigits(1);
        numberFormat01.setMaximumIntegerDigits(50000);

        numberFormat01.setMinimumFractionDigits(1);
        numberFormat01.setMaximumFractionDigits(50000);

        int digits01 = numberFormat01.getMinimumIntegerDigits();
        int digits02 = numberFormat01.getMaximumIntegerDigits();

        int digits03 = numberFormat01.getMinimumFractionDigits();
        int digits04 = numberFormat01.getMaximumFractionDigits();

        // ---------------------------------------------------------------------
        // Rounding Mode
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("da", "DK"));

        numberFormat.setRoundingMode(RoundingMode.HALF_DOWN);
        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setMaximumFractionDigits(0);

        String number = numberFormat.format(99.50);
        System.out.println(number);

        // ---------------------------------------------------------------------
        // Parsing Numbers
        // You can also use the NumberFormat class to parse numbers. Here is an example:

        numberFormat = NumberFormat.getInstance(new Locale("da", "DK"));

        Number parse;
        try {
            parse = numberFormat.parse("100,00"); // parse throws a checked exception, you need pay attention
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }

        System.out.println(parse.intValue());
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        studying01();
    }
}
