package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.Canonical
import groovy.transform.Sortable

@Canonical
@Sortable(includes = ["lastName", "firstName" ])
class MyPerson {
	String firstName
	String lastName
}

def p01 = new MyPerson("Fernando", "Silva")
def p02 = new MyPerson("Katie", "Silva")
def p03 = new MyPerson("Andy", "Silva")
def p04 = new MyPerson("Joe", "Silva")
def p05 = new MyPerson("Fulano", "NotSilva")

def silvas = [ p01, p02, p03, p04, p05 ]

println silvas.toSorted();