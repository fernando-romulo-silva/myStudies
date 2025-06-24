package br.com.fernando.code11_working_with_the_GDK

def t = new Thread() { /* do something */ }

t.start();

Thread.start { /* do something */ }

Thread.start('thead-name') { /* do something */ }

Thread.startDaemon('thead-name') { /* do something */ }

// -------------------------------------------------------------------------------------------

def numbers = []

// push
10.times { 
	println "push:\t ${it}"
	numbers << it
}

//println numbers

// pop
for (i in 9..0) {
	println "pop:\t ${i}" 
	numbers.pop()
}

// println numbers


// -------------------------------------------------------------------------------------------

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

def sharedQueue = [] as LinkedBlockingDeque

Thread.start("push") { 
	10.times { 
		try {
			println "${Thread.currentThread().name}\t: ${it}"
			sharedQueue << it
			sleep 100
		} catch (e) {
			// do something
		}
	}
}

Thread.start("pop") {
	for (i in 0..9) {
		sleep 200
		println "${Thread.currentThread().name}\t: ${sharedQueue.take()}"		
	}
}





