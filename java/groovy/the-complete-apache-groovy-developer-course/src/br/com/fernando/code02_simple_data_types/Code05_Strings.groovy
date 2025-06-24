package br.com.fernando.code02_simple_data_types

// Java

char c = 'c'
println c.class

String str = "this is a string"
println str.class

String name = "Fernando"
String msg = "Hello " + name + " ... "

println msg


// Groovy
def c2 = 'c'
println c2.class

def str2 = 'This is a string too'
println str2.class

def msg2 = "Hello ${name}"
println msg2

def msg3 = 'Hello ${name}'
println msg3

def msg4 = "We can evaluate expressions here: ${1 + 2}"
println msg4

def largeMsg = """
This is a big text ${name}
Here we have a big text
"""

println largeMsg

// dollar slashy
def folder = $/C:\groovy\fernando\foo\bar/$
println folder

