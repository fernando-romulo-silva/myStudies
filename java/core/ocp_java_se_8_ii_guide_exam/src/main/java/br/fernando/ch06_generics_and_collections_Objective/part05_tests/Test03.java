package br.fernando.ch06_generics_and_collections_Objective.part05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test03 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {

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
    static void test01_02() throws Exception {
        // Given:
        String[] p = { "1", "2", "3" };
        // Which of the following lines of code is/are valid?

        List<?> list2 = new ArrayList<>(Arrays.asList(p));

        // Here, list2 is a list of any thing. You cannot add any thing to it and you can only retrieve Objects from it:
        // list2.add(new Object());
        // list2.add("aaa");
        // both will not compile.

        Object obj = list2.get(0); // Valid
        // String str = list2.get(0); //will not compile.

        // Note that you can add null to it though i.e.
        list2.add(null); // is valid

    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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

    static void test01_04() throws Exception {
        // Which line will cause compilation failure?

        // There is no problem with the code.

        // Explanation
        // The given code illustrates how to define a generic class. It has two generic types K and V.
        // The only interesting piece of code here is this method:

        /**
         * <pre>
         *  public static <X> PlaceHolder<X, X> getDuplicateHolder(X x) {    
         *     return new PlaceHolder<X, X>(x, x); 
         *  }
         * </pre>
         */

        // You could write the same method in the following way also:
        /**
         * <pre>
         * public static <X> PlaceHolder<X, X> getDuplicateHolder(X x){     
         *    return new PlaceHolder<>(x, x); // use diamond operator because we already know that X is a generic type. 
         * }
         * </pre>
         */
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
