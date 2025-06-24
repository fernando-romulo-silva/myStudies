package br.com.fernando.code10_working_with_REST_services

@Grapes(
@Grab(group='org.codehaus.groovy', module='groovy-xml', version='3.0.23')
)

import groovy.xml.XmlSlurper;


def xml = '''
<sports>
	<sport>
		<name>Football</name>
	</sport>
</sports>
'''

def xmlSlurper = new XmlSlurper();

def sports = xmlSlurper.parseText(xml)

println sports.getClass().getName()
println sports.sport.name;
