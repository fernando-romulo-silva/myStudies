package org.nandao.ch02TheStreamAPI.part11GroupingAndPartitioning;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Forming groups of values with the same characteristic is very common, and the groupingBy
// method supports it directly.
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

    public static void main(final String[] args) {
        final Stream<Locale> locales1 = Stream.of(Locale.getAvailableLocales());

        final Map<String, List<Locale>> countryToLocales = locales1.collect( //
                Collectors.groupingBy(Locale::getCountry) //
        );
        
        countryToLocales.forEach((k,v) -> System.out.println(k + " " + v));

        // When the classifier function is a predicate function (that is, a function returning
        // a boolean value), the stream elements are partitioned into two lists: those where
        // the function returns true and the complement. In this case, it is more efficient to
        // use partitioningBy instead of groupingBy. For example, here we split all locales
        // into those that use English, and all others:

        final Stream<Locale> locales2 = Stream.of(Locale.getAvailableLocales());

        final Map<Boolean, List<Locale>> englishAndOtherLocales = locales2.collect( //
                Collectors.partitioningBy(l -> l.getLanguage().equals("en")) // 
        );

        final List<Locale> englishLocales = englishAndOtherLocales.get(true);
        // englishLocales.forEach((Locale l)-> System.out.println(l));
        englishLocales.forEach(l -> System.out.println(l));


        final List<City> cities = Arrays.asList(new City(1L, "Rio Janeiro", 100, "RJ"), new City(2L, "Sao Paulo", 500, "SP"), new City(3L, "Santos", 20, "SP"));

        // The groupingBy method yields a map whose values are lists. If you want to process
        // those lists in some way, you supply a “downstream collector.” For example, if
        // you want sets instead of lists, you can use the Collectors.toSet collector that you
        // saw in the preceding section:
        final Stream<Locale> locales3 = Stream.of(Locale.getAvailableLocales());
        final Map<String, Set<Locale>> countryToLocaleSet = locales3.collect( //
                groupingBy(Locale::getCountry, toSet()));
        //
        countryToLocaleSet.forEach((k,v) -> System.out.println(k + " " + v));

        //Several other collectors are provided for downstream processing of grouped elements:
        // • counting produces a count of the collected elements. For example,
        final Stream<Locale> locales4 = Stream.of(Locale.getAvailableLocales());
        final Map<String, Long> countryToLocaleCounts = locales4.collect(groupingBy(Locale::getCountry, counting()));
        //counts how many locales there are for each country.
        for (final Map.Entry<String, Long> entry : countryToLocaleCounts.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        //
        //• summing(Int|Long|Double) takes a function argument, applies the function to the downstream elements, and produces their sum. For example,
        final Stream<City> citiesStream1 = cities.stream();
        final Map<String, Integer> stateToCityPopulation = citiesStream1.collect( //
                groupingBy(City::getState, // 
                        summingInt(City::getPopulation)));
        //
        // computes the sum of populations per state in a stream of cities.
        for (final Map.Entry<String, Integer> entry : stateToCityPopulation.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        //
        //• maxBy and minBy take a comparator and produce maximum and minimum of the downstream elements.
        final Stream<City> citiesStream2 = cities.stream();
        final Map<String, Optional<City>> stateToLargestCity = citiesStream2.collect( //
                groupingBy(City::getState, // 
                        maxBy(Comparator.comparing( //
                                City::getPopulation))));
        //
        // Here, we group cities by state. Within each state, we produce the names of the cities and reduce by maximum length.
        for (final Map.Entry<String, Optional<City>> entry : stateToLargestCity.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        //

        // • mapping applies a function to downstream results, and it requires yet another collector for processing its results. For example,
        final Stream<City> citiesStream3 = cities.stream();
        final Map<String, Optional<String>> stateToLongestCityName = citiesStream3.collect(//
                groupingBy(City::getState, //
                        mapping(City::getName, //
                                maxBy(Comparator.comparing(String::length)))));

        for (final Map.Entry<String, Optional<String>> entry : stateToLongestCityName.entrySet()) {
            System.out.println("summarizingInt method: " + entry.getKey() + " " + entry.getValue());
        }
        //
        //
        // The mapping method also yields a nicer solution to a problem from the preceding section, to gather a set of all languages in a country.
        final Stream<Locale> locales5 = Stream.of(Locale.getAvailableLocales());

        final Map<String, Set<String>> countryToLanguages = locales5.collect( //
                groupingBy(l -> l.getDisplayCountry(), // 
                        mapping(l -> l.getDisplayLanguage(), toSet())));

        for (final Map.Entry<String, Set<String>> entry : countryToLanguages.entrySet()) {
            System.out.println("summarizingInt method: " + entry.getKey() + " " + entry.getValue());
        }

        // • If the grouping or mapping function has return type int, long, or double,
        // you can collect elements into a summary statistics object, as discussed in
        // Section 2.9, “Collecting Results,” on page 33. For example,
        final Stream<City> citiesStream4 = cities.stream();
        final Map<String, IntSummaryStatistics> stateToCityPopulationSummary = citiesStream4.collect( //
                groupingBy(City::getState, // 
                        summarizingInt(City::getPopulation)));

        for (final Map.Entry<String, IntSummaryStatistics> entry : stateToCityPopulationSummary.entrySet()) {
            System.out.println("summarizingInt method: " + entry.getKey() + " " + entry.getValue());
        }

        // • Finally, the reducing methods apply a general reduction to downstream
        // elements. There are three forms: reducing(binaryOperator), reducing(identity,
        // binaryOperator), and reducing(identity, mapper, binaryOperator). In the first form,
        // the identity is null. 
        final Stream<City> citiesStream5 = cities.stream();
        final Map<String, String> stateToCityNames = citiesStream5.collect( //
                groupingBy(City::getState, //
                        reducing("", City::getName, //
                                (s, t) -> s.length() == 0 ? t : s + ", " + t)));

        for (final Map.Entry<String, String> entry : stateToCityNames.entrySet()) {
            System.out.println("reducing method: " + entry.getKey() + " " + entry.getValue());
        }

        // As with Stream.reduce, Collectors.reducing is rarely necessary. In this case, you can achieve the same result more naturally as
        final Stream<City> citiesStream6 = cities.stream();
        final Map<String, String> stateToCityNames2 = citiesStream6.collect(groupingBy(City::getState, //
                mapping(City::getName, //
                        joining(", "))));
        //
        for (final Map.Entry<String, String> entry : stateToCityNames2.entrySet()) {
            System.out.println("joining method: " + entry.getKey() + " " + entry.getValue());
        }
    }

}
