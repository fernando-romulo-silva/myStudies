package br.com.fernando.code01_basics


// -------------------------------------------------
// Basic
assert  1  + 2 == 3
assert  4  - 3 == 1
assert  3  * 5 == 15
assert  3  / 2 == 1.5
assert 10  % 3 == 1
assert  2 ** 3 == 8 // 2 * 2 * 3

// -------------------------------------------------
// Ternary operator
// Instead of:

def text = "Not fount"

if (text!=null && text.length()>0) {
	println 'Found'
} else {
	println 'Not found'
}

// You can write:

println (text!=null && text.length() > 0) ? 'Found' : 'Not found'


// -------------------------------------------------
// Elvis operator

class User {
	String name
}

def user = new User(name: "Fernando")

displayName = user.name ? user.name : 'Anonymous'
displayName = user.name ?: 'Anonymous'

println displayName

// -------------------------------------------------
// Object operator

def someName = user?.name; // safe operator

println someName


// -------------------------------------------------
// Unary operators
// The + and - operators are also available as unary operators:

def a = 2
def b = a++ * 3

assert a == 3 && b == 6

def c = 3
def d = c-- * 2

assert c == 2 && d == 6

def e = 1
def f = ++e + 3

assert e == 2 && f == 5

def g = 4
def h = --g + 1

assert g == 3 && h == 4

// -------------------------------------------------