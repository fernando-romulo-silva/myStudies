package br.fernando.ch14_tests.part01_most_missed_tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        // Identify the correct statements about the following code:
        List<Integer> values = Arrays.asList(2, 4, 6, 9); // 1

        Predicate<Integer> check = (Integer i) -> {
            System.out.println("Checking");
            return i == 4; // 2
        };

        // Predicate even = (Integer i) -> i % 2 == 0; // 3
        //
        // It will not compile because of code at //3.
        //
        // Observe the lambda expression used to instantiate the Predicate is using Integer as the type of the variable.
        // To make this work, the declaration part must be typed to Integer.

        Predicate<Integer> even02 = (Integer i) -> i % 2 == 0;
        // or
        Predicate even03 = i -> ((Integer) i) % 2 == 0;

        values.stream().filter(check).filter(even02).count(); // 4

        // After you fix the line at //3, it will print:

        // Checking
        // Checking
        // Checking
        // Checking
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Identify correct statements about the following code:

        List<String> vals = Arrays.asList("a", "b");
        String join = vals.parallelStream().reduce("_", (a, b) -> a.concat(b));
        System.out.println(join);

        // It will print either _ab or _a_b
        //
        // Since we are creating a parallel stream, it is possible for both the elements of the stream to be processed by two different threads.
        // In this case, the identity argument will be used to reduce both the elements.
        // Thus, it will print _a_b.
        //
        // It is also possible that the result of the first reduction ( _a ) is reduced further using the second element (b).
        // In this case, it will print _ab.
        //
        // Even though the elements may be processed out of order individualy in different threads, the final output will be produced by
        // joining the individual reduction results in the same order. Thus, the output can never have b before a.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // You want to execute a task that returns a result without blocking.
        // Which of the following types from java.util.concurrent package will be required to achieve this?

        // ExecutorService
        // ExecutorService extends Executor and provide a submit(Callable ) method:
        // <T> Future<T> submit(Callable<T> task)
        // Submits a value-returning task for execution and returns a Future representing the pending results of the task.
        //
        // Executors
        // Executors is a utility class that contains factory methods to create various kinds of ExecutorService implementations.
        //
        // Callable
        // The difference between Callable and Runnable is that Callable.call() returns a value.
        // It may also throw an Exception. Runnable.run() cannot return any value and cannot throw any Exception.
        //
        // Future
        // When you submit a task (i.e. a Callable) to an ExecutorService using ExecutorService.submit(Callable ) method,
        // it returns a Future object immediately without blocking.
        // You can check the status of the Future object later to get the actual result once it is done.

        // Explanation
        // The following is a sample code that can be used to create and execute a task that returns the result without blocking.
        // import java.util.concurrent.Callable;
        // import java.util.concurrent.ExecutorService;
        // import java.util.concurrent.Executors;
        // import java.util.concurrent.Future;

        // Create a thread pool of two threads
        ExecutorService es = Executors.newFixedThreadPool(2);

        class MyTask implements Callable<String> {

            public String call() {
                try {
                    // simulate a long running task;
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return "Data from " + Thread.currentThread().getName();
            }
        }

        MyTask task1 = new MyTask();
        Future<String> result = es.submit(task1);
        System.out.println("Proceeding without blocking... ");
        while (!result.isDone()) {
            try {
                // check later
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Result is " + result.get());
        es.shutdown();

    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
    static void test01_06() throws Exception {
        // What will the following code fragment print?

        final String p1Win = "c:\\personal\\.\\photos\\..\\readme.txt";

        final String p2Win = "c:\\personal\\index.html";

        Path p1 = Paths.get(p1Win);
        Path p2 = Paths.get(p2Win);
        Path p3 = p1.relativize(p2);
        System.out.println(p3);

        // ..\..\..\..\index.html -> win

        // ../../../../index.html -> linux
        //
        // Observe that if you append this path to p1, you will get p2. Therefore, this is the right answer. p1 + ..\..\..\..\index.html
        // =>c:\\personal\\.\\photos\\..\\readme.txt + ..\..\..\..\index.html
        // =>c:\\personal\\.\\photos\\.. + ..\..\..\index.html
        // =>c:\\personal\\.\\photos + ..\..\index.html
        // =>c:\\personal\\. + ..\index.html
        // =>c:\\personal + index.html
        // =>c:\\personal\index.html

        // A ".." implies parent folder, therefore imagine that you are taking off one ".." from the right side of the plus sign and removing
        // the last name of the path on the left side of the plus sign.

        // Explanation
        // You need to understand how relativize works for the purpose of the exam. The basic idea of relativize is to determine a path,
        // which, when applied to the original path will give you the path that was passed.
        //
        // For example, "a/c" relativize "a/b" is "../b" because "/a/c/../b" is "/a/b" Notice that "c/.." cancel out.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Consider the following code:
        double amount = 53000.35;
        Locale jp = new Locale("jp", "JP");
        // 1 create formatter here.

        Format formatter = NumberFormat.getCurrencyInstance(jp);
        // This is valid because java.text.NumberFormat extends from java.text.Format.
        // The return type of the method getCurrencyInstance() is NumberFormat.
        //
        // or
        //
        // getCurrencyInstance is actually defined in NumberFormat. However, since DecimalFormat extends NumberFormat, this is valid.
        //
        // To format a number in currency format, you should use getCurrencyInstance() instead of getInstance() or getNumberInstance().
        formatter = DecimalFormat.getCurrencyInstance(jp);

        System.out.println(formatter.format(amount));

        // How will you create formatter using a factory at //1 so that the output is in Japanese Currency format?
        //
        //
        // Wrongs
        // NumberFormat formatter =  NumberFormat.getInstance(jp);
        // getInstance(Locale ) is a valid factory method in NumberFormat class but it will not not format the given number as per the currency.
        //
        // NumberFormat formatter =  new DecimalFormat("#.00");
        // While it is a valid way to create a DecimalFormat object, it is not valid for two reasons:
        // 1. We need a currency formatter and not just a simple numeric formatter.
        // 2. This is not using a factory to create the formatter object
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following statements is true about the take method of WatchService?

        // Retrieves and removes next watch key, waiting if none are yet present.

        // WatchKey take() throws InterruptedException -> Retrieves and removes next watch key, waiting if none are yet present.
        //
        // Explanation
        // WatchService has two types of methods for retrieving the WatchKeys - poll and take. The two poll methods return null if no key
        // is present and do not wait for ever. The take method doesn't return null but keeps waiting until a key is available.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Given:

        class ItemProcessor extends Thread { // LINE 1 // implements Runnable

            CyclicBarrier cb;

            public ItemProcessor(CyclicBarrier cb) {
                this.cb = cb;
            }

            public void run() {
                System.out.println("processed");
                try {
                    cb.await();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        class Merger implements Runnable { // LINE 2

            public void run() {
                System.out.println("Value Merged");
            }
        }

        // What should be inserted in the following code such that run method of Merger will be executed only after the
        // thread started at 4 and the main thread have both invoked await?

        Merger m = new Merger();

        // 1. ItemProcessor needs to extend Thread otherwise ip.start() will not compile.

        // 2. Since there are a total two threads that are calling cb.await (one is the ItemProcessor thread and another one is the main thread),
        // you need to create a CyclicBarrier with number of parties parameter as 2. If you specify the number of parties parameter as 1,
        // Merger's run will be invoke as soon as the any thread invokes await but that is not what the problem statement wants.

        // two because the main thread will execute cb.await();
        CyclicBarrier cb = new CyclicBarrier(2, m); // LINE 3

        ItemProcessor ip = new ItemProcessor(cb);
        ip.start(); // LINE 4

        cb.await(); // wait the others threads arrive at the barrier

        System.out.println("Ends!");
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given that daylight Savings Time ends on Nov 1 at 2 AM in US/Eastern time zone. (As a result, 2 AM becomes 1 AM.),
        // what will the following code print?

        LocalDateTime ld1 = LocalDateTime.of(2015, Month.NOVEMBER, 1, 2, 0);
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of("US/Eastern"));

        LocalDateTime ld2 = LocalDateTime.of(2015, Month.NOVEMBER, 1, 1, 0);
        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of("US/Eastern"));
        long x = ChronoUnit.HOURS.between(zd1, zd2);

        System.out.println(x); // -2

        // Explanation
        // Think of it as follows - The time difference between two dates is simply the amount of time you need to go from date 1 to date 2.
        //
        // So if you want to go from 2AM to 3AM, how many hours do you need? On a regular day, you need 1 hour. That is, if you add 1 hour to 2AM,
        // you will get 3AM. However, as given in the problem statement, at the time of DST change, 2 AM becomes 3AM. That means, even though your
        // local date time is 2 AM, your ZonedDateTime is actually 3AM.
        //
        // The answer can therefore be short listed to 2 or -2. Now, as per the JavaDoc description of the between method, it returns negative 
        // value if the end is before the start. In the given code, our end date is 1AM, while the start date is 2AM. 
        // This means, the answer is -2.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
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
    static void test01_12() throws Exception {
        // Given: Daylight Savings Time ends on Nov 1 at 2 AM in US/Eastern time zone. As a result, 2 AM becomes 1 AM.
        // What will the following code print ?

        LocalDateTime ld = LocalDateTime.of(2015, Month.OCTOBER, 31, 10, 0);

        ZonedDateTime date = ZonedDateTime.of(ld, ZoneId.of("US/Eastern"));
        date = date.plus(Duration.ofDays(1)); // PT24H, but it wont add a day, just 24H to 10H, minus 1 of the Daylight savings...
        System.out.println(date); // 2015-11-01T09:00-05:00[US/Eastern]

        date = ZonedDateTime.of(ld, ZoneId.of("US/Eastern"));
        date = date.plus(Period.ofDays(1)); // add a day
        System.out.println(date); // 2015-11-01T10:00-05:00[US/Eastern]

        // Explanation
        // Important thing to remember here is that Period is used to manipulate dates in terms of days, months, and years, while Duration
        // is used to manipulate dates in terms of hours, minutes, and seconds. Therefore, Period doesn't mess with the time component of
        // the date while Duration may change the time component if the date is close to the DST boundary.
        //
        // Durations and periods differ in their treatment of daylight savings time when added to ZonedDateTime. A Duration will add an exact
        // number of seconds, thus a duration of one day is always exactly 24 hours. By contrast, a Period will add a conceptual day,
        // trying to maintain the local time.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        List<Integer> ls = Arrays.asList(1, 2, 3);
        // Which of the following options will compute the sum of all Integers in the list correctly?

        double sum01 = ls.stream().reduce(0, (a, b) -> a + b);
        // The reduce method performs a reduction on the elements of this stream, using the provided identity value and an associative 
        // accumulation function, and returns the reduced value.

        double sum02 = ls.stream().mapToInt(x -> x).sum();
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Which import statements are required to compile the following code?

        Date d = new Date();
        DateFormat df = DateFormat.getInstance();

        // import java.util.*;
        //
        // import java.text.*;
        //
        // Explanation
        // Date class is in java.util package. (There is one in java.sql package as well but you need not worry about it for the exam.) 
        // DateFormat (and other Formatters as well) is in java.text package.

    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // What will the following code print when run?

        LocalDateTime greatDay = LocalDateTime.parse("2015-01-01"); // 2
        String greatDayStr = greatDay.format(DateTimeFormatter.ISO_DATE_TIME); // 3
        System.out.println(greatDayStr);// 4

        // 2 will throw an exception at run time.

        // It will throw a DateTimeException because it doesn't have time component.
        // Exception in thread "main" java.time.format.DateTimeParseException: Text '2015-01-01' could not be parsed at index 10.
        // A String such as 2015-01-01T17:13:50 would have worked.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // What will the following code print?

        AtomicInteger ai = new AtomicInteger();

        Stream<String> stream = Stream.of("old", "king", "cole", "was", "a", "merry", "old", "soul").parallel();

        stream.filter(e -> {
            ai.incrementAndGet();
            return e.contains("o");

        }).allMatch(x -> x.indexOf("o") > 0);

        System.out.println("AI = " + ai);

        // Any number between 1 to 8
        //
        // Explanation
        // 1. In the given code, stream refers to a parallel stream. This implies that the JVM is free to break up the original stream into
        // multiple smaller streams, perform the operations on these pieces in parallel, and finally combine the results.
        //
        // 2. Here, the stream consists of 8 elements. It is, therefore, possible for a JVM running on an eight core machine to split this
        // stream into 8 streams (with one element each) and invoke the filter operation on each of them. If this happens, ai will
        // be incremented 8 times.
        //
        // 3. It is also possible that the JVM decides not to split the stream at all. In this case, it will invoke the filter predicate on
        // the first element (which will return true) and then invoke the allMatch predicate (which will return false because "old".indexOf("o") is 0).
        // Since allMatch is a short circuiting terminal operation, it knows that there is no point in checking other elements because the result
        // will be false anyway. Hence, in this scenario, ai will be incremented only once.
        //
        // 4. The number of pieces that the original stream will be split into could be anything between 1 and 8 and by applying the same logic
        // as above, we can say that ai will be incremented any number of times between 1 and 8.
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Which of the following statements are valid usages of StandardOpenOption options that determine how the file is opened?
        // You had to select three options.

        // 1º Option
        OpenOption[] op1 = new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DSYNC };

        // 2º Option
        OpenOption[] op2 = new OpenOption[]{ StandardOpenOption.APPEND, StandardOpenOption.SYNC };

        // 3º Option
        OpenOption[] op3 = new OpenOption[]{ StandardOpenOption.READ, StandardOpenOption.SYNC };

        // Explanation
        // Observe that some combinations such as CREATE and READ do not make sense if put together and therefore an IllegalArgumentException
        // will be thrown in such cases. There are questions on the exam that expect you to know such combinations.
        //
        // if you want to truncate a file, then you must open it with an option that allows writing. Thus, READ and TRUNCATE_EXISTING
        // (or WRITE, APPEND, or DELETE_ON_CLOSE) cannot go together.  READ and SYNC (or DSYNC) cannot go together either because when a file is
        // opened for READ, there is nothing to synch, but on some platforms it works.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Given:

        List<Book> books = Arrays.asList(new Book(1, "Book A", "technology", "a1", 10), new Book(2, "Book B", "thriller", "b1", 15));

        // Assuming that books is a List of Book objects, what can be inserted in the code below at DECLARATION and EXPRESSION so that
        // it will classify the books by genre and then also by author?

        // DECLARATION
        Map<String, Map<String, List<Book>>> classified = books.stream() //
                .collect( //
                        Collectors.groupingBy( // EXPRESSION
                                Book::getGenre, Collectors.groupingBy(Book::getAuthor) //
                        ) //
                );

        // Map<String, Map<String, List<Book>>>
        // and
        // Book::getGenre, Collectors.groupingBy(Book::getAuthor)
        //
        // Explanation
        // "classify" implies that you need to create a Map with classifier values as keys and whatever values are generated by the
        // downstream collector as values.
        //
        // Here, we want to have genre names as keys for the top level map. The values of this map will also be Maps with author
        // names as keys and the List of books as values.
        //
        // This declaration and the classification expression correctly achieve the requirement. The following code and its output show a sample:

        books = Arrays.asList(new Book(1, "There is a hippy on the highway", "Thriller", "James Hadley Chase", 15), //
                new Book(2, "Coffin from Hongkong", "Thriller", "James Hadley Chase", 10), //
                new Book(3, "The Client", "Thriller", "John Grisham", 5), //
                new Book(4, "Gone with the wind", "Fiction", "Margaret Mitchell", 40));

        Map<String, Map<String, List<Book>>> classified2 = books.stream() //
                .collect( //
                        Collectors.groupingBy(x -> x.getGenre(), //
                                Collectors.groupingBy(x -> x.getAuthor())));

        System.out.println(classified);
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

        public Book(int id, String title, String genre, String author, double price) {
            super();
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.author = author;
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
    static void test01_19(String records1, String records2) throws Exception {
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
    static void test01_20() throws Exception {
        // Which of the following statements are true regarding the Fork/Join framework?
        //
        // The worker threads in the ForkJoinPool extend java.lang.Thread and are created by a factory.
        // By default, they are created by the default thread factory but another factory may be passed in the constructor. They do extend Thread.
        //
        // One worker thread may steal work from another worker thread.
        // A ForkJoinPool differs from other kinds of ExecutorService mainly by virtue of employing work-stealing: all threads in the pool 
        // attempt to find and execute subtasks created by other active tasks (eventually blocking waiting for work if none exist). 
        // This enables efficient processing when most tasks spawn other subtasks (as do most ForkJoinTasks).
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
