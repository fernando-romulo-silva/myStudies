package br.fernando.ch14_tests.part01_most_missed_tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What is the state of the WatchKey at the end of the following code?

        Path path = Paths.get("C:/temp");

        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, //
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

        WatchKey key = watchService.take();

        // status of key here

        // Signaled

        // Explanation
        // When initially created the key is said to be ready. When an event is detected then the key is signaled and queued so that it can be
        // retrieved by invoking the watch service's poll or take methods. Once signalled, a key remains in this state until its reset method
        // is invoked to return the key to the ready state.
        // Cancels the registration with the watch service. Upon return the watch key will be invalid.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // The following line of code has thrown java.nio.file.FileSystemNotFoundException when run.
        // What might be the reason(s)?

        Path p1 = Paths.get(new URI("file://e:/temp/records"));

        // The file system, identified by the URI, does not exist.
        //
        // The provider identified by the URI's scheme component is not installed.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Consider the following code.

        Date d = new Date();
        DateFormat df = null; // 1 INSERT CODE HERE
        String s = null; // 2 INSERT CODE HERE
        System.out.println(s);

        // What should be inserted at //1 and //2 above so that it will print the date in default date format for the UK Locale?

        df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.UK);
        // and
        df.format(d);

        // Explanation
        // There are several getXXXInstance() methods in DateFormat class. For the purpose of the exam, you should remember the following:
        //
        // static DateFormat getDateInstance()
        // Gets the date formatter with the default formatting style for the default locale.
        //
        // static DateFormat getDateInstance(int style)
        // Gets the date formatter with the given formatting style for the default locale.
        //
        // static DateFormat getDateInstance(int style, Locale aLocale)
        // Gets the date formatter with the given formatting style for the given locale.
        //
        // The formatting styles include DEFAULT, FULL, LONG, MEDIUM, and SHORT.
        //
        // Errors
        //
        // The getInstance() method doesn't take any parameter. static getInstance() - Get a default date/time formatter that uses the 
        // SHORT style for both the date and the time.
    }

    // =========================================================================================================================================

    static int count = 0;

    static void test01_04() throws Exception {
        // Consider the following code and the directory structure of c:\works\pathtest shown in the image.

        // pathtest(dir1(c.java, dir12(d.java)), dir2(b.java), a.java)

        class MyFileChecker extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;

            public MyFileChecker() { //** ATENTION **   
                matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");
            }

            void check(Path p) {
                //** ATENTION ** 
                if (p != null && matcher.matches(p)) {
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

        MyFileChecker mfc = new MyFileChecker();
        Files.walkFileTree(Paths.get("c:\\works\\pathtest"), mfc);
        System.out.println(mfc.getCount());

        // Assuming appropriate imports, what will be the output when this program is run?

        // 0
        //
        // Observe that in the glob pattern we have used a single * (which means it won't cross directory boundaries) and the Path objects 
        // that we are checking contain the complete path including directories (such as c:\works\pathtest\a.java and not just a.java).
        //
        // Therefore, nothing will match. We should either get a path with just the last name using Path lastName = p.getFileName(p) 
        // or change the glob pattern to **.java.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
        //  Note that ReentrantLock implements Lock
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
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
    static void test01_07() throws Exception {
        // Which of the following statements is/are true about java.util.function.IntFunction?

        // It takes int primitive as an argument. It can be parameterized to return any thing. For example
        IntFunction<String> f = x -> "" + x;

        // It avoids additional cost associated with auto-boxing/unboxing
        // Remember that primitive and object versions of data types (i.e. int and Integer, double and Double, etc.) are not really compatible with each other in java.
        //
        // To eliminate this problem, the function package contains primitive specialized versions of streams as well as functional interfaces.
        // For example, instead of using Stream<Integer>, you should use IntStream. You can now process each element of the stream using IntFunction.
        // This will avoid auto-boxing/unboxing altogether.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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
    static void test01_09(final String records1, final String records2) throws Exception {
        // Consider the following method code:

        try {
            InputStream is = new FileInputStream(records1);
            OutputStream os = new FileOutputStream(records2);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                System.out.println("Read and written bytes " + bytesRead);
            }

            // } catch (FileNotFoundException | IndexOutOfBoundsException e) { // original
        } catch (IOException | IndexOutOfBoundsException e) {

        }

        // Assuming appropriate import statements and the existence of both the files, what will happen when the program is compiled and run?

        // The program will not compile because it does not handle exceptions correctly.
        // Remember that most of the I/O operations (such as opening a stream on a file, reading or writing from/to a file) throw IOException 
        // and this code does not handle IOException.
        //
        // FileNotFoundException is a subclass of IOException and IndexOutOfBoundsException is subclass of RuntimeException. 
        // The code can be fixed by replacing FileNotFoundException | IndexOutOfBoundsException with IOException or by adding another catch 
        // block that catches IOException.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // What will the following code fragment print when compiled and run?
        Locale myloc = new Locale.Builder().setLanguage("en").setRegion("UK").build(); // L1
        ResourceBundle msgs = ResourceBundle.getBundle("mymsgs", myloc);

        Enumeration<String> en = msgs.getKeys();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            String val = msgs.getString(key);
            System.out.println(key + " : " + val);
        }

        // Assume that only the following two properties files (contents of the file is shown below the name of the file)
        // are accessible to the code.
        //
        // 1. mymsgs.properties
        // okLabel=OK
        // cancelLabel=Cancel
        //
        // 2. mymsgs_en_UK.properties
        //
        // okLabel=YES
        // noLabel=NO

        // Result:
        //
        // noLabel : NO
        // okLabel : YES
        // cancelLabel : Cancel
        //
        // mymsgs.properties is the base file for this resource bundle. Therefore, it will be loaded first. Since the language and region specific
        // file is also present (_en_UK), it will also be loaded and the values in this file will be superimposed on the values of the base file.
        //
        // Remember that if there were another properties file named mymsgs_en.properties also present, then that file would have been
        // loaded before mymsgs_en_UK.properties
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Assume that dt refers to a valid java.util.Date object and that df is a reference variable of class DateFormat.
        // Which of the following code fragments will print the country and the date in the correct local format?

        Date dt = new Date();

        Locale l = Locale.getDefault();
        DateFormat df = DateFormat.getDateInstance();
        System.out.println(l.getCountry() + " " + df.format(dt));

        // Only way to apply the locale is on the DateFormat constructor.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // You have to complete a big task that operates on a large array of integers. The task has to look at each element of the array and
        // update that element. The new value of the element is generated by a utility class's static method, which takes in the existing
        // value as a parameter and returns the new value. This method is computationally very intensive.
        //
        // What would be a good approach to solve this problem?
        //
        // Subclass RecursiveAction and implement the compute() method that computes the new value but does not return anything.
        //
        // Create a RecursiveAction that subdivides the task into two, then forks one of the tasks and computes another.
        // This is a standard way of using the Fork/Join framework. You create a RecursiveTask or RecursiveAction
        // (depending on where you need to return a value or not) and in that RecursiveTask, you subdivide the task into two equal parts.
        // You then fork out one of the halfs and compute the second half.
        //
        // Explanation
        // Since there is no requirement to do anything with the newly computed value (such as summing them up), you don't need to
        // return that value to anybody. You just need to update the array element with the new value. Therefore, you don't need RecursiveTask,
        // you need RecursiveAction. The following is a possible implementation:

        class ComplicatedAction extends RecursiveAction {

            int[] ia;

            int from;

            int to;

            public ComplicatedAction(int[] ia, int from, int to) {
                this.ia = ia;
                this.from = from;
                this.to = to;
            }

            protected void compute() {

                if (from == to) { // Update the value using logic implemented somewhere else.
                    ia[from] = UtilityClass.utilityMethod(ia[from]);
                } else {
                    int mid = (from + to) / 2;
                    ComplicatedAction newtask1 = new ComplicatedAction(ia, from, mid);
                    ComplicatedAction newtask2 = new ComplicatedAction(ia, mid + 1, to);
                    newtask2.fork();
                    newtask1.compute();
                    newtask2.join();
                }
            }
        }
        ;

        int ia[] = new int[]{ 1, 2, 3, 4, 5, 6, 7 };
        ForkJoinPool fjp = new ForkJoinPool();
        ComplicatedAction st = new ComplicatedAction(ia, 0, 6);
        fjp.invoke(st);
        System.out.print("New Array Values = ");

        for (int i : ia) {
            System.out.print(i + " ");
        }

    }

    private static class UtilityClass {

        public static int utilityMethod(int i) {
            return i + 1;
        }
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // What will the following code print when run?

        try {
            m2();
        } catch (Exception e) {
            Throwable[] ta = e.getSuppressed(); // Returns an array containing all of the exceptions that were suppressed, typically by the try-with-resources statement
            for (Throwable t : ta) {
                System.out.println(t.getMessage());
            }

            // the Exception e is java.lang.RuntimeException: Exception from finally
        }

        // It will not print any thing.

        // Explanation
        // In the given code, method m2() throws an exception explicitly from the catch block as well as from the finally block. Since this is an explicit finally
        // block (and not an implicit finally block that is created when you use a try-with-resources statement), the exception thrown by the finally block
        // is the one that is thrown from the method. The exception thrown from the catch block is lost. It is not added to the suppressed exceptions list of
        // the exception thrown from the finally block.
        //
        // Therefore, e.getSuppressed() returns an array with 0 elements and nothing is printed.
        //
        // Had the code for m3() been something like this:
        // Now, if m1() throws an exception and r.close() also throws an exception, the exception thrown by m1 would have been the one thrown by the method m3
        // and the exception thrown by r.close() would have been added to the list of suppressed exception of the exception thrown from the try block.
    }

    public static void m1() throws Exception {
        throw new Exception("Exception from m1");
    }

    @SuppressWarnings("finally")
    public static void m2() throws Exception {
        try {
            m1();
        } catch (Exception e) { // Can't do much about this exception so rethrow it
            throw e;
        } finally {
            throw new RuntimeException("Exception from finally");
        }
    }

    public static void m3() throws Exception {
        try (Connection c = DriverManager.getConnection("url", "user", "password");) {
            m1();
        }
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Given
        ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String, Object>();

        chm.put("a", "aaa");
        chm.put("b", "bbb");
        chm.put("c", "ccc");
        new Thread() {

            public void run() {
                Iterator<Entry<String, Object>> it = chm.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> en = it.next();
                    if (en.getKey().equals("a") || en.getKey().equals("b")) {
                        it.remove();
                    }
                }
            }
        }.start();

        new Thread() {

            public void run() {
                Iterator<Entry<String, Object>> it = chm.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> en = it.next();
                    System.out.print(en.getKey() + ", ");
                }
            }
        }.start();

        // Which of the following are possible outputs when the above program is run?
        //
        // It may print any combination except: a, or b, or a, b, or b, a,
        //
        // This is correct because the order of iteration is not known and so the thread that removes "a" and "b", may remove them in any order.
        // Thus, the iterator thread may or may not see "a" and/or "b" through its Iterator. However, "c" is never removed from the map and so c,
        // will always be printed.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Given
        class Book {

            String name;

            public Book(String name) {
                super();
                this.name = name;
            }
        }

        // What changes should be applied to the following class to update it to use generics without changing any functionality?
        // Choose minimal changes that are necessary to take advantage of generics.

        class BookStore {

            Map map = new HashMap(); // 1

            public BookStore() {
                map.put(new Book("A111"), new Integer(10)); // 2
            }

            public int getNumberOfCopies(Book b) { // 3
                Integer i = (Integer) map.get(b); // 4
                return i == null ? 0 : i.intValue(); // 5
            }
        }

        // Replace line //1 with Map<Book, Integer> map = new HashMap<Book, Integer>();
        // There is no need to change //4 and //5.
        //
        // Explanation
        //
        // Generics allow you to write type safe code by letting you specify what kinds of object you are going to store in a collection.
        // Here, by replacing Map with Map<Book, Integer>, we are making sure that only Book-Integer pairs are stored in the map.

        class BookStoreAnswer {

            Map<Book, Integer> map = new HashMap<Book, Integer>(); // 1

            public BookStoreAnswer() {
                map.put(new Book("A111"), new Integer(10)); // 2
            }

            public int getNumberOfCopies(Book b) { // 3
                Integer i = (Integer) map.get(b); // 4
                return i == null ? 0 : i.intValue(); // 5
            }
        }
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Given Course Class

        class Course {

            private String id;

            private String category;

            public Course(String id, String category) {
                this.id = id;
                this.category = category;
            }

            public String toString() {
                return id + " " + category;
            }

            public String getId() {
                return id;
            }

            public String getCategory() {
                return category;
            }
        }

        // What will the following code print?
        List<Course> s1 = Arrays.asList(new Course("OCAJP", "Java"), new Course("OCPJP", "Java"), //  
                new Course("C#", "C#"), new Course("OCEJPA", "Java"));

        s1.stream() //
                .collect(Collectors.groupingBy(c -> c.getCategory())) //
                .forEach((m, n) -> System.out.println(n));

        // [C# C#]
        // [OCAJP Java, OCPJP Java, OCEJPA Java]
        //
        // 1. Collectors.groupingBy(Function<? super T,? extends K> classifier) returns a Collector that groups elements of a Stream into multiple groups.
        // Elements are grouped by the value returned by applying a classifier function on an element.
        //
        // 2. It is important to understand that the return type of the collect method depends on the Collector that is passed as an argument.
        // In this case, the return type would be Map<K, List<T> because that is the type specified in the Collector returned by the groupingBy method.
        //
        // 3. Java 8 has added a default forEach method in Map interface. This method takes a BiConsumer function object and applies this function to each
        // key-value pair of the Map. In this case, m is the key and n is the value.
        //
        // 4. The given code provides a trivial lambda expression for BiConsumer that just prints the second parameter, which happens to be the value part
        // of of the key-value pair of the Map.
        //
        // 5. The value is actually an object of type List<Course>, which is printed in the output. Since there are two groups, two lists are printed.
        // First list has only one Course element and the second list has three.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
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
    static void test01_19() throws Exception {
        // What can be inserted in the code below so that it will print true when run?

        boolean b01 = false;// WRITE CODE HERE

        // 1º
        // The test method of Predicate returns a boolean. So all you need for your  body part in your lambda expression is an expression that
        // returns a boolean. isEmpty() is a valid method of ArrayList, which returns true if there are no elements in the list.
        // Therefore, al.isEmpty() constitutes a valid body for the lambda expression in this case.
        b01 = checkList(new ArrayList(), al -> al.isEmpty());
        //
        //
        // 2º
        // The add method of ArrayList returns a boolean. Further, it returns true if the list is altered because of the call to add.
        // In this case, al.add("hello") indeed alters the list because a new element is added to the list.
        b01 = checkList(new ArrayList(), al -> al.add("hello"));

        System.out.println(b01);
    }

    public static boolean checkList(List list, Predicate<List> p) {
        return p.test(list);
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Consider the following code.

        Date d = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        // df.setLocale(new Locale("fr", "FR")); // error here

        String s = null; // 1 insert code here.          

        System.out.println(s);

        // What should be inserted at //1 above so that it will print the date in French format?

        // None of these.

        // Observe that the code is doing df.setLocale(...). There is no such setLocale method in DateFormat or NumberFormat.
        // You cannot change the Locale of these objects after they are created. So this code will not compile.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
