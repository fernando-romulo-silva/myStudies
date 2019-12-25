package br.com.fernando.ch06_collectiing_date_with_streams.part01_Collectors_in_a_nutshell;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.fernando.Currency;
import br.com.fernando.Transaction;

// Grouping transactions by currency in imperative style
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

    // If you’re an experienced Java developer, you’ll probably feel comfortable writing something like
    // this, but you have to admit that it’s a lot of code for such a simple task. Even worse, this is
    // probably harder to read than to write!
    public static void groupImperatively() {

        final Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();

        for (final Transaction transaction : transactions) {

            Currency currency = transaction.getCurrency();

            List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);

            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionsByCurrencies.put(currency, transactionsForCurrency);
            }

            transactionsForCurrency.add(transaction);
        }

        System.out.println(transactionsByCurrencies);
    }

    // The comparison is quite embarrassing, isn’t it?
    public static void groupLambda() {
        Map<Currency, List<Transaction>> transactionsByCurrencies = transactions.stream() //
            .collect(groupingBy(Transaction::getCurrency));

        System.out.println(transactionsByCurrencies);
    }

    // The reduction process grouping the transactions by currency
    public static void test1() {
        // Typically, the Collector applies a transforming function to the element (quite often this is the
        // identity transformation, which has no effect, for example, as in toList), and accumulates the
        // result in a data structure that forms the final output of this process.
        //
        // The most straightforward and frequently used collector is the toList static
        // method, which gathers all the elements of a stream into a List:
        List<Transaction> transactionsList = transactions.stream().collect(Collectors.toList());
        //
        System.out.println(transactionsList);
    }

    // Predefined collectors
    public static void test2() {
        // In the rest of this chapter, we mainly explore the features of the predefined collectors, those that
        // can be created from the factory methods (such as groupingBy) provided by the Collectors class.
        //
        // These offer three main functionalities:
        //
        //  Reducing and summarizing stream elements to a single value
        //  Grouping elements
        //  Partitioning elements
    }

}
