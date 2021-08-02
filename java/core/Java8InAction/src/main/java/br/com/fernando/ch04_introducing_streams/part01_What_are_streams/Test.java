package br.com.fernando.ch04_introducing_streams.part01_What_are_streams;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.fernando.Dish;

// What are streams?
//
// Streams are an update to the Java API that lets you manipulate collections of data in a
// declarative way (you express a query rather than code an ad hoc implementation for it).
// For now you can think of them as fancy iterators over a collection of data. In addition, streams can be
// processed in parallel transparently, without you having to write any multithreaded code!
public class Test {

    // Before (Java 7):
    public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes) {
	final List<Dish> lowCaloricDishes = new ArrayList<>();

	for (final Dish d : dishes) {
	    if (d.getCalories() < 400) { // filter the elements using an accumulator.
		lowCaloricDishes.add(d);
	    }
	}

	final List<String> lowCaloricDishesName = new ArrayList<>();

	Collections.sort(lowCaloricDishes, new Comparator<Dish>() { // Sort the dishes with an anonymous class

	    @Override
	    public int compare(Dish d1, Dish d2) {
		return Integer.compare(d1.getCalories(), d2.getCalories());
	    }
	});

	for (final Dish d : lowCaloricDishes) {
	    lowCaloricDishesName.add(d.getName()); // Process the sorted list to select the names of dishes
	}

	return lowCaloricDishesName;
    }

    // After (Java 8)
    //
    // In this code you use a “garbage variable,” lowCaloricDishes. Its only purpose is to act as an
    // intermediate throwaway container. In Java 8, this implementation detail is pushed into the
    // library where it belongs.
    public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes) {
	return dishes.stream() // create stream
	    .filter(d -> d.getCalories() < 400) // Select dishes tha are bellow 400 calories
	    .sorted(comparing(Dish::getCalories)) // Sort them by calories
	    .map(Dish::getName) // Estract the names of these dishes
	    .collect(toList()); // Store all names in a list
    }

    // To exploit a multicore architecture and execute this code in parallel, you need only change
    // stream() to parallelStream():
    public static List<String> getLowCaloricDishesNamesInJava8Parallel(List<Dish> dishes) {
	return dishes.parallelStream() // create a parallel stream
	    .filter(d -> d.getCalories() < 400) // Select dishes tha are bellow 400 calories
	    .sorted(comparing(Dish::getCalories)) // Sort them by calories
	    .map(Dish::getName) // Estract the names of these dishes
	    .collect(toList()); // Store all names in a list
    }

}
