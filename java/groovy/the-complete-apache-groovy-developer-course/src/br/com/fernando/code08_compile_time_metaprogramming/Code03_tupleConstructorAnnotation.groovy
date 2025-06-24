package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString
@TupleConstructor(includeFields=true)
class Director {
	
    String name
    List likes
    private boolean active = false
	
	boolean isActivated() { active }
}

def p01 = new Director("Fernando", ["Java", "Groovy"]);

def p02 = new Director("Fernando", ["Java", "Groovy"], true);


println p01

println p02
