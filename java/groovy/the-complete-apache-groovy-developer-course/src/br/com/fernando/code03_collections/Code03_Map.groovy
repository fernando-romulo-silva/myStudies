package br.com.fernando.code03_collections

def map = [:]

println map
println map.getClass().getName()

// ================

def emailKey = "email"

def person = [first:"Dan", last:"Vega", emailKey:"danvega.@gmail.com"]
println person
println person.first

person.twitter = "@threaalvanvega"

println person

for (entry in person) {
	println entry
}

for (key in person.keySet()) {
	println "$key:${person[key]}"
}