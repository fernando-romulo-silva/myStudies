package br.fernando.ch06_generics_and_collections_Objective.part05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test04 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
        // Which of the following are valid?

        List<String> list01 = new ArrayList<>();

        List<String> list02 = new ArrayList<>(10);

        // While creating an ArrayList you can pass in an integer argument that specifies the initial size of the ArrayList. 
        // This doesn't actually insert any element into the list. So list.getSize() would still return 0.	
    }

    // =========================================================================================================================================
    static void test01_02() throws Exception {
        // Which of the following code fragments is/are appropriate usage(s) of generics?

        List<String> list01 = new ArrayList<>();
        list01.add("A");
        list01.addAll(new ArrayList<>());

        // and

        List<String> list02 = new ArrayList<>();
        list02.add("A");
        List<? extends String> list2 = new ArrayList<>();
        list02.addAll(list2);

        // list2.add("C"); // you can't do that
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
        // Given:

        Map<String, List<? extends CharSequence>> stateCitiesMap01 = new HashMap<String, List<? extends CharSequence>>();

        // Which of the following options correctly achieves the same declaration using type inferencing?

        Map<String, List<? extends CharSequence>> stateCitiesMap02 = new HashMap<>();
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
