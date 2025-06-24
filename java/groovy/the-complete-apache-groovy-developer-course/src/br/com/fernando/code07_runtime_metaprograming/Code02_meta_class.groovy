package br.com.fernando.code07_runtime_metaprograming

// meta class

class Developer {
	
}


def dev01 = new Developer();

println dev01.metaClass

dev01.metaClass.name = "Fernando"
dev01.metaClass.writeCode = { -> println "$name loves to write code..."}

dev01.writeCode()


// Expando

def expando01 = new Expando()
expando01.name = "Fernando"
expando01.writeCode = { -> println "$name loves to write code..."}

expando01.writeCode()

def expando02 = new Expando()
// expando02.writeCode() // error

// Every instance
String.metaClass.shout = { -> toUpperCase() }
println "Hello guys".shout()