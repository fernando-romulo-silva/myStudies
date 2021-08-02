package br.com.fernando.ch06_collectiing_date_with_streams.part04_Partitioning;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import br.com.fernando.Dish;

// Partitioning
public class Test {

    public static void test0() {
	// Partitioning is a special case of grouping: having a predicate (a function returning a boolean),
	// called a partitioning function, as a classification function. The fact that the partitioning function
	// returns a boolean means the resulting grouping Map will have a Boolean as a key type and
	// therefore there can be at most two different groups—one for true and one for false.

	final Map<Boolean, List<Dish>> partitionedMenu = Dish.menu.stream() //
	    .collect( //
	              partitioningBy(Dish::isVegetarian));

	partitionedMenu.forEach((k, v) -> System.out.println("K:" + k + " v" + v));

	// So you could retrieve all the vegetarian dishes by getting from this Map the value indexed with
	// the key true:

	final List<Dish> vegetarianDishes = partitionedMenu.get(true);

	vegetarianDishes.forEach(v -> System.out.println(v));

	// Note that you could achieve the same result by just filtering the stream created from the menu
	// List with the same predicate used for partitioning and then collecting the result in an additional
	// List:

	final List<Dish> vegetarianDishes2 = Dish.menu.stream() //
	    .filter(Dish::isVegetarian) //
	    .collect(toList());

	vegetarianDishes2.forEach(v -> System.out.println(v));
    }

    // Advantages of partitioning
    public static void test1() {

	// Partitioning has the advantage of keeping both lists of the stream elements, for which the
	// application of the partitioning function returns true or false. So in the previous example, you can
	// obtain the List of the nonvegetarian Dishes by accessing the value of the key false in the
	// partitionedMenu Map, using two separate filtering operations: one with the predicate and one
	// with its negation.
	//
	// Also, as you already saw for grouping, the partitioningBy factory method has
	// an overloaded version to which you can pass a second collector, as shown here:

	final Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = Dish.menu.stream() //
	    .collect(partitioningBy( //
	                             Dish::isVegetarian, //
	                             groupingBy(Dish::getType)));

	vegetarianDishesByType.forEach((k, v) -> System.out.println("K:" + k + " v" + v));

	// Here the grouping of the dishes by their type is applied individually to both of the substreams of
	// vegetarian and nonvegetarian dishes resulting from the partitioning, producing a two-level Map
	// that’s similar to the one you obtained when you performed the two-level grouping in section 6.3.1.
	//
	// As another example, you can reuse your earlier code to find the most caloric dish among
	// both vegetarian and nonvegetarian dishes:

	final Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = Dish.menu.stream() //
	    .collect( //
	              partitioningBy(Dish::isVegetarian, //
	                             collectingAndThen( //
	                                                maxBy(comparingInt(Dish::getCalories)), //
	                                                Optional::get)));

	mostCaloricPartitionedByVegetarian.forEach((k, v) -> System.out.println("K:" + k + " v:" + v));

    }

    // Partitioning numbers into prime and nonprime
    public static void test2() {
	// Suppose you want to write a method accepting as argument an int n and partitioning the first n
	// natural numbers into prime and nonprime.
	final Map<Boolean, List<Integer>> primes = partitionPrimes(7);
	primes.forEach((v, k) -> System.out.println("K:" + k + " v:" + v));
    }

    // But first, it will be useful to develop a predicate that
    // tests to see if a given candidate number is prime or not:
    public static boolean isPrime(int candidate) {
	final int candidateRoot = (int) Math.sqrt(candidate);

	return IntStream.rangeClosed(2, candidateRoot) // Generate a range of natural numbers starting from and including 2 up to but excluding candidate.
	    .noneMatch(i -> candidate % i == 0); // Return true if the candidate isn't divisible for any of the numbers in the stream.
    }

    // Now the biggest part of the job is done. To partition the first n numbers into prime and
    // nonprime, it’s enough to create a stream containing those n numbers and reduce it with a
    // partitioningBy collector using as predicate the isPrime method you just developed:
    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
	return IntStream.rangeClosed(2, n).boxed() //
	    .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    public static void main(String[] args) {
	test2();
    }
}
