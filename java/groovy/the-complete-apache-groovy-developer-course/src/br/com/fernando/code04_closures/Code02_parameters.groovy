package br.com.fernando.code04_closures

// ---------------------------
// implicit parameter
def foo = {
	println it
}

foo("Fer")


// ---------------------------
// no params?
def noparams = {
	println "no params"
}

noparams(1)
noparams()

// ---------------------------
def sayHello = { first, last -> 
	println "Hello $first $last"
}

sayHello("Monica", "Santiago")

// ---------------------------
// default values

def greet = { name, greeting = "Howdy" ->

	println "$greeting, $name"
}

greet("Dan", "Hello")
greet("Joe")

// ---------------------------
// var-arg

def concat = { String... args -> 
	args.join(" ")
}

println concat('abc', 'def')
println concat('abc', 'def', '123', '234')

// ---------------------------
// 

def someMethod(Closure c) {
	println "..."
	println c.maximumNumberOfParameters
	println c.parameterTypes
}

def someClosure = { int x, int y -> 
	x + y
}

someMethod(someClosure)


