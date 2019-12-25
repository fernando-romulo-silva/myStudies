package br.com.fernando.ch06_collectiing_date_with_streams.part06_Developing_your_own_collector_for_better_performance;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.partitioningBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Developing your own collector for better performance
public class Test {

    // Simple Collector
    public static Map<Boolean, List<Integer>> partitionPrimesWithInlineCollector(int n) {
        return Stream.iterate(2, i -> i + 1).limit(n) //
            .collect( //
                      () -> new HashMap<Boolean, List<Integer>>() {

                          private static final long serialVersionUID = 1L;

                          { // Supplier
                              put(true, new ArrayList<Integer>()); //
                              put(false, new ArrayList<Integer>()); //
                          }
                      }, //
                      (acc, candidate) -> { // Accumulator
                          acc.get(isPrime(acc.get(true), candidate)) //
                              .add(candidate); //
                      }, (map1, map2) -> { // Combiner
                          map1.get(true).addAll(map2.get(true)); //
                          map1.get(false).addAll(map2.get(false)); //
                      });//
    }

    // Using the partitioningBy
    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    public static boolean isPrime(int candidate) {
        return IntStream.rangeClosed(2, candidate - 1) //
            .limit((long) Math.floor(Math.sqrt(candidate)) - 1) //
            .noneMatch(i -> candidate % i == 0);
    }

    //
    //
    //
    // Divide only by prime numbers
    public static boolean isPrime(List<Integer> primes, int candidate) {
        return primes.stream() //
            .noneMatch(i -> candidate % i == 0);
    }

    // You can now use this new custom collector in place of the former one created with the
    // partitioningBy factory method in section 6.4 and obtain exactly the same result:
    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed() //
            .collect(new PrimeNumbersCollector());
    }

    // Therefore, you’ll create a method called takeWhile, which, given a sorted list and a predicate,
    // returns the longest prefix of this list whose elements satisfy the predicate:
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {

        int i = 0;
        for (final A item : list) { // Check if the current item in the list satisfies the Predicate
            if (!p.test(item)) {
                return list.subList(0, i); // If it doesn't, return the sublist prefix until the item before the tested one
            }

            i++;
        }

        return list;
    }

    public static boolean isPrimeBetter(List<Integer> primes, int candidate) {
        final int candidateRoot = (int) Math.sqrt(candidate);

        return takeWhile(primes, i -> i <= candidateRoot).stream() //
            .noneMatch(p -> candidate % p == 0);
    }

    public static class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

        // Here you’re not only creating the Map that you’ll use as the accumulator, but you’re also
        // initializing it with two empty lists under the true and false keys. This is where you’ll add
        // respectively the prime and nonprime numbers during the collection process.
        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() { // Start the collction process with a Map containing two emprty Lists.

                // serial version
                private static final long serialVersionUID = 1L;

                // block
                {
                    put(true, new ArrayList<Integer>());
                    put(false, new ArrayList<Integer>());
                }
            };
        }

        // The most important method of your collector is the accumulator method, because it contains the logic
        // defining how the elements of the stream have to be collected. In this case, it’s also the key to
        // implementing the optimization we described previously.
        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {

                // Pass to the isPrime method the list of already found primes.
                acc.get(isPrimeBetter(acc.get(true), candidate)) // Get the list of prime or nonprime numbers depending on the result of isPrime
                    .add(candidate); // Add the candidate to the appropriate list; Get from the map the list of prime or nonprime numbers,
                // according to what the isPrime method returned, and add to it the current candidate.
            };
        }

        // Making the collector work in parallel (if possible)
        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {

            // Note that in reality this collector can’t be used in parallel, because the algorithm is inherently
            // sequential. This means the combiner method won’t ever be invoked, and you could leave its
            // implementation empty (or better, throw an UnsupportedOperation-Exception). We decided to
            // implement it anyway only for completeness.

            return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false)); // Merge the second Mpa into the first one.
                return map1;
            };
        }

        // the accumulator coincides with the collector’s result so it won’t need any further transformation,
        // and the finisher method returns the identity function:
        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity(); // No transformation is necessary at the end of the collection process,
            // so terminate it with the 'identy' function
        }

        // As for the characteristic method, we already said that it’s neither CONCURRENT nor
        // UNORDERED but is IDENTITY_FINISH:
        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH)); // This collector is IDENTITY_FISISH but neither UNORDERED nor CONCURRENT
            // because it relies on the fact that prime numbers are discoverd in sequence.
        }
    }

    // Comparing collectors’ performances
    public static void test02() {
        System.out.println("Partitioning partitionPrimesWithInlineCollector done in: " + execute(Test::partitionPrimesWithInlineCollector) + " msecs"); // 28691
        System.out.println("Partitioning partitionPrimes done in: " + execute(Test::partitionPrimes) + " msecs"); // 758
        System.out.println("Partitioning partitionPrimesWithCustomCollector done in: " + execute(Test::partitionPrimesWithCustomCollector) + " msecs"); // 487

        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithInlineCollector(100));
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithCustomCollector(100));
    }

    private static long execute(Consumer<Integer> primePartitioner) {

        long fastest = Long.MAX_VALUE;

        for (int i = 0; i < 10; i++) {
            final long start = System.nanoTime();

            primePartitioner.accept(1_000_000);

            final long duration = (System.nanoTime() - start) / 1_000_000;

            if (duration < fastest) {
                fastest = duration;
            }

            System.out.println("done in " + duration);
        }

        return fastest;
    }

    public static void main(String[] args) {
        test02();
    }
}
