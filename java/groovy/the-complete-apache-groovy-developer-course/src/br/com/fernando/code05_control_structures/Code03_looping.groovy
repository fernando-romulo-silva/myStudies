package br.com.fernando.code05_control_structures



// while

def numbers = [1, 2, 3]

while ( numbers ) {
	//
	numbers.remove(0)
}

assert numbers == []

// for

numbers = [1, 2, 3]

for (def i in numbers) {
	println i
}

println ""

for (def i in 1..10) {
	println i
}

// return/break/continue

String getFoo() {
	// return "foo"
	"foo"
}

def a = 1
while ( true ) {
	a++
	break
}


for (def s in 'a'..'z') {
	if ( a == 'a') {
		continue
	}
	println s
	
	if (s > 'b') {
		break
	}
}


