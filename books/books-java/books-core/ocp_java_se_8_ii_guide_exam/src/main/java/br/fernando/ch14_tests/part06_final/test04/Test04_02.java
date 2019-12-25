package br.fernando.ch14_tests.part06_final.test04;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04_02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given :
        ArrayList<Data> al = new ArrayList<Data>();
        al.add(new Data(1));
        al.add(new Data(2));
        al.add(new Data(3));

        // Which of the following options can be inserted above so that it will print 1 4 9?
        //

        processList(al, (int a, int b) -> System.out.println(a * b));

        processList(al, (a, b) -> System.out.println(a * b));
        // It is ok to omit the parameter types in case of a functional interface because the compiler can determine the type of the parameters
        // by looking at the interface method.

        processList(al, (a, b) -> {
            System.out.println(a * b);
        }); //
        // If you enclose your method body within curly braces, you must write complete lines of code including the semi-colon.
    }

    public static void processList(ArrayList<Data> dataList, Process p) {
        for (Data d : dataList) {
            p.process(d.value, d.value);
        }
    }

    static interface Process {

        public void process(int a, int b);
    }

    static public class Data {

        int value;

        Data(int value) {
            this.value = value;
        }
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Identify the correct statements about the following code:
        class Account {

            private String id;

            public Account(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            };

        }

        class BankAccount extends Account {

            private double balance;

            public BankAccount(String id, double balance) {
                super(id);
                this.balance = balance;
            }

            public double getBalance() {
                return balance;
            }
        }

        Map<String, Account> myAccts = new HashMap<>();
        myAccts.put("111", new Account("111"));
        myAccts.put("222", new BankAccount("111", 200.0));

        BiFunction<String, Account, Account> bif = (a1, a2) -> a2 instanceof BankAccount ? new BankAccount(a1, 300.0) : new Account(a1); // 1
        myAccts.computeIfPresent("222", bif); // 2
        BankAccount ba = (BankAccount) myAccts.get("222");
        System.out.println(ba.getBalance());

        // It will print 300.0
        // Since myAccts map does contain a key "222", computeIfPresent method will execute the function and replace the existing value
        // associated with the given key in the map with the new value returned by the function.

        // Explanation
        // You need to know about the three flavors of compute methods of Map:
        //
        // 1. public V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
        //
        // 2. public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
        //
        // 3. public V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)        
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code print?

        Stream<String> ss = Stream.of("a", "b", "c");
        String str = ss.collect(Collectors.joining(",", "-", "+"));
        System.out.println(str);

        // -a,b,c+
        //
        // Collectors.joining(",", "-", "+") returns a Collector that joins all the Strings in the given Stream separated by comma and then
        // prefixes the resulting String with "-" and suffixes the String with "+".
        //
        // Explanation
        // Returns a Collector that concatenates the input elements, separated by the specified delimiter, with the specified prefix and
        // suffix, in encounter order.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // Which of the following is true about the PathMatcher returned by FileSystem.getPathMatcher()?

        // It can match Paths using a regular expression or a glob pattern depending on how it is acquired.
        //
        // You can get a PathMatcher using either of the expressions. For example:

        FileSystems.getDefault().getPathMatcher("glob:*.java"); // Uses a glob pattern
        // or
        FileSystems.getDefault().getPathMatcher("regex:a.*b\\.txt"); // uses a regular expression pattern
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Consider the Student code:

        class Student {

            private Map<String, Integer> marksObtained = new HashMap<String, Integer>();

            private ReadWriteLock lock = new ReentrantReadWriteLock();

            public void setMarksInSubject(String subject, Integer marks) {
                // 1 INSERT CODE HERE
                lock.writeLock().lock();

                try {
                    marksObtained.put(subject, marks);
                } finally {
                    // 2 INSERT CODE HERE
                    lock.writeLock().unlock();
                }

            }

            public double getAverageMarks() {

                lock.readLock().lock(); // 3

                double sum = 0.0;
                try {
                    for (Integer mark : marksObtained.values()) {
                        sum = sum + mark;
                    }
                    return sum / marksObtained.size();
                } finally {
                    lock.readLock().unlock();// 4
                }
            }
        }

        // What should be inserted at //1 and //2?

        // lock.writeLock().lock();
        // and
        // lock.writeLock().unlock();

        // Explanation
        //
        // From a ReadWriteLock, you can get one read lock (by calling lock.readLock() ) and one write lock (by calling lock.writeLock()).
        //
        // Even if you call these methods multiple times, the same lock is returned.
        // A read lock can be locked by multiple threads simultaneously (by calling lock.readLock().lock() ), if the write lock is free.
        //
        // If the write lock is not free, a read lock cannot be locked. The write lock can be locked (by calling lock.writeLock().lock() )
        // only by only one thread and only when no thread already has a read lock or the write lock. In other words, if one thread is reading,
        // other threads can read, but no thread can write. If one thread is writing, no other thread can read or write.
        //
        // Methods that do not modify the collection (i.e. the threads that just "read" a collection) should acquire a read lock and threads
        // that modify a collection should acquire a write lock.
        //
        // The benefit of this approach is that multiple reader threads can run without blocking if the write lock is free.
        // This increases performance for read only operations. The following is the complete code that you should try to run:

        final Student s = new Student();

        // create one thread that keeps adding marks
        new Thread() {

            public void run() {
                int x = 0;
                while (true) {
                    int m = (int) (Math.random() * 100);
                    s.setMarksInSubject("Sub " + x, m);
                    x++;
                }
            }
        }.start();

        // create 5 threads that get average marks
        for (int i = 0; i < 5; i++) {
            new Thread() {

                public void run() {
                    while (true) {
                        double av = s.getAverageMarks();
                        System.out.println(av);
                    }
                }
            }.start();
        }

        // Note that if you remove the line //1, //2, //3, and //4, (i.e. if you don't use any locking), you will see a ConcurrentModificationException.
    }

    // =========================================================================================================================================
    static void test01_06(String records1, String records2) throws Exception {
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
    static void test01_07() throws Exception {
        // What will the following line of code print?

        System.out.println(LocalDate.of(2015, Month.JANUARY, 31).format(DateTimeFormatter.ISO_DATE_TIME));

        // Exception at run time.
        //
        // Observe that you are creating a LocalDate and not a LocalDateTime. LocalDate doesn't have time component and therefore,
        // you cannot format it with a formatter that expects time component such as DateTimeFormatter.ISO_DATE_TIME
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // A Locale object represents...

        // a specific geographical, political, or cultural region.

        // Explanation
        // A Locale object just represents a region. Based on a given Locale, you can do various things such as display text in that region's
        // language and/or style. Every Java runtime has a default Locale, which represents the region set by the operating system,
        // but it can be changed.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // What will the following code print?
        Instant ins = Instant.parse("2015-06-25T16:43:30.00z");
        ins.plus(10, ChronoUnit.HOURS);
        System.out.println(ins);

        // 2015-06-25T16:43:30Z

        // Explanation
        // java.time.Instant has several versions of plus and minus methods. However, remember that none of these methods change the Instant object itself.
        // They return a new Instant object with the modification. In this case, it would return a new Instant object containing 2015-06-26T02:43:30Z.
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Which of the given option when inserted in the code below will make the code print true?

        List<String> values = Arrays.asList("Alpha A", "Alpha B", "Alpha C");

        //INSERT CODE HERE
        boolean flag = false;

        flag = values.stream().allMatch(str -> str.equals("Alpha")); // false
        flag = values.stream().findFirst().get().equals("Alpha");// false
        flag = values.stream().findAny().get().equals("Alpha"); // false
        flag = values.stream().anyMatch(str -> str.equals("Alpha")); // false

        // None of the above
        System.out.println(flag);

        // Predicate's test in options 1 and 4 and the call to equals in options 2 and 3 always return false, whichever element we apply them to. 
        // If we replaced equals() with contains() or startsWith(), they would all be correct.

    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Given
        Map<String, List<? extends CharSequence>> stateCitiesMap01 = new HashMap<String, List<? extends CharSequence>>();

        // Which of the following options correctly achieves the same declaration using type inferencing?

        Map<String, List<? extends CharSequence>> stateCitiesMap02 = new HashMap<>();

        // This option use the diamond operators correctly to indicate the type of objects stored in the HashMap to the compiler. 
        // The compiler inferences the type of the objects stored in the map using this information and uses it to prevent the code from adding 
        // objects of another type.
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Identify correct statements about Java Date and Time API

        // Classes used to represent dates and times in java.time package are thread safe.

        // All classess in java.time package such as classes for date, time, date and time combined, time zones, instants,
        // duration, and clocks are immutable and thread-safe.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // Which of the following standard functional interfaces is most suitable to process a large collection of int primitives and 
        // return processed data for each of them?

        // IntFunction

        // Explanation
        // Using the regular functional interfaces by parameterizing them to Integer is inefficient as compared to using specially designed 
        // interfaces for primitives because they avoid the cost of boxing and unboxing the primitives.
        //
        // Now, since the problem statement requires something to be returned after processing each int, you need to use a Function instead 
        // of a Consumer or a Predicate.
        //
        // Therefore, IntFunction is most appropriate in this case.
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // Assuming that the directory /works/ocpjp/code exists but /works/ocpjp/code/sample does not exist, what will the following code output?

        Path d1 = Paths.get("/works");
        Path d2 = d1.resolve("ocpjp/code"); // 1

        d1.resolve("ocpjp/code/sample"); // 2

        d1.toAbsolutePath(); // 3

        System.out.println(d1);
        System.out.println(d2);

        // \works
        // \works\ocpjp\code
        //
        // 1. The first println for d1 is straight forward.
        //
        // 2. d2 is created by resolving "ocpjp/code" with "/works". Since "ocpjp/code" is a relative path, d2 will contain "/works/ocpjp/code"

        // Explanation
        // 1. Path operations don't really care about whether the path actually exists on the file system. A Path object just represents an
        // abstract path that may or may not exist. It is only when you actually try to create or write something to the given path that its
        // existence is required.
        //
        // 2. As per JavaDoc description of Path interface, its implementations are immutable and safe for use by multiple concurrent
        // threads. This implies that the operations such as resolve and toAbsolutePath don't change the Path object itself.
        // They return a new Path object.
        //
        // In this case, lines //2 and //3 will actually produce Path objects representing "\works\ocpjp\code\sample"
        // and "C:\works" respectively. (Assuming that C:\ is the root of the file system.)
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Which of the following code fragments correctly prints all the roots of the default file system?

        FileSystem fs = FileSystems.getDefault();

        Iterable<Path> roots = fs.getRootDirectories();

        for (Path p : roots) {
            System.out.println(p);
        }
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Consider the following piece of code, which is run in an environment where the default locale is English - US:

        Locale.setDefault(new Locale("fr", "CA")); // Set default to French Canada
        Locale l = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", l);
        String msg = rb.getString("greetings");
        System.out.println(msg);

        // You have created two resource bundles for appmessages, with the following contents:
        //
        // #In English US resource bundle file
        // greetings=Hello
        //
        // #In French CA resource bundle file
        // greetings=bonjour

        // What will be the output?
        //
        // bonjour
        //
        // Explanation
        // While retrieving a message bundle, you are passing a locale explicitly (fr CA). Therefore, appmessages_fr_CA.properties will be loaded.
        // This file contains "bonjour" for "greetings", which is what is printed.
        //
        // Observe that the setting of default locale makes no difference in this case because the new default locale and the locale that you are
        // passing are same and a resource bundle is available for that locale.
        //
        // 
        // However, if you do ResourceBundle.getBundle("appmessages", new Locale("es", "ES")); and if there is no appmessages_es_ES.properties 
        // file, the role of default locale becomes crucial. In this case, appmessages_fr_CA.properties will be loaded instead of 
        // appmessages_en_US.properties because you've changed the default locale and when a resource bundle is not found for a given locale, 
        // the default locale is used to load the resource bundle.
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Which of the following attributes are supported by BasicFileAttributeView?

        // isDirectory
        // size
        // creationTime

        // Explanation
        // The following attributes are supported by BasicFileAttributeView:
        //
        // Name ----------------------Type
        // "lastModifiedTime"---------FileTime
        // "lastAccessTime"-----------FileTime
        // "creationTime"-------------FileTime
        // "size"---------------------Long
        // "isRegularFile"------------Boolean
        // "isDirectory---------------Boolean
        // "isSymbolicLink"-----------Boolean
        // "isOther"------------------Boolean
        // "fileKey"------------------Object

        // Attributes supported by DosFileAttributeView (which extends BasicFileAttributeView) are:
        // "readOnly"-----------------Boolean
        // "hidden"-------------------Boolean
        // "system"-------------------Boolean
        // "archive"------------------Boolean
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // You need to recursively traverse through a given directory structure. Some files in that directory structure may not be accessible
        // to your application because of insufficient access rights. In such a case, you want to skip visiting files and directories in
        // that sub directory. Which method of FileVisitor will be helpful in implementing this requirement?

        // visitFileFailed

        // In case of an error while visiting a file, this method is called. From this method,
        // if you return FileVisitResult.SKIP_SIBLINGS, none of the remaining files or subdirectories in that directory will be visited.
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
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
    static void test01_20() throws Exception {
        // What will the following code print when run?

        Path p1 = Paths.get("c:\\code\\java\\PathTest.java");
        System.out.println(p1.getName(3).toString());

        // It will throw IllegalArgumentException

        // Explanation
        //
        // Thus, for example, If your Path is "c:\\code\\java\\PathTest.java",
        //
        // p1.getRoot() is c:\  ((For Unix based environments, the root is usually / ).
        // p1.getName(0) is code
        // p1.getName(1) is java
        // p1.getName(2) is PathTest.java
        // p1.getName(3) will cause IllegalArgumentException to be thrown.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
