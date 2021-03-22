package br.com.fernando.myExamCloud.useCdiBeans;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;

public class Question01 {

    // Given the below CDI beans
    //
    // Employee bean
    @Default
    public class Customer {
	// ...
    }

    // Product bean
    public class Product {
	// ...
    }

    // How many qualifiers are defined Employee and Product beans?
    //
    // Choice A - Employee bean has one qualifier and Product bean has none
    //
    // Choice B - Both Employee and Product bean has only one qualifier
    //
    // Choice C - Both Employee and Product bean has two qualifiers
    //
    // Choice D - Both Employee and Product bean has three qualifiers
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
    // Every bean has the built-in qualifier @Any, even if it does not explicitly declare this qualifier, except for the special @New qualified beans
    // defined in @New qualified beans.
    //
    // If a bean does not explicitly declare a qualifier other than @Named or @Any, the bean has exactly one additional qualifier, of type @Default.
    // This is called the default qualifier.
    //
    // Both Employee and Product declarations result in a bean with two qualifiers: @Any and @Default.
    // The following declarations are equivalent:
    @Any
    @Default
    public class Customer2 {
    }
    
    @Any
    @Default
    public class Product2 {
    }

}
