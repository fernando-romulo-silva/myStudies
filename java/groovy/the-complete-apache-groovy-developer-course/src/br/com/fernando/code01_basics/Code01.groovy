package br.com.fernando.code01_basics

import groovy.transform.ToString

def msg = "Hello comment!"; // comments here

/*
 * this a multi-line comment
 */

@ToString
class Person {
	String id;
	String name;
}

def person = new Person(id:"13434", name:"Paul");

println person;