package br.com.fernando.code04_closures



class ScopeDemo {
	
	def outerClosure = {
		println this.class.name
		println owner.class.name
		println delegate.class.name
		
		def nestedClosure = {
			println this.class.name
			println owner.class.name;
			println delegate.class.name
		}
		
		nestedClosure()
	}
}

def demo = new ScopeDemo()
demo.outerClosure()

// ======================================================================

def writer = {
	append 'Dan'
	append ' lives in Cleveland'
}

def append(String s) {
	println "append() called with argument of $s"
}

def sb = new StringBuffer()
writer.resolveStrategy = Closure.DELEGATE_FIRST
writer.delegate = sb
writer()

