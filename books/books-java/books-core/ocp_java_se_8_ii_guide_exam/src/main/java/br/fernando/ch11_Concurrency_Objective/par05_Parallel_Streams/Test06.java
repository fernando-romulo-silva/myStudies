package br.fernando.ch11_Concurrency_Objective.par05_Parallel_Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Test06 {

    // =========================================================================================================================================
    // Reducing Parallel Streams with reduce()
    static void test01_01() throws Exception {
        // You can build your own custom reduction with the reduce() function, as you saw in Chapter 9.
        //
        // First, remember that a reduction produces an Optional value unless we provide an identity.
        //
        // Is our product function associative? It is. That is, we can multiply a * b and then by c and get the same answer 
        // as when we multiply b * c and then by a
        //
        // Is our accumulator stateless? That is, does it rely on any additional state in the stream to be computed? 
        // If it’s not stateless, then we could run into trouble; either we’ll potentially get incorrect results or 
        // we’ll drastically reduce the performance of the parallel stream (or both!).

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int mult = nums.stream() //
                .unordered() // unordered for efficiency
                .parallel() // make the stream parallel
                .reduce(1, (i1, i2) -> i1 * i2); // reduce to the product

        System.out.println("Product reduction: " + mult);

        // Running the code again several times produces the same result each time. It looks like we got the code correct.
    }

    // =========================================================================================================================================
    // Collecting Values from a Parallel Stream
    static void test01_02() throws Exception {
        final Dog aiko = new Dog("aiko", 10, 10);
        final Dog boi = new Dog("boi", 6, 6);
        final Dog charis = new Dog("charis", 7, 7);
        final Dog clover = new Dog("clover", 12, 12);
        final Dog zooey = new Dog("zooey", 8, 8);

        final List<Dog> dogs = new ArrayList<>();
        final List<Dog> dogsOlderThan7 = new ArrayList<>();

        dogs.add(aiko);
        dogs.add(boi);
        dogs.add(charis);
        dogs.add(clover);
        dogs.add(zooey);

        long count01 = dogs.stream() // stream the dogs
                .unordered() // make the stream unordered
                .parallel() // make the stream parallel
                .filter(d -> d.getAge() > 7) // filter the dogs
                .peek(d -> dogsOlderThan7.add(d)) // save ... with a side effect
                .count(); //

        System.out.println("Dogs older than 7, via side effect: " + dogsOlderThan7);

        // Clearly, as you should know well by now, this is not the way to collect results from a stream pipeline. 
        // The reason this fails is because we have multiple threads trying to access the List dogsOlderThan7 at the same time 
        // and dogsOlderThan7 is not thread-safe.

        // We could fix this code by using a synchronized list, like this:
        final List<Dog> dogsOlderThan7New = Collections.synchronizedList(new ArrayList<>());
        //  However, you’re also sacrificing some performance because you’re forcing a synchronization of all thethreads attempting 
        // to write to the synchronized List
        //
        // A better way to collect values from a parallel stream pipeline is to use Collectors, as we did in Chapter 9 with sequential streams.
        //
        List<Dog> dogsOlderThan7Newest = dogs.stream() // stream of dogs
                .unordered() // make the stream unordered
                .parallel() // make the stream parallel
                .filter(d -> d.getAge() > 7) //
                .collect(Collectors.toList()); // collect older dogs into a list

        // 
        // It turns out that the way collect() works under the covers is that each worker processing a piece of the stream collects its data into its own collection.
        // Worker 1 will create a List of dogs older than 7; worker 2 will create a List of dogs older than 7; and so on. 
        //
        // Each of these List s is separate, built from the pieces of the stream that each worker got when the stream was split up across the parallel workers.
        // At the end, once each thread is complete, the separate List s are merged together, in a thread-safe manner, to create one final List of dogs.        
    }

    static class Dog {

        private final String name;

        private final int age;

        private final int weight;

        public Dog(String name, int age, int weight) {
            super();
            this.name = name;
            this.age = age;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return name + " is " + age + " years old";
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}
