package br.com.fernando.code01_basics

import groovy.transform.Immutable
import groovy.transform.ToString

@ToString
@Immutable
class Customer {

	String first;
	String last;
}

def date = new Date();

def c1 = new Customer(first: "Paul", last: "Smith");
def c2 = new Customer(first: "Paul", last: "Smith");

assert c1 == c2;
c1.first = "bla";