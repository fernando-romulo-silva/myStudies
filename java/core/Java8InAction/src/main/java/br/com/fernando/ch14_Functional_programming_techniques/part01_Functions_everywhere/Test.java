package br.com.fernando.ch14_Functional_programming_techniques.part01_Functions_everywhere;

import static java.util.Comparator.comparing;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import br.com.fernando.Apple;
import br.com.fernando.Letter;

// Functions everywhere
public class Test {

    static final List<String> STRINGS = Arrays.asList("Test5", "Test3", "Test2", "Test4");

    static final List<Apple> INVENTORY = Arrays.asList(new Apple(80, "green"), //
                                                       new Apple(155, "green"), //
                                                       new Apple(120, "red"));

    // Higher-order functions
    public static void test01() {

        // Another interesting example was the use of the static method Comparator.comparing,
        // which takes a function as parameter and returns another function (a Comparator)
        final Comparator<Apple> comparator = comparing(Apple::getWeight);

        INVENTORY.sort(comparator);

        // We did something similar when we were composing functions in chapter 3 to create
        // a pipeline of operations:
        final Function<String, String> addHeader = Letter::addHeader;

        final Function<String, String> transformationPipeline1 = addHeader //
            .andThen(Letter::checkSpelling) //
            .andThen(Letter::addFooter); //

        STRINGS.stream() //
            .map(transformationPipeline1);

        // Functions (like Comparator.comparing) that can do at least one of the following are called
        // higher-order functions within the functional programming community:
        //
        //  Take one or more functions as parameter
        //  Return a function as result
        //
        // Currying
        //
        // The basic pattern of all unit conversion is as follows:
        // 1. Multiply by the conversion factor.
        // 2. Adjust the baseline if relevant.
        //
        // Currying is a technique that can help you modularize functions and reuse code;
        //
        // You could obviously call the converter method with three arguments on each occasion,
        // but supplying the factor and baseline each time would be tedious and you might accidentally mistype them.

        System.out.println(converter(24, 9.0 / 5, 32));
        System.out.println(converter(100, 0.6, 0));
        System.out.println(converter(20, 0.6214, 0));

        System.out.println();

        // As a result, your code is more flexible and it reuses the existing conversion logic! Let’s reflect on
        // what you’re doing here. Instead of passing all the arguments x, f, and b all at once to the
        // converter method, you only ask for the arguments f and b and return another function, which
        // when given an argument x returns x * f + b. This enables you to reuse the conversion logic and
        // create different functions with different conversion factors.

        DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
        DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

        System.out.println(convertCtoF.applyAsDouble(24));
        System.out.println(convertUSDtoGBP.applyAsDouble(100));
        System.out.println(convertKmtoMi.applyAsDouble(20));

        System.out.println(convertUSDtoGBP.applyAsDouble(1000));

        DoubleUnaryOperator convertFtoC = expandedCurriedConverter(-32, 5.0 / 9, 0);
        System.out.println(convertFtoC.applyAsDouble(98.6));
    }

    // Here x is the quantity you want to convert, f is the conversion factor, and b is the baseline. But
    // this method is a bit too general.
    static double converter(double x, double f, double b) {
        return x * f + b;
    }

    // Here’s an easy way to benefit from the existing logic while tailoring the converter for particular
    // applications. You can define a “factory” that manufactures one-argument conversion functions
    // to exemplify the idea of currying.
    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return (double x) -> x * f + b;
    }

    static DoubleUnaryOperator expandedCurriedConverter(double w, double y, double z) {
        return (double x) -> (x + w) * y + z;
    }
}
