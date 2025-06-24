package br.com.fernando.code10_working_with_REST_services

@Grapes(
	@Grab(group='org.codehaus.groovy', module='groovy-json', version='3.0.11')
)

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper;


def builder = new JsonBuilder();

builder.books {
	currentBook {
		title 'Teh 4 Hour Work Week'
		isbn '979-0-394-45431-1'
		author (first: 'Timothy', last: 'Ferriss', twitter: '@tferris')
		related 'The 4 Hour Body', 'The 4 Hour Chef'
	}
	
	nextBook {
		title '#AskGaryVee'
		isbn '394-4-34-34532-3'
		author (first: 'Gary', last: 'Vaynerchuck', twitter: '@garyee')
		related 'Jab, Jab, Jab, Right Hook', "Crush It!"		
	}
}

def books = builder.toPrettyString()

def sluper = new JsonSlurper()

def json = sluper.parseText(books)

println json
println json.books.nextBook.title
println json.books.nextBook.author
println json.books.nextBook.related





