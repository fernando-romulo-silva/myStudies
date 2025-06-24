package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.ToString

// groovy.transform

@ToString(includeNames = true, excludes= "email")
class Person {
	
	String first;
	String last;
	String email;
	
//	@Override
//	String toString() {
//		"Person[first $first]"
//	}
	
}

def person = new Person(first: "Fernando", last: "Silva", email: "fernando@gmail.com");

println person.toString()