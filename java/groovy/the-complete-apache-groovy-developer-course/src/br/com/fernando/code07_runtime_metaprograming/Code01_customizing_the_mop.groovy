package br.com.fernando.code07_runtime_metaprograming

class InvokeDemo {
	
	def invokeMethod(String name, Object args) {
		"called invokeMethod $name $args"
	}
	
	def test() {
		"method exists"
	}
}

def invokeDemo = new InvokeDemo()

assert invokeDemo.test() == "method exists";

assert invokeDemo.someMethod() == "called invokeMethod someMethod []"

// ==========================================================================

class PropertyDemo {
	
	def prop1 = "prop1"
	def prop2 = "prop2"
	def prop3 = "prop3"
	
	def getProperty(String name) {
		println "getProperty() called with argument $name"
		
		if (metaClass.hasProperty(this, name)) {
			metaClass.getProperty(this, name)
		} else {
			println "lets do something fun with this property"
			"party time..."
		}
				
	}
} 

def pd = new PropertyDemo()

println pd.prop1
println pd.prop2
println pd.prop3
println pd.prop4

// ==========================================================================

// You can intercept write access to properties by overriding the theProperty() method:

class POGO {
	String property
	
	void setProperty(String name, Object value) {
		this.@"$name" = "overriden"
	}
}

def pogo = new POGO()
pogo.property = 'a';

assert pogo.property == "overriden"

// ==========================================================================

// Groovy supports the concept of methodMissing.
// This method differs from invokeMethod in that it is only invoked in case of a failed method dispatch, 
// when no method can be found for the given name and/or the given arguments

class MyEmployee {
	
	def methodMissing(String name, def args) {
		
		if (name != 'someMethod') {
			throw new MissingMethodException(name, MyEmployee.class, args)
		}
		
		println "Method missing called on: $name"
		println "with arguments ${args}"
	}
}

def myEmployee = new MyEmployee()
myEmployee.someMethod(1, 2, 3)
myEmployee.someOtherMethod(1, 2, 3)



