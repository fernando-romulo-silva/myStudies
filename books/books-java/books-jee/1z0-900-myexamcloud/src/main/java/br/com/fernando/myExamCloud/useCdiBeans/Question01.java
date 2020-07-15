package br.com.fernando.myExamCloud.useCdiBeans;

import javax.enterprise.inject.Default;

public class Question01 {

    // Given the below CDI beans
    //
    // Customer bean
    @Default
    public class Customer {
	// ...
    }

    // Product bean
    public class Product {
	// ...
    }

    // How many qualifiers are defined Customer and product beans?
    //
    //
    // Choice A - Customer bean has one qualifier and Product bean has none
    //
    // Choice B - Both Customer and Product bean has only one qualifier
    //
    // Choice C - Both Customer and Product bean has two qualifiers
    //
    // Choice D - Both Customer and Product bean has three qualifiers
    //
    //
    // Explanation :
    //
    // Choice C is correct.
    // Every bean has the built-in qualifier @Any, even if it does not explicitly declare this qualifier, except for the special @New qualified beans 
    // defined in @New qualified beans.
    //
    // If a bean does not explicitly declare a qualifier other than @Named or @Any, the bean has exactly one additional qualifier, of type @Default. 
    // This is called the default qualifier.
    //
    // Both Customer and Product declarations result in a bean with two qualifiers: @Any and @Default.

}
