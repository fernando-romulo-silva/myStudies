package br.fernando.ch09_Streams_Objective.par12_tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test01 {

    // =========================================================================================================================================
    //
    static void test01_01() {

        // Given:

        Stream<String> names = Stream.of("Sarah Adams", "Suzy Pinnell", "Paul Basgall");

        // Which of the following options will correctly assign a stream of just first names to firstNames?

        // Returns a stream consisting of the results of applying the given function to the elements of this stream.
        Stream<String> firstNames = names.map(e -> e.split(" ")[0]);

        System.out.println(firstNames.collect(Collectors.toList()));
    }

    // =========================================================================================================================================
    //
    static void test01_02() {
        // What will the following code print?

        List<Item> items = Arrays.asList(new Item("Pen", "Stationery", 3.0), //
                new Item("Pencil", "Stationery", 2.0), //
                new Item("Eraser", "Stationery", 1.0), //
                new Item("Milk", "Food", 2.0), //
                new Item("Eggs", "Food", 3.0) //
        );

        ToDoubleFunction<Item> priceF = Item::getPrice; // 1

        items.stream() //
                .collect(Collectors.groupingBy(Item::getCategory)) // 2
                .forEach((a, b) -> { //
                    double av = b.stream().collect(Collectors.averagingDouble(priceF)); // 3
                    System.out.println(a + " : " + av); //
                });

        // Stationery : 2.0
        // Food : 2.5
        //
        // 1. collect(Collectors.groupingBy(Item::getCategory)) returns a Map where the keys are the Category values and the values are Lists of the elements.
        //
        // 2. collect(Collectors.averagingDouble(priceF)) returns the average of the values returned by the priceF function when it is applied to each element of the Stream.
        // In this case, a stream is created from the List of elements belonging to each categtory by the call to forEach.
    }

    private static class Item {

        private int id;

        private String name;

        private String category;

        private double price;

        public Item(int id, String name) {
            this(name, null, 0);
            this.id = id;
        }

        public Item(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    // =========================================================================================================================================
    //
    static void test01_03() {
        // Given that Book is a valid class with appropriate constructor and getTitle and getPrice methods that return a String and a Double respectively,
        // what can be inserted at //1 and //2 so that it will print the price of all the books having a title that starts with "A"?

        List<Book> books = Arrays.asList( //
                new Book("Atlas Shrugged", 10.0), //
                new Book("Freedom at Midnight", 5.0), //
                new Book("Gone with the wind", 5.0) //
        );

        // 1 INSERT CODE HERE

        // The first line generates a Map<String, Double> from the List using Stream's collect method.
        // The Collectors.toMap method uses two functions to get two values from each element of the stream.  
        // The value returned by the first function is used as a key and the value returned by the second function is used as a value to build the resulting Map.
        Map<String, Double> bookMap = books.stream().collect(Collectors.toMap(b -> b.getTitle(), b -> b.getValue()));

        // The forEach method of a Map requires a BiConsumer. This function is invoked for each entry, that is each key-value pair, in the map.
        // The first argument of this function is the key and the second is the value.
        BiConsumer<String, Double> func = (a, b) -> {
            if (a.startsWith("A")) {
                System.out.println(b);
            }
        };

        // 2 INSERT CODE HERE
        bookMap.forEach(func);
    }

    static class Book {

        String title;

        Double value;

        public Book(String title, Double value) {
            super();
            this.title = title;
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public Double getValue() {
            return value;
        }
    }

    // =========================================================================================================================================
    static void test01_04() {
        // What will the following code print?

        AtomicInteger ai = new AtomicInteger();

        Stream<Integer> stream = Stream.of(11, 11, 22, 33).parallel();

        stream.filter(e -> {
            ai.incrementAndGet();
            return e % 2 == 0;
        });

        // 0

        // Notice that filter is an intermediate operation. It does not do anything until a terminal operation is invoked on the stream.
        // Therefore, in this case, ai will remain 0.
        System.out.println(ai);
    }

    // =========================================================================================================================================
    static void test01_05() {

        // What will the following code print when compiled and run?

        List<String> names = Arrays.asList("charles", "chuk", "cynthia", "cho", "cici");

        // int x = names.stream().filter(name->name.length()>4).collect(Collectors.counting()); 
        // System.out.println(x);

        // Compilation failure

        // Collector.counting returns a Collector that returns a object Long.
        // You cannot assign it to an int without a cast. If you make it long, it will print 2 because there are two elements with 
        // length greater than 4.

        long x = names.stream().filter(name -> name.length() > 4).collect(Collectors.counting()); // change to long type

        System.out.println(x);

        // Explanation
        // public static <T> Collector<T,?,Long> counting()
        // Returns a Collector accepting elements of type T that counts the number of input elements. 
        // If no elements are present, the result is 0.
    }

    // =========================================================================================================================================
    static void test01_06() {

        // What will the following code print?

        Object v1 = IntStream.rangeClosed(10, 15) //
                .boxed() //
                .filter(x -> x > 12) //
                .parallel() //
                .findAny(); //

        Object v2 = IntStream.rangeClosed(10, 15) //
                .boxed() //
                .filter(x -> x > 12) //
                .sequential()//
                .findAny();

        // <An Optional containing 13, 14, or 15> : <An Optional conataining 13, 14, or 15>
        System.out.println(v1 + ":" + v2);

        // (Note: < and > in the options below denote the possible output and not the sign themselves.)
        //
        // Explanation
        // Since the first stream is made parallel, it may be partitioned into multiple pieces and each piece may be processed by 
        // a different thread. findAny may, therefore, return a value from any of those partitions. 
        // Hence, any number from 13 to 15 may be printed.
        //
        // The second stream is sequential and therefore, ideally, findAny should return the first element. However, findAny is 
        // deliberately designed to be non-deterministic. Its API specifically says that it may return any element from the stream. 
        // If you want to select the first element, you should use findFirst.
        //
        // Further findAny returns Optional object. Therefore, the output will be Optional[15] instead of just 15 (or any other number).
    }

    // =========================================================================================================================================
    static void test01_07() {

        boolean result = false;

        // Given:
        List<Integer> ls = Arrays.asList(11, 11, 22, 33, 33, 55, 66);

        // Which of the following expressions will return true?
        //
        result = ls.stream().noneMatch(x -> x % 11 > 0);
        // noneMatch returns true only if none of the elements in the stream satisfy the given Predicate.
        // Here, all the elements are divisible by 11 and x%11 will be 0 for each element.
        // Therefore, the given Predicate will return false for every element, causing noneMatch to return true.
        System.out.println(result);
        //
        // and
        //
        result = ls.stream().distinct().anyMatch(x -> x == 11);
        // anyMatch(Predicate<? super T> predicate) returns whether any elements of this stream match the provided predicate.
        // May not evaluate the predicate on all elements if not necessary for determining the result.
        // If the stream is empty then false is returned and the predicate is not evaluated. This is a short-circuiting terminal operation.
        System.out.println(result);
        //
        // Errors
        //
        // ls.stream().anyMatch(44);
        // This will not compile because anyMatch requires a Predicate object as an argument, not an int.
        //
        // ls.stream().distinct().anyMatch(11);
        // This will not compile because anyMatch requires a Predicate object as an argument, not an int.
        //
        // ls.stream().distinct().allMatch(11);
        // This will not compile because anyMatch requires a Predicate object as an argument, not an int.
    }

    // =========================================================================================================================================
    static void test01_08() {
        // What will the following code print when compiled and run?

        List<Integer> iList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        Predicate<Integer> p = x -> x % 2 == 0;

        List newList = iList.stream().filter(x -> x > 3).filter(p).collect(Collectors.toList());

        System.out.println(newList); // [4, 6]

        // The given code illustrates how you can filter a stream multiple times. The important thing here is that only the elements that 
        // satisfy the filter condition remain in the stream. Rest are eliminated.
        //
        // Here, the first condition (implemented by Predicate p) is that the numbers must be even. This means 1, 3, 5, and 7 are out. 
        // Next, the second condition (specified directly in the call to filter method using lambda expression x->x>3 means that the number 
        // must be greater than 3. This means only 4 and 6 will be left.
    }

    // =========================================================================================================================================
    //
    static void test01_09() {
        // Given:
        List<String> l1 = Arrays.asList("a", "b");
        List<String> l2 = Arrays.asList("1", "2");
        // Which of the following lines of code will print the following values? a b 1 2

        // The objective of flatMap is to take each element of the current stream and replace that element with elements contained in the stream returned
        // by the Function that is passed as an argument to flatMap. It is perfect for the requirement of this question. You have a stream that contains Lists.  
        // So you need a Function object that converts a List into a Stream of elements. Now, List does have a method named stream() that does just that.
        // It generates a stream of its elements. Therefore, the lambda expression x-> x.stream() can be used here to create the Function object.

        Stream.of(l1, l2).flatMap((x) -> x.stream()).forEach((x) -> System.out.println(x));
    }

    // =========================================================================================================================================
    //
    static void test01_10() {
        List<Item> l = Arrays.asList(new Item(1, "Screw"), new Item(2, "Nail"), new Item(3, "Bolt"));
        // Which of the following options can be inserted in the above code independent of each other, so that the code will print BoltNailScrew?

        // 1. The call to map converts the stream of Items to a stream of Strings. 
        // 2. The call to sorted() sorts the stream of String by their natural order, which is what we want here.
        l.stream().map(i -> i.getName()).sorted().forEach(System.out::println);
    }

    // =========================================================================================================================================

    public static void main(String[] args) {
        test01_07();
    }
}
