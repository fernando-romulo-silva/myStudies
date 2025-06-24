package br.com.fernando.code07_runtime_metaprograming

String.metaClass.shout = { -> toUpperCase() }
println "Hello guys".shout()

class StringCategory {
	
	static String shout(String string) {
		string.toUpperCase()
	}
}

use(StringCategory) {
	println "Hello world!".shout()
}

use(TimeCategory) {
	println 1.minute.from.now
	
	println 10.hours.ago
	
	def someDate = new Date()
	println someDate - 3.months
}