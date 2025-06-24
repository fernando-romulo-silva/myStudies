package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.Canonical

// @ToString + @EqualsAndHashCode + @TupleConstructors

@Canonical
class Manager {
	
	String first
	String last
	String email
}

def p01 = new Manager(first: "Fernando", last: "Silva", email: "fernando@gmail.com")
def p02 = new Manager(first: "Fernando", last: "Silva", email: "fernando@gmail.com")

println p01