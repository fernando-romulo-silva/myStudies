package br.fernando.ch14_tests.part06_final.test01;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test01_01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // Which of the following statements correctly creates a PathMatcher?

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**.{htm,xml}");

        // FYI, the given glob pattern matches all the files with extension .htm or .xml. ** means, the pattern will match across
        // directory boundaries. For example, It will match index.htm as well as c:\temp\test\index.htm But if you replace ** with *,
        // it will match only index.htm.

        // Explanation:
        // java.nio.file.FileSystem provides an interface to a file system and is the factory for objects to access files and other
        // objects in the file system.
        // A file system is the factory for several types of objects:
        //
        // The getPath method converts a system dependent path string, returning a Path object that may be used to locate and access a file.
        FileSystems.getDefault().getPath(""); // like Paths.get(" ");
        //
        // The getPathMatcher method is used to create a PathMatcher that performs match operations on paths.
        FileSystems.getDefault().getPathMatcher("glob:*.java");
        //
        // The getFileStores method returns an iterator over the underlying file-stores.
        FileSystems.getDefault().getFileStores();
        //
        // The getUserPrincipalLookupService method returns the UserPrincipalLookupService to lookup users or groups by name.
        FileSystems.getDefault().getUserPrincipalLookupService();
        //
        // The newWatchService method creates a WatchService that may be used to watch objects for changes and events.
        FileSystems.getDefault().newWatchService();
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
        // Assuming that Book has appropriate constructor and accessor methods, what will the following code print?

        class Book {

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

            public void setPrice(Double price) {
                this.price = price;
            }
        }

        List<Book> books = Arrays.asList( //
                new Book("Freedom at Midnight", 5.0), //
                new Book("Gone with the wind", 5.0), //
                new Book("Midnight Cowboy", 15.0));

        books.stream().filter(b -> b.getTitle().startsWith("F")) //
                .forEach(b -> b.setPrice(10.0));

        books.stream().forEach(b -> System.out.println(b.getTitle() + ":" + b.getPrice()));

        // Freedom at Midnight:10.0
        // Gone with the wind:5.0
        // Midnight Cowboy:15.0

        //
        // Explanation
        //
        // This code illustrates how you can go through a list of elements, filter the elements based on a criteria, and call methods on each of the
        // elements in the filtered list.
        //
        // filter method removes all the elements for which the given condition (i.e. b.getTitle().startsWith("F")) returns false from the stream.
        // These elements are not removed from the underlying list but only from the stream. Therefore, when you create a stream from the list again,
        // it will have all the elements from the list.
        // Since the setPrice operation changes the Book object contained in the list, the updated value is shown the second time when you go through the list.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print when compiled and run? (new)

        List<String> names = Arrays.asList("greg", "dave", "don", "ed", "fred");

        Map<Integer, Long> data = names.stream() //
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        System.out.println(data.values());
        // [1, 1, 3]

        // The code first groups the elements based on their length and then counts the number of elements in each group.
        // Therefore, data map will actually contain: {2=1, 3=1, 4=3}.
        // Since we are printing only values, it will print [1, 1, 3].

    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {

        class Student {
        }

        // Given:
        List<Student> sList = new CopyOnWriteArrayList<Student>();

        // Which of the following statements are correct?

        // Multiple threads can safely add and remove objects from sList simultaneously.

        // Explanation
        // A thread-safe variant of ArrayList in which all mutative operations (add, set, and so on) are implemented by making
        // a fresh copy of the underlying array.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
        // Given:
        Path p = Paths.get("c:\\temp\\test.txt");

        // Which of the following statements will make the file test.txt hidden?

        Files.setAttribute(p, "dos:hidden", true);
    }

    // =========================================================================================================================================
    static void test01_10(String... args) throws Exception {
        // What will the following code print when run with command line: java TestSIS a A

        for (String arg : args) {
            switch (arg) {
            case "a":
                System.out.println("great!");
                break;
            default:
                System.out.println("unknown");
            }
        }

        // great!
        // unknown

        // Remember that String comparison is case sensitive in a switch statement.
        // Also, FYI, if the switch variable (i.e. arg, in this example) turns out to be null at run time, a NullPointerException is thrown
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // What will the following code print?

        /**
         * <pre>
         *  
        int value = 1,000,000; //1         
        
        switch(value){             
            case 1_000_000 : System.out.println("A million 1"); //2                 
            break;             
            case 1000000 : System.out.println("A million 2"); //3                 
            break;         
        }
         * </pre>
         **/

        // Compilation error because of //1 and //3

        // Explanation
        //
        // 1. You may use underscores (but not commas) to format a number for better code readability. So //1 is invalid.
        //
        // 2. Adding underscores doesn't actually change the number. The compiler ignores the underscores.
        // So 1_000_000 and 1000000 are actually same and you cannot have two case blocks with the same value. Therefore, the second case at //3 is invalid.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // What will the following code print when compiled and run?

        List<Movie> movies = Arrays.asList( //
                new Movie("On the Waterfront", Movie.Genre.DRAMA, 'U'), //
                new Movie("Psycho", Movie.Genre.THRILLER, 'U'), //
                new Movie("Oldboy", Movie.Genre.THRILLER, 'R'), //
                new Movie("Shining", Movie.Genre.HORROR, 'U')); //

        movies.stream() //
                .filter(mov -> mov.getRating() == 'R') //
                .peek(mov -> System.out.println(mov.getName())) //
                .map(mov -> mov.getName());

        // It will not print anything.
        //
        // Explanation
        // To answer this question, you need to know two things - distinction between "intermediate" and "terminal" operations and which
        // operations of Stream are "intermediate" operations.
    }

    static class Movie {

        static enum Genre {
            DRAMA,
            THRILLER,
            HORROR,
            ACTION
        };

        private Genre genre;

        private String name;

        private char rating = 'R';

        Movie(String name, Genre genre, char rating) {
            this.name = name;
            this.genre = genre;
            this.rating = rating;
        } // accessors not shown

        public String getName() {
            return name;
        }

        public Genre getGenre() {
            return genre;
        }

        public char getRating() {
            return rating;
        }
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // What will the following code print?

        // ReentrantLock rlock01 = new ReentrantLock();
        // boolean f1 = rlock01.lock();
        // System.out.println(f1);
        // boolean f2 = rlock01.lock();
        // System.out.println(f2);

        //
        // It will not compile.
        ReentrantLock rlock02 = new ReentrantLock();

        // Lock.lock() returns void.
        rlock02.lock();

        // Lock.tryLock() returns boolean. Had the code been:         
        boolean f1 = rlock02.tryLock();
        System.out.println(f1);
        boolean f2 = rlock02.tryLock();
        System.out.println(f2);

        // It would have printed: true true
        //
        // Note that ReentrantLock implements Lock
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Given:

        List<Book> books = Arrays.asList(//
                new Book(1, "Book A", "technology", "a1", 10), // 
                new Book(2, "Book B", "thriller", "b1", 15));

        // Assuming that books is a List of Book objects, what can be inserted in the code below at DECLARATION and EXPRESSION so that
        // it will classify the books by genre and then also by author?

        // DECLARATION
        Map<String, Map<String, List<Book>>> classified = books.stream() //
                .collect( //
                        Collectors.groupingBy( // EXPRESSION
                                Book::getGenre, // 
                                Collectors.groupingBy(Book::getAuthor) //
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

        books = Arrays.asList(//
                new Book(1, "There is a hippy on the highway", "Thriller", "James Hadley Chase", 15), //
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
    static void test01_15() throws Exception {
        // Assume that dt refers to a valid java.util.Date object and that df is a reference variable of class DateFormat.
        // Which of the following code fragments will print the country and the date in the correct local format?

        Date dt = new Date();

        Locale l = Locale.getDefault();
        DateFormat df = DateFormat.getDateInstance(); // watch out!
        System.out.println(l.getCountry() + " " + df.format(dt));
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Given that Book is a valid class with appropriate constructor and getPrice and getTitle methods that returns a double and a String respectively,
        // consider the following code:

        List<List<Book>> books = Arrays.asList( //
                Arrays.asList( //
                        new Book("Windmills of the Gods", 7.0), //
                        new Book("Tell me your dreams", 9.0)), //
                Arrays.asList( //
                        new Book("There is a hippy on the highway", 5.0), //
                        new Book("Easy come easy go", 5.0))); //

        // What can be inserted in the above code so that it will print 26.0?
        double d = books.stream() //
                .flatMap(bs -> bs.stream()) // 1
                .mapToDouble(book -> book.getPrice())// 2
                .sum();

        System.out.println(d);

        // .flatMap(bs->bs.stream()) 
        // and 
        // .mapToDouble(book->book.getPrice())
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // You want to create a new file. If the file already exists, you want the new file to overwrite the existing one (the content of
        // the existing file, if any, should go away). Which of the following code fragments will accomplish this?

        Path myfile = Paths.get("c:\\temp\\test.txt");

        BufferedWriter br = Files.newBufferedWriter(myfile, Charset.forName("UTF-8"), // 
                new OpenOption[]{ StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE });

        // If the file already exists, TRUNCATE_EXISTING will take care of the existing content. 
        // If the file does not exist, CREATE will ensure that it is created.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Given:
        LocalDate d1 = LocalDate.parse("2015-02-05", DateTimeFormatter.ISO_DATE);
        LocalDate d2 = LocalDate.of(2015, 2, 5);
        LocalDate d3 = LocalDate.now();
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        // Assuming that the current date on the system is 5th Feb, 2015, which of the following will be a part of the output?
        //
        // None of the above.
        //
        // All the three printlns will produce 2015-02-05.
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // What will the following code print when run?
        // Assume that the local time on the system at the time this code is run is 2nd Sep 2015 1:00 AM.

        Period p = Period.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1));
        System.out.println(p);

        Duration d = Duration.between(LocalDate.now(), LocalDate.of(2015, Month.SEPTEMBER, 1)); // throw exception here
        System.out.println(d);

        // It will throw an exception at run time.
        //
        // The call to Duration.between will throw java.time.temporal.UnsupportedTemporalTypeException because LocalDate.now() does not have a 
        // time component, while Duration.between method needs Temporal arguments that have a time component.
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Given
        Map<String, List<? extends CharSequence>> stateCitiesMap01 = new HashMap<String, List<? extends CharSequence>>();

        // Which of the following options correctly achieves the same declaration using type inferencing?

        Map<String, List<? extends CharSequence>> stateCitiesMap02 = new HashMap<>();

        // This option use the diamond operators correctly to indicate the type of objects stored in the HashMap to the compiler. 
        // The compiler inferences the type of the objects stored in the map using this information and uses it to prevent the code from adding 
        // objects of another type.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
