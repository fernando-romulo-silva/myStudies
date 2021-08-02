package br.com.fernando.ch01_why_should_you_care.part03_Streams;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fernando.Currency;
import br.com.fernando.Transaction;

// Streams
public class Test {

    public static List<Transaction> transactions = Arrays.asList(new Transaction(Currency.EUR, 1500.0), //
                                                                 new Transaction(Currency.USD, 2300.0), //
                                                                 new Transaction(Currency.GBP, 9900.0), //
                                                                 new Transaction(Currency.EUR, 1100.0), //
                                                                 new Transaction(Currency.JPY, 7800.0), //
                                                                 new Transaction(Currency.CHF, 6700.0), //
                                                                 new Transaction(Currency.EUR, 5600.0), //
                                                                 new Transaction(Currency.USD, 4500.0), //
                                                                 new Transaction(Currency.CHF, 3400.0), //
                                                                 new Transaction(Currency.GBP, 3200.0), //
                                                                 new Transaction(Currency.USD, 4600.0), //
                                                                 new Transaction(Currency.JPY, 5700.0), //
                                                                 new Transaction(Currency.EUR, 6800.0));

    // The Streams API provides a very different way to process data in comparison to the Collections
    // API. Using a collection, you’re managing the iteration process yourself. You need to iterate
    // through each element one by one using a for-each loop and then process the elements.
    public static void test1() {

        // Before Java 8

        final Map<Currency, List<Transaction>> transactionsByCurrencies1 = new HashMap<>();

        for (final Transaction transaction : transactions) {

            final Currency currency = transaction.getCurrency();

            List<Transaction> transactionsForCurrency = transactionsByCurrencies1.get(currency);

            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionsByCurrencies1.put(currency, transactionsForCurrency);
            }

            transactionsForCurrency.add(transaction);
        }

        System.out.println(transactionsByCurrencies1);

        // After Java 8

        final Map<Currency, List<Transaction>> transactionsByCurrencies = transactions.stream() //
            .collect(groupingBy(Transaction::getCurrency));

        System.out.println(transactionsByCurrencies);

    }

    public static void main(String[] args) {

    }
}
