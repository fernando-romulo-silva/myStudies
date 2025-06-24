package br.com.fernando.code06_object_oriented_programming

import groovy.transform.ToString

@ToString
class Employee implements Serializable {
	String first, last, email
	
	String fullName
	
	void setFullName(String fullName) {
//		this.fullName = fullName;
	}
	
	String getFullName(String fullName) {
		fullName
	}
}

def employee01 = new Employee(first: "Fernando", last: "Silva", email: "fernando@gmail.com")

println employee01

employee01.setEmail("silva@gmail.com")
employee01.email = "noemail@gmail.com" // execute the set method

employee01.@fullName = "No bla bla" // access property without set method
employee01.fullName = "Bla bla"

println employee01