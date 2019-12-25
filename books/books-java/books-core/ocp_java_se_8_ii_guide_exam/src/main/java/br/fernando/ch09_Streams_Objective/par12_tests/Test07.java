package br.fernando.ch09_Streams_Objective.par12_tests;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.fernando.ch09_Streams_Objective.par12_tests.Test06.Item;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test07 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        List<Item> l = Arrays.asList(new Item(1, "Screw"), new Item(2, "Nail"), new Item(3, "Bolt"));

        // Which of the following options can be inserted in the above code independent of each other, so that the code will print BoltNailScrew?

        l.stream() //
                // 1. This option uses Comparator's comparing method that accepts a function that extracts a Comparable sort key, and returns a
                // Comparator that compares by that sort key. Note that this is helpful only if the type of the object returned by the function
                // implements Comparable. Here, it returns a String, which does implement Comparable and so it is ok.
                //
                // 2. Although the map part is not required because Item class overrides the toString method to print the name anyway, it is valid.
                .sorted(Comparator.comparing(a -> a.getName())) //
                .map((i) -> i.getName())
                //
                .forEach(System.out::print);

        // or

        l.stream()
                // 1. The call to map converts the stream of Items to a stream of Strings.
                //
                // 2. The call to sorted() sorts the stream of String by their natural order, which is what we want here.
                //
                .map((i) -> i.getName()) //
                .sorted()
                //
                .forEach(System.out::print);
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        List<Integer> ls = Arrays.asList(1, 2, 3);
        // Which of the following options will compute the sum of all Integers in the list correctly?

        double sum01 = ls.stream().reduce(0, (a, b) -> a + b);
        // The reduce method performs a reduction on the elements of this stream, using the provided identity value and an associative
        // accumulation function, and returns the reduced value.

        double sum02 = ls.stream().mapToInt(x -> x).sum();
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {

        // Which of the following options correctly replaces the line marked //1 with code that uses method reference?

        process(() -> new MyProcessor()); // 1 REPLACE THIS LINE OF CODE

        process(MyProcessor::new); // This is the correct syntax for referring to a constructor

        // Explanation
        // Observe that the TestClass's static method process takes Supplier<MyProcessor> as an argument.
        // The lambda expression // ()->new MyProcessor() // creates a Supplier with the implementation of its functional method named get
        // that just returns a new MyProcessor object.
        // If it makes it easier to understand, you can think of this lambda expression in terms of an anonymous class like this:

        new Supplier<MyProcessor>() {

            public MyProcessor get() {
                return new MyProcessor(); // the body of a lambda expression becomes the body of the functional method of the functional interface.    
            }
        };

        // The expression new MyProcessor() in the lambda expression can be replaced by MyProcessor::new.
    }

    private static class MyProcessor {

        public void process() {
            System.out.println("Processing ");
        }
    }

    public static void process(Supplier<MyProcessor> s) {
        s.get().process();
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
        // Identify the correct statements about the following code -

        IntStream is1 = IntStream.of(1, 3, 5); // 1
        OptionalDouble x = is1.filter(i -> i % 2 == 0).average(); // 2
        System.out.println(x); // 3

        IntStream is2 = IntStream.of(2, 4, 6); // 4
        int y = is2.filter(i -> i % 2 != 0).sum(); // 5
        System.out.println(y); // 6

        // Successful compilation
        //
        // Explanation
        // Remember that average() methods of all numeric streams (i.e. IntStream, DoubleStream, and LongStream) returns an OptionalDouble.
        // If the stream has no elements (as is the case in this question), the returned OptionalDouble will be empty (not 0 or null).
        //
        // But the sum() methods of the numeric streams return a primitive value of the same type i.e. IntStream returns an int,
        // DoubleStream returns a double, and LongStream returns a long. If the stream is empty, the sum() method returns 0.
        //
        // Thus, the given code will compile and run without any issue. It will print : OptionalDouble.empty 0
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code print?
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", 1);

        map1.merge("b", 1, (i1, i2) -> i1 + i2);
        map1.merge("c", 3, (i1, i2) -> i1 + i2);

        System.out.println(map1);
        // {a=1, b=2, c=3}

        // Explanation
        //
        // If the specified key is not already associated with a value or is associated with null, associates it with the given non-null value.
        // Otherwise, replaces the associated value with the results of the given remapping function, or removes if the result is null.
        // This method may be of use when combining multiple mapped values for a key.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        List<String> al = Arrays.asList("aa", "aaa", "b", "cc", "ccc", "ddd", "a");

        // Which of the following options will correctly print the number of elements that will come before the string "c" 
        // if the list were sorted alphabetically?

        // INSERT CODE HERE
        long count = al.stream().filter((str) -> str.compareTo("c") < 0).count();

        System.out.println(count);

        // Explanation
        //
        // This is a very straight forward question. filter method will filter the stream based on the given criteria and count method will
        // count the number of items in the resulting list. There is no need for sorting the list because you just want to count the number
        // of items that satisfy the given criteria.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
        // Given:
        String sentence = "Life is a box of chocolates, Forrest. You never know what you're gonna get."; // 1

        // Optional<String> theword = Stream.of(sentence.split("[ ,.]")).anyMatch(w->w.startsWith("g")); //2
        // System.out.println(theword.get()); //3

        // It will fail to compile.
        // anyMatch returns a boolean and not an Optional. Therefore, //2 will not compile.
        // The expression

        boolean anyMatch = Stream.of(sentence.split("[ ,.]")).anyMatch(w -> w.startsWith("g")); // will actually just return true.
        System.out.println(anyMatch);
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
