package br.com.fernando.ch10_using_optional_as_a_better_alternative_to_null.part05_Summary;

// Summary
public class Test {

    //  null references have been historically introduced in programming languages to generally signal the
    // absence of a value.
    //
    //  Java 8 introduces the class java.util.Optional<T> to model the presence or absence of a value.
    //
    // You can create Optional objects with the static factory methods Optional.empty, Optional.of, and Optional.ofNullable.
    //
    // The Optional class supports many methods such as map, flatMap, and filter, which are conceptually similar to the methods of a stream.
    //
    // Using Optional forces you to actively unwrap an optional to deal with the absence of a value; as a result, you protect your code against 
    // unintended null pointer exceptions. 
    //
    //  Using Optional can help you design better APIs in which, just by reading the signature of a method, users can tell whether to expect an optional 
}
