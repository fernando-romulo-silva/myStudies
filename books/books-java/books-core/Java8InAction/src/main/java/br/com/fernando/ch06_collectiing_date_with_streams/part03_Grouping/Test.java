package br.com.fernando.ch06_collectiing_date_with_streams.part03_Grouping;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import br.com.fernando.Dish;

// Grouping
public class Test {

    public static enum CaloricLevel {
	DIET, NORMAL, FAT
    }

    //
    public static void test0() {
	// As you saw in the earlier transactions-currency-grouping example, this operation can be
	// cumbersome, verbose, and error prone when implemented with an imperative style. But it can
	// be easily translated in a single, very readable statement by rewriting it in a more functional style
	// as encouraged by Java 8.

	final Map<Dish.Type, List<Dish>> dishesByType = Dish.menu.stream() //
	    .collect(groupingBy(Dish::getType));

	dishesByType.forEach((k, V) -> System.out.println("key:" + k + " value:" + V));

	// Here, you pass to the groupingBy method a Function (expressed in the form of a method
	// reference) extracting the corresponding Dish.Type for each Dish in the stream. We call this
	// Function a classification function because it’s used to classify the elements of the stream into
	// different groups.
	//
	// But it isn’t always possible to use a method reference as a classification function, because you
	// may wish to classify using something more complex than a simple property accessor.

	final Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = Dish.menu.stream() //
	    .collect(//
	             groupingBy(dish -> { //
	                 if (dish.getCalories() <= 400) {
		             return CaloricLevel.DIET;
	                 } else if (dish.getCalories() <= 700) {
		             return CaloricLevel.NORMAL;
	                 } else {
		             return CaloricLevel.FAT;
	                 }
	             }));

	dishesByCaloricLevel.forEach((k, V) -> System.out.println("key:" + k + " value:" + V));
    }

    // Multilevel grouping
    public static void test1() {
	// You can achieve multilevel grouping by using a collector created with a two-argument version of
	// the Collectors.groupingBy factory method, which accepts a second argument of type collector
	// besides the usual classification function. So to perform a two-level grouping, you can pass an
	// inner groupingBy to the outer groupingBy, defining a second-level criterion to classify the
	// stream’s items

	final Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = Dish.menu.stream() //
	    .collect(groupingBy(Dish::getType, // First-level classification function
	                        groupingBy(dish -> { // Second-level classification function
	                            if (dish.getCalories() <= 400) {
		                        return CaloricLevel.DIET;
	                            } else if (dish.getCalories() <= 700) {
		                        return CaloricLevel.NORMAL;
	                            } else {
		                        return CaloricLevel.FAT;
	                            }
	                        })));

	dishesByTypeCaloricLevel.forEach((k, v) -> System.out.println("Key:" + k + " Value:" + v));
    }

    // Collecting data in subgroups
    public static void test2() {

	// In the previous section, you saw that it’s possible to pass a second groupingBy collector to the
	// outer one to achieve a multilevel grouping. But more generally, the second collector passed to
	// the first groupingBy can be any type of collector, not just another groupingBy. For instance, it’s
	// possible to count the number of Dishes in the menu for each type, by passing the counting
	// collector as a second argument to the groupingBy collector:

	final Map<Dish.Type, Long> typesCount1 = Dish.menu.stream() //
	    .collect(groupingBy(Dish::getType, counting()));

	typesCount1.forEach((k, v) -> System.out.println("Key:" + k + " Value:" + v));

	// You could rework the collector you already used to find the
	// highest-calorie dish in the menu to achieve a similar result,
	// but now classified by the type of dish:

	final Map<Dish.Type, Optional<Dish>> mostCaloricByType = Dish.menu.stream() //
	    .collect(groupingBy(Dish::getType, //
	                        maxBy(comparingInt(Dish::getCalories))));

	mostCaloricByType.forEach((k, v) -> System.out.println("Key:" + k + " Value:" + v));

	// The values in this Map are Optionals because this is the resulting type of the collector generated
	// by the maxBy factory method, but in reality if there’s no Dish in the menu for a given type, that
	// type won’t have an Optional.empty() as value; it won’t be present at all as a key in the Map.
    }

    // Adapting the collector result to a different type
    public static void tes21() {

	// Because the Optionals wrapping all the values in the Map resulting from the last grouping
	// operation aren’t very useful in this case, you may want to get rid of them. To achieve this, or
	// more generally, to adapt the result returned by a collector to a different type, you could use the
	// collector returned by the Collectors.collectingAndThen factory method

	final Map<Dish.Type, Dish> mostCaloricType = Dish.menu.stream() //
	    .collect(groupingBy( //
	                         Dish::getType, // First-level classification function
	                         collectingAndThen( // downstream
	                                            maxBy(comparingInt(Dish::getCalories)), // Wrapped collector
	                                            Optional::get))); // Trasformation function

	mostCaloricType.forEach((k, v) -> System.out.println("key:" + k + " value:" + v));

	// This factory method takes two arguments, the collector to be adapted and a transformation
	// function, and returns another collector. This additional collector acts as a wrapper for the old
	// one and maps the value it returns using the transformation function as the last step of the collect
	// operation. In this case, the wrapped collector is the one created with maxBy, and the
	// transformation function, Optional::get, extracts the value contained in the Optional returned.
    }

    // Other examples of collectors used in conjunction with groupingBy
    public static void test22() {

	// For example, you could also reuse the collector created to sum the calories of all
	// the dishes in the menu to obtain a similar result, but this time for each group of Dishes:
	final Map<Dish.Type, Integer> totalCaloriesByType = Dish.menu.stream() //
	    .collect(groupingBy( //
	                         Dish::getType, // First-level classification function
	                         summingInt(Dish::getCalories)));

	totalCaloriesByType.forEach((k, v) -> System.out.println("key:" + k + " value:" + v));

	// Yet another collector (summingInt), commonly used in conjunction with groupingBy,
	// is one generated by the mapping method.
	// This method takes two arguments: a function transforming the elements in a
	// stream and a further collector accumulating the objects resulting from this transformation.
	// Its purpose is to adapt a collector accepting elements of a given type to one working on
	// objects of a different type, by applying a mapping function to each input element before
	//
	// To see a practical example of using this collector, suppose you want to know which CaloricLevels
	// are available in the menu for each type of Dish. You could achieve this result combining a
	// groupingBy and a mapping collector as follows:
	final Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = Dish.menu.stream() //
	    .collect( //
	              groupingBy( //
	                          Dish::getType, //
	                          mapping( //
	                                   dish -> {
	                                       if (dish.getCalories() <= 400) {
		                                   return CaloricLevel.DIET;
	                                       } else if (dish.getCalories() <= 700) {
		                                   return CaloricLevel.NORMAL;
	                                       } else {
		                                   return CaloricLevel.FAT;
	                                       }
	                                   }, //
	                                   toSet())));

	caloricLevelsByType.forEach((k, v) -> System.out.println("key:" + k + " value:" + v));

	// Here the transformation function passed to the mapping method maps a Dish into its
	// CaloricLevel, as you’ve seen before. The resulting stream of CaloricLevels is then passed to a
	// toSet collector, analogous to the toList one, but accumulating the elements of a stream into a Set
	// instead of into a List, to keep only the distinct values. As in earlier examples, this mapping
	// collector will then be used to collect the elements in each substream generated by the grouping
	// function,
	//
	// Note that in the previous example, there are no guarantees about what type of Set is
	// returned. But by using toCollection, you can have more control. For example, you can ask for a
	// HashSet by passing a constructor reference to it:

	final Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType2 = Dish.menu.stream() //
	    .collect(groupingBy( //
	                         Dish::getType, //
	                         mapping( //
	                                  dish -> {
	                                      if (dish.getCalories() <= 400) {
		                                  return CaloricLevel.DIET;
	                                      } else if (dish.getCalories() <= 700) {
		                                  return CaloricLevel.NORMAL;
	                                      } else {
		                                  return CaloricLevel.FAT;
	                                      }
	                                  }, //
	                                  toCollection(HashSet::new))));

	caloricLevelsByType2.forEach((k, v) -> System.out.println("key:" + k + " value:" + v));
    }

    public static void main(String[] args) {
	test2();
    }
}
