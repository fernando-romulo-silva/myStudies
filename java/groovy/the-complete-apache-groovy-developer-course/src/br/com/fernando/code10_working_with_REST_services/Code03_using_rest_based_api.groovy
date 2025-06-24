package br.com.fernando.code10_working_with_REST_services

@Grapes([
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1'),
	@Grab(group='org.codehaus.groovy', module='groovy-json', version='3.0.11')
])

import groovyx.net.http.*

//define empty class with old name to prevent failure
this.getClass().getClassLoader().getParent().parseClass '''
  package groovy.util.slurpersupport
  class GPathResult{}
''' 

// println 'http://groovy-lang.org'.toURL().text
// println 'https://api.chucknorris.io/'.toURL().text

def base = "https://api.chucknorris.io/"

def chuck = new RESTClient(base)
def params = [firstName: "Fernando", lastname: "Silva"]

chuck.contentType = ContentType.JSON;

chuck.get(path: '/jokes/random', query: params) { response, json ->
	println response.status
	println json
}

