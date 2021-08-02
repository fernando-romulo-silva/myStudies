package com.apress.prospring5.ch12.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "singer")
public class Singer implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    public Long getId() {
	return id;
    }

    public int getVersion() {
	return version;
    }

    public String getFirstName() {
	return firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setVersion(int version) {
	this.version = version;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
    }

    public Date getBirthDate() {
	return birthDate;
    }

    @Override
    public String toString() {
	return "Singer - Id: " + id + ", First name: " + firstName + ", Last name: " + lastName + ", Birthday: " + birthDate;
    }
}
