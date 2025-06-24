package br.com.fernando.code11_working_with_the_GDK

@Grapes(
    @Grab(group='org.codehaus.groovy', module='groovy-templates', version='3.0.11')
)

def staticEmail = '''
Dear "$firstName $lastName",
This is your monthly notification of your Github activity.
You currently have ${repositories.size()} repositories on Github and you had a total of "$commits" commits this month.
We wanted to let you know that these are your latest 3 repositories by activity.

<% 
repositories.each { repo ->
	println "\t> $repo.name"
}
%>

We thank you for using Github and wish you another month of happy committing.

Thank you
Github
www.github.com
'''

// 3 components to building a dynamic template
//
// -> 1. Engine (SimpleTemplateEngine)
// -> 2. Template (the email)
// -> 3. Data Bindings (The data to insert into the dynamic portions of our email)

import groovy.text.SimpleTemplateEngine
import groovy.text.Template

def engine = new SimpleTemplateEngine(true);

def template = engine.createTemplate(staticEmail)

def bindings = [
	firstName: "Dan",
	lastName: "Vega",
	commits: 27,
	repositories: [
		[name: "Apache Groovy Course", url: "https://github.com/cfaddic/apache-groovy-course"],
		[name: "Spring Boot REST", url: "https://github.com/cfaddic/spring-boot-rest"],
		[name: "Learn Spring Boot", url: "https://github.com/cfaddic/learn-springboot"]
	]
]

println template.make(bindings)

