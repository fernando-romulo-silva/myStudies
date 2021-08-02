package br.com.fernando.ch04_introducing_streams.part04_Stream_operations;

import static java.util.stream.Collectors.toList;

import java.util.List;

import br.com.fernando.Dish;

// Stream operations
public class Test {

    public static void test1() {
	final List<String> names = Dish.menu.stream() // get a stream from the list of dishes.
	    .filter(d -> d.getCalories() > 300) // Intermediate operation
	    .map(Dish::getName) // Itermediate operation
	    .limit(3) // intermediate operation
	    .collect(toList()); // converts the Stream into a List
	//
	// You can see two groups of operations:
	//  filter, map, and limit can be connected together to form a pipeline.
	//  collect causes the pipeline to be executed and closes it.
	//
	// Stream operations that can be connected are called intermediate operations, and operations
	// that close a stream are called terminal operations
	//
	System.out.println(names);
    }

    // Intermediate operations
    public static void test2() {
	// Intermediate operations such as filter or sorted return another stream as the return type. This
	// allows the operations to be connected to form a query.
	//
	// What’s important is that intermediate operations don’t perform any processing until a terminal operation is invoked on the stream
	// pipeline—they’re lazy. This is because intermediate operations can usually be merged and
	// processed into a single pass by the terminal operation.

	final List<String> names = Dish.menu.stream() //
	    .filter(d -> { // Printing the dishes as they're filtered
	        System.out.println("filtering " + d.getName()); //
	        return d.getCalories() > 300; //
	    }) //
	    .map(d -> { // Prihting the dishes as you extract their names
	        System.out.println("mapping " + d.getName());
	        return d.getName();
	    })//
	    .limit(3) //
	    .collect(toList());
	//
	System.out.println(names);

	// You can notice several optimizations due to the lazy nature of streams. First, despite the fact
	// that many dishes have more than 300 calories, only the first three are selected! This is because
	// of the limit operation and a technique called short-circuiting
    }

    // Terminal operations
    public static void test3() {

	// Terminal operations produce a result from a stream pipeline. A result is any nonstream value
	// such as a List, an Integer, or even void.

	Dish.menu.stream().forEach(System.out::println);

	final long count = Dish.menu.stream() // create a stream
	    .filter(d -> d.getCalories() > 300) // return a stream - intermediate
	    .distinct() // return a stream - intermediate
	    .limit(3) // return a stream - intermediate
	    .count();
	// The last operation in the stream pipeline count returns a long, which is a non-Stream value. It’s
	// therefore a terminal operation.
    }

    // Working with streams
    public static void test4() {

	// To summarize, working with streams in general involves three items:
	//  A data source (such as a collection) to perform a query on
	//  A chain of intermediate operations that form a stream pipeline
	//  A terminal operation that executes the stream pipeline and produces a result
	// The idea behind a stream pipeline is similar to the builder pattern.
	//
	// Intermediate operations
	// 
	// Operation ============= Type ====================== Return type =================== Argument of the operation =============== Function Descriptor
	// 
	// filter ================ Intermediate ============== Stream<T> ===================== Predicate<T> ============================ T -> boolean
	//
	// map =================== Intermediate ============== Stream<R> ===================== Function<T, R> ========================== T -> R
	//
	// limit ================= Intermediate ============== Stream<T>
	//
	// sorted ================ Intermediate ============== Stream<T> ===================== Comparator<T> =========================== (T, T) -> int
	// 
	// distinct ============== Intermediate ============== Stream<T>
	//
	// Terminal operations
	//
	// Operation ============= Type ====================== Purpose
	//
	// forEach =============== Terminal ================== Consumes each element from a stream and applies a lambda to each of them. The operation returns void.
	//
	// count ================= Terminal ================== Returns the number of elements in a stream. The operation returns a long.
	//
	// collect =============== Terminal ================== Reduces the stream to create a collection such as a List, a Map, or even an Integer
    }

    public static void main(String[] args) {
	test2();
    }
}
