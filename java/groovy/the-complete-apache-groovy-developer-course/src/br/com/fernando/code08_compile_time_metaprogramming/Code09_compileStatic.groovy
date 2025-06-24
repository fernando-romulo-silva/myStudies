package br.com.fernando.code08_compile_time_metaprogramming

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

@CompileStatic // java byte code generation
class SomeClass {
	
	String foo() {
		"foo"
	}
	
	String bar() {
		"bar"
	}
	
	@CompileStatic(TypeCheckingMode.SKIP)
	def noReturn() {
		"bla"
	}
}

def someObject = new SomeClass()
