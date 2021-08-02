package br.com.fernando.ch06_collectiing_date_with_streams.part02_Reducing_and_summarizing;

// import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;

import br.com.fernando.Dish;

// Reducing and summarizing
public class Test {

    // As you just learned, collectors (the parameters to the Stream method collect) are typically used
    // in cases where it’s necessary to reorganize the stream’s items into a collection. But more
    // generally, they can be used every time you want to combine all the items in the stream into a
    // single result. This result can be of any type, as complex as a multilevel map representing a tree
    // or as simple as a single integer

    // Finding maximum and minimum in a stream of values
    public static void test1() {
        // Suppose you want to find the highest-calorie dish in the menu. You can use two collectors,
        // Collectors.maxBy and Collectors.minBy, to calculate the maximum or minimum value in a stream.

        final Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

        final Optional<Dish> mostCalorieDish = Dish.menu.stream() //
            .collect(maxBy(dishCaloriesComparator));

        System.out.println(mostCalorieDish.orElse(null));
    }

    // Summarization
    public static void test2() {

        // The Collectors class provides a specific factory method for summing: Collectors .summingInt. It
        // accepts a function that maps an object into the int that has to be summed and returns a collector
        // that, when passed to the usual collect method, performs the requested summarization.

        final int totalCalories = Dish.menu.stream() //
            .collect(summingInt(Dish::getCalories));

        System.out.println(totalCalories);

        // The Collectors.summingLong and Collectors.summingDouble methods behave exactly the same
        // way and can be used where the field to be summed is respectively a long or a double.
        // But there’s more to summarization than mere summing; also available is a
        // Collectors .averagingInt, together with its averagingLong and averagingDouble counterparts,
        // to calculate the average of the same set of numeric values:

        final double avgCalories = Dish.menu.stream() //
            .collect(averagingInt(Dish::getCalories));

        System.out.println(avgCalories);

        // Quite often, though, you may want to retrieve two or more of these results, and possibly
        // you’d like to do it in a single operation. In this case, you can use the collector returned by the
        // summarizingInt factory method.
        final IntSummaryStatistics menuStatistics = Dish.menu.stream() //
            .collect(summarizingInt(Dish::getCalories));

        System.out.println(menuStatistics);
    }

    // Joining Strings
    public static void test3() {

        // The collector returned by the joining factory method concatenates into a single string all strings
        // resulting from invoking the toString method on each object in the stream. This means you can
        // concatenate the names of all the dishes in the menu as follows:

        final String shortMenu = Dish.menu.stream() //
            .map(Dish::getName) //
            .collect(joining());

        // Both produce the following string,
        System.out.println(shortMenu); //

        final String shortMenu2 = Dish.menu.stream() //
            .map(Dish::getName) //
            .collect(joining(", "));

        System.out.println(shortMenu2); //
    }

    // Generalized summarization with reduction
    public static void test4() {

        // All the collectors we’ve discussed so far are, in reality, only convenient specializations of a
        // reduction process that can be defined using the reducing factory method.
        //
        // The Collectors.reducing factory method is a generalization of all of them. The special cases discussed
        // earlier are arguably provided only for programmer convenience. (But remember that
        // programmer convenience and readability are of prime importance!)
        //
        final int totalCalories = Dish.menu.stream() //
            .collect( //
                      reducing(0, // Initial value
                               Dish::getCalories, // transformation function
                               (i, j) -> i + j) // aggregating function
        );
        System.out.println(totalCalories);

        // It takes three arguments:
        //
        //  The first argument is the starting value of the reduction operation and will also be the value returned
        // in the case of a stream with no elements, so clearly 0 is the appropriate value in the case of a numeric sum.
        //
        //  The second argument is the same function you used in section 6.2.2 to transform a dish into an int
        // representing its calorie content.
        //
        //  The third argument is a BinaryOperator that aggregates two items into a single value of the same type. Here, it just sums two ints
        //
        // Similarly, you could find the highest-calorie dish using the one-argument version of reducing as follows:
        final Optional<Dish> mostCalorieDish = Dish.menu.stream().collect( //
                                                                     reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2) // aggregating function
        );

        System.out.println(mostCalorieDish);

        // Collection framework flexibility: doing the same operation in different ways
        // You can further simplify the previous sum example using the reducing collector by using a
        // reference to the sum method of the Integer class instead of the lambda expression you used to
        // encode the same operation.

        final int totalCalories2 = Dish.menu.stream().collect(reducing(0, // initial value
                                                                 Dish::getCalories, // Transformation function
                                                                 Integer::sum)); // Aggregating Function
        System.out.println(totalCalories2);

        // Logically, this reduction operation proceeds as shown in figure 6.3, where an accumulator,
        // initialized with a starting value, is iteratively combined, using an aggregating function, with the
        // result of the application of the transforming function on each element of the stream.
        //
        // We already observed in chapter 5 that there’s another way to perform the same operation
        // without using a collector—by mapping the stream of dishes into the number of calories of each
        // dish and then reducing this resulting stream with the same method reference used in the
        // previous version:
        final int totalCalories3 = Dish.menu.stream() //
            .map(Dish::getCalories) //
            .reduce(Integer::sum) //
            .get();

        System.out.println(totalCalories3);

        // Finally, and even more concisely, you can achieve the same result by mapping the stream to an IntStream and then
        // invoking the sum method on it:
        final int totalCalories4 = Dish.menu.stream() //
            .mapToInt(Dish::getCalories).sum();

        System.out.println(totalCalories4);
    }

    // Choosing the best solution for your situation
    public static void test6() {
        // Our suggestion is to explore the largest number of solutions possible for the problem at hand,
        // but always choose the most specialized one that’s general enough to solve it. This is often the
        // best decision for both readability and performance reasons. For instance, to calculate the total
        // calories in our menu, we’d prefer the last solution (using IntStream) because it’s the most
        // concise and likely also the most readable one. At the same time, it’s also the one that performs
        // best, because IntStream lets us avoid all the auto-unboxing operations, or implicit conversions
        // from Integer to int, that are useless in this case.
    }

    public void test5() {

    }

}
