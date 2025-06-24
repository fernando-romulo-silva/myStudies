package br.com.fernando.code01_basics

import groovy.transform.Canonical
import groovy.transform.Sortable

@Canonical // EqualsAndHascode, ToString and TuppleConstructor
@Sortable(excludes = "favItems")
class PersonDemo {
	
	String first, last
	Collection<String> favItems = ['Fodd']
	
}

def p1 = new PersonDemo(first:"Joe", last: "Vega")
def p2 = new PersonDemo(first:"Dan", last: "Vega")


def people = [p1, p2]

println people

def sorted = people.sort(false) // do not mutate original collection

println sorted