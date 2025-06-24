package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(excludes="email")
class Worker {
	
	String first;
	String last;
	String email;
}

def p01 = new Worker(first: "Fernando", last: "Silva", email: "fernando@gmail.com");
def p02 = new Worker(first: "Fernando", last: "Silva", email: "silva@gmail.com");

println p01 == p02 // true