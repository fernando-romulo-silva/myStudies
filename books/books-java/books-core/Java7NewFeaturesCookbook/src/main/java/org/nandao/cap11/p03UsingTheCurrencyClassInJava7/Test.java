package org.nandao.cap11.p03UsingTheCurrencyClassInJava7;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;

// The java.util.Currency class introduced four new methods for retrieving information
// about available currencies and their properties.
public class Test {

    public static void main(String[] args) {

        final Set<Currency> currencies = Currency.getAvailableCurrencies();

        for (final Currency currency : currencies) {
            System.out.printf("%s - %s - %s\n", currency.getDisplayName(), currency.getDisplayName(Locale.US), currency.getNumericCode());
        }
    }

}
