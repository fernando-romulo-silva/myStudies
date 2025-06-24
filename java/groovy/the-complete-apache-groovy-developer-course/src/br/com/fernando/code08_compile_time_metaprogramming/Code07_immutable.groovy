package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.Immutable
import groovy.transform.ToString

@ToString
@Immutable
class Customer {
	
	String first;
	String last;
}

def c01 = new Customer(first: "Fernando", last: "Silva")
// c01.first = "Nancy" // not allowed

println c01
