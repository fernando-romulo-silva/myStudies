package br.com.fernando.code09_working_with_builders

@Grapes(
	@Grab(group='org.codehaus.groovy', module='groovy-json', version='3.0.11')
)

import groovy.json.JsonBuilder;


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

println builder.toString()

println builder.toPrettyString()


new File('json/books.json').write(builder.toPrettyString())