package br.com.fernando.ch01_why_should_you_care.part02_Functions_in_Java;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// Functions in Java
public class Test {

    // Methods and lambdas as first-class citizens
    public static void test1() {

	// Suppose you want to filter all the hidden files in a directory. You need to start writing a method
	// that given a File will tell you whether it’s hidden or not. Thankfully there’s such a method inside
	// the File class called isHidden.
	// It can be viewed as a function that takes a File and returns a boolean. But to use it for
	// filtering you need to wrap it into a FileFilter object that you then pass to the File.listFiles
	// method, as follows:
	final File[] hiddenFiles1 = new File(".").listFiles(new FileFilter() {

	    @Override
	    public boolean accept(File dir) {
		return dir.isHidden();
	    }
	});

	System.out.println(hiddenFiles1);

	// Now, in Java 8 you can rewrite that code as follows:

	final File[] hiddenFiles2 = new File(".").listFiles(File::isHidden);
	System.out.println(hiddenFiles2);
    }

    // ===================================== BEFORE JAVA 8 =====================================================//
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if ("green".equals(apple.getColor())) {
		result.add(apple);
	    }
	}
	return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if (apple.getWeight() > 150) {
		result.add(apple);
	    }
	}
	return result;
    }

    // ===================================== AFTER JAVA 8 =====================================================//

    public static boolean isGreenApple(Apple apple) {
	return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
	return apple.getWeight() > 150;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
	final List<Apple> result = new ArrayList<>();
	for (final Apple apple : inventory) {
	    if (p.test(apple)) {
		result.add(apple);
	    }
	}
	return result;
    }

    // Lambdas—anonymous functions
    public static void test2() {

	final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

	// Passing code: an example

	// before after java 8

	final List<Apple> greenApplesOld = filterGreenApples(inventory);
	System.out.println(greenApplesOld);
	
	final List<Apple> heavyApplesOld = filterHeavyApples(inventory);
	System.out.println(heavyApplesOld);
	
	
	// after java 8
	//
	// The previous code passed a method Apple::isGreenApple 
	// (which takes an Apple for argument and returns a boolean) 
	// to filterApples, which expected a Predicate-<Apple> parameter. 
	// The word predicate is often used in mathematics to mean something function-like that takes a value
	// for an argument and returns true or false
	
	
	// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
	final List<Apple> greenApples = filterApples(inventory, Test::isGreenApple);
	System.out.println(greenApples);

	// [Apple{color='green', weight=155}]
	final List<Apple> heavyApples = filterApples(inventory, Test::isHeavyApple);
	System.out.println(heavyApples);
	
	
	// From passing methods to lambdas
	//
	// Passing methods as values is clearly useful, but it’s a bit annoying having to write a definition for
	// short methods such as isHeavyApple and isGreenApple when they’re used perhaps only once or
	// twice. But Java 8 has solved this too. It introduces a new notation (anonymous functions, or
	// lambdas) that enables you to write just

	// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
	final List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
	System.out.println(greenApples2);

	// [Apple{color='green', weight=155}]
	final List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
	System.out.println(heavyApples2);

	// []
	final List<Apple> weirdApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));
	System.out.println(weirdApples);
    }

    public static void main(String[] args) {

    }

    static class Apple {

	private int weight = 0;

	private String color = "";

	public Apple(int weight, String color) {
	    this.weight = weight;
	    this.color = color;
	}

	public Integer getWeight() {
	    return weight;
	}

	public void setWeight(Integer weight) {
	    this.weight = weight;
	}

	public String getColor() {
	    return color;
	}

	public void setColor(String color) {
	    this.color = color;
	}

	@Override
	public String toString() {
	    return "Apple{" + "color='" + color + '\'' + ", weight=" + weight + '}';
	}
    }
}
