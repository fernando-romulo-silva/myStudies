package br.fernando.ch09_Streams_Objective.par12_tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print when compiled and run?

        List<Integer> iList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Predicate<Integer> p = x -> x % 2 == 0;

        List newList = iList.stream().filter(x -> x > 3).filter(p).collect(Collectors.toList());
        System.out.println(newList);

        // [4, 6]

        // The given code illustrates how you can filter a stream multiple times. The important thing here is that only the elements that 
        // satisfy the filter condition remain in the stream. Rest are eliminated.
        //
        // Here, the first condition (implemented by Predicate p) is that the numbers must be even. This means 1, 3, 5, and 7 are out. 
        // Next, the second condition (specified directly in the call to filter method using lambda expression x->x>3 means that the number 
        // must be greater than 3. This means only 4 and 6 will be left.        

    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // Given:

        List<String> l1 = Arrays.asList("a", "b");
        List<String> l2 = Arrays.asList("1", "2");

        // Which of the following lines of code will print the following values?

        Stream.of(l1, l2).flatMap((x) -> x.stream()).forEach((x) -> System.out.println(x));

        // Explanation
        // The objective of flatMap is to take each element of the current stream and replace that element with elements contained in the
        // stream returned by the Function that is passed as an argument to flatMap.
        //
        // It is perfect for the requirement of this question. You have a stream that contains Lists.
        //
        // So you need a Function object that converts a List into a Stream of elements.
        //
        // Now, List does have a method named stream() that does just that. It generates a stream of its elements.
        //
        // Therefore, the lambda expression x->x.stream() can be used here to create the Function object.

        final Stream<List<String>> stream01 = Stream.of(l1, l2);

        stream01 //
                .flatMap(x -> x.stream()) //
                .forEach(x -> System.out.println(x));

    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given:

        List<String> letters = Arrays.asList("j", "a", "v", "a");

        // Which of the following code snippets will print JAVA?

        // 1º
        UnaryOperator<String> uo = str -> str.toUpperCase();
        letters.replaceAll(uo);
        letters.forEach(System.out::print);

        // The replaceAll method replaces each element of this list with the result of applying the operator to that element.
        //
        // str->str.toUpperCase() is a valid lambda expression that captures UnaryOperator function.
        //
        // The forEach method performs the given action for each element of the Iterable until all elements have been processed by feeding
        // each element to the given Consumer. System.out::print is a valid method reference that can be used to create a Consumer instance.

        // 2º
        letters.forEach(letter -> System.out.print(letter.toUpperCase()));

        // letter->System.out.print(letter.toUpperCase()) is a valid lambda expression that captures the Consumer functional interface.

        // 3º
        System.out.print(letters.stream().collect(Collectors.joining()).toUpperCase());

        // Collectors.joining() returns a Collector that operators on a Stream containing CharSequences (String extends CharSequence)
        // and joins all the elements into one big String.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What changes can be made to the following code so that it will print :

        // Old Rating A
        // New Rating R

        List<Character> ratings = Arrays.asList('U', 'R', 'A');

        ratings.stream() //
                .filter(x -> x == 'A') // 1
                .peek(x -> System.out.println("Old Rating " + x)) // 2
                .map(x -> x == 'A' ? 'R' : x) // 3
                .peek(x -> System.out.println("New Rating " + x)); // 4

        // Replace //4 with
        // .forEach(x->System.out.println("New Rating "+x))

        ratings.stream() //
                .filter(x -> x == 'A') // 1
                .peek(x -> System.out.println("Old Rating " + x)) // 2
                .map(x -> x == 'A' ? 'R' : x) // 3
                .forEach(x -> System.out.println("New Rating " + x)); // 4        

        // Explanation
        //
        // To answer this question, you need to know two things - distinction between "intermediate" and "terminal" operations and which
        // operations of Stream are "intermediate" operations.
        //
        // The distinction between an intermediate operation and a termination operation is that an intermediate operation is lazy
        // while a terminal operation is not.
        //
        // When you invoke an intermediate operation on a stream, the operation is not executed immediately.
        // It is executed only when a terminal operation is invoked on that stream.
        //
        // In a way, an intermediate operation is memorized and is recalled as soon as a terminal operation is invoked.
        //
        // You can chain multiple intermediate operations and none of them will do anything until you invoke a terminal operation, at
        // which time, all of the intermediate operations that you invoked earlier will be invoked along with the terminal operation.
        //
        // It is easy to identify which operations are intermediate and which are terminal.
        // All intermediate operations return Stream (that means, they can be chained), while terminal operations don't.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Given the following code:

        List<Integer> al = Arrays.asList(100, 200, 230, 291, 43);

        // Which of the following options will correctly print the number of elements that are less than 200?

        long count = al.stream().filter((i) -> i < 200).count(); // count return a long

        System.out.println(count);
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code print when compiled and run?

        Stream<List<String>> s1 = Stream.of(//
                Arrays.asList("a", "b"), //
                Arrays.asList("a", "c"));

        Stream<String> news = s1.filter(s -> s.contains("c")).flatMap(olds -> olds.stream());
        news.forEach(System.out::print);

        // ac

        // Explanation
        // The given code first creates a Stream, where each element is a List<String>.
        //
        // Next, it filters the stream using a criteria s.contains("c");, which means only the elements that satisfy the criteria will
        // remain in the resulting stream. Hence, the list containing a, b will be removed.
        //
        // Next, it uses the flatMap method to generate elements of the new stream. The flatMap method returns a stream consisting of the
        // results of replacing each element of this stream with the contents of a mapped stream produced by applying the provided
        // mapping function to each element.
        //
        // In this case, our original stream has only one element, which is a List of Strings.
        // The lambda expression passed to this method converts this List object into a stream using List's stream() method.
        //
        // Finally, it uses the forEach method of Stream to print each element. Therefore, the code will print ac.
    }

    // =========================================================================================================================================
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

        public String getName() {
            return name;
        }

        public String toString() {
            return name + ":" + grade;
        }
    }

    static void test01_08() throws Exception {
        // Given Student class
        // What can be inserted in the code below so that it will print:
        // {C=[S3], A=[S1, S2]}
        //

        List<Student> ls = Arrays.asList(new Student("S1", Student.Grade.A), new Student("S2", Student.Grade.A), new Student("S3", Student.Grade.C));

        Map<Student.Grade, List<String>> grouping = ls.stream() //
                .collect(Collectors.groupingBy(Student::getGrade, Collectors.mapping(Student::getName, Collectors.toList())));

        System.out.println(grouping);

        // This code illustrates how to cascade Collectors.
        // Here, you are first grouping the elements by Grade and then collecting each element of a particular grade into a list after
        // mapping it to a String. This will produce the required output.

        // Another Mapping

        ls.stream().collect(Collectors.mapping(s -> s.getName(), Collectors.toList()));

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

        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17); // 1

        Stream<Integer> primeStream = primes.stream(); // 2

        Predicate<Integer> test1 = k -> k < 10; // 3

        long count1 = primeStream.filter(test1).count();// 4

        Predicate<Integer> test2 = k -> k > 10; // 5

        long count2 = primeStream.filter(test2).count(); // 6

        System.out.println(count1 + " " + count2); // 7

        // Identify correct statements.

        // It will print 34 if lines 4 to 7 are replaced with:

        primeStream.collect(Collectors.partitioningBy(test1, Collectors.counting())).values().forEach(System.out::print);
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Given that Book is a valid class with appropriate constructor and getTitle and getPrice methods that return a String and
        // a Double respectively, what will the following code print?

        List<Book> books = Arrays.asList( //
                new Book("Gone with the wind", 5.0), //
                new Book("Gone with the wind", 10.0), //
                new Book("Atlas Shrugged", 15.0));

        books.stream() //
                .collect(Collectors.toMap((b -> b.getTitle()), b -> b.getPrice())) //
                .forEach((a, b) -> System.out.println(a + " " + b));

        // It will throw an exception at run time.

        // The Collector created by Collectors.toMap throws java.lang.IllegalStateException if an attempt is made to store a key
        // that already exists in the Map.
        //
        // If you want to collect items in a Map and if you expect duplicate entries in the source, you should use
        // Collectors.toMap(Function, Function, BinaryOperator) method.
        // The third parameter is used to merge the duplicate entries to produce one entry. For example, in this case, you can do:

        books.stream() //
                .collect(Collectors.toMap(b -> b.getTitle(), b -> b.getPrice(), (v1, v2) -> v1 + v2)) //
                .forEach((a, b) -> System.out.println(a + " " + b));

        // This Collector will sum the values of the entries that have the same key. Therefore, it will print :
        // Gone with the wind 15.0
        // Atlas Shrugged 15.0
    }

    private static class Book {

        private int id;

        private String title;

        private String genre;

        private String author;

        private double price;

        public Book(String title, double price) {
            super();
            this.title = title;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getGenre() {
            return genre;
        }

        public String getAuthor() {
            return author;
        }

        public double getPrice() {
            return price;
        }

        public String getTitle() {
            return title;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        List<Integer> ls = Arrays.asList(1, 2, 3);
        // Which of the following options will compute the sum of all Integers in the list correctly?

        double sum01 = ls.stream().reduce(0, (a, b) -> a + b);
        // The reduce method performs a reduction on the elements of this stream, using the provided identity value and an associative 
        // accumulation function, and returns the reduced value.

        double sum02 = ls.stream().mapToInt(x -> x).sum();
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_03();
    }
}
