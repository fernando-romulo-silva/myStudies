package br.fernando.ch14_tests.part06_final.test01;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test01_02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following correctly identify the differences between a Callable and a Runnable?

        // A Callable cannot be passed as an argument while creating a Thread but a Runnable can be.
        // new Thread( aRunnable ); is valid. But new Thread( aCallable ); is not.
        //
        // A Callable needs to implement call() method while a Runnable needs to implement run() method.
        //
        // A Callable can return a value but a Runnable cannot.
        //
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What will the following code print?

        List<Item> items = Arrays.asList(new Item("Pen", "Stationery", 3.0), //
                new Item("Pencil", "Stationery", 2.0), //
                new Item("Eraser", "Stationery", 1.0), //
                new Item("Milk", "Food", 2.0), //
                new Item("Eggs", "Food", 3.0) //
        );

        ToDoubleFunction<Item> priceF = Item::getPrice; // 1

        items.stream() //
                .collect(Collectors.groupingBy(Item::getCategory)) // 2
                .forEach((a, b) -> { //
                    double av = b.stream().collect(Collectors.averagingDouble(priceF)); // 3
                    System.out.println(a + " : " + av); //
                });

        // Stationery : 2.0
        // Food : 2.5
        //
        // 1. collect(Collectors.groupingBy(Item::getCategory)) returns a Map where the keys are the Category values and the values are Lists of the elements.
        //
        // 2. collect(Collectors.averagingDouble(priceF)) returns the average of the values returned by the priceF function when it is applied to each element of the Stream.
        // In this case, a stream is created from the List of elements belonging to each categtory by the call to forEach.
    }

    private static class Item {

        private int id;

        private String name;

        private String category;

        private double price;

        public Item(int id, String name) {
            this(name, null, 0);
            this.id = id;
        }

        public Item(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // Given that Book is a valid class with appropriate constructor and getTitle and getPrice methods that return a String and
        // a Double respectively, what will the following code print?

        List<Book> books = Arrays.asList( ///
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
    static void test01_04() throws Exception {
        // Give
        final ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String, Object>();

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

        // It may print any combination except: a, or b, or a, b, or b, a,
        // This is correct because the order of iteration is not known and so the thread that removes "a" and "b", may remove them in any order.
        // Thus, the iterator thread may or may not see "a" and/or "b" through its Iterator. However, "c" is never removed from the
        // map and so c, will always be printed.

        // Explanation
        // An important thing to know about the Iterators retrieved from a ConcurrentHashMap is that they are backed by that ConcurrentHashMap,
        // which means any operations done on the ConcurrentHashMap instance may be reflected in the Iterator.

        // The view's iterator is a "weakly consistent" iterator that will never throw ConcurrentModificationException, and guarantees to traverse
        // elements as they existed upon construction of the iterator, and may (but is not guaranteed to) reflect any modifications subsequent to construction.

        // only for wait the end
        Thread.sleep(10000);
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Consider the following code:
        Locale myLoc = new Locale("fr", "FR");
        ResourceBundle rb = ResourceBundle.getBundle("appmessages", myLoc);

        // Which of the following lines of code will assign a ResourceBundle for a different Locale to rb than the one currently assigned?

        rb = ResourceBundle.getBundle("appmessages", new Locale("ch", "CH"));

        // and

        rb = ResourceBundle.getBundle("appmessages", Locale.CHINA);

        // Explanation
        // Note that once a ResourceBundle is retrieved, changing the Locale will not affect the ResourceBundle.
        // You have to retrieve a new ResourceBundle by passing in the new Locale and then assign it to the variable.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print?
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", 1);

        map1.merge("b", 1, (i1, i2) -> i1 + i2);
        map1.merge("c", 3, (i1, i2) -> i1 + i2);

        System.out.println(map1);
        // {a=1, b=2, c=3}

        // Explanation
        //
        // If the specified key is not already associated with a value or is associated with null, associates it with the given non-null value.
        // Otherwise, replaces the associated value with the results of the given remapping function, or removes if the result is null.
        // This method may be of use when combining multiple mapped values for a key.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Given the following code:
        AtomicInteger status = new AtomicInteger(0);

        int oldstatus = status.get();
        /* valid code here */
        int newstatus = 10;

        // Assuming that an instance of this class is shared among multiple threads, you want to update the status to newstatus only if the
        // oldstatus has not changed. Which of the following lines of code will you use?

        status.compareAndSet(oldstatus, newstatus);

        // compareAndSet(expectedValue, newValue) is meant exactly for this purpose. It first checks if the current value is same as the
        // expected value and if so, updates to the new value.

        // Explanation
        // public final boolean compareAndSet(int expect, int update) Atomically sets the value to the given updated value if the current
        // value == the expected value. Parameters: expect - the expected value update - the new value Returns: true if successful.
        // False return indicates that the actual value was not equal to the expected value.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // What will the following code print?
        int x = 1____3; // 1
        long y = 1_3; // 2
        float z = 3.234_567f; // 3

        System.out.println(x + " " + y + " " + z);

        // 13 13 3.234567

        // The number at //1 and //2 are actually the same. Although confusing, it is legal to have multiple underscores between two digits.
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // What can be inserted at line 2 in the code snippet given below?
        String[] names = { "Alex", "Bob", "Charlie" };

        // Insert code here
        //
        // List<?> list means that list is a List of anything. It does not tell you anything about the type of objects that will be there in
        // the list except that they are all Objects. Remember that List<?> is not same as List<Object>.
        List<?> list01 = new ArrayList<>(Arrays.asList(names)); // when create it, okay
        //
        // or
        //
        // Here, list is a List of Strings.
        List<String> list02 = new ArrayList<>(Arrays.asList(names));

        System.out.println(list01.get(0));
        System.out.println(list02.get(0));

        // list01.add(""); // you can't add something ...

        // You can't create a instance of '?', instance okay?
        // List<?> list = new ArrayList<?>();

        // list01.addAll(new ArrayList<String>()); // you can't addAll too

    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
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
    static void test01_11() throws Exception {
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

    static int count;

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // What will the following code snippet print?

        Stream<Integer> strm1 = Stream.of(2, 3, 5, 7, 11, 13);
        double av = strm1 //
                .filter(x -> {
                    if (x > 10)
                        return true;
                    else return false;
                }) // 1
                // .peek() //2 error here
                .collect(Collectors.averagingInt(y -> y)); // 3

        System.out.println(av);

        // Compilation failure due to code at //2
        // peek expects a Consumer object as an argument. 
        // Calling peek() without any argument will, therefore, fail to compile.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
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
    static void test01_14() throws Exception {
        class Device implements AutoCloseable {

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

        // What will be printed when the following code is compiled and run?
        try (Device d = new Device()) {
            d.open();
            d.writeHeader("TEST");
            d.close();
        } catch (IOException e) {
            System.out.println("Got Exception");
        }

        // Device Opened
        // Writing : TEST
        // Device closed
        // Device closed

        // Explanation
        //
        // 1. d.open() will print Device Opened
        // 2. d.writeHeader("TEST") will print Writing : TEST
        // 3. d.close() will print Device Closed
        //
        // Since Device is instantiated in try-with-resources block, d.close() will be called automatically at the end of the try block.
        // Thus, Device Closed will be printed again. The fact that the code explicitly calls d.close() is irrelevant.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // Given Student class
        //
        // What can be inserted in the code below so that it will print:
        // {C=[S3:C], A=[S1:A, S2:A]}

        List<Student> ls = Arrays.asList(new Student("S1", Student.Grade.A), new Student("S2", Student.Grade.A), new Student("S3", Student.Grade.C));

        Map<Student.Grade, List<Student>> grouping = ls.stream().collect(Collectors.groupingBy(s -> s.getGrade()));
        
        System.out.println(grouping);

        // Explanation
        // This code illustrates the usage of the simplest of the Collectors methods.
        //
        // Collectors.groupingBy(s -> s.getGrade()) returns a Collector that applies a function on the elements of a stream to get a key and
        // then group the elements of the stream by that key into lists.
        //
        // Stream's collect method uses this Collector and returns a Map where the key is the key returned by the function and the value is a
        // List containing all the elements that returned the same key.
        //
        // Here, the function s ->s.getGrade() provides the Grades as the key. The keys returned by this function are, therefore, A, A, and
        // C respectively. Since there are two unique keys (A, C), the Map returned by the collect method will contain
        // two key-value pairs: {C=[S3:C], A=[S1:A, S2:A]}
    }

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

        public String toString() {
            return name + ":" + grade;
        }
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Which of the following options correctly makes use of java.util.concurrent.Callable?

        // Since MyTask binds Callable to String, the return value of call() must be a String.

        class MyTask implements Callable<String> {

            public String call() throws Exception {
                // do something
                return "Data from callable";
            }
        }

        Future<String> result = Executors.newSingleThreadExecutor().submit(new MyTask());
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Assuming that a file named "a.java" exists in c:\pathtest\ as well as in c:\pathtest\dir2, what will happen when the following
        // code is compiled and run?

        Path p1 = Paths.get("c:\\pathtest\\a.java");
        Path p2 = Paths.get("c:\\pathtest\\dir2\\a.java");
        Files.move(p1, p2, StandardCopyOption.ATOMIC_MOVE);
        Files.delete(p1);
        System.out.println(p1.toFile().exists() + " " + p2.toFile().exists());

        // It will throw an exception at run time.

        // There are two contradictory factors at play here.
        //
        // 1. By default, Files.move method attempts to move the file to the target file, failing if the target file exists except
        // if the source and target are the same file, in which case this method has no effect. Therefore, this code should throw an exception
        // because a.java exists in the target directory.
        //
        // 2. However, when the CopyOption argument of the move method is StandardCopyOption.ATOMIC_MOVE, the operation is implementation
        // dependent if the target file exists. The existing file could be replaced or an IOException could be thrown.
        // If the exiting file at p2 is replaced, Files.delete(p1) will throw java.nio.file.NoSuchFileException
        //
        // Therefore, in this case, the given code on the whole will end up with an exception.
        //
        // Some candidates have reported being tested on StandardCopyOption.ATOMIC_MOVE. Unfortunately, it is not clear whether the
        // exam tests on the implementation dependent aspect of this option. If you get a question on it and the options do not contain any
        // option referring to its implementation dependent nature, we suggest the following: Assume that the move operation will succeed and
        // then the delete operation will throw a java.nio.file.NoSuchFileException.
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // Which of the following options correctly create an ExecutorService instance?

        ExecutorService es01 = Executors.newFixedThreadPool(2);

        // You need to remember the following points about a few important classes in java.util.concurrent package:
        //
        // 1. ExecutorService interface extends Executor interface. While Executor allows you to execute a Runnable, ExecutorService allows you to execute a Callable.
        //
        // 2. Executors is a utility class that provides several static methods to create instances of ExecutorService.
        // All such methods start with new e.g. newSingleThreadExecutor().
        //
        // You should at least remember the following methods:
        //
        // newFixedThreadPool(int noOfThreads), newSingleThreadExecutor(), newCachedThreadPool(), newSingleThreadScheduledExecutor(),
        // newScheduledThreadPool(int corePoolSize)

        ExecutorService es02 = Executors.newSingleThreadExecutor();

        ExecutorService es03 = Executors.newCachedThreadPool();

        ExecutorService es04 = Executors.newSingleThreadScheduledExecutor();

        ExecutorService es05 = Executors.newScheduledThreadPool(10);
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // Which of the following options correctly add 1 month and 1 day to a given LocalDate
        LocalDate ld = LocalDate.now();

        LocalDate ld2 = ld.plus(Period.of(0, 1, 1));
        //
        // public static Period of(int years, int months, int days) Obtains a Period representing a number of years, months and days.
        // This creates an instance based on years, months and days.
        //
        // Wrong Answer
        LocalDate ld3 = ld.plus(Period.ofMonths(1).ofDays(1));
        //
        // ofXXX are static methods of Period class. Therefore, Period.ofMonths(1).ofDays(1) will give you a Period of only 1 day. 
        // The previous call to ofMonths(1) does return an instance of Period comprising 1 month but that instance is irrelevant 
        // because ofDays is a static method.
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // What will the following code print full text time zone name.
        // For example, Eastern Standard Time (If the Locale is US, UK, or any other English based Locale)
        // Heure normale de l'Est (If the Locale is France)
        
        SimpleDateFormat sdf = new SimpleDateFormat("zzzz");
        System.out.println(sdf.format(new Date())); // Horário de Verão de Brasília        

        // Letter---------Date or Time Component------Presentation---------------Examples
        // G--------------Era designator--------------Text-----------------------AD

        // y--------------Year------------------------Year-----------------------1996; 96
        // Y--------------Week year-------------------Year-----------------------2009; 09

        // M--------------Month in year---------------Number---------------------July; Jul; 07

        // w--------------Week in year----------------Number---------------------27
        // W--------------Week in month---------------Number---------------------2
        // D--------------Day in year-----------------Number---------------------189
        // d--------------Day in Month----------------Number---------------------10
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
