package br.fernando.ch14_tests.part05_foundation;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleSupplier;
import java.util.function.Predicate;

public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following are correct about java.util.function.Predicate?
        //
        // It is an interface that has only one abstract method with the signature - public boolean test(T t);
        //
        // Explanation
        // java.util.function.Predicate is one of the several functional interfaces that have been added to Java 8.
        // This interface has exactly one abstract method named test, which takes any object as input and returns a boolean.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following statements are correct regarding a functional interface?

        // It must have exactly one abstract method and may have other default or static methods.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // You want to execute your tasks after a given delay. Which ExecutorService would you use?

        // ScheduledExecutorService
        // An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.
        // The schedule methods create tasks with various delays and return a task object that can be used to cancel or check execution.
        //
        // Explanation
        // Following is a usage example that sets up a ScheduledExecutorService to beep every ten seconds for an hour:

        new BeeperControl().beepForAnHour();
    }

    static class BeeperControl {

        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        public void beepForAnHour() {

            final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(() -> System.out.println("beep"), 10, 10, TimeUnit.SECONDS);

            scheduler.schedule(() -> beeperHandle.cancel(true), 60 * 60, TimeUnit.SECONDS);
        }
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Which of the following classes should you use to represent just a date without any time or zone information?

        // java.time.LocalDate

        // Java 8 introduces a new package java.time to deal with dates. The old classes such as java.util.Date are not recommended anymore.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following are true regarding the new Date-Time API of Java 8?
        //
        // It uses the calendar system defined in ISO-8601 as the default calendar.
        //
        // This calendar is based on the Gregorian calendar system and is used globally as the defacto standard for representing date and time.
        // The core classes in the Date-Time API have names such as LocalDateTime, ZonedDateTime, and OffsetDateTime.
        // All of these use the ISO calendar system.
        //
        // Most of the actual date related classes in the Date-Time API such as LocalDate, LocalTime, and LocalDateTime are immutable.
        //
        // These classes do not have any setters. Once created you cannot change their contents. Even their constructors are private.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Identify the correct statements.
        //
        // LocalDate, LocalTime, and LocalDateTime implement TemporalAccessor.
        //
        //
        // Explanation
        //
        // Here are some points that you should keep in mind about the new Date/Time classes introduced in Java 8
        //
        // 1. They are in package java.time and they have no relation at all to the old java.util.Date and java.sql.Date.
        //
        // 2. java.time.TemporalAccessor is the base interface that is implemented by LocalDate, LocalTime, and LocalDateTime concrete classes.
        // This interface defines read-only access to temporal objects, such as a date, time, offset or some combination of these, which are
        // represented by the interface TemporalField.
        //
        // 3. LocalDate, LocalTime, and LocalDateTime classes do not have any parent/child relationship among themselves. As their names imply,
        // LocalDate contains just the date information and no time information, LocalTime contains only time and no date, while LocalDateTime
        // contains date as well as time. None of them contains zone information. For that, you can use ZonedDateTime.
        // These classes are immutable and have no public constructors. You create objects of these classes using their static factory
        // methods such as of(...) and from(TemporalAccessor ).
        //
        // 4. Formatting of date objects into String and parsing of Strings into date objects is done by java.time.format.DateTimeFormatter class.
        // This class provides public static references to readymade DateTimeFormatter objects through the fields
        // named ISO_DATE, ISO_LOCAL_DATE, ISO_LOCAL_DATE_TIME,
        //
        // 5. Besides dates, java.time package also provides Period and Duration classes. Period is used for quantity or amount of time in
        // terms of years, months and days, while Duration is used for quantity or amount of time in terms of hour, minute, and seconds.
        // Durations and periods differ in their treatment of daylight savings time when added to ZonedDateTime.
        // A Duration will add an exact number of seconds, thus a duration of one day is always exactly 24 hours. By contrast, a
        // Period will add a conceptual day, trying to maintain the local time.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Which of the following is/are valid functional interfaces?
        //
        // F
        //
        // A functional interface is an interface that contains exactly one abstract method. It may contain zero or more default methods and/or static methods in
        // addition to the abstract method. Because a functional interface contains exactly one abstract method, you can omit the name of that method when you
        // implement it using a lambda expression.
    }

    interface F {

        default void m() {
        }

        // The use of abstract keyword is redundant here, but it is legal.
        abstract void n();
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which method must be implemented by a class implementing the Callable interface?

        // call()

        Callable<Long> myCall = new Callable<Long>() {

            public Long call() throws Exception {
                return 10L;
            }
        };

        // or

        myCall = () -> 10L;

        ExecutorService executor = Executors.newFixedThreadPool(10);

        Future<Long> submit = executor.submit(myCall);

        // A task that returns a result and may throw an exception. Implementers define a single method with no arguments called call.
        // The Callable interface is similar to Runnable, in that both are designed for classes whose instances are potentially
        // executed by another thread. A Runnable, however, does not return a result and cannot throw a checked exception.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Which of the following methods are available in java.util.concurrent.ConcurrentMap in addition to the methods provided by java.util.Map?
        //
        // None of the above.

        // from Map not from ConcurrentMap
        Map<String, Object> map01 = new HashMap<>();
        map01.putIfAbsent("new1", new Object());

        // Explanation

        // ConcurrentMap is important for the exam. You should go the API description for this interface. In short:
        //
        // It is a Map providing additional atomic putIfAbsent, remove, and replace methods.
        //
        // Memory consistency effects: As with other concurrent collections, actions in a thread prior to placing an object into a ConcurrentMap
        // as a key or value happen-before actions subsequent to the access or removal of that object from the ConcurrentMap in another thread.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given:
        ArrayList<Data> al = new ArrayList<Data>();

        Data d = new Data(1);
        al.add(d);
        d = new Data(2);
        al.add(d);
        d = new Data(0);
        al.add(d);

        filterData01(al, new MyFilter()); // 1

        // How can you use a lambda expression to achieve the same result?

        System.out.println(al);

        // Remove MyFilter class altogether.
        // Change type of f from MyFilter to java.util.function.Predicate<Data> in filterData method and replace the line at 1 with:
        filterData02(al, x -> x.value == 0);
    }

    static class MyFilter {

        public boolean test(Data d) {
            return d.value == 0;
        }
    }

    public static void filterData01(ArrayList<Data> dataList, MyFilter f) {
        Iterator<Data> i = dataList.iterator();
        while (i.hasNext()) {
            if (f.test(i.next())) {
                i.remove();
            }
        }
    }

    public static void filterData02(ArrayList<Data> dataList, Predicate<Data> f) {
        Iterator<Data> i = dataList.iterator();
        while (i.hasNext()) {
            if (f.test(i.next())) {
                i.remove();
            }
        }
    }

    // In Data.java
    static class Data {

        int value;

        Data(int value) {
            this.value = value;
        }

        public String toString() {
            return "" + value;
        }
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // What will the following code fragment print?

        Path p1 = Paths.get("\\personal\\readme.txt");
        Path p2 = Paths.get("\\index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\index.html

        // Observe that if you append this path to p1, you will get p2. Therefore, this is the right answer. p1 + ..\..\index.html
        // =>\personal\readme.txt + ..\..\index.html
        // =>\personal + ..\index.html
        // =>\index.html

        // A ".." implies parent folder, therefore imagine that you are taking off one ".." from the right side of the plus sign and removing
        // the last name of the path on the left side of the plus sign.
        // For example, .. appended to personal makes it personal\.., which cancels out.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Which of the following statements are true regarding the try-with-resources statement?

        // catch and finally blocks are executed after the resources opened in the try blocks are closed.

        // Explanation
        // You need to know the following points regarding try-with-resources statement for the exam:
        //
        // 1. The resource class must implement java.lang.AutoCloseable interface. Many standard JDK classes such as BufferedReader, BufferedWriter)
        // implement java.io.Closeable interface, which extends java.lang.AutoCloseable.
        //
        // 2. Resources are closed at the end of the try block and before any catch or finally block.
        //
        // 3. Resources are not even accessible in the catch or finally block. For example:
        /**
         * <pre>
        try(Device d = new Device()){ 
            d.read();         
        }finally{
            d.close(); //This will not compile because d is not accessible here.         
        }
         * </pre>
         */
        // 4. Resources are closed in the reverse order of their creation.
        //
        // 5. Resources are closed even if the code in the try block throws an exception.
        //
        // 6. java.lang.AutoCloseable's close() throws Exception but java.io.Closeable's close() throws IOException.
        //
        // 7. If code in try block throws exception and an exception also thrown while closing is resource, the exception thrown while
        // closing the resource is suppressed. The caller gets the exception thrown in the try block.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // Which of the following are required to construct a Locale?

        // language
        new Locale("fr"); // language is French
        new Locale("fr", "FR"); // language is French, Country is France.
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // What will the following code print when compiled and run?

        class Book {

            private int id;

            private String title;

            private Double price;

            public Book(String title, Double price) {
                super();
                this.title = title;
                this.price = price;
            }

            public Double getPrice() {
                return price;
            }

            public String getTitle() {
                return title;
            }

            public int getId() {
                return id;
            }
        }

        Book b1 = new Book("Java in 24 hrs", null);
        DoubleSupplier ds1 = b1::getPrice;
        System.out.println(b1.getTitle() + " " + ds1.getAsDouble());

        //
        // It will throw a NullPointerException.
        //
        //
        // Explanation
        //
        // java.util.function.DoubleSupplier (and other similar Suppliers such as IntSupplier and LongSupplier)
        // is a functional interface with the functional method named getAsDouble.
        //
        // The return type of this method is a primitive double (not Double). Therefore, if your lambda expression for this function returns
        // a Double, it will automatically be converted into a double because of auto-unboxing.
        //
        // However, if your expression returns a null, a NullPointerException will be thrown.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // What will the following lines of code print

        java.time.LocalDate dt = java.time.LocalDate.parse("2015-01-01").minusMonths(1).minusDays(1).plusYears(1);
        System.out.println(dt);

        // 2015-11-30
        // The numbering for days and months starts with 1. Rest is simple math.

        // Observe that most of the methods of LocalDate (as well as LocalTime and LocalDateTime) return an object of the same class.
        // This allows you to chain the calls as done in this question. However, these methods return a new object.
        // They don't modify the object on which the method is called.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {

        // Identify the correct statements regarding DateFormat and NumberFormat classes.
        // The following line of code will work on a machine in any locale :
        double x = 12345.123;
        String str = NumberFormat.getInstance().format(x);

        // If you don't pass a Locale in getInstance() methods of NumberFormat and DateFormat, they are set to default Locale of the machine.
        // If you run this code on a French machine, it will format the number in French format ( 12 345,123 ) and if you run it on a US machine,
        // it is format the number in US format ( 12,345.123 ).
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // You want to walk through a directory structure recursively. Which interface do you need to implement for this purpose and which
        // class can you extend from to avoid implementing all the methods of the interface?

        // FileVisitor and SimpleFileVisitor
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Which import statements are required to compile the following code?

        Date d = new Date();
        DateFormat df = DateFormat.getInstance();

        // import java.util.*;
        //
        // import java.text.*;
        //
        // Explanation
        // Date class is in java.util package. (There is one in java.sql package as well but you need not worry about it for the exam.) 
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // Which method do you need to implement if you want to implement your own java.nio.file.PathMatcher?

        // boolean matches(Path path)

        PathMatcher pm = new PathMatcher() {

            @Override
            public boolean matches(Path path) {
                return false;
            }
        };

        Path path = Paths.get("D:/cp/testFile.t");

        pm.matches(path);

        // it's an alternative to

        FileSystem fileSystem = FileSystems.getDefault();
        PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:**.{java,class,txt}");
        pathMatcher.matches(path);

    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Which of the following statements is true about the take method of WatchService?

        // Retrieves and removes next watch key, waiting if none are yet present.

        // WatchKey take() throws InterruptedException -> Retrieves and removes next watch key, waiting if none are yet present.
        //
        // Explanation
        // WatchService has two types of methods for retrieving the WatchKeys - poll and take. The two poll methods return null if no key 
        // is present and do not wait for ever. The take method doesn't return null but keeps waiting until a key is available.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
