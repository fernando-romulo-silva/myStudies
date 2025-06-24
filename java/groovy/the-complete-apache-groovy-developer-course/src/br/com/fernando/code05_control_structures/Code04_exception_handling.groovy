package br.com.fernando.code05_control_structures

// exceptions, all exceptions are optional (unchecked)

def foo( ) {
	throw new Exception("Foo Exception")
}

def log = []

try {
	foo()
} catch (e) {
	log << e.message
} finally {
	log << "Finally"
}

println log

// Java 7 introduced a multi-catch syntax
try {
	// do stuff
} catch (FileNotFoundException| NullPointerException ex) {
	println ex.class.name
	println ex.message
}