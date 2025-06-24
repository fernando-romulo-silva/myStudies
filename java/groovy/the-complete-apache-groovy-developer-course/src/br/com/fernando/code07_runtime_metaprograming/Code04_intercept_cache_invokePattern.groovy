package br.com.fernando.code07_runtime_metaprograming

class DeveloperIn {
	
	List languages = []
	
	def methodMissing(String name, args) {
		println "${name}() method called"
		
		if (name.startsWith("write")) {
			def language = name.split('write')[1]
			
			if (languages.contains(language)) {
				def impl = { Object[] theArgs ->
					println "I like to write the code in ${language}"
				}
				
				getMetaClass()."$name" = impl
				return impl(args)
			}
		}
	} 
}

def dev01 = new DeveloperIn();

dev01.languages << "Groovy"
dev01.languages << "Java"

println dev01.languages

dev01.writeGroovy()
dev01.writeGroovy()
dev01.writeGroovy()

println dev01.languages

dev01.writeJava()

println dev01.languages