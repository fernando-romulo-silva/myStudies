package com.apress.springboot2recipes.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final String email;

    Customer() {
	this(null, null);
    }

    Customer(String name, String email) {
	this.name = name;
	this.email = email;
    }

    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getEmail() {
	return email;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	Customer customer = (Customer) o;
	
	return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
	return "Customer [" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ']';
    }
}
