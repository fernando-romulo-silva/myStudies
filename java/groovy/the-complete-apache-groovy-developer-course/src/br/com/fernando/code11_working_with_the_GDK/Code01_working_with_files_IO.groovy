package br.com.fernando.code11_working_with_the_GDK

def file = new File('dummy.txt')

file.write("This is some text\n")

file.append("I am some more text... \n")

def lines = file.readLines()
lines.each { line ->
	println line
}

println "---------------------------------------------------------"

def dir = '/home/fernando'
new File(dir).eachFile { folder ->
	// eachFileRecurse
	if (!folder.isFile()) {
		println folder.name
	}
}

new File('.').eachFile { f ->
	if (f.name.endsWith(".groovy")) {
		println f.name
	}
}

println "---------------------------------------------------------"

def newDir = '/home/fernando/Downloads'

def hidden = []

new File(newDir).eachFile { f ->

	if (f.isDirectory()) {
		println f.name
	}
	
	if (file.isHidden()) {
		hidden << file.name
	}
}

println "---------------------------------------------------------"

def dirFolder = '/home/fernando/Downloads'

new File(newDir).eachDir { f ->
	
	println f
}


  