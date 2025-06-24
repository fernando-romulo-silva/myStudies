package br.com.fernando.code01_basics

import groovy.transform.ToString

// all class are public

class AngryBirds {
}

class Bird {
	
}

class Pig {
	
}

@ToString
class Developer {

	// all fields are private
	String first;
	String last;
	def languages = [];
	
	// all methods are public
	void work() {
		println "$first $last is working ...";
	}		
}

var d = new Developer(first: "Dan", last: "Vega");

d.first = "Mary";
d.setFirst("Paul")

d.languages << "Groovy"
d.languages << "Java"

d.work();

println d