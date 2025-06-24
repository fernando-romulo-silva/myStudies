package br.com.fernando.code01_basics

if ( true ) {
	println "It is true!";
}


class Fruit {

}

Fruit fruit = null;

if (fruit) {
	println "Object fruit is not null";
}

String name = "Fer";
if (name) {
	println "Name has a value";
}

String middle = null;
if (middle) {
	println "Name has a value";
}

String last = "";
if (last) {
	println "Last has a value";
}

def i = 1;
while ( i <= 10) {
	println i;
	i++;
}

def list1 = [1, 2, 3, 4];
for (num in list1) {
	println num;
}

// closure
def list2 = [1, 2, 3, 4];
list2.each { println "closure "+it }

// switch
def myNumber = 10;

switch (myNumber) {
	case 7 : println "number is 7"; break;
	case 10 : println "number is 10"; break;
	default : println "number is unknown";
} 



