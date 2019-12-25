package br.fernando.ch09_Streams_Objective.par12_tests;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    static class Item {

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
    static void test01_01() throws Exception {
        List<Item> l = Arrays.asList(new Item(1, "Screw"), new Item(2, "Nail"), new Item(3, "Bolt"));

        // Which of the following options can be inserted in the above code independent of each other, so that the code will print BoltNailScrew?

        l.stream() //
                // 1. This option uses Comparator's comparing method that accepts a function that extracts a Comparable sort key, and returns a
                // Comparator that compares by that sort key. Note that this is helpful only if the type of the object returned by the function
                // implements Comparable. Here, it returns a String, which does implement Comparable and so it is ok.
                //
                // 2. Although the map part is not required because Item class overrides the toString method to print the name anyway, it is valid.
                .sorted(Comparator.comparing(a -> a.getName())).map((i) -> i.getName())
                //
                .forEach(System.out::print);

        // or

        l.stream()
                // 1. The call to map converts the stream of Items to a stream of Strings.
                //
                // 2. The call to sorted() sorts the stream of String by their natural order, which is what we want here.
                //
                .map((i) -> i.getName()).sorted()
                //
                .forEach(System.out::print);
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Which of the following options will print 10?

        // Answer 01

        IntStream is01 = IntStream.rangeClosed(1, 4);
        int sum01 = is01.reduce(0, (a, b) -> a + b);
        System.out.println(sum01);

        // 1. IntStream.rangeClosed(1, 4) returns 1, 2, 3, and 4 elements in the stream.
        //
        // 2. ((a, b)-> a + b) correctly captures IntBinaryOperator function that sums all the two given values.
        //
        // 3. The reduce method uses a function that combines two values and produces one value repeatedly to reduce all the elements of the
        // stream into one final value.  Notice that the first argument to reduce method is called the "identity" value. This value is the
        // basically the starting value of the result. The first element of the stream is combined with this value.
        // The result is then combined with the second value and so on. This ensures that a final result is always returned even if
        // the stream contains no element.

        // Answer 02

        IntStream is02 = IntStream.range(1, 5);
        OptionalInt sum02 = is02.reduce((a, b) -> a + b);
        System.out.println(sum02.orElse(0));

        // This is same as option 3 except that we are using range instead of rangeClosed.
        // Also notice that here we are not passing an identity value as the first argument to reduce method.
        //
        // Thus, if the stream contains no element, there will not be any result value.
        // For this reason, the return type of this flavor of the reduce method is an OptionInt instead of int.
        // The orElse(0) of OptionalInt will return 0 if the OptionalInt contains null
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given Student class
        // What can be inserted in the code below so that it will print:
        // {C=[S3], A=[S1, S2]}

        List<Student> ls = Arrays.asList(new Student("S1", Student.Grade.A), new Student("S2", Student.Grade.A), new Student("S3", Student.Grade.C));

        Map<Student.Grade, List<String>> grouping = ls.stream().collect( //
                Collectors.groupingBy(Student::getGrade, //
                        Collectors.mapping(Student::getName, Collectors.toList())) //
        );

        // This code illustrates how to cascade Collectors.
        // Here, you are first grouping the elements by Grade and then collecting each element of a particular grade into a list after mapping
        // it to a String. This will produce the required output.
    }

    static class Student {

        static enum Grade {
            A,
            B,
            C,
            D,
            F
        }

        private final String name;

        private final Grade grade;

        public Student(String name, Grade grade) {
            this.name = name;
            this.grade = grade;
        }

        public Grade getGrade() {
            return grade;
        }

        public String toString() {
            return name + ":" + grade;
        }

        public String getName() {
            return name;
        }
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What will the following code print?

        List<String> ls = Arrays.asList("Tom Cruise", "Tom Hart", "Tom Hanks", "Tom Brady");

        Predicate<String> p = str -> {
            System.out.println("Looking...");
            return str.indexOf("Tom") > -1;
        };

        boolean flag = ls.stream().filter(str -> str.length() > 8).allMatch(p);

        // Looking...
        // Looking...
        // Looking...

        // Explanation
        // Remember that filter is an intermediate operation, which means it will not execute until a terminal operation is invoked on the stream.
        // allMatch is a short circuiting terminal operation. Thus, when allMatch is invoked, the filter method will be invoked and it will keep
        // only those elements in the stream that satisfy the condition given in the filter i.e. the string must be longer than 8 characters.  
        // After this method is done, only three elements will be left in the stream. When allMatch is invoked, the code in predicate will
        // be executed for each element until it finds a mismatch. Thus, Looking... will be printed three times.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
        // Given:

        Stream<Integer> strm1 = Stream.of(2, 3, 5, 7, 11, 13, 17, 19); // 1

        Stream<Integer> strm2 = strm1.filter(i -> {
            return i > 5 && i < 15;
        }); // 2

        strm2.forEach(System.out::print); // 3

        // Which of the following options can be used to replace line at //2 without affecting the output?

        Stream<Integer> strm2_new = strm1.parallel().filter(i -> i > 5).filter(i -> i < 15).sequential();
        // Stream pipelines may execute either sequentially or in parallel. This execution mode is a property of the stream.
        //
        // Streams are created with an initial choice of sequential or parallel execution. (For example, Collection.stream() creates a 
        // sequential stream, and Collection.parallelStream() creates a parallel one.) 
        // This choice of execution mode may be modified by the BaseStream.sequential() or BaseStream.parallel() methods.
        //
        // It is not documented by Oracle exactly what happens when you change the stream execution mode multiple times in a pipeline. 
        // It is not clear whether it is the last change that matters or whether operations invoked after calling () parallel can be executed 
        // in parallel and operations invoked after calling sequential() will be executed sequentially.
        //
        // In either case, in the given line of code, the end result will be sequential.

        strm2_new.forEach(System.out::print);

    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Identify the correct statements about Java Stream API.

        // Streams support aggregate operations.
        // Streams support several aggregate operations such as forEach, count, average, and sum.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // What can be inserted in the code below so that it will print:
        // {C=[S3:C], A=[S1:A, S2:A]}

        List<Student> ls = Arrays.asList(new Student("S1", Student.Grade.A), new Student("S2", Student.Grade.A), new Student("S3", Student.Grade.C));

        Map<Student.Grade, List<Student>> grouping = ls.stream().collect(Collectors.groupingBy(s -> s.getGrade()));

        // Explanation
        // This code illustrates the usage of the simplest of the Collectors methods.
        //
        // Collectors.groupingBy(s -> s.getGrade()) returns a Collector that applies a function on the elements of a stream to get a key and
        // then group the elements of the stream by that key into lists.
        //
        // Stream's collect method uses this Collector and returns a Map where the key is the key returned by the function and the value is a
        // List containing all the elements that returned the same key.
        //
        // Here, the function s ->s.getGrade() provides the Grades as the key. The keys returned by this function are, therefore, A, A, and
        // C respectively. Since there are two unique keys (A, C), the Map returned by the collect method will contain
        // two key-value pairs: {C=[S3:C], A=[S1:A, S2:A]}
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    static void test01_11() {

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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
