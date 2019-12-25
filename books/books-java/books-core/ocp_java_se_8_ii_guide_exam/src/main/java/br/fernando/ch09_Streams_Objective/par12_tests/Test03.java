package br.fernando.ch09_Streams_Objective.par12_tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print?

        List<Integer> ls = Arrays.asList(1, 2, 3);
        Function<Integer, Integer> func = a -> a * a; // 1
        ls.stream().map(func).peek(System.out::print); // 2

        // It will compile and run fine but will not print anything.
        // Remember that none of the "intermediate" operations on a stream are executed until a "terminal" operation is called on that stream.
        //
        // In the given code, there is no call to a terminal operation. map and peek are intermediate operations. 
        // Therefore, the map function will not be invoked for any of the elements.
        //
        // If you append a terminal operation such as count(), 
        // for example, 
        ls.stream().map(func).peek(System.out::print).count(); //, it will print 149.
        //
        // Note that you can invoke at most one terminal operation on a stream and that too at the end.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Given:
        List<String> l1 = Arrays.asList("a", "b");
        List<String> l2 = Arrays.asList("1", "2");

        Stream.of(l1, l2).flatMap((x) -> x.stream()).forEach((x) -> System.out.println(x));

        // The objective of flatMap is to take each element of the current stream and replace that element with elements contained in the stream
        // returned by the Function that is passed as an argument to flatMap. It is perfect for the requirement of this question.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Identify correct statement(s) about the following code:

        Optional<String> stro = Optional.of(getValue());// 1
        System.out.println(stro.isPresent());// 2
        System.out.println(stro.get());// 3
        System.out.println(stro.orElse(null));// 4

        // It will throw a NullPointerException at //1
        // Optional.of method throws NullPointerException if you try to create an Optional with a null value.
    }

    static String getValue() {
        return null;
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception { // X
        // What will the following code print when compiled and run?

        IntStream is1 = IntStream.range(1, 3); // 1
        IntStream is2 = IntStream.rangeClosed(1, 3); // 2
        IntStream is3 = IntStream.concat(is1, is2); // 3

        Object val = is3 //
                .boxed() // 4
                .collect(Collectors.groupingBy(k -> k)) // 5
                .get(3); // 6

        System.out.println(val);

        // [3]
        //
        // Explanation
        //
        // 1. IntStream.range returns a sequential ordered IntStream from startInclusive (inclusive) to endExclusive (exclusive) by an
        // incremental step of 1. Therefore, is1 contains 1, 2.
        //
        // 2. IntStream.rangeClosed returns a sequential ordered IntStream from startInclusive (inclusive) to endInclusive (inclusive) by an
        // incremental step of 1. Therefore, is2 contains 1, 2, 3.
        //
        // 3. IntStream.concat returns a lazily concatenated stream whose elements are all the elements of the first stream followed by all the
        // elements of the second stream. Therefore, is3 contains 1, 2, 1, 2, 3.
        //
        // 4. is3 is a stream of primitive ints. is3.boxed() returns a new Stream containing Integer objects instead of primitives. This allows
        // the use various flavors of collect method available in non-primitive streams.
        // [IntStream does have one collect method but it does not take a Collector as argument.]
        //
        // 5. Collectors.groupingBy(k->k) creates a Collector that groups the elements of the stream by a key returned by the function k->k,
        // which is nothing but the value in the stream itself. Therefore, it will group the elements into a Map<Integer, List<Integer>>
        // containing: {1=[1, 1], 2=[2, 2], 3=[3]}
        //
        // 6. Finally, get(3) will return [3].
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code snippet print?

        Stream<Integer> strm1 = Stream.of(2, 3, 5, 7, 11, 13);
        double av = strm1 //
                .filter(x -> {
                    if (x > 10)
                        return true;
                    else return false;
                }) // 1
                // .peek() //2 error here
                .collect(Collectors.averagingInt(y -> y)); // 3

        System.out.println(av);

        // Compilation failure due to code at //2
        // peek expects a Consumer object as an argument. 
        // Calling peek() without any argument will, therefore, fail to compile.

    }

    // =========================================================================================================================================
    static void test01_06() throws Exception { // X
        // Assuming that Book has appropriate constructor and accessor methods, what will the following code print?

        List<Book> books = Arrays.asList( //
                new Book("Freedom at Midnight", 5.0), //
                new Book("Gone with the wind", 5.0), //
                new Book("Midnight Cowboy", 15.0));

        books.stream().filter(b -> b.getTitle().startsWith("F")) //
                .forEach(b -> b.setPrice(10.0));

        books.stream().forEach(b -> System.out.println(b.getTitle() + ":" + b.getPrice()));

        // Freedom at Midnight:10.0
        // Gone with the wind:5.0
        // Midnight Cowboy:15.0

        //
        // Explanation
        //
        // This code illustrates how you can go through a list of elements, filter the elements based on a criteria, and call methods on each of the
        // elements in the filtered list.
        //
        // filter method removes all the elements for which the given condition (i.e. b.getTitle().startsWith("F")) returns false from the stream.
        // These elements are not removed from the underlying list but only from the stream. Therefore, when you create a stream from the list again,
        // it will have all the elements from the list.
        // Since the setPrice operation changes the Book object contained in the list, the updated value is shown the second time when you go through the list.
    }

    static class Book {

        String title;

        Double price;

        public Book(String title, Double price) {
            super();
            this.title = title;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception { // X
        // What will the following code print when compiled and run?

        List<String> values = Arrays.asList("Java EE", "C#", "Python");

        boolean flag = values.stream().allMatch(str -> {
            System.out.println("Testing: " + str);
            return str.equals("Java");
        });

        System.out.println(flag);

        // Testing: Java EE
        // false
        //
        // Explanation
        //
        // This question is based on the fact that allMatch, noneMatch, anyMatch, findFirst, and findAny are short-circuiting terminal operations.
        // This means, the given predicate will not be executed for each element of the stream if the result can be determined by testing an element
        // in the beginning itself.
        //
        // For example, if you invoke predicate on the given stream, the predicate will return false for the first element.
        // If any one element in the list does not satisfy the predicate, the result of the call to allMatch will certainly be false even
        // if you test all other elements irrespective of whether other tests returns true or false.
        // Therefore, it is clear that there is no need for testing other elements.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given Course class ... What will the following code print?

        List<Course> s1 = Arrays.asList( //
                new Course("OCAJP", "Java"), //
                new Course("OCPJP", "Java"), //
                new Course("C#", "C#"), //
                new Course("OCEJPA", "Java"));

        s1.stream() //
                .collect(Collectors.groupingBy(c -> c.getCategory())) //
                .forEach((m, n) -> System.out.println(n));

        // [C# C#]
        // [OCAJP Java, OCPJP Java, OCEJPA Java]
        //
        // 1. Collectors.groupingBy(Function<? super T,? extends K> classifier) returns a Collector that groups elements of a Stream into multiple groups.
        // Elements are grouped by the value returned by applying a classifier function on an element.
        //
        // 2. It is important to understand that the return type of the collect method depends on the Collector that is passed as an argument.
        // In this case, the return type would be Map<K, List<T> because that is the type specified in the Collector returned by the groupingBy method.
        //
        // 3. Java 8 has added a default forEach method in Map interface. This method takes a BiConsumer function object and applies this function to
        // each key-value pair of the Map. In this case, m is the key and n is the value.
        //
        // 4. The given code provides a trivial lambda expression for BiConsumer that just prints the second parameter, which happens to be the value
        // part of of the key-value pair of the Map.
        //
        // 5. The value is actually an object of type List<Course>, which is printed in the output. Since there are two groups, two lists are printed.
        // First list has only one Course element and the second list has three.
    }

    static class Course {

        private String id;

        private String category;

        public Course(String id, String category) {
            this.id = id;
            this.category = category;
        }

        public String toString() {
            return id + " " + category;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }
    }

    // =========================================================================================================================================
    // Given Book class ... What will the following code print?
    static void test01_09() throws Exception {
        List<Book> books = Arrays.asList( //
                new Book("Thinking in Java", 30.0), //
                new Book("Java in 24 hrs", 20.0), //
                new Book("Java Recipies", 10.0));

        double averagePrice = books.stream() //
                .filter(b -> b.getPrice() > 10) //
                .mapToDouble(b -> b.getPrice()) //
                .average() // Return a OptionalDouble, sometimes, it's empty
                .getAsDouble();

        System.out.println(averagePrice); // 25.0
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given Item class ... What will the following code print?

        List<Item> items = Arrays.asList(//
                new Item("Pen", "Stationery", 3.0), //
                new Item("Pencil", "Stationery", 2.0), //
                new Item("Eraser", "Stationery", 1.0), //
                new Item("Milk", "Food", 2.0), //
                new Item("Eggs", "Food", 3.0));

        ToDoubleFunction<Item> priceF = Item::getPrice; // 1

        items.stream().collect(Collectors.groupingBy(Item::getCategory)) // 2
                .forEach((a, b) -> {
                    double av = b.stream().collect(Collectors.averagingDouble(priceF)); // 3
                    System.out.println(a + " : " + av);
                });

        // Stationery : 2.0
        // Food : 2.5
        //
        // 1. collect(Collectors.groupingBy(Item::getCategory)) returns a Map where the keys are the Category values and the values are
        // Lists of the elements.
        //
        // 2. collect(Collectors.averagingDouble(priceF)) returns the average of the values returned by the priceF function when it is
        // applied to each element of the Stream. In this case, a stream is created from the List of elements belonging to each categtory
        // by the call to forEach.
    }

    static class Item {

        private final String name;

        private final String category;

        private final double price;

        public Item(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }
    }
}
