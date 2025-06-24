package br.com.fernando.code06_object_oriented_programming

import groovy.transform.ToString

@ToString
class Person02 {
	
	String first, last
	
	// constructor
	Person02(String fullName) {
		def parts = fullName.split(" ");
		this.first = parts[0]
		this.last = parts[1]
	}
	
	def foo(String a, String b) {
		"$first $last" // default return
	}
	
	def bar() {
		
	}
	
	static String doGoodWork() {
		println "Doing good work..."
	}
	
	def someMethod(List numbers = [1, 2, 3], Boolean canAccess = false) {
		
	}
	
	def concat(String... args) {
		println args.length
	}
}

// def person01 = new Person02(first: "Fernando", last: "Silva") // default constructor

def person02 = new Person02("Fernando Silva");

println person02

println person02.foo("A", "B")



