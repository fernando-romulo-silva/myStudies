package br.com.fernando.code02_simple_data_types

import java.math.BigDecimal

def s1 = "Hello"
def s2 = ", World!"

println s1 + s2
println s1.plus(s2)

class Account {

	BigDecimal balance;
	
	Account plus(Account other) {
		new Account(balance: this.balance + other.balance)
	}
	
	String toString() {
		"Account Balance: $balance"
	}
}

def savings = new Account(balance: 100.00)
def checking = new Account(balance: 500.00)

println savings
println checking
println savings + checking
