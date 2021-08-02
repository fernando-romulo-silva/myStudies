package br.fernando.ch06_generics_and_collections_Objective.part05_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Test05 {

    // =========================================================================================================================================
    static void test01_01() throws Exception {
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

    static void test01_02() throws Exception {
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
    static void test01_03() throws Exception {
        // Which of the following code fragments is/are appropriate usage(s) of generics?

        List<String> list01 = new ArrayList<>();
        list01.add("A");
        list01.addAll(new ArrayList<>());

        // and

        List<String> list02 = new ArrayList<>();
        list02.add("A");
        List<? extends String> list03 = new ArrayList<>();
        list02.addAll(list03);

        // list03.add("C"); // you can't do that

        list03.addAll(new ArrayList<>()); // but ...
    }

    // =========================================================================================================================================
    static class Shape {

    }

    static void test01_04() throws Exception {

        // Assume that Shape is a valid non-final class.
        // Identify valid methods:

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
    static void test01_05() throws Exception {
        // Given:
        String[] p = { "1", "2", "3" };
        // Which of the following lines of code is/are valid?

        List<?> list2 = new ArrayList<>(Arrays.asList(p));

        // Here, list2 is a list of any thing. You cannot add any thing to it and you can only retrieve Objects from it:
        // list2.add(new Object());
        // list2.add("aaa");
        // both will not compile.

        list2.addAll(new ArrayList<>()); // but ...

        Object obj = list2.get(0); // Valid
        // String str = list2.get(0); //will not compile.

        // Note that you can add null to it though i.e.
        list2.add(null); // is valid
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
