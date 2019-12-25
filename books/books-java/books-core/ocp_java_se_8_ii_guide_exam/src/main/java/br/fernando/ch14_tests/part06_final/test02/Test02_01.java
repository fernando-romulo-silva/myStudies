package br.fernando.ch14_tests.part06_final.test02;

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02_01 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // What will the following code print when compiled and run?

        List<Integer> iList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Predicate<Integer> p = x -> x % 2 == 0;

        List newList = iList.stream().filter(x -> x > 3).filter(p).collect(Collectors.toList());
        System.out.println(newList);

        // [4, 6]

        // The given code illustrates how you can filter a stream multiple times. The important thing here is that only the elements that
        // satisfy the filter condition remain in the stream. Rest are eliminated.
        //
        // Here, the first condition (implemented by Predicate p) is that the numbers must be even. This means 1, 3, 5, and 7 are out.
        // Next, the second condition (specified directly in the call to filter method using lambda expression x->x>3 means that the number
        // must be greater than 3. This means only 4 and 6 will be left.
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // What can be inserted into the following code at //1 so that it will print "Oldboy"?

        List<Movie> movies = Arrays.asList( //
                new Movie("On the Waterfront", Movie.Genre.DRAMA), //
                new Movie("Psycho", Movie.Genre.THRILLER), //
                new Movie("Oldboy", Movie.Genre.THRILLER), //
                new Movie("Shining", Movie.Genre.HORROR)); //

        Predicate<Movie> horror = mov -> mov.getGenre() == Movie.Genre.THRILLER;

        Predicate<Movie> name = mov -> mov.getName().startsWith("O");

        // 1 INSERT CODE HERE .forEach(mov->System.out.println(mov.getName()));
        movies.stream() //
                .filter(horror) //
                .filter(name) //
                .forEach(mov -> System.out.println(mov.getName()));

        // Explanation
        // The given code illustrates how you can use functional interfaces, lambda expressions, and streams to filter a List of objects based
        // on multiple criteria. First, you need to convert the List into a Stream.
        //
        // List has stream() method that returns a Stream. Stream support filter(Predicate ) method, which filters the elements in the stream.
        // Only those elements for which Predicate's test() method returns true, are left remaining in the stream.
        //
        // The return type of filter method is Stream, so you can chain multiple filter methods one after another.
    }

    static class Movie {

        static enum Genre {
            DRAMA, //
            THRILLER, //
            HORROR, //
            ACTION //
        };

        private Genre genre;

        private String name;

        Movie(String name, Genre genre) {
            this.name = name;
            this.genre = genre;
        } // accessors not shown

        public String getName() {
            return name;
        }

        public Genre getGenre() {
            return genre;
        }
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // java.util.Locale allows you to do which of the following?
        //
        // Provide country and language specific formatting for Dates.
        //
        // Provide country specific formatting for Currencies.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        List<String> al = Arrays.asList("aa", "aaa", "b", "cc", "ccc", "ddd", "a");

        // Which of the following options will correctly print the number of elements that will come before the string "c"
        // if the list were sorted alphabetically?

        // INSERT CODE HERE
        long count = al.stream().filter((str) -> str.compareTo("c") < 0).count();

        System.out.println(count);

        // Explanation
        //
        // This is a very straight forward question. filter method will filter the stream based on the given criteria and count method will
        // count the number of items in the resulting list. There is no need for sorting the list because you just want to count the number
        // of items that satisfy the given criteria.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given
        class Book {

            private final String title;

            private final String genre;

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

        // and the following code:
        List<Book> books = Arrays.asList(new Book("Gone with the wind", "Fiction"), new Book("Bourne Ultimatum", "Thriller"), new Book("The Client", "Thriller"));

        List<String> genreList01 = new ArrayList<>();

        List<String> genreList02 = new ArrayList<>();

        List<String> genreList03 = new ArrayList<>();

        List<String> genreList04 = new ArrayList<>();

        // Which of the following options will correctly populate genreList with the genres of the books present in books List?

        // INSERT CODE HERE
        // 1º
        books.stream().map(Book::getGenre).forEach(s -> genreList01.add(s));

        // 2º
        genreList02 = books.stream().map(Book::getGenre).collect(Collectors.toList());

        // 3º
        books.stream().map(Book::getGenre).forEach(genreList03::add);

        // 4º
        books.stream().map(b -> b.getGenre()).forEach(genreList04::add);

        System.out.println(genreList01);

        System.out.println(genreList02);

        System.out.println(genreList03);

        System.out.println(genreList04);
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // What will the following code print?

        Path p1 = Paths.get("\\photos\\vacation");
        Path p2 = Paths.get("\\yellowstone");
        System.out.println(p1.resolve(p2) + "  " + p1.relativize(p2));

        // \yellowstone  ..\..\yellowstone
        //
        // 1. Since the argument to resolve starts with \\, the result will be the same as the argument. If the argument doesn't start with a \
        // and it doesn't start with a root such as c:, the output is the result on appending the argument to the path on which the
        // method is invoked.
        //
        // 2. To arrive at \\yellowstone from \\photos\\vacation, you have to first go two directories up and then down to yellowstone.
        // Therefore, p1.relativize(p2) will be ..\..\yellowstone
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // The following line of code has thrown java.nio.file.FileSystemNotFoundException when run.
        // What might be the reason(s)?

        Path p1 = Paths.get(new URI("file://e:/temp/records"));

        // The file system, identified by the URI, does not exist.
        //
        // The provider identified by the URI's scheme component is not installed.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
        // Given:
        class Book {

            private String title;

            private double price;

            public Book(String title, double price) {
                super();
                this.title = title;
                this.price = price;
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

        // What will the following code print?

        List<Book> books = Arrays.asList( //
                new Book("Thinking in Java", 30.0), //
                new Book("Java in 24 hrs", 20.0), //
                new Book("Java Recipies", 10.0));

        double averagePrice = books.stream() //
                .filter(b -> b.getPrice() > 10) //
                .mapToDouble(b -> b.getPrice()) //
                .average() // average return a OptionalDouble
                .getAsDouble();

        System.out.println(averagePrice);

        // 25.0
    }

    // =========================================================================================================================================
    static void test01_09() throws Exception {
        // Complete the following pseudo code for the compute method of a RecursiveAction:

        // Assume that the following methods exist: 
        // processDirectly(DataSet) : processes the DataSet without forking 
        // splitDataSet(DataSet) : splits the DataSet into multiple smaller DataSet objects 
        // isSmallEnough(DataSet) :  checks if the DataSet can be processed directly.

        // processDirectly(currentDataSet); //Line 10
        // splitDataSet(currentDataSet); //Line 13
        // invokeAll(subactions); //Line 15

        // Explanation
        //
        // This is the general logic of how the fork/join frame work is used:
        //
        // 1. First check whether the task is small enough to be performed directly without forking. If so, perform it without forking.
        //
        // 2. If no, then split the task into multiple small tasks (at least 2) and submit the subtasks back to the pool using invokeAll(list of tasks).
    }

    static class RecursiveAction {

        private DataSet currentDataSet;

        public RecursiveAction(DataSet currentDataSet) {
            super();
            this.currentDataSet = currentDataSet;
        }

        void compute() {
            if (isSmallEnough(currentDataSet)) {
                processDirectly(currentDataSet); // LINE 10
            } else {
                List<DataSet> list = splitDataSet(currentDataSet); // LINE 13

                // build MyRecursiveAction objects for each DataSet
                RecursiveAction left = new RecursiveAction(list.get(0));
                RecursiveAction right = new RecursiveAction(list.get(1));

                List<RecursiveAction> subactions = Arrays.asList(left, right);
                invokeAll(subactions);// LINE 15
            }
        }
    }

    static class DataSet {

    }

    static boolean isSmallEnough(DataSet currentDataSet) {
        return false;
    }

    static void processDirectly(DataSet currentDataSet) {

    }

    static List<DataSet> splitDataSet(DataSet currentDataSet) {
        return null;
    }

    static void invokeAll(List<RecursiveAction> actions) {
    }

    // =========================================================================================================================================
    static void test01_10() throws Exception {
        // Given:
        ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();
        Object value = new Object();

        // Which of the given statements are correct about the following code fragment:

        if (!cache.containsKey("key"))
            cache.put("key", value);

        // (Assume that key and value refer to appropriate objects.)

        // To ensure that an entry in cache must never be overwritten, this statement should be replaced with:
        cache.putIfAbsent("key", value);

        // This method internally checks for the presence of the key and then put the key-value if it not already present in the map.
        // Both the operations (i.e. the check and put) are combined in a single method call so that they can be done atomically.
    }

    // =========================================================================================================================================
    static void test01_11() throws Exception {
        // You want to print the date that represents upcoming tuesday from now even if the current day is a tuesday.
        // Which of the following lines of code accomplishe(s) this?

        System.out.println(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)));

        System.out.println(TemporalAdjusters.next(DayOfWeek.TUESDAY).adjustInto(LocalDate.now()));

        // Error, but interesting ...
        // This will return today's date if it is a tuesday, which is not what the question wants.
        System.out.println(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)));
    }

    // =========================================================================================================================================
    static void test01_12() throws Exception {
        // Consider the following MyCache class,

        class MyCache {

            private CopyOnWriteArrayList<String> cal = new CopyOnWriteArrayList<>();

            public void addData(List<String> list) {
                cal.addAll(list);
            }

            public Iterator getIterator() {
                return cal.iterator();
            }
        }
        //
        // Given that one thread calls the addData method on an instance of the above class and another thread calls the getIterator method on
        // the same instance at the same time and starts iterating through its values, which of the following options are correct?
        //
        // Both the threads will complete their operations successfully without getting any exception
        //
        // CopyOnWriteArrayList guarantees that the Iterator acquired from its instance will never get this exception.
        //
        // This is made possible by creating a copy of the underlying array of the data. The Iterator is backed by this duplicate array.
        // An implication of this is that any modifications done to the list are not reflected in the Iterator and no modifications can be
        // done on the list using that Iterator (such as by calling iterator.remove() ).
        //
        // Calls that try to modify the iterator will get UnsupportedOperationException.
    }

    // =========================================================================================================================================
    static void test01_13() throws Exception {
        // Consider the directory structure shown in the attached image.
        // (context root is a directory, which contains two files Home.htm, index.html and a directory web-inf, which in turn contains a file web.xml)
        // How will you create a PathMatcher that will match web.xml, Home.htm, and index.html?

        // context root (web-inf(web.xml), Home.htm, index.html)

        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**.{htm*,xml}");

        // **.{htm*, xml} recursive extension .{htm* ou xml}
        //

        pm.matches(Paths.get(""));
    }

    // =========================================================================================================================================
    static void test01_14() throws Exception {
        // How will you initialize a SimpleDateFormat object so that the following code will print the number of the month
        // (i.e. 02 for Feb, 12 for Dec, and so on) and a two digit calendar year of any given date?

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy", Locale.US);
        // Upper case M is for Month and lower case y is for Calendar Year.

        System.out.println(sdf.format(new Date()));

        // Explanation
        // For the purpose of the exam, you need to know the basic codes for printing out a date. The important ones are m, M, d, D, y, s, S, h, H, and z.
        // The following table shows all the codes (Please check http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html for details)
    }

    // =========================================================================================================================================
    private static Path p1 = Paths.get("c:\\main\\project\\Starter.java");

    static void test01_15() throws Exception {
        // What will the following code print when run?

        System.out.println(getData());

        // main

        // Explanation 
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

    private static String getData() {
        String data = p1.getName(0).toString();
        return data;
    }

    // =========================================================================================================================================
    static void test01_16() throws Exception {
        // Replace XXX with a declaration such that the following code will compile without any error or warning.

        class Test {
            // public void m1(XXX list) {
            public void m1(List<? extends Number> list) {
                Number n = list.get(0);
            }
        }

        // Read it aloud like this: A List containing instances of Number or its subclass(es). This will allow you to retrieve Number objects 
        // because the compiler knows that this list contains objects that can be assigned to a variable of class Number. 
        //
        // However, you cannot add any object to the list because the compiler doesn't know the exact class of objects contained by the list 
        // so it cannot check whether whatever you are adding is eligible to be added to the list or not.	
    }

    // =========================================================================================================================================
    static void test01_17() throws Exception {
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
    static void test01_18() throws Exception {
        // Consider the following code:

        class MyCache {

            private CopyOnWriteArrayList<String> cal = new CopyOnWriteArrayList<>();

            public void addData(List<String> list) {
                cal.addAll(list);
            }

            public int getCacheSize() {
                return cal.size();
            }
        }

        //
        // Given that one thread calls the addData method on an instance of the above class with a List containing 10 Strings
        // and another thread calls the getCacheSize method on the same instance at the same time, which of the following options are correct?
        //
        // (Assume that no other calls have been made on the MyCache instance.)
        //
        // The getCacheSize call may return either 0 or 10.
        //
        // All modification operations of a CopyOnWriteArrayList are considered atomic.
        // So the thread that calls size() will either see no data in the list or will see all the elements added to the list.
    }

    // =========================================================================================================================================
    static void test01_19() throws Exception {
        // What will the following code print when compiled and run?

        String val1 = "hello";
        StringBuilder val2 = new StringBuilder("world");
        UnaryOperator<String> uo1 = s1 -> s1.concat(val1); // 1
        UnaryOperator<String> uo2 = s -> s.toUpperCase(); // 2

        // System.out.println(uo1.apply(uo2.apply(val2))); // 3

        // Compilation error at //3

        // Remember that StringBuilder and StringBuffer do not extend String. The UnaryOperaty uo2 has been typed to String and therefore,
        // you cannot apply it to a StringBuilder object.
    }

    // =========================================================================================================================================
    static void test01_20() throws Exception {
        // Which line will cause compilation failure?

        // There is no problem with the code.

        // Explanation
        // The given code illustrates how to define a generic class. It has two generic types K and V.
        // The only interesting piece of code here is this method:

        /**
         * <pre>
         *  public static <X> PlaceHolder<X, X> getDuplicateHolder(X x) {    
         *      return new PlaceHolder<X, X>(x, x); 
         *  }
         * </pre>
         */

        // You could write the same method in the following way also:
        /**
         * <pre>
         * public static <X> PlaceHolder<X, X> getDuplicateHolder(X x){     
         *     return new PlaceHolder<>(x, x); // use diamond operator because we already know that X is a generic type. 
         * }
         * </pre>
         */
    }

    static class PlaceHolder<K, V> { // 1
        // Given:

        private K k;

        private V v;

        public PlaceHolder(K k, V v) { // 2
            this.k = k;
            this.v = v;
        }

        public K getK() {
            return k;
        }

        public static <X> PlaceHolder<X, X> getDuplicateHolder(X x) { // 3
            return new PlaceHolder<X, X>(x, x); // 4
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
