package br.com.fernando.code04_closures

def c = {}

println c.class.name

def sayHello = { name ->
	println "Hello $name"
}

sayHello("Fernando")

def nums = [12, 26, 31, 4, 5, 6, 7, 32]

nums.each({ num -> 
	println num
})

println ""

// closures are objects and last param
def timesTen(num, closure) {
	closure(num * 10)
}

timesTen(10, {println it})

timesTen(2) { println it }

println ""

10.times { println it }

println ""

def random = new Random()

3.times { println random.nextInt() }


