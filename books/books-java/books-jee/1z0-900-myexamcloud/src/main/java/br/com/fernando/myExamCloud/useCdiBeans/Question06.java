package br.com.fernando.myExamCloud.useCdiBeans;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Named;

public class Question06 {

    // Given Code:
    @Named("order")
    public class Order {
    }

    // How many qualifiers are defined in Order bean?
    //
    // Choice A
    // One
    //
    // Choice B
    // Two
    //
    // Choice C
    // Three
    //
    // Choice D
    // Four
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    // Choice C is correct.
    // 
    // Every bean has the built-in qualifier @Any, even if it does not explicitly declare this qualifier, except for the special @New. 
    // If a bean does not explicitly declare a qualifier other than @Named or @Any, the bean has exactly one additional qualifier, of type @Default. 
    // This is called the default qualifier.
    //
    // Both declarations result in a bean with two qualifiers: @Any and @Default. 
    // The following declaration results in a bean with three qualifiers: @Any, @Default and @Named("ord").
    // The following declarations are equivalent:
    @Any
    @Named
    @Default
    public class Order02 { }
    
}
