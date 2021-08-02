package br.fernando.ch14_tests.part06_final.test04;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.fernando.ch08_lambda_expressions_and_functional_interfaces_Objective.par05_tests.Test03;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04_03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print when compiled and run?

        IntStream is1 = IntStream.range(1, 3); // 1, 2
        IntStream is2 = IntStream.rangeClosed(1, 3); // 1, 2, 3
        IntStream is3 = IntStream.concat(is1, is2); // 1, 2, 1, 2, 3
        Object val = is3.boxed() //
                .collect(Collectors.groupingBy(k -> k)) // Map<Integer, List<Integer>> {1=[1, 1], 2=[2, 2], 3=[3]}
                .get(3); // get key 3 = [3]

        // [3]
        //
        // Explanation
        // There are several things going on here:
        //
        // 1. IntStream.range returns a sequential ordered IntStream from startInclusive (inclusive) to endExclusive (exclusive) by an incremental
        // step of 1. Therefore, is1 contains 1, 2.
        //
        // 2. IntStream.rangeClosed returns a sequential ordered IntStream from startInclusive (inclusive) to endInclusive (inclusive) by an
        // incremental step of 1. Therefore, is2 contains 1, 2, 3.
        //
        // 3. IntStream.concat returns a lazily concatenated stream whose elements are all the elements of the first stream followed by all the
        // elements of the second stream. Therefore, is3 contains 1, 2, 1, 2, 3
        //
        // 4. is3 is a stream of primitive ints. is3.boxed() returns a new Stream containing Integer objects instead of primitives.
        // This allows the use various flavors of collect method available in non-primitive streams. [IntStream does have one collect method
        // but it does not take a Collector as argument.]
        //
        // 5. Collectors.groupingBy(k->k) creates a Collector that groups the elements of the stream by a key returned by the function k->k,
        // which is nothing but the value in the stream itself.
        // Therefore, it will group the elements into a Map<Integer, List<Integer>> containing: {1=[1, 1], 2=[2, 2], 3=[3]}
        //
        // 6. Finally, get(3) will return [3].
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following code print when compiled and run?
        Stream<List<String>> s1 = Stream.of(Arrays.asList("a", "b"), Arrays.asList("a", "c"));

        Stream<String> news = s1.filter(s -> s.contains("c")).flatMap(olds -> olds.stream());

        news.forEach(System.out::print); // ac

        // Explanation
        // The given code first creates a Stream, where each element is a List<String>. Next, it filters the stream using a criteria s.contains("c");,
        // which means only the elements that satisfy the criteria will remain in the resulting stream. Hence, the list containing a, b will be removed.
        //
        // Next, it uses the flatMap method to generate elements of the new stream. The flatMap method returns a stream consisting of the results of
        // replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.
        //
        // In this case, our original stream has only one element, which is a List of Strings. The lambda expression passed to this method converts this
        // List object into a stream using List's stream() method.
        //
        // Finally, it uses the forEach method of Stream to print each element. Therefore, the code will print ac.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // How will you initialize a SimpleDateFormat object so that the following code will print the full text time zone of the given date?

        SimpleDateFormat sdf = new SimpleDateFormat("zzzz", Locale.FRANCE);
        // Remember that if the number of pattern letters is 4 or more, the full form is used; otherwise a short or abbreviated form
        // is used if available. For parsing, both forms are accepted, independent of the number of pattern letters.

        System.out.println(sdf.format(new Date()));
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Which of the following code fragments will you use to create an ExecutorService?

        // Executors

        // .newSingleThreadExecutor();

        // Explanation
        //
        // You need to remember the following points about a few important classes in java.util.concurrent package:
        //
        // 1. ExecutorService interface extends Executor interface. While Executor allows you to execute a Runnable, ExecutorService
        // allows you to execute a Callable.
        //
        // 2. Executors is a utility class that provides several static methods to create instances of ExecutorService. All such methods start
        // with new e.g. newSingleThreadExecutor().
        // You should at least remember the following methods: newFixedThreadPool(int noOfThreads), newSingleThreadExecutor(), newCachedThreadPool(),
        // newSingleThreadScheduledExecutor(), newScheduledThreadPool(int corePoolSize).

        ExecutorService es01 = Executors.newFixedThreadPool(2);

        ExecutorService es02 = Executors.newSingleThreadExecutor();

        ExecutorService es03 = Executors.newCachedThreadPool();

        ExecutorService es04 = Executors.newSingleThreadScheduledExecutor();

        ExecutorService es05 = Executors.newScheduledThreadPool(10);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // An existing application contains the following code: AmountValidator e Account
        // Which interface from java.util.function package can be used to refactor this code?

        // Predicate
        // Represents a predicate (boolean-valued function) of one argument. This interface is used when you want to check
        // for some condition. For example, the given code can be easily refactored as follows:
        //

        class Account2 {

            public void updateBalance(double bal) {
                Predicate<Double> p = val -> val >= 0.0; // use lamba expression to create a Predicate
                boolean isOK = p.test(bal); // other irrelevant code
            }
        }

        // There is no need to create the AmountValidator interface and the anonymous class.
    }

    static interface AmountValidator {

        public boolean checkAmount(double value);
    }

    static public class Account {

        public void updateBalance(double bal) {
            boolean isOK = new AmountValidator() {

                public boolean checkAmount(double val) {
                    return val >= 0.0;
                }
            }.checkAmount(bal); // other irrelevant code
        }

    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print when compiled and run?
        Stream<Integer> values = IntStream.rangeClosed(10, 15).boxed(); // 1
        Object obj = values.collect(Collectors.partitioningBy(x -> x % 2 == 0)); // 2
        System.out.println(obj);

        // {false=[11, 13, 15], true=[10, 12, 14]}
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Identify the correct statements about Java Stream API.

        // Streams support aggregate operations.
        // Streams support several aggregate operations such as forEach, count, average, and sum.
    }

    // =========================================================================================================================================
    static void test01_08(String records1, String records2) throws Exception {
        // Consider the following code:
        try (InputStream is = new FileInputStream(records1); OutputStream os = new FileOutputStream(records2);) { // 1

            // if (os == null) os = new FileOutputStream("c:\\default.txt"); // 2 // don't compile here

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) { // 3
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }
        } catch (IOException e) { // 4 e.printStackTrace(); }
        }

        // The program will not compile because line //2 is invalid.
        // The auto-closeable variables defined in the try-with-resources statement are implicitly final. Thus, they cannot be reassigned.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Consider the follwing code snippet :

        // readData method

        // Given that getConnection method throws ClassNotFoundException and that an IOException is thrown while closing fr1, which exception
        // will be received by the caller of readData() method?

        // java.lang.ClassNotFoundException

        // Explanation
        //
        // If an exception is thrown within the try-with-resources block, then that is the exception that the caller gets.
        //
        // But before the try block returns, the resource's close() method is called and if the close() method throws an exception as well,
        // then this exception is added to the original exception as a supressed exception.
    }

    public static void readData(String fileName) throws Exception {
        try (FileReader fr1 = new FileReader(fileName)) {
            Connection c = getConnection(); // ...other valid code
        }
    }

    static class Connection {

    }

    static Connection getConnection() throws ClassNotFoundException {
        throw new ClassNotFoundException();

    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which statements about the following code are correct?

        House ci = new MyHouse(); // 1
        System.out.println(ci.getAddress()); // 2

        // The code will compile successfully.
        //
        // Explanation
        // There is no problem with the code. It is perfectly valid for a subinterface to override a default method of the base interface.
        // A class that implements an interface can also override a default method.
        //
        // It is valid for MyHouse to say that it implements Bungalow as well as House even though House is redundant because
        // Bungalow is a House anyway.
        //
        // It will actually print 101 Smart str.
    }

    static interface House {

        public default String getAddress() {
            return "101 Main Str";
        }
    }

    static interface Bungalow extends House {

        public default String getAddress() {
            return "101 Smart Str";
        }
    }

    static class MyHouse implements Bungalow, House {

    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
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
    static void test01_12() throws Exception {
        // Complete the following code fragment so that it will print owner's name of a file:

        Path path = Paths.get("c:\\temp\\test.txt");
        // INSERT CODE HERE
        PosixFileAttributeView pfav = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        PosixFileAttributes attrs = pfav.readAttributes();

        // Pay Attention with attrs."property", don't use java beans pattern
        String ownername = attrs.owner().getName();

        System.out.println(ownername);

        // Ex:
        attrs.creationTime();
        attrs.group();
        // etc

        // But we have:
        attrs.isDirectory();
        attrs.isSymbolicLink();
        attrs.isOther();
        attrs.isRegularFile();
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
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
    static void test01_14() throws Exception {
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
    static void test01_15() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that control how the file is opened?

        OpenOption[] op01 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DELETE_ON_CLOSE }; // ok

        OpenOption[] op02 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.DELETE_ON_CLOSE }; // ok

        OpenOption[] op03 = new OpenOption[]{ StandardOpenOption.DELETE_ON_CLOSE, StandardOpenOption.TRUNCATE_EXISTING };
        // TRUNCATE_EXISTING : If the file already exists and it is opened for WRITE access, then its length is truncated to 0.
        // if you want to truncate a file, then you must open it with an option that allows writing.
        // Thus, READ and TRUNCATE_EXISTING (or WRITE, APPEND, or DELETE_ON_CLOSE) should not go together.

        OpenOption[] op04 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Which of the following are valid?

        List<String> list01 = new ArrayList<>();

        List<String> list02 = new ArrayList<>(10);

        // While creating an ArrayList you can pass in an integer argument that specifies the initial size of the ArrayList. 
        // This doesn't actually insert any element into the list. So list.getSize() would still return 0.
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Given:
        List<String> strList = Arrays.asList("a", "aa", "aaa");
        Function<String, Integer> f = x -> x.length();
        Consumer<Integer> c = x -> System.out.print("Len:" + x + " ");
        strList.stream().map(f).forEach(c);

        // What will it print when compiled and run?

        // Len:1 Len:2 Len:3 

        // The function f accepts a String and returns its length. The call to map(f), uses this function to replace each element of
        // the stream with an Integer. The call to forEach(c) uses function c to print each element.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Given :
        // glob pattern: glob:?{pdf,rtf}
        //
        // Actual name of the files in a directory (Separated by comma, not including the space): 
        // .rtf, a.pdf, ab.rtf, ab.pdf, pdf
        //
        // Which files will be captured by the glob pattern?
        //
        // .rtf
        //
        // Remember that ? matches exactly one character in a glob pattern (as opposed to zero or one in a regex). Therefore, ?{pdf,rtf} will 
        // match only those file names that have exactly four characters and the last three characters have to be pdf or rtf.
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // Given:
        Stream<Integer> strm1 = Stream.of(2, 3, 5, 7, 11, 13, 17, 19); //1

        Stream<Integer> strm2 = strm1.filter(i -> {
            return i > 5 && i < 15;
        }); //2

        strm2.forEach(System.out::print); //3

        // Which of the following options can be used to replace line at //2 without affecting the output?

        Stream<Integer> strm2New = strm1.parallel().filter(i -> i > 5).filter(i -> i < 15).sequential();

        // Stream pipelines may execute either sequentially or in parallel. This execution mode is a property of the stream. 
        // Streams are created with an initial choice of sequential or parallel execution.
        // 
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Given:

        List<String> fnames = Arrays.asList("a", "b", "c");
        Tiger t = new Tiger();

        // Which of the following options can be inserted independent of each other in the code above without any compilation error?

        process(fnames, t);

        process(fnames, t::eat);

        process(fnames, t::calories);

        process(fnames, Test03::size);

        // Explanation
        // Don't be confused by twisted code. The method process(List<String> names, Carnivore c) expects a List<String> and a Carnivore instance as arguments.
        //
        // Carnivore has exactly one abstract method and therefore it is a functional interface. You can either pass a Carnivore instance explicitly or pass a
        // reference to a method that matches the parameter list of Carnivore's abstract method eat(List<String> foods);.
        //
        // t::eat, t::calories, and TestClass::size are all valid method references with the exact same parameter requirements and are therefore valid.
        //
        // Carnivore::calories is invalid because Carnivore is an interface. It does not refer to any object upon which calories method can be invoked.
        //
        // t::calories, on the other hand is valid because t does refer to an object upon which calories method can be invoked. t::eat is valid for the same reason.
        //
        // Tiger::eat is a valid method reference that can mean to refer either to a static method eat of Tiger class or to an instance method of any arbitraty
        // instance of Tiger class. Which meaning is implied depends on the context in which it is used.
        //
        // Here, the context does not supply any instance of Tiger class. Therefore, Tiger::eat will refer to a static method eat. But there is no such
        // static method in Tiger class. Therefore, it is invalid in this context.
        //
        // To use Tiger::eat, you need a reference to a Tiger instance, which is not available here. TestClass::size, on the other hand, is a static
        // method of TestClass, which means you don't need an object of TestClass to invoke this method and that is why it is valid.
    }

    public static int size(List<String> names) {
        return names.size() * 2;
    }

    public static void process(List<String> names, Carnivore c) {
        c.eat(names);
    }

    interface Carnivore {

        default int calories(List<String> food) {
            return food.size() * 100;
        }

        int eat(List<String> foods);
    }

    static class Tiger implements Carnivore {

        public int eat(List<String> foods) {
            System.out.println("Eating " + foods);
            return foods.size() * 200;
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
