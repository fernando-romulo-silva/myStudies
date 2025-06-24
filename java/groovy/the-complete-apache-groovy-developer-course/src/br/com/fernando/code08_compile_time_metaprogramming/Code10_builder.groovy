package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@ToString
class Developer {
	
	String firstName;
	String lastName;
	String middleInitial
	String email;
	Date hireDate
	List languages
}

def dev = Developer.builder()
	.firstName("Fernando")
	.lastName("Silva")
	.middleInitial("D")
	.email("fernando@gmail.com")
	.hireDate(new Date())
	.languages(["Java", "Groovy"])
	.build();
