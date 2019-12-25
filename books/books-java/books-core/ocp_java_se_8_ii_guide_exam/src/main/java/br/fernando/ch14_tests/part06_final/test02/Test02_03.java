package br.fernando.ch14_tests.part06_final.test02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02_03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print ?

        List<Integer> ls = Arrays.asList(1, 2, 3);

        Function<Integer, Integer> func = a -> a * a; // 1

        ls.stream().map(func).peek(System.out::print); // 2

        // It will compile and run fine but will not print anything.
        // Remember that none of the "intermediate" operations on a stream are executed until a "terminal" operation is called on that stream.
        //
        // In the given code, there is no call to a terminal operation. map and peek are intermediate operations.
        // Therefore, the map function will not be invoked for any of the elements.
        //
        // If you append a terminal operation such as count(),
        // for example,
        ls.stream().map(func).peek(System.out::print).count(); // , it will print 149.
        //
        // Note that you can invoke at most one terminal operation on a stream and that too at the end.

    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // You need to recursively traverse through a given directory structure but under that directory structure, you need to traverse
        // sub directories only if the sub directory name starts with the name data.
        //
        // Which method of FileVisitor will be helpful in implementing this requirement?
        //
        // preVisitDirectory
        //
        // Explanation
        //
        // While walking a directory structure, the return value of preVisitDirectory determines if the files in that directory will be visited
        // or not. For example, the following code shows how this requirement can be implemented:
        //

        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/data*");

        class MyFileChecker extends SimpleFileVisitor<Path> {

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (matcher.matches(dir)) {
                    System.out.println("Will Visit files in " + dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    System.out.println("Will Skip files in " + dir);
                    return FileVisitResult.SKIP_SUBTREE;
                }
            }
        }

        MyFileChecker mfc = new MyFileChecker();
        Files.walkFileTree(Paths.get("c:\\works\\pathtest"), mfc);
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
        // Which of the following are valid enum values defined in java.nio.file.FileVisitResult?
        //
        // SKIP_SIBLINGS;
        //
        // SKIP_SUBTREE;

        // Explanation
        // ava.nio.file.FileVisitResult defines the following four enum constants :

        // Continue
        FileVisitResult fvr01 = FileVisitResult.CONTINUE;

        // Continue without visiting the siblings of this file or directory.
        FileVisitResult fvr02 = FileVisitResult.SKIP_SIBLINGS;

        // Continue without visiting the entries in this directory.
        FileVisitResult fvr03 = FileVisitResult.SKIP_SUBTREE;

        // Terminate.
        FileVisitResult fvr04 = FileVisitResult.TERMINATE;
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given:
        List<Integer> ls = Arrays.asList(10, 47, 33, 23);

        int max = 0; // INSERT code HERE

        System.out.println(max); // 1

        // Which of the following options can be inserted above so that it will print the largest number in the input stream?

        ls.stream().max(Comparator.comparing(a -> a)).get();

        // Comparator.comparing method requires a Function that takes an input and returns a Comparable. This Comparable, in turn, is used
        // by the comparing method to create a Comparator. The max method uses the Comparator to compare the elements int he stream.
        //
        // The lambda expression a->a creates a Function that takes an Integer and returns an Integer (which is a Comparable).
        // Here, the lambda expression does not do much but in situations where you have a class that doesn't implement Comparable and you
        // want to compare objects of that class using a property of that class that is Comparable, this is very useful.
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Given that the user account under which the following code is run does not have the permission to access a.java, what will the the
        // following code print when run?
        try (BufferedReader bfr = new BufferedReader(new FileReader("c:\\works\\a.java"))) {
            String line = null;
            while ((line = bfr.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) { // } catch (NoSuchFileException | IOException | AccessDeniedException e) {
            e.printStackTrace();
        }

        // It will not compile because the catch clause is invalid.

        // NoSuchFileException is a subclass of IOException. In the same multi-catch block, you cannot include classes that are related
        // by inheritance.   
        // Remember that BufferedReader.close (which is called automatically at the end of the try-with-resources block)
        // and BufferedReader.readLine methods throw java.io.IOException.
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // Consider the following program:

        try (FileReader fr = new FileReader("c:\\temp\\license.txt"); //
                FileWriter fw = new FileWriter("c:\\temp\\license2.txt")) {

            int x = -1;
            while ((x = fr.read()) != -1) {
                fw.write(x);
            }
        }
        // Identify the correct statements.

        // The FileWriter object will always be closed before the FileReader object.
        //
        // Resources are closed automatically at the end of the try block in reverse order of their creation.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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
    static void test01_09() throws Exception {
        // Consider the following code:
        double amount = 53000.35;
        Locale jp = new Locale("jp", "JP");

        // How will you create formatter using a factory at //1 so that the output is in Japanese Currency format?
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
    static void test01_10() throws Exception {
        // What will the following code print?

        AtomicInteger ai = new AtomicInteger();

        Stream<Integer> stream = Stream.of(11, 11, 22, 33).parallel();

        stream.filter(e -> {
            ai.incrementAndGet();
            return e % 2 == 0;
        });

        // 0

        // Notice that filter is an intermediate operation. It does not do anything until a terminal operation is invoked on the stream.
        // Therefore, in this case, ai will remain 0.
        System.out.println(ai);
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // Given:
        List<Integer> ls = Arrays.asList(1, 2, 3);

        // Which of the following options will compute the sum of all Integers in the list correctly?

        double sum01 = ls.stream().reduce(0, (a, b) -> a + b);
        // The reduce method performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation
        // function, and returns the reduced value.

        System.out.println(sum01);

        double sum02 = ls.stream().mapToInt(x -> x).sum();

        System.out.println(sum02);
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Given that daylight Savings Time starts on March 8th at 2 AM in US/Eastern time zone.
        // (As a result, 2 AM becomes 3 AM.), what will the following code print?

        LocalDateTime ld1 = LocalDateTime.of(2015, Month.MARCH, 8, 2, 0);
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of("US/Eastern"));
        LocalDateTime ld2 = LocalDateTime.of(2015, Month.MARCH, 8, 3, 0);
        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of("US/Eastern"));
        long x = ChronoUnit.HOURS.between(zd1, zd2);
        System.out.println(x);

        // 0

        // Explanation
        // Think of it as follows - The time difference between two dates is simply the amount of time you need to go from date 1 to date 2.
        //
        // So if you want to go from 2AM to 3AM, how many hours do you need? On a regular day, you need 1 hour. That is, if you add 1 hour to 2AM,
        // you will get 3AM. However, as given in the problem statement, at the time of DST change, 2 AM becomes 3AM. That means, even though your
        // local date time is 2 AM, your ZonedDateTime is actually 3AM.
        // Therefore, you are already at 3AM, which means, there is no time difference between 2 AM and 3 AM. The answer is, therefore, 0.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // Given that java.lang.String has two overloaded toUpperCase methods - toUpperCase() and toUpperCase(Locale ), consider the following code:

        String name = "bob";
        String val = null;
        // Insert code here
        System.out.print(val);

        // Which of the following code fragments can be inserted in the above code so that it will print BOB?

        Supplier<String> s = name::toUpperCase;
        val = s.get();
        // The functional method of the interface Supplier<T> is T get(). name::toUpperCase correctly implements this interface.
        // It returns the value returned by calling name.toUpperCase.

        // and

        // Only one of the above is correct.
        //
        // If you want to make use of toUpperCase(Locale ) method, you should do:
        Function<Locale, String> f1 = name::toUpperCase;
        // You can then use it like this:
        val = f1.apply(Locale.UK);
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // You have multiple threads in your application that need to generate random numbers between 1 to 10 (both inclusive) frequently.
        // Which of the following statements would you use considering overhead and contention as the main criteria?

        int r = ThreadLocalRandom.current().nextInt(1, 11);
        // Note that the concurrent use of the same 'java.util.Random' instance across threads may encounter contention and consequent poor performance.
        // Consider instead using ThreadLocalRandom in multithreaded designs.
        // There are a few basic questions about generating random numbers in the exam.
        // Besides Math.random() method, you should know about ThreadLocalRandom class.
    }

    // =========================================================================================================================================
    static void test01_15() throws Exception {
        // You have a collection (say, an ArrayList) which is read by multiple reader threads and which is modified by a single writer thread.
        // The collection allows multiple concurrent reads but does not tolerate concurrent read and write.
        // Which of the following strategies will you use to obtain best performance?

        //
        // Encapsulate the collection into another class and use ReadWriteLock to manage read and write access.
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // How will you initialize a SimpleDateFormat object so that the following code will print the full name of the month of the given date?

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.FRANCE);

        // Upper case M is for Month. For example, for February and December, the following will be printed:
        // M => 2, 12
        // MM => 02, 12
        // MMM => févr., déc.
        // MMMM => février, décembre

        System.out.println(sdf.format(new Date()));
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
        // Given classes
        // What should be inserted in the following code such that run method of Merger will be executed only after the thread started at 4 and
        // the main thread have both invoked await?

        // Make ItemProcessor extend Thread instead of implementing Runnable
        class ItemProcessor extends Thread { // implements Runnable { // LINE 1

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

        Merger m = new Merger();

        CyclicBarrier cb = new CyclicBarrier(2, m); // LINE 3

        ItemProcessor ip = new ItemProcessor(cb);

        ip.start(); // LINE 4

        cb.await();

        // 1. ItemProcessor needs to extend Thread otherwise ip.start() will not compile.
        //
        // 2. Since there are a total two threads that are calling cb.await ( one is the ItemProcessor thread and another one is the main thread),
        // you need to create a CyclicBarrier with number of parties parameter as 2.
        // If you specify the number of parties parameter as 1, Merger's run will be invoke as soon as the any thread invokes await but that is not
        // what the problem statement wants.
        //
        // Explanation:
        // Briefly, a CyclicBarrier allows multiple threads to run independently but wait at one point until all of the coordinating threads arrive
        // at that point. Once all the threads arrive at that point, all the threads can then proceed.
        // It is like multiple cyclists taking different routes to reach a particular junction. They may arrive at different times but they will wait
    }

    // =========================================================================================================================================
    static void test01_18() throws Exception {
        // What will the following code print when compiled and run?

        String[] sa = new String[]{ "a", "b", null };

        for (String s : sa) {
            switch (s) {
            case "a":
                System.out.println("Got a");
            }
        }

        // Got a 
        // a NullPointerException stack trace
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // Identify valid statements.

        // 1º Option
        Locale myLocale01 = Locale.getDefault();
        //
        // 2º Option
        Locale myLocale02 = Locale.US;
        // Locale class has several static constants for standard country locales.
        //
        // 3º Option
        Locale myLocale03 = new Locale("ru", "RU");
        // You don't have to worry about the actual values of the language and country codes. Just remember that both are two lettered codes
        // and country codes are always upper case.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
