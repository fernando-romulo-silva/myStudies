package br.fernando.ch14_tests.part06_final.test05_final;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05_02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print?

        Path p1 = Paths.get("c:\\temp\\test.txt");
        Path p2 = Paths.get("report.pdf");
        System.out.println(p1.resolve(p2));

        // c:\temp\test.txt\report.pdf
        // If the argument is a relative path (i.e. if it doesn't start with a root), the argument is simply appended to
        // the path to produce the result.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following lambda expressions can be used to invoke a method that accepts a java.util.function.Predicate as an argument?

        // x -> x == null
        //
        // and
        //
        // x -> true
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code fragment print?

        Path p1 = Paths.get("x\\y");
        Path p2 = Paths.get("z");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\z
        // Observes what happens when you append this path to p1:
        // x\y + ..\..\z
        // x + ..\z
        // z
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given that daylight Savings Time ends on Nov 1 at 2 AM in US/Eastern time zone. (As a result, 2 AM becomes 1 AM.),
        // what will the following code print?

        LocalDateTime ld1 = LocalDateTime.of(2015, Month.NOVEMBER, 1, 2, 0);
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of("US/Eastern"));

        System.out.println(zd1); // 2015-11-01T02:00-05:00[US/Eastern]

        LocalDateTime ld2 = LocalDateTime.of(2015, Month.NOVEMBER, 1, 1, 0);
        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of("US/Eastern"));

        System.out.println(zd2); // 2015-11-01T01:00-04:00[US/Eastern]

        long x = ChronoUnit.HOURS.between(zd1, zd2);
        System.out.println(x);

        // -2

        // Explanation
        // The time difference between two dates is simply the amount of time you need to go from date 1 to date 2.
        //
        // So if you want to go from 1AM to 2AM, how many hours do you need? On a regular day, you need 1 hour.
        //
        // That is, if you add 1 hour to 1AM, you will get 2AM. However, as given in the problem statement, at the time of DST change, 2 AM becomes 1AM.
        //
        // That means, even after adding 1 hour to 1AM, you are not at 2AM. You have to add another hour to get to 2 AM.
        // In total, therefore, you have to add 2 hours to 1AM to get to 2AM.
        //
        // The answer can therefore be short listed to 2 or -2. Now, as per the JavaDoc description of the between method, it returns negative
        // value if the end is before the start.
        //
        // In the given code, our end date is 1AM, while the start date is 2AM. This means, the answer is -2.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What can be inserted in the code below so that System.out.println(map1); will print {a=x, b=xy, c=y}?
        Map<String, String> map1 = new HashMap<>();
        map1.put("a", "x");
        map1.put("b", "x");

        System.out.println(map1);

        // INSERT CODE HERE
        BiFunction<String, String, String> f = String::concat;

        map1.merge("b", "y", f);
        map1.merge("c", "y", f);

        System.out.println(map1);
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print when run?

        class MyCallable implements Callable<String> {

            public String call() throws Exception {
                Thread.sleep(50000);
                return "DONE";
            }
        }

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future = es.submit(new MyCallable());
        System.out.println(future.get()); // 1
        es.shutdownNow(); // 2

        // DONE
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Consider the following code:
        Locale.setDefault(new Locale("es", "ES"));
        ResourceBundle rb = ResourceBundle.getBundle("appmessages");
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // You have created a valid resource bundle file named appmessages_es_ES.properties that contains 'greetings' property. However,
        // when you run the above code, you get an exception saying:
        // "java.util.MissingResourceException: Can't find bundle for base name appmessages, locale es_ES".

        // What could be the reason?

        // appmessages_es_ES.properties is not present in CLASSPATH.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given:

        class Student {

            private String name;

            private int marks;

            public Student(String name, int marks) {
                super();
                this.name = name;
                this.marks = marks;
            }

            public void addMarks(int m) {
                this.marks += m;
            }

            public void debug() {
                System.out.println(name + ":" + marks);
            }
        }
        
        //  What will the following code print when compiled and run?

        List<Student> slist = Arrays.asList(new Student("S1", 40), new Student("S2", 35), new Student("S3", 30));

        Consumer<Student> increaseMarks = s -> s.addMarks(10);
        slist.forEach(increaseMarks);
        slist.stream().forEach(Student::debug);

        // S1:50
        // S2:45
        // S3:40
        //
        // Explanation
        //
        // 1. Java 8 has added a default method default void forEach(Consumer<? super T> action) in java.lang.Iterable interface (which is extended
        // by java.util.List interface). It performs the given action for each element of the Iterable until all elements have been processed or
        // the action throws an exception. Unless otherwise specified by the implementing class, actions are performed in the order of iteration
        // (if an iteration order is specified). Exceptions thrown by the action are relayed to the caller.
        //
        // 2. java.util.Stream interface also contains the same void forEach(Consumer<? super T> action) method that applies the given action to
        // each element of the stream. This is a terminal operation, which means: 1. it will cause all other intermediate operations (such as peek)
        // chained before it to be executed and 2. you cannot chain any more operations after calling this method.
    }

    // =========================================================================================================================================
    @SuppressWarnings("resource")
    static void test01_09() throws Exception {
        // Given that c:\temp\pathtest is a directory that contains several directories. Each sub directory contains several files but there
        // is exactly one regular file named test.txt within the whole directory structure. Which of the given options can be inserted in the
        // code below so that it will print complete path of test.txt?
        try {
            Stream<Path> s = null;

            // INSERT CODE HERE
            s = Files.find(Paths.get("c:\\temp\\pathtest"), Integer.MAX_VALUE, (p, a) -> p.endsWith("test.txt") && a.isRegularFile());

            // public static Stream<Path> find(Path start, int maxDepth, BiPredicate<Path,BasicFileAttributes> matcher, FileVisitOption... options) throws IOException
            // Return a Stream that is lazily populated with Path by searching for files in a file tree rooted at a given starting file.

            s.forEach(System.out::println);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given that the file test.txt is accessible and contains multiple lines, which of the following code fragments will correctly
        // print all the lines from the file?

        // 1º
        Stream<String> lines01 = Files.lines(Paths.get("test.txt"));
        lines01.forEach(System.out::println);

        // 2º
        Stream<String> lines02 = Files.lines(Paths.get("test.txt"), Charset.defaultCharset());
        lines02.forEach(s -> System.out.println(s));
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // What can be inserted in the following code so that it will print 12?

        // 1º
        Doer d1 = (a, b) -> b.substring(0, a);

        // 2º
        Doer d2 = (int a, String b) -> b.substring(0, a);

        System.out.println(d2.doIt(2, "12345"));
    }

    interface Doer {

        String doIt(int x, String y);
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Identify correct statements about the following code -

        // CrazyDrink must either implement getName or be marked abstract.
        // Since CrazyDrink says it implements ColdDrink, it must either implement all the abstract methods of the interface or be marked abstract.
        class CrazyDrink implements ColdDrink {

            // INSERT CODE HERE
            @Override
            public String getName() {
                return null;
            }
        }
    }

    interface Drink {

        default double getAlcoholPercent() {
            return 0.0;
        }

        static double computeAlcoholPercent() {
            return 0.0;
        }
    }

    interface ColdDrink extends Drink {

        String getName();
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {

        // What will the following code print if file test1.txt exists but test2.txt doesn't exist?

        Path p1 = Paths.get("c:\\temp\\test1.txt");
        Path p2 = Paths.get("c:\\temp\\test2.txt");

        copy1(p1, p2);

        if (Files.isSameFile(p1, p2)) {
            System.out.println("file copied");
        } else {
            System.out.println("unable to copy file");
        }

        // It will print 'unable to copy file' but test2.txt will contain the same data as test1.txt.

        // Explanation
        //
        // Files.copy method will copy the file test1.txt into test2.txt. If test2.txt doesn't exist, it will be created. However, Files.isSameFile
        // method doesn't check the contents of the file. It is meant to check if the two path objects resolve to the same file or not. In this case,
        // they are not, and so, it will return false.
    }

    public static void copy1(Path p1, Path p2) throws Exception {
        Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Given:
        List<Integer> names = Arrays.asList(1, 2, 3);

        // How many of the following lines will print exactly 6?

        // 1.
        System.out.println(names.stream().mapToInt(x -> x).sum());

        // 2.
        // System.out.println(names.stream().forEach((sum, x) -> sum = sum + x)); // don't compile

        // 3.
        System.out.println(names.stream().reduce(0, (a, b) -> a + b));

        // 4.
        System.out.println(names.stream().collect(Collectors.mapping(x -> x, Collectors.summarizingInt(x -> x))).getSum());

        // 5.
        System.out.println(names.stream().collect(Collectors.summarizingInt(x -> x)).getSum());

        // Answer: 4
        //
        // Explanation:
        //
        // 1. System.out.println(names.stream().mapToInt(x->x).sum()); mapToInt will return an IntStream, which has a sum method.
        // It will, therefore, print 6.
        //
        // 2. System.out.println(names.stream().forEach((sum, x)->sum = sum + x)); This will not compile because forEach expects a Consumer.
        // A Consumer takes exactly one argument. Not two.
        //
        // 3. System.out.println(names.stream().reduce(0, (a, b)->a+b)); Here, we are passing the starting value 0 and a BinaryOperator
        // that adds the two arguments and returns the result. It will therefore sum up all the elements in the stream.
        // It will, therefore, print 6.
        //
        // 4. System.out.println(names.stream().collect(Collectors.mapping(x -> x, Collectors.summarizingInt(x -> x))).getSum());
        // System.out.println(names.stream().collect(Collectors.summarizingInt(x->x)).getSum());
        //
        // 5. Both the lines are doing the same thing. The first line maps elements of the original stream to the int elements.
        // This is redundant but is not an error. The summarizingInt collector applies an int-producing mapping function to each input element,
        // and returns summary statistics for the resulting values. The result is captured in an IntSummaryStatistics object,
        // which has a getSum method. The function x->x basically just unboxes the Integer objects to an ints.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // The signature of a method in a class is as follows:

        // public static <E extends CharSequence> List<? super E> doIt(List<E> nums)

        // This method is being called in the following code:

        // Insert code here
        ArrayList<String> in = new ArrayList<>();
        List result;

        result = doIt(in);

        // Given that String implements CharSequence interface, what should be the reference type of 'in' and 'result' variables?
        //
        // Explanation
        //
        // The input parameter has been specified as List<E>, where E has to be some class that extends CharSequence. So ArrayList<String>,
        // List<String>, or List<CharSequence> are all valid as reference types for 'in'.
        //
        // The output type of the method has been specified as List<? super E> , which means that it is a List that contains objects of
        // some class that is a super class of E. Here, E will be typed to whatever is being used for 'in'. For example, if you declare
        // ArrayList<String> in, E will be String.
        //
    }

    public static <E extends CharSequence> List<? super E> doIt(List<E> nums) {
        return null;
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Using a Callable would be more appropriate than using a Runnable in which of the following situations?

        // Answer
        //
        // When your task might throw a checked exception and you want to execute it in a separate Thread.
        //
        // Callable.call() allows you to declare checked exceptions while Runnable.run() does not. So if your task throws a checked exception,
        // it would be more appropriate to use a Callable.

    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {

        Object student1 = new Object();
        Object student2 = new Object();

        // Given:

        ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

        cache.put("111", student1);
        // (Assume that student1 and student2 are references to a valid objects.)

        // Which of the following statements are legal but will NOT modify the Map referenced by cache?

        cache.putIfAbsent("111", student2);

        // putIfAbsent first checks if the key already exists in the Map and if it does, does nothing. In other words, this method does not
        // overwrite a key with new value. The check and update happens atomically.
        // Since the given Map already contains a key "111", this call will not modify the Map.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // What will the following code print when run?
        Date d = new Date();
        // DateFormat df = new DateFormat(DateFormat.LONG); not compile here
        // System.out.println(df.format(d));

        // It will not compile.
        // DateFormat cannot be instantiated because it is an abstract class. You must use DateFormat.getDateInstance(...) methods.
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // Which of the following are valid enum values defined in java.nio.file.FileVisitResult?

        // TERMINATE
        //
        // CONTINUE

        // Explanation
        //
        // CONTINUE: Continue.
        //
        // SKIP_SIBLINGS: Continue without visiting the siblings of this file or directory.
        //
        // SKIP_SUBTREE: Continue without visiting the entries in this directory.
        //
        // TERMINATE: Terminate.
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Given:
        class Book {

            private String title;

            private Double price;

            public Book(String title, Double price) {
                this.title = title;
                this.price = price;
            }

            public Double getPrice() {
                return price;
            }

            public String getTitle() {
                return title;
            }
        }

        // What will the following code print when compiled and run?
        Book b1 = new Book("Java in 24 hrs", 30.0);
        Book b2 = new Book("Thinking in Java", null);

        Supplier s1 = b1::getPrice;
        System.out.println(b1.getTitle() + " " + s1.get());

        Supplier s2 = b2::getPrice;
        // System.out.println(b2.getTitle() + " " + s2.getAsDouble()); not compile here

        // java.util.function.Supplier is a functional interface and has only one method named get. It doesn't have getAsDouble. 
        // Therefore, this code will not compile.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_05();
    }
}
