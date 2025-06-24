package br.com.fernando.code02_simple_data_types

import java.math.BigDecimal

import groovy.transform.ToString

@ToString
class AccountNew {
	
	BigDecimal balance = 0.0
	
	String type
	
	BigDecimal deposit(BigDecimal amount) {
		balance += amount;
	}
	
	BigDecimal withdraw(BigDecimal amount) {
		balance -= amount
	}
	
	BigDecimal plus(AccountNew other) {
		this.balance + other.balance
	}
}

def checking = new AccountNew(type: "Checking");
checking.deposit(100.00)

def savings = new AccountNew(type: "Savings");
savings.deposit(50.00)

println checking;
println savings;

def total = checking + savings;
println total