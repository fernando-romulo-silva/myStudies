package br.com.fernando.code07_runtime_metaprograming

class Dev {
	String first
	String last
	String email
	List languages
	
	Dev() {
		
	}
	
	def invokeMethod(String name, Object args) {
		println "invokeMethod() called with args $args"
	}
	
	def getProperty(String property) {
		println "getProperty called ..."
		metaClass.getProperty(this, property)
	}
	
	void setProperty(String property, Object value) {
		println "setProperty() called with name $property and value $value"
		metaClass.setProperty(this, property, value)
	}
}


def dev01 = new Dev(first: "Dan", last: "vega", email: "danvega@gmail.com")
dev01.writeCode("Groovy")
println dev01.first

dev01.languages = ["Groovy", "Java"]

// ===================================================================================================

Integer.metaClass.timesTwo = { delegate * 2 }

println 2.timesTwo()


