package br.com.fernando.code06_object_oriented_programming

import groovy.transform.ToString

@ToString
class Person {
	String id
	String name
}

interface IPersonService {
	
	Person find()
	
	List<Person> findAll()
	
}

class PersonService implements IPersonService {

	@Override
	public Person find() {
		new Person(id: "1", name: "Fernando")
	}

	@Override
	public List<Person> findAll() {
		def p1 = new Person(id: "1", name: "Fernando")
		def p2 = new Person(id: "2", name: "Joao")
		
		return List.of(p1, p2);
	}	
}


println new PersonService().find()