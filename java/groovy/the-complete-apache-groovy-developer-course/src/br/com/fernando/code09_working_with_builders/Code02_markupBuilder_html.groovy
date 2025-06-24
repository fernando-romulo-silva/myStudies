package br.com.fernando.code09_working_with_builders

@Grapes(
	@Grab(group='org.codehaus.groovy', module='groovy-xml', version='3.0.23')
)

import groovy.xml.MarkupBuilder;

def writer = new FileWriter("html/about.html")

def builder = new MarkupBuilder(writer)

def courses = [
	[id: 1, name: 'Apache Groovy'],
	[id: 2, name: 'Spring Boot']
]

builder.html {
	
	head {
		title 'About Fernando Silva'
		description 'This is an about me page'
		keywords 'Fernando, Groovy, Java'
	}
	
	body {
		h1 'About me'
		p 'This is a bunch of text about me...'
		section {
			h2 'Courses'
			table {
				tr {
					th 'id'
					th 'name'
				}
				courses.each { course -> 
					tr {
						td course.id
						td course.name
					}
				}
			}
		}
	}
}