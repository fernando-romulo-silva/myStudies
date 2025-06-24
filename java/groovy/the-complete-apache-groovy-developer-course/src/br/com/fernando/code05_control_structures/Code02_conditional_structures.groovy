package br.com.fernando.code05_control_structures

// ------------------------------------------------------
// if ( boolean expressions ) { logic }

if ( true ) {
	println true
}

def age = 35 

if ( age >= 35 ) {
	println "You can run for president"
} else {
	println "You cannot run for president"
}

// ------------------------------------------------------
// ternary operator ( expression ) ? true : false

def name = 'Dan'
def isitdan = (name.toUpperCase() == 'DAN') ? 'YES' : 'NO'
println isitdan


def msg
def output = (msg != null) ? msg : 'default message...'
def elvisOutput = msg ?: 'default message'

println msg
println output
println elvisOutput

// ------------------------------------------------------
// Switch operator

def num = 6

switch ( num ) {
	case 1	: 
		println "1" 
		break
		
	case 2 :
		println "2"
		break
		
	case 1..3:
		println "in range 1..3"
		break
		
	case [5, 6, 12]:
		println "num is in list [5, 6, 12]"
		break
		
	case Integer :
		println "num is an Integer"
		break
		
	case Float :
		println "num is an Float"
		break
		
	default :
		println "default..."
}

// ------------------------------------------------------
// in operator

def validAges = 18..35
def someAge = 19

println someAge in validAges




