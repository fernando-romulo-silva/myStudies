package org.example.webapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Book {

    @Id
    @Column(name = "book_key")
    private String key;

    @Column(name = "book_isbn")
    private String isbn;
    
    @Column(name = "book_name")
    private String name;
    
    @Column(name = "book_cost")
    private String cost;

    public Book() {
	super();
    }
    
    
    public Book(String isbn, String name, String cost) {
	this.key = isbn;
	this.isbn = isbn;
	this.name = name;
	this.cost = cost;
    }

    @Override
    public String toString() {
	return "Book{" + "isbn=" + isbn + ", name=" + name + ", cost=" + cost + '}';
    }

}
