package org.nandao.ch03ProgrammingWithLambdas.part09LambdasAndGenerics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// One of the unhappy consequences of type erasure is that you cannot construct
// a generic array at runtime. For example, the toArray() method of Collection<T> and
// Stream<T> cannot call T[] result = new T[n]. Therefore, these methods return Object[]
// arrays. In the past, the solution was to provide a second method that accepts
// an array. That array was either filled or used to create a new one via reflection.
// For example, Collection<T> has a method toArray(T[] a). With lambdas, you have a
// new option, namely to pass the constructor.
public class Test {

    public static void main(String[] args) {

        final String contents = "This a example test to use stream API";
        final List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        final Stream<String> wordsStream = words.stream();
        final String[] result = wordsStream.toArray(String[]::new);
        
        System.out.println(result);
        
        
        // In Java, when you declare a generic functional interface, you can’t specify that
        // function arguments are always contravariant and return types always covariant.
        // Instead, you have to repeat it for each use. For example, look at the javadoc for
        // Stream<T>:
        //
        // void forEach(Consumer<? super T> action)
        // Stream<T> filter(Predicate<? super T> predicate)
        // <R> Stream<R> map(Function<? super T, ? extends R> mapper)
        //
        // The general rule is that you use super for argument types, extends for return
        // types. That way, you can pass a Consumer<Object> to forEach on a Stream<String>. If it
        // is willing to consume any object, surely it can consume strings.
        // But the wildcards are not always there. Look at:
        //
        // T reduce(T identity, BinaryOperator<T> accumulator)
    }

}
