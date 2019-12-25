package br.fernando.ch09_Streams_Objective.par12_tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.fernando.ch09_Streams_Objective.par12_tests.Test01.Book;
import br.fernando.ch09_Streams_Objective.par12_tests.Test02.Person;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test08 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
        Map<String, Double> bookMap = books.stream().collect( //
                Collectors.toMap(b -> b.getTitle(), b -> b.getValue()));

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

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
        long count = ls.stream().map(func).peek(System.out::print).count(); // , it will print 149.
        System.out.println(count);
        //
        // Note that you can invoke at most one terminal operation on a stream and that too at the end.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Which of the following method(s) of java.util.stream.Stream interface is/are used for reduction?

        // collect
        //
        // count
        // Returns the count of elements in this stream. This is a special case of a reduction and is equivalent to:
        // return mapToLong(e -> 1L).sum();
        // This is a terminal operation.

        // The streams classes have multiple forms of general reduction operations, called reduce() and collect(), as well as multiple
        // specialized reduction forms such as sum(), max(), or count().
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following code print when compiled and run?
        List<Integer> ls = Arrays.asList(1, 2, 3);

        // ls.stream()
        // .forEach(System.out::print).map(a->a*2)
        // .forEach(System.out::print);

        // Compilation failure
        // Remember that forEach is a terminal operation. It returns void. This means that you cannot chain methods after calling forEach.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Identify correct statements about the following code:

        List<String> vals = Arrays.asList("a", "b");

        String join = vals.parallelStream().reduce("_", (a, b) -> a.concat(b));

        System.out.println(join);

        // It will print either _ab or _a_b
        //
        // Since we are creating a parallel stream, it is possible for both the elements of the stream to be processed by two different threads.
        // In this case, the identity argument will be used to reduce both the elements.
        //
        // Thus, it will print _a_b. It is also possible that the result of the first reduction ( _a ) is reduced further using the second
        // element (b). In this case, it will print _ab.
        //
        // Even though the elements may be processed out of order individualy in different threads, the final output will be produced by
        // joining the individual reduction results in the same order. Thus, the output can never have b before a
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Given Peson class
        // What will the following code print?
        List<Person> friends = Arrays.asList(new Person("Bob", 31), new Person("Paul", 32), new Person("John", 33));

        double averageAge = friends.stream() //
                .filter(f -> f.getAge() < 30) //
                .mapToInt(f -> f.getAge()) //
                .average() //
                .getAsDouble(); // error here

        System.out.println(averageAge);

        // It will throw an exception at runtime.
        //
        // Explanation
        // The given code chains three operations to a stream. First, it filters out all the element that do not satisfy the condition f.getAge()<30,
        // which means there will no element in the stream, second, it maps each Person element to an int using the mapping function f.getAge().
        // Since there is no element in the stream anyway, the stream remains empty.
        // Finally, the average() method computes the average of all the elements, which is 0.0.
        //
        // However, there is a problem with this code. The average() method actually returns an OptionalDouble (and not Double).
        // Since the stream contains no element and the average() method returns an OptionalDouble containing OptionalDouble.empty.
        //
        // Therefore, getAsDouble method throws a java.util.NoSuchElementException.  
        //
        // To avoid this problem, instead of getAsDouble, you should use orElse(0.0). That way, if the OptionalDouble is empty, it will return 0.0.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Identify correct statement(s) about the following code:

        Optional<String> stro = Optional.of(getValue());// 1
        System.out.println(stro.isPresent());// 2
        System.out.println(stro.get());// 3
        System.out.println(stro.orElse(null));// 4

        // It will throw a NullPointerException at //1
        // Optional.of method throws NullPointerException if you try to create an Optional with a null value. If you expect the argument to be null,
        // you should use Optional.ofNullable method, which returns an empty Optional if the argument is null.
    }

    private static String getValue() {
        return null;
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given the following code:

        List<Integer> al = Arrays.asList(100, 200, 230, 291, 43);

        // Which of the following options will correctly print the number of elements that are less than 200?

        long count = al.stream().filter((i) -> i < 200).count(); // count return a long

        System.out.println(count);
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // What will be the result of compilation and execution of the following code ?

        IntStream is1 = IntStream.range(0, 5); // 1
        OptionalDouble x = is1.average(); // 2
        System.out.println(x); // 3

        // It will print OptionalDouble[2.0]

        // Explanation
        //
        // There are three things that you need to watch out for here:
        //
        // 1. The range method includes the starting number but not the ending number. Thus, range(0, 5), will give you numbers 0, 1, 2, 3, and 4.
        // (The rangeClosed method includes the ending number also.)
        //
        // 2. The average method of all numeric streams (i.e. IntStream, LongStream, and DoubleStream) returns an OptionalDouble and not a double.
        // It never returns a null. (If there are no elements in the stream, it returns OptionalDouble.empty but not 0).
        // Note that this is unlike the sum method which always returns a primitive value of the same type as the type of the stream
        // (i.e. int, long, or double). In this case, the average of the given 5 numbers is 2, so it returns an OptionalDouble containing 2.0.
        //
        // 3. OptionalDouble's toString method returns a String of the form OptionalDouble[<double value>].
        // Therefore, the given code prints OptionalDouble[2.0].
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {

        // What can be inserted in the code below so that it will print 1 2 3?
        class MyProcessor {

            Integer value;

            public MyProcessor(Integer value) {
                this.value = value;
            }

            public void process() {
                System.out.println(value + " ");
            };
        }

        List<Integer> ls = Arrays.asList(1, 2, 3);

        ls.stream() //
                .map(MyProcessor::new) // 
                .forEach(MyProcessor::process);

        // Here, map method does have an implicit Integer object in the context, which is in fact the current element of the list. 
        // This element will be passed as an argument to the MyProcessor constructor.
        //
        // Similarly, forEach has a MyProcessor object in the context while invoking the process method. Since process is an instance 
        // method of MyProcessor, the MyProcessor instance that is available in the context will be used to invoke the process method.
        //
        // An important point to understand with method or constructor references is that you can never pass arguments while referring 
        // to a constructor or a method. 
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
