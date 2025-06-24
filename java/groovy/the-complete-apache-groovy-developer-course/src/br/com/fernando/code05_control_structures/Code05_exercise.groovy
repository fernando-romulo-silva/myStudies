package br.com.fernando.code05_control_structures

import java.math.BigDecimal

// 

class Account {
	
	BigDecimal balance = 0.0
	
	def deposit(BigDecimal amount) {
		if ( amount <= 0 ) {
			throw new Exception("Deposit amount must be greater than 0")
		}
		
		
		balance += amount
	}
	
	def deposit(List amounts) {
		for ( amount in amounts ) {
			deposit(amount)
		}
	}
}


def checking = new Account()

checking.deposit(10)
println checking.balance

try {
	checking.deposit(-1)
} catch (ex) {
	println ex.message
}

checking.deposit([1, 2, 5, 6, 30, 50])

println checking.balance




