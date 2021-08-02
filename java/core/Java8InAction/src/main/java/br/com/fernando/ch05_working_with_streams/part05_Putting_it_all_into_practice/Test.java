package br.com.fernando.ch05_working_with_streams.part05_Putting_it_all_into_practice;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import br.com.fernando.Trader;
import br.com.fernando.Transaction;

public class Test {
    static Trader raoul = new Trader("Raoul", "Cambridge");

    static Trader mario = new Trader("Mario", "Milan");

    static Trader alan = new Trader("Alan", "Cambridge");

    static Trader brian = new Trader("Brian", "Cambridge");

    static List<Transaction> transactions = Arrays.asList( //
                                                           new Transaction(brian, 2011, 300), //
                                                           new Transaction(raoul, 2012, 1000), //
                                                           new Transaction(raoul, 2011, 400), //
                                                           new Transaction(mario, 2012, 710), //
                                                           new Transaction(mario, 2012, 700), //
                                                           new Transaction(alan, 2012, 950));

    
    //
    public static void test1() {

        // Query 1: Find all transactions from year 2011 and sort them by value (small to high).
        final List<Transaction> tr2011 = transactions.stream() //
                                               .filter(transaction -> transaction.getYear() == 2011) // Pass a predicate to filter to select transactions in year 2011
                                               .sorted(comparing(Transaction::getValue)) // Sort them by using the value of the transaction
                                               .collect(toList()); // Collect all the elements of the resulting Stream into a List
        System.out.println(tr2011);
        
        // Query 2: What are all the unique cities where the traders work?
        final List<String> cities = transactions.stream() // 
                        .map(transaction -> transaction.getTrader().getCity()) // Extract the city from each trader
                        .distinct() // select only unique cities
                        .collect(toList());//
        
        System.out.println(cities);
        
        // You haven’t seen this yet, but you could also drop distinct() and use toSet() instead, which
        // would convert the stream into a set. You’ll learn more about it in chapter 6. 
        final Set<String> cities2 = transactions.stream() //
        	.map(transaction -> transaction.getTrader().getCity()) //
        	.collect(toSet());
        
        System.out.println(cities2);
        

        // Query 3: Find all traders from Cambridge and sort them by name.
        final List<Trader> traders = transactions.stream() //
                        .map(Transaction::getTrader) // Extract all traders from the transactions
                        .filter(trader -> trader.getCity().equals("Cambridge"))// Select only th treaders from Cambridge
                        .distinct() // Make sure you don't have any duplicates
                        .sorted(comparing(Trader::getName)) // Sort the resulting stream of traders by their names
                        .collect(toList());//
        
        System.out.println(traders);
        
        
        // Query 4: Return a string of all traders’ names sorted alphabetically.        
        final String traderStr = transactions.stream() //
                        .map(transaction -> transaction.getTrader().getName()) // Extract all the names of the traders as a Stream of Strings
                        .distinct() // Select only the unique names
                        .sorted() // Sort the names alphabetically
                        .reduce("", (n1, n2) -> n1 + n2); // Combine each name one by one to form a String tha concatenates all the names.
        
        System.out.println(traderStr);
        
        // Note that this solution isn’t very efficient (all Strings are repeatedly concatenated, which creates
        // a new String object at each iteration). In the next chapter, you’ll see a more efficient solution
        // that uses joining() as follows (which internally makes use of a StringBuilder):
        
        final String traderStr2 = 
         transactions.stream()
         	.map(transaction -> transaction.getTrader().getName())
         	.distinct()
         	.sorted()
         	.collect(joining());
        
        System.out.println(traderStr2);
        
        
        // Query 5: Are there any trader based in Milan?
        final boolean milanBased = transactions.stream() // 
                        .anyMatch(transaction -> transaction.getTrader() // 
                                                            .getCity() //
                                                            .equals("Milan") // Pass a predicate to anyMatch to check if there's a trader from Milan
                                 ); //
        System.out.println(milanBased);
        
        // Query 6: Update all transactions so that the traders from Milan are set to Cambridge.
        transactions.stream() // 
                    .map(Transaction::getTrader) // 
                    .filter(trader -> trader.getCity().equals("Milan")) //
                    .forEach(trader -> trader.setCity("Cambridge")); //
        
        System.out.println(transactions);

        // Print all transactions’ values from the traders living in Cambridge
        transactions.stream()//
        	.filter(t -> "Cambridge".equals(t.getTrader().getCity())) // Select the transactions where the traders live in Cambridge
        	.map(Transaction::getValue) // Extra the values of these trades.
        	.forEach(System.out::println); // print each value.
        
        
        // Query 7: What's the highest value in all the transactions?
        final double highestValue = transactions.stream() //
                        .map(Transaction::getValue) // Extract the value of each transaction
                        .reduce(0D, Double::max); // Calculate the max of the resulting stream
        
        System.out.println(highestValue);

        // Find the transaction with the smallest value
        final Optional<Transaction> smallestTransaction = transactions.stream()//
        	.reduce((a1, a2) -> a1.getValue() < a2.getValue() ? a1 : a2); // Find the smallest transaction by repeatedly comparing the values of each transaction
        	
        System.out.println(smallestTransaction);
        
        // You can do better. A stream supports the methods min and max that take a Comparator as
        // argument to specify which key to compare with when calculating the minimum or maximum:
        final Optional<Transaction> smallestTransaction2 =
        	transactions.stream()
        	.min(comparing(Transaction::getValue));
        
        System.out.println(smallestTransaction2);
    }
}
