package br.com.fernando.code06_object_oriented_programming

class Person01 { // by default it's public
	
	String firstName // by default it is private
	def dob // by default it is Object
	
	protected lastName // it won't create a setter/getter method
	
	def foo() {
		// local variables
		def myVariable = "This is a text!"
	}
	
}


def person = new Person01()
person.setFirstName("Fernando") // create setters/getters for default class attributes
person.setDob(1)
