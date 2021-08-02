package org.nandao.ch02TheStreamAPI.part07TheOptionalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

// The key to using Optional effectively is to use a method that either consumes the
// correct value or produces an alternative.
// There is a second form of the ifPresent method that accepts a function. If the optional
// value exists, it is passed to that function. Otherwise, nothing happens.
// Instead of using an if statement, you call

public class Test {

    public static void main(String[] args) {

        final List<String> wordList = Arrays.asList("Now", "you", "can", "see", "the", "transformation", "and", "my", "SUPERTRANSFORMATION");
        final List<String> otherWordList = new ArrayList<>();

        final Stream<String> words1 = wordList.stream();

        final Optional<String> optionalValue = words1.parallel() //
            .filter(s -> s.startsWith("s")) //
            .findAny();

        optionalValue.ifPresent(v -> otherWordList.add(v));

        optionalValue.ifPresent(otherWordList::add);

        if (optionalValue.isPresent()) {
            System.out.println("optionalValue 1: " + optionalValue.get());
        }

        // When calling this version of ifPresent, no value is returned. If you want to process
        // the result, use map instead:

        final Optional<Boolean> added = optionalValue.map(otherWordList::add);
        System.out.println("added 1: " + added.get());

        // Often, there is a default that you want to use
        // when there was no match, perhaps the empty string:
        final String result1 = optionalValue.orElse("");
        System.out.println("result1 " + result1);

        // The wrapped string, or "" if none
        // You can also invoke code to compute the default,
        final String result2 = optionalValue.orElseGet(() -> System.getProperty("user.dir"));
        System.out.println("result2 " + result2);

        // The function is only called when needed
        // Or, if you want to throw another exception if there is no value,
        // Supply a method that yields an exception object
        final String result3 = optionalValue.orElseThrow(NoSuchElementException::new);
        System.out.println("result3 " + result3);

        // Clearly, you can repeat that process if you have more methods or lambdas that
        // yield Optional values. You can then build a pipeline of steps that succeeds only
        // when all parts do, simply by chaining calls to flatMap.
        // For example, consider the safe inverse method of the preceding section. Suppose
        // we also have a safe square root:

        final Optional<Double> result = Optional.of(-4.0) //
            .flatMap(Test::inverse) //
            .flatMap(Test::squareRoot);

        System.out.println("result3 " + result.get());
    }

    // So far, we have discussed how to consume an Optional object that someone
    // else created. If you write a method that creates an Optional object, there are
    // several static methods for that purpose. Either create an Optional.of(result) or
    // Optional.empty(). For example,
    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }

}
