package br.fernando.ch06_generics_and_collections_Objective.part05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test06 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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
    static void test01_02() throws Exception {
        // Which of the following are valid?

        List<String> list01 = new ArrayList<>();

        List<String> list02 = new ArrayList<>(10);
        // While creating an ArrayList you can pass in an integer argument that specifies the initial size of the ArrayList.
        // This doesn't actually insert any element into the list. So list.getSize() would still return 0.
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

    static void test01_03() throws Exception {
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

        // You can't create a instance of '?', instance okay?
        // List<?> list = new ArrayList<?>();

        // list01.addAll(new ArrayList<String>()); // you can't addAll too
    }

    // =========================================================================================================================================
    static void test01_05() throws Exception {
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
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
