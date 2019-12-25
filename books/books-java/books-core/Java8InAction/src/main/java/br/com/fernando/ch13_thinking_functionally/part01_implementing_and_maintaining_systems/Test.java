package br.com.fernando.ch13_thinking_functionally.part01_implementing_and_maintaining_systems;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.fernando.Currency;
import br.com.fernando.Transaction;

// Implementing and maintaining systems
public class Test {

    // Shared mutable data
    public static void test01() {

        final List<Transaction> transactions = Arrays.asList(new Transaction(Currency.USD, 2.3d), //
                                                             new Transaction(Currency.USD, 6.6d), //
                                                             new Transaction(Currency.USD, 4.6d));

        // Shared mutable data
        //
        // * Shared mutable data structures make it harder to track changes in different parts of your program

        // * A method, which modifies neither the state of its enclosing class nor the state of any other objects
        // and returns its entire results using return, is called pure or side-effect free.
        //
        // What constitutes a side effect more concretely? In a nutshell, a side effect is an action that’s not
        // totally enclosed within the function itself. Here are some examples:
        //
        //  Modifying a data structure in place, including assigning to any field, apart from initialization inside a
        // constructor (for example, setter methods)
        //
        //  Throwing an exception
        //
        //  Doing I/O operations such as writing to a file

        // Declarative programming
        Transaction mostExpensive = transactions.get(0);

        if (mostExpensive == null) {
            throw new IllegalArgumentException("Empty list of transactions");
        }

        for (Transaction t : transactions.subList(1, transactions.size())) {
            if (t.getValue() > mostExpensive.getValue()) {
                mostExpensive = t;
            }
        }

        // Function style
        Optional<Transaction> mostExpensiveOptionanl = transactions.stream() //
            .max(java.util.Comparator.comparing(Transaction::getValue));

        mostExpensive = mostExpensiveOptionanl.orElse(null);

        // Why functional programming?
        //
        // Functional programming exemplifies this idea of declarative programming (“just say what you
        // want, using expressions that don’t interact, and for which the system can choose the
        // implementation”) and side-effect-free computation explained previously. As we discussed, these
        // two ideas can help you implement and maintain systems more easily.
        //
        // 

    }

}
