package org.nandao.ch02TheStreamAPI.part13ParallelStreams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Streams make it easy to parallelize bulk operations. The process is mostly automatic,
// but you need to follow a few rules. First of all, you must have a parallel
// stream. By default, stream operations create sequential streams, except for
// Collection.parallelStream()
public class Test {

    public static class City {
        private final Long id;

        private final String name;

        private final Integer population;

        private final String state;

        public City(final Long id, final String name, final Integer population, final String state) {
            super();
            this.id = id;
            this.name = name;
            this.population = population;
            this.state = state;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getPopulation() {
            return population;
        }

        public String getState() {
            return state;
        }
    }

    public static void main(String[] args) {
        final String[] wordArray = new String[]{ "Now", "you", "can", "see", "the", "transformation", "and", "my", "SUPERTRANSFORMATION" };

        // When stream operations run in parallel, the intent is that the same result is returned
        // as if they had run serially. It is important that the operations are stateless
        // and can be executed in an arbitrary order.

        final Stream<String> parallelWords = Stream.of(wordArray).parallel();

        // This is very, very bad code. The function passed to forEach runs concurrently in
        // multiple threads, updating a shared array. That’s a classic race condition. If you
        // run this program multiple times, you are quite likely to get a different sequence
        // of counts in each run, each of them wrong

        final int[] shortWords = new int[12];
        parallelWords.parallel().forEach( //
                                          s -> {
                                              if (s.length() < 12)
                                                  shortWords[s.length()]++;
                                          });
        // Error—race condition!
        System.out.println(Arrays.toString(shortWords));

        final List<City> cities = Arrays.asList(new City(1L, "Rio Janeiro", 100, "RJ"), new City(2L, "Sao Paulo", 500, "SP"), new City(3L, "Santos", 20, "SP"));
        final Stream<City> citiesStream1 = cities.stream();
        
        // Clearly, to benefit from parallelism, the order of the map
        // values will not be the same as the stream order. Even on an ordered stream, that
        // collector has a “characteristic” of being unordered, so that it can be used effi-
        // ciently without having to make the stream unordered.
        final Map<String, List<City>> result = citiesStream1.parallel().collect(Collectors.groupingByConcurrent(City::getState));
        // Values aren’t collected in stream order
        
        result.forEach((k,v) -> System.out.println(k + " " + v));
        
        // It is very important that you don’t modify the collection that is
        // backing a stream while carrying out a stream operation (even if the modification
        // is threadsafe). Remember that streams don’t collect their own data—the
        // data is always in a separate collection. If you were to modify that collection,
        // the outcome of the stream operations would be undefined.       

    }
}
