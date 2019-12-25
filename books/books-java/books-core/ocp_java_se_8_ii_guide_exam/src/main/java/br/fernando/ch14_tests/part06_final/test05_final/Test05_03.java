package br.fernando.ch14_tests.part06_final.test05_final;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05_03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given the following line of code:
        LocalDateTime dt = LocalDateTime.parse("2015-01-02T17:13:50");

        // Which of the following lines will return the date string in ISO 8601 format?

        // 1º
        dt.format(java.time.format.DateTimeFormatter.ISO_DATE_TIME);
        //
        // 2º
        dt.toString();
        // LocalDateTime's toString method generates the String in ISO 8601 format.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Given that you have the following files in a directory named "reports":
        //
        // salary.pdf
        // payslips.pdf
        // resume.rtf
        // expensesrtf (There is no . in the name)
        //
        // What will the following program print when run assuming that reports directory is present in the current directory?

        class Path3 extends SimpleFileVisitor<Path> {

            // Atention here: :*.{pdf,rtf}
            private PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:*pdf,rtf");

            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                if (pm.matches(file.getFileName())) {
                    System.out.println("" + file.getFileName());
                }
                return FileVisitResult.CONTINUE;
            }
        }

        Path3 p3 = new Path3();
        Path startwith = Paths.get("reports");
        Files.walkFileTree(startwith, p3);

        // It will not generate any output.
        //
        // Notice that the given glob pattern is valid but not appropriate. It is looking for file names that end with pdf,rtf and not for
        // file names that end with pdf or rtf. If you change it to glob:*.{pdf,rtf}, it will match any file that ends with .pdf or .rtf,
        // in which case expensesrtf will not match. If you use glob:*{pdf,rtf}, it will match all four names given in the question.

    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Which of the following are valid standard country locale codes as returned by getCountry() method of Locale?
        //
        // ES : ES is for Spain
        // US : US is for United States of America
        // FR : FR is for France

        // Explanation
        //
        // It is not possible to remember all the country code and that is not required for the exam either. All you need to know is standard
        // country codes are always in upper-case and are always two-letters.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Given:
        class ComplicatedTask extends RecursiveTask<Integer> {

            private static final long serialVersionUID = 1L;

            int[] ia;

            int from;

            int to;

            static final int THRESHOLD = 3;

            public ComplicatedTask(int[] ia, int from, int to) {
                this.ia = ia;
                this.from = from;
                this.to = to;
            }

            public int transform(int t) {
                // this is a CPU intensive operation that
                // transforms t and returns the value
                return 0;
            }

            protected Integer compute() {
                if (from + THRESHOLD > to) {
                    int sum = 0;
                    for (int i = from; i <= to; i++) {
                        sum = sum + transform(ia[i]);
                    }
                    return sum;
                } else {
                    int mid = (from + to) / 2;
                    ComplicatedTask newtask1 = new ComplicatedTask(ia, from, mid);
                    ComplicatedTask newtask2 = new ComplicatedTask(ia, mid + 1, to);

                    newtask1.fork();

                    int x = newtask2.compute(); // LINE A
                    int y = newtask1.join(); // LINE B

                    return x + y;
                }
            }
        }

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };

        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedTask st = new ComplicatedTask(ia, 0, 6);

        int sum = fjp.invoke(st);
        System.out.println("sum = " + sum);

        // What will happen if LINE A and LINE B are flipped i.e. LINE B is moved before LINE A?
        //
        // There will not be any impact on the final answer but performance will be degraded.
        //
        // The order of join() and compute() is critical. Remember that fork() causes the sub-task to be submitted to the pool and another
        // thread can execute that task in parallel to the current thread.
        //
        // Therefore, if you call join() on the newly created sub task, you are basically waiting until that task finishes. This means
        // you are using up both the threads (current thread and another thread from the pool that executes the subtask) for that sub task.
        //
        // Instead of waiting, you should use the current thread to compute another subtask and when done, wait for another thread to finish.
        // This means, both the threads will execute their respective tasks in parallel instead of in sequence.
        //
        // Therefore, even though the final answer will be the same, the performance will not be the same.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // A java programer has written the following code: Book and BookFilter
        // He wants to make use of the above code in another class as follows -

        List<Book> books = Arrays.asList( //
                new Book("30 Days", "fiction", "K Larsen"), //
                new Book("Fast Food Nation", "non-fiction", "Eric Schlosser"), //
                new Book("Wired", "fiction", "D Richards"));

        books.stream()//
                // .filter(new Book.BookFilter()) //
                .filter((Book b) -> Book.BookFilter.isFiction(b)) // 1º
                .filter(Book.BookFilter::isFiction) // 2º
                .forEach((Book b) -> System.out.print(b.getTitle()));

        // What changes mentioned below can he make independent of each other so that the above code will print 30 Days, Wired, ?

        // 1º Make the isFiction method in BookFilter class static and replace LINE 10 with: .filter((Book b)-> Book.BookFilter.isFiction(b))
        //
        // 2º Make the isFiction method in BookFilter class static and replace LINE 10 with: .filter(Book.BookFilter::isFiction)
    }

    static private class Book {

        private int id;

        private String title;

        private String genre;

        private String author;

        public Book(String title, String genre, String author) {
            this.title = title;
            this.genre = genre;
            this.author = author;
        }

        public String getGenre() {
            return genre;
        }

        public String getTitle() {
            return title;
        }

        static class BookFilter {

            public static boolean isFiction(Book b) {
                return b.getGenre().equals("fiction");
            }
        }
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print when compiled and run?
        BinaryOperator<String> bo = (s1, s2) -> s1.concat(s2);
        List<String> names = new ArrayList<>();
        names.add("Bill");
        names.add("George");
        names.add("Obama");
        String finalvalue = names.stream().reduce("Hello : ", bo);
        System.out.println(finalvalue);

        // Hello : BillGeorgeObama

        // Explanation
        //
        // This question tests you on Lambda expressions, Functional interfaces, and Stream API.
        //
        // 1. The lambda expression (s1, s2) -> s1.concat(s2); is quite straight forward. It implements the functional interface BinaryOperator,
        // which has one abstract method apply(T, T). Here, T is typed to String and the method body simply returns the concatenated String.
        //
        // 2. The reduce method of a Stream is mean to reduce a stream into just one value. It combines two elements of a stream at a time using
        // a given BinaryOperator, and replaces those two elements in the stream with the return value of the apply method of BinaryOperator.
        // It keeps on doing this reduction until there is just one value left.
        //
        // There are three versions of reduce :
        //
        // Optional<T>  reduce(BinaryOperator<T> accumulator)
        // Performs a reduction on the elements of this stream, using an associative accumulation function, and returns an Optional describing
        // the reduced value, if any.
        //
        // T reduce(T identity, BinaryOperator<T> accumulator)
        // Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function,
        // and returns the reduced value.
        //
        // <U> U  reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        // Performs a reduction on the elements of this stream, using the provided identity, accumulation and combining functions.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {

        // Given the following class:

        class SpecialPicker<K> {

            public K pickOne(K k1, K k2) {
                return k1.hashCode() > k2.hashCode() ? k1 : k2;
            }
        }

        // what will be the result of running the following lines of code:

        SpecialPicker<Integer> sp = new SpecialPicker<>(); // 1
        System.out.println(sp.pickOne(1, 2).intValue() + 1); // 2

        // It will print 3.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Which of the following elements is/are a must in a stream pipeline?

        // a source
        //
        // a terminal operation

        // Explanation:
        //
        // A pipeline contains the following components:
        //
        // A source: This could be a collection, an array, a generator function, or an I/O channel. In this example, the source is
        // the collection roster.
        //
        // Zero or more intermediate operations: An intermediate operation, such as filter, produces a new stream.
        //
        // A terminal operation.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Consider the following code:
        Runnable r = () -> System.out.println("In Runnable");

        Callable<Integer> c = () -> {
            System.out.println("In Callable");
            return 0;
        };

        ExecutorService es = Executors.newCachedThreadPool();

        // What can be inserted in the above code so that both - Runnable r and Callable c - will be executed?

        es.submit(r);
        es.submit(c);
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {

        // Given

        class Book {

            private String title;

            private String genre;

            public Book(String title, String genre) {
                this.title = title;
                this.genre = genre;
            }

            public String getTitle() {
                return title;
            }

            public String getGenre() {
                return genre;
            }
        }

        // And the following code:

        List<Book> books = Arrays.asList(new Book("book a", "horror"), new Book("book b", "commedy"), new Book("book c", "horror"));

        Comparator<Book> c1 = (b1, b2) -> b1.getGenre().compareTo(b2.getGenre()); // 1
        books.stream().sorted(c1.thenComparing(Book::getTitle)); // 2
        System.out.println(books);

        // Identify the correct statements.

        // It will print the list as it is.
        //
        // 1. Manipulating a stream doesn't manipulate the backing source of the stream. Here, when you chain the sorted method to a stream,
        // it returns a reference to a Stream that appears sorted. The original List which was used to create the stream will remain as it is.
        // If you want to sort a List permanently, you should use one of the Collections.sort methods.
        //
        // 2. There is another issue with this code. Stream.sorted is an intermediate operation. It will not be executed until a terminal
        // operation is invoked on the stream. Therefore, in this case, the sorted method will not even be invoked.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Given:
        final List<String> letters = Arrays.asList("j", "a", "v", "a");

        String word = ""; // INSERT CODE HERE

        // Which of the following code fragments when inserted in the above code independent of each other will cause it to print java?

        word = letters.stream().collect(Collectors.joining());

        System.out.println(word);
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Consider the following code:

        Path myPath = Paths.get("c:\\temp\\text.txt");
        UserPrincipal up = myPath.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("john");
        Files.setOwner(myPath, up);

        System.out.println("done");

        // Assuming that text.txt exists, which of the following statements will correctly open the file and append data to it, which is written
        // to the disk synchronously but allows the meta data to be written to the disk later?
        //
        // 1º
        try (BufferedWriter br = Files.newBufferedWriter(myPath, Charset.forName("UTF-8"), //
                new OpenOption[]{ StandardOpenOption.APPEND, StandardOpenOption.DSYNC })) {
            // Since the file already exists, APPEND option will cause data to be written at the end. DSYNC option forces the data to be written
            // to the disk synchronously but allows the meta data to be written to the disk later.

            br.write("..appended.");
        }

        // 2º

        try (BufferedWriter br = Files.newBufferedWriter(myPath, Charset.forName("UTF-8"), //
                new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.DSYNC })) {
            // This is same as option 3. The WRITE option is redundant in this case because the file already exists. Had the file not been present,
            // it would have thrown java.nio.file.NoSuchFileException.
            br.write("..appended.");
        }

        // Errors
        BufferedWriter br01 = Files.newBufferedWriter(myPath, Charset.forName("UTF-8"), new OpenOption[]{ StandardOpenOption.WRITE, StandardOpenOption.DSYNC });
        // WRITE option will cause the file to be written from the beginning. Thus, it will overwrite existing data.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // What will the following code print?

        Optional<String> grade1 = getGrade(50);
        Optional<String> grade2 = getGrade(55);
        System.out.println(grade1.orElse("UNKNOWN"));
        if (grade2.isPresent()) {
            grade2.ifPresent(x -> System.out.println(x));
        } else {
            System.out.println(grade2.orElse("Empty"));
        }

        // UNKNOWN
        // PASS

        // Explanation
        //
        // Here are a few important things you need to know about Optional class:
        //
        // 1. Optional has a static method named of(T t) that returns an Optional object containing the value passed as argument. It will throw
        // NullPointerException if you pass null. If you want to avoid NullPointerException, you should use Optional.ofNullable(T t) method.
        // This will return Optional.empty if you pass null.
        //
        // 2. You cannot change the contents of Optional object after creation. Optional does not have a set method. Therefore, grade.of,
        // although technically correct, will not actually change the Optional object referred to by grade. It will return a new Optional object
        // containing the passed argument.
        //
        // 3. The orElse method returns the actual object contained inside the Optional or the argument passed to this method if the Optional
        // is empty. It does not return an Optional object. Therefore, print(grade1.orElse("UNKNOWN")) will print UNKNOWN and not Optional[UNKNOWN].
        //
        // 4. isPresent() returns true if the Optional contains a value, false otherwise.
        //
        // 5. ifPresent(Consumer ) executes the Consumer object with the value if the Optional contains a value. Not that it is the value contained
        // in the Optional that is passed to the Consumer and not the Optional itself.
    }

    public static Optional<String> getGrade(int marks) {
        Optional<String> grade = Optional.empty();
        if (marks > 50) {
            grade = Optional.of("PASS");
        } else {
            grade.of("FAIL");
        }

        return grade;
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {

        // What will the following code fragment print?

        Path p1 = Paths.get("c:\\..\\temp\\test.txt");
        System.out.println(p1.normalize().toUri());

        // file:///c:/temp/test.txt

        // In this case, .. is at the top level and there is no parent directory at this level. Therefore, it is redundant and is removed from
        // the normalized path. Had there been a parent directory for .., for example, c:/temp/../test.txt, the parent directory and ..
        // would cancel out ( i.e. the result would be c:/test.txt ).
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Which of the following are valid declarations inside an interface independent of each other?

        // public void compute();
        //
        // and
        //
        // static void compute(){    System.out.println("computing..."); }
        //
        // This is a valid static method in an interface. Note that all members of an interface (i.e. fields as well as methods) are always public.
    }

    static interface InterfaceTest {

        public void compute01();

        static void compute02() {
            System.out.println("computing...");
        }
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // What will the following code print when run?

        Path p1 = Paths.get("c:\\finance\\data\\reports\\daily\\pnl.txt");

        System.out.println(p1.subpath(0, 2));

        // finance\data
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Consider the following code:

        Path dir = Paths.get("c:\\temp");

        /**
         * <pre>
         *   //INSERT CODE HERE            
         *       for(Path p : ds){               
         *          System.out.println(p);            
         *       }      
         *   } catch(Exception e){             
         *        e.printStackTrace();         
         *   }
         * </pre>
         */

        // What should be inserted in the above code so that it will print all the files with extension gif and jpeg?

        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*.{gif,jpeg}");

            for (Path p : ds) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Examples of glob syntax:
        // *.java : Matches a path that represents a file name ending in .java
        // *.* : Matches file names containing a dot
        // *.{java,class} : Matches file names ending with .java or .class
        // foo.? : Matches file names starting with foo. and a single character extension
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Identify the correct statements about the following code

        IntStream is1 = IntStream.of(1, 3, 5); // 1
        OptionalDouble x = is1.filter(i -> i % 2 == 0).average(); // 2
        System.out.println(x); // 3
        IntStream is2 = IntStream.of(2, 4, 6); // 4
        int y = is2.filter(i -> i % 2 != 0).sum(); // 5
        System.out.println(y); // 6

        // Successful compilation
        //
        // Explanation
        //
        // Remember that average() methods of all numeric streams (i.e. IntStream, DoubleStream, and LongStream) returns an OptionalDouble.
        // If the stream has no elements (as is the case in this question), the returned OptionalDouble will be empty (not 0 or null).
        //
        // But the sum() methods of the numeric streams return a primitive value of the same type i.e. IntStream returns an int, DoubleStream
        // returns a double, and LongStream returns a long. If the stream is empty, the sum() method returns 0.
        //
        // Thus, the given code will compile and run without any issue. It will print : OptionalDouble.empty 0

    }

    // =========================================================================================================================================
    static void test01_19(String[] args) throws Exception {

        // What will the following code print when compiled and run?

        String str = null;
        switch (str) {
        case "null":
            System.out.println("1");
            break;
        case "":
            System.out.println("2");
            break;
        default:
            System.out.println("3");
        }

        // a NullPointerException stack trace
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Given:
        HashMap<Integer, String> hm = new HashMap<>();
        hm.put(1, "a");
        hm.put(2, "b");
        hm.put(3, "c");

        // Which of the following statements will print the keys as well as values contained in the map?

        hm.forEach((key, value) -> System.out.printf("%d %s ", key, value));
    }

    // =========================================================================================================================================
    static void test01_21() throws Exception {
        // Given interface Runner

        // Which of the following is/are valid lambda expression(s) that capture(s) the above interface?

        Runner ru1 = () -> System.out.println("running...");

        Runner ru2 = () -> {
            System.out.println("running...");
            return;
        };

        // Explanation
        //
        // Runner is a valid functional interface because it has exactly one abstract method.
        // Since this method does not take any parameter, the parameter list part of the lambda expression must be ().
        // Further, since it does not return anything, the body part must be such that it does not return anything either.
    }

    interface Runner {

        public void run();
    }

    // =========================================================================================================================================
    static void test01_22() throws Exception {
        // Given:

        Instant now = Instant.now();
        Instant now2 = null; // INSERT CODE HERE
        System.out.println(now2);

        // Which of the following options can be inserted in the above code without causing any compilation or runtime errors?

        now2 = now.truncatedTo(ChronoUnit.DAYS);
        
        // Instant class has a truncatedTo method that takes in a TemporalUnit and returns a new Instant with the fields smaller than the passed 
        // unit set to zero. For example, if you pass ChronoUnit.DAYS, hours, minutes, seconds, and nano-seconds will be set to 0 in the 
        // resulting Instant.  
        // 
        // TemporalUnit is an interface and ChronoUnit is a class that implements this interface and defines constants such as DAYS, MONTHS, and YEARS.  
        // FYI, any unit larger than ChronoUnit.DAYS causes the truncatedTo method to throw UnsupportedTemporalTypeException
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
