package br.com.fernando.ch06_collectiing_date_with_streams.part05_The_Collector_interface;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import br.com.fernando.Dish;

// The Collector interface
public class Test {

    public interface CollectorFake<T, A, R> {

        Supplier<A> supplier();

        BiConsumer<A, T> accumulator();

        Function<A, R> finisher();

        BinaryOperator<A> combiner();

        Set<Characteristics> characteristics();
    }

    public static void test0() {
        // The Collector interface consists of a set of methods that provide a blueprint for how to
        // implement specific reduction operations (that is, collectors). You’ve seen many collectors that
        // implement the Collector interface, such as toList or groupingBy
        //
        // In this listing, the following definitions apply:
        //
        //  T is the generic type of the items in the stream to be collected.
        //
        //  A is the type of the accumulator, the object on which the partial result will be accumulated during the
        // collection process.
        //
        //  R is the type of the object (typically, but not always, the collection) resulting from the collect
        // operation.

        // For instance, you could implement a ToListCollector<T> class that gathers all the elements of a
        // Stream<T> into a List<T> having the following signature
    }

    // Making sense of the methods declared by Collector interface
    public static void test1() {

        // We can now analyze one by one the five methods declared by the Collector interface. When we
        // do so, you’ll notice that each of the first four methods returns a function that will be invoked by
        // the collect method, whereas the fifth one, characteristics, provides a set of characteristics that’s
        // a list of hints used by the collect method itself to know which optimizations
        //
        // ## Making a new result container: the supplier method ##
        //
        // The supplier method has to return a Supplier of an empty result—a parameterless function that
        // when invoked creates an instance of an empty accumulator used during the collection process.
        // Clearly, for a collector returning the accumulator itself as result, like our ToListCollector, this
        // empty accumulator will also represent the result of the collection process when performed on an
        // empty stream. In our ToListCollector the supplier will then return an empty List as follows:
        //
        // Supplier<List<T>> supplier()
        // return () -> new ArrayList<T>();
        //
        // ## Adding an element to a result container: the accumulator method ##
        //
        // The accumulator method returns the function that performs the reduction operation. When
        // traversing the nth element in the stream, this function is applied with two arguments, the
        // accumulator being the result of the reduction (after having collected the first n–1 items of the
        // stream) and the nth element itself. The function returns void because the accumulator is
        // modified in place, meaning that its internal state is changed by the function application to
        // reflect the effect of the traversed element.
        //
        // BiConsumer<List<T>, T> accumulator() {
        // return (list, item) -> list.add(item);
        //
        // ## Applying the final transformation to the result container: the finisher method ##
        //
        // The finisher method has to return a function that’s invoked at the end of the accumulation
        // process, after having completely traversed the stream, in order to transform the accumulator
        // object into the final result of the whole collection operation. Often, as in the case of the
        // ToListCollector, the accumulator object already coincides with the final expected result. As a
        // consequence, there’s no need to perform a transformation, so the finisher method just has to
        // return the identity function:
        //
        // public Function<List<T>, List<T>> finisher() {
        // return Function.identity();
        //
        // ## Merging two result containers: the combiner method ##
        //
        // The combiner method, the last of the four methods that return a function used by the reduction
        // operation, defines how the accumulators resulting from the reduction of different subparts of
        // the stream are combined when the subparts are processed in parallel.
        //
        // public BinaryOperator<List<T>> combiner() {
        // return (list1, list2) -> {
        // list1.addAll(list2);
        // return list1;
        // }
        //
        // ## Characteristics method ##
        //
        // The last method, characteristics, returns an immutable set of Characteristics, defining the
        // behavior of the collector—in particular providing hints about whether the stream can be reduced
        // in parallel and which optimizations are valid when doing so.
    }

    // Putting them all together
    public static void test02() {
        // The five methods analyzed in the preceding subsection are everything you need to develop your
        // own ToListCollector, so you can implement it by putting all of them together, as the next listing
        // shows.

        final List<Dish> dishes1 = Dish.menu.stream() //
            .collect(new ToListCollector<Dish>());

        dishes1.forEach(x -> System.out.println(x));
        
        
        // In the case of an IDENTITY_FINISH collection operation, there’s a further possibility of
        // obtaining the same result without developing a completely new implementation of the Collector
        // interface. Stream has an overloaded collect method accepting the three other
        // functions—supplier, accumulator, and combiner—having exactly the same semantics as the ones
        // returned by the corresponding methods of the Collector interface. So, for instance, it’s possible
        // to collect in a List all the items in a stream of dishes as follows:

        final List<Dish> dishes2 = Dish.menu.stream() //
            .collect( //
                      ArrayList::new, // supplier
                      List::add, // Accumulator
                      List::addAll //
        );

        dishes2.forEach(x -> System.out.println(x));

    }

    public static class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

        @Override
        public Supplier<List<T>> supplier() {
            return () -> new ArrayList<T>(); // Creates the collection operation starting point
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return (list, item) -> list.add(item); // Accumulates the traversed item, modifying the accumulator in place
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return i -> i; // Identiy function
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2); // Midfies the first accumulator, combining it with the content of the second one.
                return list1; // Returns the modified first accumulator
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT)); // Flags the collector as IDENTIFY_FINISH and CONCURRENT
        }
    }

    public static void main(String[] args) {

    }
}
