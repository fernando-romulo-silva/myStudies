package br.com.fernando.ch02_passing_code_whith_behavior_parameterization.part01_Coping_with_changing_requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.fernando.Apple;

// Coping with changing requirements
public class Test {

    public static void main(String... args) {

	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
	final List<Apple> greenApples = filterApplesByColor(inventory, "green");
	System.out.println(greenApples);

	// [Apple{color='red', weight=120}]
	final List<Apple> redApples = filterApplesByColor(inventory, "red");
	System.out.println(redApples);

    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if ("green".equals(apple.getColor())) {
		result.add(apple);
	    }
	}
	return result;
    }

    // Second attempt: parameterizing the color
    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if (apple.getColor().equals(color)) {
		result.add(apple);
	    }
	}
	return result;
    }

    // This is a good solution, but notice how you have to duplicate most of the implementation for
    // traversing the inventory and applying the filtering criteria on each apple.
    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if (apple.getWeight() > weight) {
		result.add(apple);
	    }
	}
	return result;
    }

    // Third attempt: filtering with every attribute you can think of
    //
    // This solution is extremely bad. First, the client code looks terrible. What do true and false mean?
    // In addition, this solution doesn’t cope well with changing requirements. What if the farmer asks
    // you to filter with different attributes of an apple, for example, its size, its shape, its origin, and so on? 
    // Furthermore, what if the farmer asks you for more complicated queries that combine attributes, such as green 
    // apples that are also heavy? You’d either have multiple duplicated filter methods or one giant, very complex method.
    public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if ((flag && apple.getWeight() > weight) || (!flag && apple.getColor().equals(color))) {
		result.add(apple);
	    }
	}
	return result;
    }

}
