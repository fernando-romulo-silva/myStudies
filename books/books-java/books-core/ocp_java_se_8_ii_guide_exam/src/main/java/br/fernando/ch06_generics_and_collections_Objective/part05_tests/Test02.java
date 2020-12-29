package br.fernando.ch06_generics_and_collections_Objective.part05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test02 {

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
        // It is required that //1 must compile and //2 must NOT compile.
        // Which of the following declarations of class Transaction will satisfy the requirement?

        class Transaction<T, S extends T> {

            public Transaction(T t, S s) {
            }
        }

        // Given the following code:

        Transaction<Number, Integer> t1 = new Transaction<>(1, 2); // 1

        // Transaction<Number, String> t2 = new Transaction<>(1, "2"); // 2

        // Putting the upper bound for the second type as S extends T, you are forcing that the second type must be a subclass of the first type.
        // Thus, if the first type is Number, the second cannot be a String because String doesn't extend Number.
        // This will make the //1 compile but not //2.
    }

    // =========================================================================================================================================
    static void test01_03() throws Exception {

        // Assume that Shape is a valid non-final class. 
        // Identify valid methods:
    }

    static class Shape {

    }

    // Here, list is a List of Shapes and strList is a List of some class that extends from Shape.
    // Any class that extends from Shape IS-A Shape, therefore, you can add elements in strList to list.
    public static List<? extends Shape> m4(List<? extends Shape> strList) {
        List<Shape> list = new ArrayList<>();
        list.add(new Shape());
        list.addAll(strList);
        return list;
    }

    // Same as above
    public void m5(ArrayList<? extends Shape> strList) {
        List<Shape> list = new ArrayList<>();
        list.add(new Shape());
        list.addAll(strList);
    }

    // Errors
    //
    // strList and list both are Lists of some class that extends from Shape. However, the compiler does not know which class(es).
    // Therefore, you cannot add contents of strList to list or vice-versa
    /**
     * <pre>
     * 
     * public List<Shape> m3(ArrayList<? extends Shape> strList) {
     *     List<? extends Shape> list = new ArrayList<>();
     *     list.addAll(strList); // The method addAll is not applicable for the arguments (ArrayList<? extends Shape>)
     *     return list; // Type mismatch: cannot convert from List<? extends Shape> to List<Shape>
     * }
     * 
     * </pre>
     */

    // list is a List of some class that extends from Shape. It may not necessarily be Shape. 
    // Therefore, you cannot add a Shape to list. But you can add all the elements of list to strList.
    /**
     * <pre>
     * 
     * public void m6(ArrayList<Shape> strList) {
     *     List<? extends Shape> list = new ArrayList<>();
     *     list.add(new Shape()); // The method add(? extends Shape) in the type List<? extends Shape> is not applicable for the arguments (Shape)
     *     strList.addAll(list);
     * }
     * 
     * </pre>
     */

    // =========================================================================================================================================
    static void test01_04() throws Exception {
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

        // You can't create a instance of '?'
        // List<?> list = new ArrayList<?>();
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
        // Which of the following code fragments is/are appropriate usage(s) of generics?
        //
        List<String> listA = new ArrayList<>();
        listA.add("A");
        listA.addAll(new ArrayList<>());
        //
        //
        List<String> listB = new ArrayList<>();
        listB.add("A");

        List<? extends String> list2 = new ArrayList<>();
        listB.addAll(list2);

        // 
        // list2.add("C"); // you can't do that

        List<? extends String> list3 = new ArrayList<>();

        // list2.addAll(list3); // don't work too

        list3.addAll(new ArrayList<>()); // just it work

        list3.add(null);// it too
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_05();
    }
}
