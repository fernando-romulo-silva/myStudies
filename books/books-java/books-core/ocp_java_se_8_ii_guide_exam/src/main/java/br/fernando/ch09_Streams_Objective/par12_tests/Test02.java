package br.fernando.ch09_Streams_Objective.par12_tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        // Given
        String sentence = "Life is a box of chocolates, Forrest. You never know what you're gonna get."; // 1

        System.out.println(sentence.split("[ ,.]"));

        Optional<String> theword = Stream.of(sentence.split("[ ,.]")) //
                .findAny(); // this line be here only for compile the code ;)
        // .anyMatch(w->w.startsWith("g")); //2 don't compile because anyMatch return true or false.

        System.out.println(theword.get()); // 3

        // anyMatch returns a boolean and not an Optional. Therefore, //2 will not compile.
        // The expression Stream.of(sentence.split("[ ,.]")).anyMatch(w->w.startsWith("g")); will actually just return true.

    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
        // What will the following code print?
        AtomicInteger ai = new AtomicInteger();
        Stream<Integer> stream = Stream.of(11, 11, 22, 33).parallel();
        stream.filter(e -> {
            ai.incrementAndGet();
            return e % 2 == 0;
        });
        System.out.println(ai); // 0

        // Notice that filter is an intermediate operation. It does not do anything until a terminal operation is invoked on the stream.
        // Therefore, in this case, ai will remain 0.
    }

    // =========================================================================================================================================
    static void test01_04() throws Exception {
        // What will the following code print?

        List<String> ls = Arrays.asList("Tom Cruise", "Tom Hart", "Tom Hanks", "Tom Brady");

        Predicate<String> p = str -> {
            System.out.println("Looking...");
            return str.indexOf("Tom") > -1;
        };

        boolean flag = ls.stream().filter(str -> str.length() > 8).allMatch(p);

        // Looking...
        // Looking...
        // Looking...

        // Explanation
        // Remember that filter is an intermediate operation, which means it will not execute until a terminal operation is invoked on the stream.
        // allMatch is a short circuiting terminal operation. Thus, when allMatch is invoked, the filter method will be invoked and it will keep
        // only those elements in the stream that satisfy the condition given in the filter i.e. the string must be longer than 8 characters.  
        // After this method is done, only three elements will be left in the stream. When allMatch is invoked, the code in predicate will
        // be executed for each element until it finds a mismatch. Thus, Looking... will be printed three times.
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Given Peson class
        // What will the following code print?
        List<Person> friends = Arrays.asList(new Person("Bob", 31), new Person("Paul", 32), new Person("John", 33));

        double averageAge = friends.stream() //
                .filter(f -> f.getAge() < 30) //
                .mapToInt(f -> f.getAge()) //
                .average() //
                .getAsDouble(); // error here

        System.out.println(averageAge);

        // It will throw an exception at runtime.
        //
        // Explanation
        // The given code chains three operations to a stream. First, it filters out all the element that do not satisfy the condition f.getAge()<30,
        // which means there will no element in the stream, second, it maps each Person element to an int using the mapping function f.getAge().
        // Since there is no element in the stream anyway, the stream remains empty.
        // Finally, the average() method computes the average of all the elements, which is 0.0.
        //
        // However, there is a problem with this code. The average() method actually returns an OptionalDouble (and not Double).
        // Since the stream contains no element and the average() method returns an OptionalDouble containing OptionalDouble.empty.
        //
        // Therefore, getAsDouble method throws a java.util.NoSuchElementException.  
        //
        // To avoid this problem, instead of getAsDouble, you should use orElse(0.0). That way, if the OptionalDouble is empty, it will return 0.0.
    }

    static class Person {

        private String name;

        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        } // getters/setters not shown

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    // =========================================================================================================================================
    static void test01_06() throws Exception {
        // Given
        Stream<String> names = Stream.of("Sarah Adams", "Suzy Pinnell", "Paul Basgall");

        // Which of the following options will correctly assign a stream of just first names to firstNames?

        Stream<String> firstNames = names.map(e -> e.split(" ")[0]);// INSERT CODE HERE
    }

    // =========================================================================================================================================
    static void test01_07() throws Exception {
        // What will the following code print when compiled and run?
        Stream<List<String>> s1 = Stream.of(Arrays.asList("a", "b"), Arrays.asList("a", "c"));

        Stream<String> news = s1.filter(s -> s.contains("c")).flatMap(olds -> olds.stream());

        news.forEach(System.out::print); // ac

        // Explanation
        // The given code first creates a Stream, where each element is a List<String>. Next, it filters the stream using a criteria s.contains("c");,
        // which means only the elements that satisfy the criteria will remain in the resulting stream. Hence, the list containing a, b will be removed.
        //
        // Next, it uses the flatMap method to generate elements of the new stream. The flatMap method returns a stream consisting of the results of
        // replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.
        //
        // In this case, our original stream has only one element, which is a List of Strings. The lambda expression passed to this method converts this
        // List object into a stream using List's stream() method.
        //
        // Finally, it uses the forEach method of Stream to print each element. Therefore, the code will print ac.
    }

    // =========================================================================================================================================
    static void test01_08() throws Exception {
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

    static void test01_09() throws Exception {
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
    static void test01_10() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_02();
    }
}
