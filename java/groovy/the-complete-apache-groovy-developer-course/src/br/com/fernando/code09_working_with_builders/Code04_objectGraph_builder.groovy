package br.com.fernando.code09_working_with_builders

import groovy.transform.ToString

@ToString(includeNames = true)
class Book {
	String title
	String summary
	List<Section> sections = []
}

@ToString(includeNames = true)
class Section {
	String title
	List<Chapter> chapters = []
}

@ToString(includeNames = true)
class Chapter {
	String title
}


// Java style
public Book createBookJava() {
	final Book b = new Book();
	b.setTitle("My Book");
	b.setSummary("My Summary");
	
	Section s = new Section();
	s.setTitle("Section 1");
	
	Chapter c1 = new Chapter();
	c1.setTitle("Chapter 1");
	
	Chapter c2 = new Chapter();
	c2.setTitle("Chapter 2");
	
	s.getChapters().add(c1);
	s.getChapters().add(c2);
	
	b.getSections().add(s);
}

// Groovy style

def builder = new ObjectGraphBuilder()

def book = builder.book (
	title: "My Book",
	summary: "My Summary") {
		section(title: "Section 1"){
			chapter(title: "Chapter 1")
			chapter(title: "Chapter 2")
			chapter(title: "Chapter 3")
		}		
		section(title: "Section 2"){
			chapter(title: "Chapter 3")
			chapter(title: "Chapter 4")
			chapter(title: "Chapter 5")
		}
	}

println book;





