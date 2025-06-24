package br.com.fernando.code04_closures

def myMethod(Closure c) {
	c()
}

def foo =  {
	println "foo..."
}

myMethod(foo)

// -------------------------------------------

def names = ["Dan", "Joe", "Andy", "Katie"]
names.each { name -> 
	println name
}

// -------------------------------------------

def teams = [baseball: "Cleveland Indians", basketbal: "Cleveland Cavs", football: "Cleveland Browns"]
teams.each { k, v ->
	println "$k = $v"
}

// -------------------------------------------

def greet = { String greeting, String name -> 
	println "$greeting, $name"
}

greet("Hello", "Fernando")
def sayHello = greet.curry("Hello")
sayHello("Joe")

// =====================================================================================================================

def people = [
	[name: 'Jane', city: "New York City"],
	[name: 'John', city: "Cleveland"],
	[name: 'Mary', city: "New York City"],
	[name: 'Dan', city: "Cleveland"],
	[name: 'Tom', city: "New York City"],
	[name: 'Frank', city: "New York City"],
	[name: 'Jason', city: "Cleveland"]
]

println people.find { person -> person.city == 'Cleveland'}
println people.findAll { person -> person.city == 'Cleveland'}

println people.any { person -> person.city == "Cleveland" }
println people.every { person -> person.city == "Cleveland" }

println people.every { person -> person.name.size() >= 3 }

def peopleByCity = people.groupBy { person -> person.city }
println peopleByCity

def newyorkers = peopleByCity["New York City"]
def clevelanders = peopleByCity.Cleveland;

clevelanders.each {
	println it.name
}


