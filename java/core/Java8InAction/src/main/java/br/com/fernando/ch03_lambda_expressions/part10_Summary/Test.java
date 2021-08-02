package br.com.fernando.ch03_lambda_expressions.part10_Summary;

// Summary
public class Test {

    //  A lambda expression can be understood as a kind of anonymous function: it doesn’t have a name, but
    // it has a list of parameters, a body, a return type, and also possibly a list of exceptions that can be thrown.
    //
    //  Lambda expressions let you pass code concisely.
    //
    //  A functional interface is an interface that declares exactly one abstract method.
    //
    //  Lambda expressions can be used only where a functional interface is expected.
    //
    //  Lambda expressions let you provide the implementation of the abstract method of a functional
    // interface directly inline and treat the whole expression as an instance of a functional interface.
    //
    //  Java 8 comes with a list of common functional interfaces in the java.util .function package, which
    // includes Predicate<T>, Function<T, R>, Supplier<T>, Consumer<T>, and BinaryOperator<T>,.
    //
    //  There are primitive specializations of common generic functional interfaces such as Predicate<T>
    // and Function<T, R> that can be used to avoid boxing operations: IntPredicate, IntToLongFunction, and so on.
    //
    //  The execute around pattern (that is, you need to execute a bit of behavior in the middle of code that’s
    // always required in a method, for example, resource allocation and cleanup) can be used with lambdas
    // to gain additional flexibility and reusability.
    //
    //  The type expected for a lambda expression is called the target type.
    //
    //  Method references let you reuse an existing method implementation and pass it around directly.
    //
    //  Functional interfaces such as Comparator, Predicate, and Function have several default methods
    // that can be used to combine lambda expressions
    
}
