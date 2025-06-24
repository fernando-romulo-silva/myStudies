package br.com.fernando.code02_simple_data_types

import java.util.regex.*;

Pattern pattern1 = Pattern.compile("a\\\\b");

println pattern1;

// Groovy

def slashy = /a\b/
def url = $/http://www.google.com/blog/$

println slashy.class

// Find | Match

def pattern2 = ~/a\b/


def text = "Being a Cleveland Sports Fan is no way to go through life" // true
def pattern = ~/Cleveland Sports Fan/

def matcher1 = text =~pattern

if (matcher1.find()) { // (matcher2)
	println matcher1
}

def matcher2 = "This is summer" ==~pattern
println matcher2

if (matcher2) { // (matcher2)
	println matcher2
}

text = text.replaceFirst(~/Cleveland/, "Buffalo")
println text

