package br.fernando.ch06_generics_and_collections_Objective.part05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test08 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Given

        List<String> cities = new ArrayList<>();
        cities.add("New York");
        cities.add("Albany");

        // Complete the following code by inserting declaration for stateCitiesMap:

        // Answer
        // This is how you would do it without using the diamond operator.
        Map<String, List<String>> stateCitiesMap01 = new HashMap<String, List<String>>();

        // This is the right way to use the diamond operator.
        Map<String, List<String>> stateCitiesMap02 = new HashMap<>();

        stateCitiesMap01.put("NY", cities);

        stateCitiesMap02.put("NY", cities);
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

        // list2.addAll(Arrays.asList(p)); // obsever that, it's invalid too
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {
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
    static void test01_04() throws Exception {
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
    static void test01_05() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}