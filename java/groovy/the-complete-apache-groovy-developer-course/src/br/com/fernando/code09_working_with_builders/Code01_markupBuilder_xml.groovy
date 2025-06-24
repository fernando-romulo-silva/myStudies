package br.com.fernando.code09_working_with_builders

@Grapes(
	@Grab(group='org.codehaus.groovy', module='groovy-xml', version='3.0.23')
)

import groovy.xml.MarkupBuilder;

def builder = new MarkupBuilder()
builder.omitEmptyAttributes = true
builder.omitNullAttributes = true


builder.sports{

	sport(id:1) {
		name "Baseball"
	}
	
	sport(id:2) {
		name "Basketball"
	}
	
	sport(id:3) {
		name "Football"
	}
	
	sport(id:4) {
		name 'Hockey'
	}
	
	sport(id:null){
		name ''
	}
}
