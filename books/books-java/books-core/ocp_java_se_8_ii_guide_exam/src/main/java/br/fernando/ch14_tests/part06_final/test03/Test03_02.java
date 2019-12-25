package br.fernando.ch14_tests.part06_final.test03;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03_02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print when compiled and run?

        BiPredicate<String, Integer> bip = (s, i) -> s.length() > i; // 1
        BiFunction<String, Integer, String> bif = (s, i) -> { // 2
            if (bip.test(s, i)) { // 3
                return s.substring(0, i);
            } else return s;
        };
        String str = bif.apply("hello world", 5); // 4
        System.out.println(str);

        // hello
        // There is no problem with the code.
    }

    // =========================================================================================================================================
    //
    static void test01_02() throws Exception {
        // The following code was written for Java 7

        groupedValues.put("test01", Arrays.asList(4d, 4.4d));

        process01("test01", 4d);

        // Which of the following implementations correctly makes use of the Java 8 functional interfaces to achieve the same?

        process02("test01", 4d);
    }

    // The objective of the given code is to collect multiple values for a given key in a map. When a value for a new key is to
    // be inserted, it needs to put a List in the map first before adding the key to the List.
    //
    // computeIfAbsent is perfect for this. This methods checks if the key exists in the map. If it does, the method just returns
    // the value associated with that key. If it doesn't, the method executes the Function, associates the value returned by that
    // Function in the map with that key, and returns that value.
    public static void process02(String name, Double value) {
        groupedValues //
                .computeIfAbsent(name, (a) -> new ArrayList<Double>()).add(value);
    }
    // Remember that while compute and computeIfPresent take a BiFunction as an argument, computeIfAbsent takes a Function.
    // (a, b)->new ArrayList<Double>() is valid lambda expression for a BiFunction but will not be helpful here.

    static Map<String, List<Double>> groupedValues = new HashMap<>();

    public static void process01(String name, Double value) {
        List<Double> values = groupedValues.get(name);
        if (values == null) {
            values = new ArrayList<Double>();
            groupedValues.put(name, values);
        }
        values.add(value);
    }

    // Explanation
    // You need to know about the three flavors of compute methods of Map:
    //
    // 1. public V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
    //
    // 2. public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
    //
    // 3. public V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code print when run?

        System.out.println(getRoot());

        // c:\

        // Explanation
        // Path getRoot()
        // Returns the root component of this path as a Path object, or null if this path does not have a root component.
        //
        // Path getName(int index)
        // Returns a name element of this path as a Path object. The index parameter is the index of the name element to return.
        // The element that is closest to the root in the directory hierarchy has index 0. The element that is farthest from the root has index count-1.

        // for example, If your Path is "c:\\code\\java\\PathTest.java",
        // p1.getRoot()  is c:\  ((For Unix based environments, the root is usually / ).
        // p1.getName(0)  is code
        // p1.getName(1)  is java
        // p1.getName(2)  is PathTest.java
        // p1.getName(3)  will cause IllegalArgumentException to be thrown.
    }

    static Path p1 = Paths.get("c:\\main\\project\\Starter.java");

    public static String getRoot() {
        String root = p1.getRoot().toString();
        return root;
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given the getDateString method:
        // Which of the following statements are correct?

        // The code will compile but will always throw a DateTimeException (or its subclass) at run time.

        // Note that LocalDateTime class does not contain Zone information but ISO_ZONED_DATE_TIME requires it. Thus, it will throw the
        // following exception: Exception in thread "main" java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: OffsetSeconds
    }

    public String getDateString(LocalDateTime ldt) {
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ldt);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // What can be inserted at //2 so that 6 will be printed by the following code?

        AtomicInteger ai = new AtomicInteger(5);

        // 2 INSERT CODE HERE
        int x = ai.incrementAndGet();

        // The ai.incrementAndGet method atomically increments the current value by 1 and returns the new value.
        // Therefore, 5 will be incremented by 1 to 6 and 6 will be returned.

        // or

        x = ai.addAndGet(1);

        // The addAndGet method atomically adds the given value to the current value and returns the new value.
        // Here, we are passing 1. So 1 will be added to 5 and 6 will be returned.

        System.out.println(x);
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print?
        Duration d = Duration.ofHours(25);
        System.out.println(d);
        Period p = Period.ofDays(1);
        System.out.println(p);

        // PT25H
        // P1D

        // There are two important things to note here:
        // 1. Duration string starts with PT (because duration is "time" based i.e. hours/minutes/seconds) and Period string starts with just P
        // (because period does not include time. It contains only years, months, and days).
        //
        // 2. Duration does not convert hours into days. i.e. 25 hours will remain as 25 hours instead of 1 day and 1 hour.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Given Account and PremiumAccount,
        // Which of the following options can be inserted in PremiumAccount independent of each other?

        // String getId();
        //
        // or
        //
        // default String getId(){  return "1111"; };

        // Explanation
        // In case of classes, you cannot override a static method with a non-static method and vice-versa.
        //
        // However, in case of interfaces, it is possible for a sub interface to have a non-static (i.e. default) method with the same signature
        // as that of a static method of a super interface.
        //
        // This is because static methods are not inherited in any sense in the sub-interface
        // and that is why it is allowed to declare a default method with the same signature. The reverse is not true though.
        // That is, you cannot have a static method in a sub-interface with the same signature as that of a default method in a super interface.
    }

    interface Account {

        public default String getId() {
            return "0000";
        }
    }

    interface PremiumAccount extends Account {
        // INSERT CODE HERE
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given that the bit pattern for the amount $20,000 is 100111000100000, which of the following is/are valid declarations of an int
        // variable that contains this value?

        int b = 0b01001110_00100000;

        // Beginning with Java 7, you can include underscores in between the digits. This helps in writing long numbers.
        // For example, if you want to write 1 million, you can write: 1_000_000, which is easier to interpret for humans than 1000000.

        // Note that you cannot start or end a value with an underscore though. Thus, 100_ or _100 would be invalid values.
        // The value _100 would actually be a valid variable name!
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given Counter Class
        //
        // This class is supposed to keep an accurate count for the number of times the increment method is called.
        // Several classes share this class and call its increment method. What should be inserted at //1 ?
        //
        // Explanation
        // AtomicInteger allows you to manipulate an integer value atomically. It provides several methods for this purpose.
        // Please go through the JavaDoc API description for this class as it is important for the exam.
    }

    static class Counter {

        static AtomicInteger ai = new AtomicInteger(0);

        public static void increment() {
            // 1
            ai.incrementAndGet();
            // or
            ai.addAndGet(1);

        } // other valid code
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code print?

        Duration d = Duration.ofDays(1);
        System.out.println(d);
        d = Duration.ofMinutes(0);
        System.out.println(d);
        Period p = Period.ofMonths(0);
        System.out.println(p);

        // PT24H
        // PT0S
        // P0D

        // * Attention *
        // Duration counts in terms of hours, minutes, and seconds. Therefore, days are converted into hours.
        // That is why the first println prints PT24H and not PT1D.
        //

        // A Duration of 0 is printed as 0S and a Period of 0 is printed as 0D.
    }

    // =========================================================================================================================================
    static int test01_11(String a, int b) throws Exception {
        // Consider the following code fragment appearing in a class:

        final String aa = " ";

        String c = "c";

        switch (a) {
        case "":
            return 1;

        case aa:
            return 1;

        case " z ": // only const
            return 1;

        // case c : return 3; // must be a const    
        }
        return 0;

        // What should be the type of XXXX to make the code compile?

        // java.lang.String

        // Explanation
        // Rule 1 : The type of the switch expression and the type of the case labels must be compatible. In this case, the switch expression
        // is of type String, so all the case labels must be of type String.

        // FYI : case labels must be compile time constants. Thus, you cannot use non-final variable names as labels. final variables can be used.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Which of the following is/are valid double values for 10 million? (A million has 6 zeros)

        double d = 10_000_000;

        // Explanation
        // Beginning with Java 7, you can include underscores in between the digits. This helps in writing long numbers. 
        // For example, if you want to write 1 million, you can write: 1_000_000, which is easier than 1000000 for humans to interpret.
        //
        // Note that you cannot start or end a value with an underscore though. Thus, 100_ or _100 are invalid values. 
        // The _100 is actually a valid variable name!
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // Consider the following code that is a part of a big application:

        ResourceBundle rb = ResourceBundle.getBundle("appmessages", Locale.US);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // This application is developed by multiple teams in multiple locations and runs in many regions but you want the above code to always
        // print the message in US English. What should be the name of the resource bundle file that contains the actual English text to be displayed
        // for "greetings" and where should that file be placed?
        //
        // appmessages_en_US.properties in CLASSPATH.
        // A resource bundle file should always be present somewhere in the CLASSPATH.
        //
        // Explanation
        // The a program to load a resource bundle, the resource bundle properties file must be in the CLASSPATH.
        //
        // The JVM attempts to find a "resource" with this name using ClassLoader.getResource.
        // (Note that a "resource" in the sense of getResource has nothing to do with the contents of a resource bundle, it is just a container of data,
        // such as a file.)
        // If it finds a "resource", it attempts to create a new PropertyResourceBundle instance from its contents.
        // If successful, this instance becomes the result resource bundle.
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // What will the following code print?

        List<Integer> str = Arrays.asList(1, 2, 3, 4);

        str.stream().filter(x -> {
            System.out.print(x + " ");
            return x > 2;
        });

        // It will not print anything.
        //
        // Remember that filter is an intermediate operation. It will not be executed until you invoke a terminal operation such as
        // count or forEach on the stream.
        //
        // Explanation
        // To answer this question, you need to know two things - distinction between "intermediate" and "terminal" operations and
        // which operations of Stream are "intermediate" operations.
        //
        // It is easy to identify which operations are intermediate and which are terminal. All intermediate operations return Stream
        // (that means, they can be chained), while terminal operations don't.
        //
        // filter, peek, and map are intermediate operations.
        //
        // count, forEach, sum, allMatch, noneMatch, anyMatch, findFirst, and findAny are terminal operations.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // What will be printed when the following code is compiled and run?
        class Device {

            String header = null;

            public void open() {
                header = "OPENED";
                System.out.println("Device Opened");
            }

            public String read() throws IOException {
                throw new IOException("Unknown");
            }

            public void writeHeader(String str) throws IOException {
                System.out.println("Writing : " + str);
                header = str;
            }

            public void close() {
                header = null;
                System.out.println("Device closed");
            }
        }

        /**
         * <pre>
         * try (Device d = new Device()) {
         *     d.open();
         *     d.read();
         *     d.writeHeader("TEST");
         *     d.close();
         * } catch (IOException e) {
         *     System.out.println("Got Exception");
         * }
         * </pre>
         */

        // The code will not compile.
        // Remember that when you use a resource in try-with-resources, the resource must implement java.lang.AutoCloseable interface.
        // In this case, although Device class contains the close() method required by this interface but it does not say that it implements
        // AutoCloseable in its declaration. Therefore, compilation will fail.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Consider the following code:

        Path p1 = Paths.get("\\temp\\records");
        Path p2 = p1.resolve("clients.dat");
        System.out.println(p2 + " " + isValid(p2));
        //
        // \temp\records\clients.dat false
        //
        // p2 will be set to \temp\records\clients.dat. Since it starts with \temp and not with temp, the method isValid will return false.
    }

    static boolean isValid(Path p) {
        return p.startsWith("temp") && p.endsWith("clients.dat");
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Given Counter01

        // This class is supposed to keep an accurate count for the number of times the increment method is called.
        // Several classes share an instance of this class and call its increment method.
        // What should be inserted at //1 and //2?

        // AtomicInteger count = new AtomicInteger(0); at //1
        // count.incrementAndGet(); at //2
    }

    static class Counter01 {

        // 1
        AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            // 2
            count.incrementAndGet();
        }
        // other valid code
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Given that Book is a valid class with appropriate constructor and getPrice method that returns a double,
        // what can be inserted at //1 so that it will sum the price of all the books that are priced greater than 5.0?

        List<Book> books = Arrays.asList( //
                new Book("Gone with the wind", 10.0), //
                new Book("Atlas Shrugged", 10.0), //
                new Book("Freedom at Midnight", 5.0), //
                new Book("Gone with the wind", 5.0));

        // 01
        double sum = books.stream() //
                .mapToDouble(b -> b.getPrice() > 5 ? b.getPrice() : 0.0) //
                .sum();

        // 02 
        sum = books.stream() //
                .mapToDouble(b -> b.getPrice()) //
                .filter(b -> b > 5.0) //
                .sum();

        System.out.println(sum);
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
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // Consider the following code and the directory structure of c:\works\pathtest shown in the image.

        class MyFileChecker extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;

            public MyFileChecker() { // * Attention *
                matcher = FileSystems.getDefault().getPathMatcher("glob:**.java");
            }

            void check(Path p) {
                Path name = p.getFileName(); // * Attention *
                if (name != null && matcher.matches(name)) {
                    count++;
                }
            }

            public int getCount() {
                return count;
            }

            public FileVisitResult visitFile(Path p, BasicFileAttributes attr) {
                check(p);
                return FileVisitResult.CONTINUE;
            }
        }

        // pathtest(dir1(c.java, dir12(d.java)), dir2(b.java), a.java)

        MyFileChecker mfc = new MyFileChecker();
        Files.walkFileTree(Paths.get("c:\\works\\pathtest"), mfc);
        System.out.println(mfc.getCount());

        // 4

        // Note that Files.walkFileTree method will cause each subdirectory under the given directory to be travelled.
        // For each file in each directory, the FileVisitor's visitFile will be invoked. This particular visitor simply tries to
        // match the full file name with the given glob pattern.
        // The glob pattern will match only the files ending with .java.
    }

    static int count;

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Given: 
        String[] p = { "1", "2", "3" };

        // Which of the following lines of code is/are valid?
        //
        List<?> list2 = new ArrayList<>(Arrays.asList(p));

        // Here, list2 is a list of any thing. You cannot add any thing to it and you can only retrieve Objects from it:

        // list2.add(new Object()); list2.add("aaa"); //both will not compile.
        Object obj = list2.get(0); //Valid
        // String str = list2.get(0); //will not compile

        // Note that you can add null to it though i.e. 
        list2.add(null); // is valid.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
