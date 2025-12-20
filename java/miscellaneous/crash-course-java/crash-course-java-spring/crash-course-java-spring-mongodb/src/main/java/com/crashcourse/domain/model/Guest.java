package com.crashcourse.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotEmpty;

@Document(collection = "guests")
public class Guest {

    @Id
    private String id;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String contact;
    
    Guest() {
	super();
    }
    
    public Guest(final String name, final String contact) {
	super();
	this.name = name;
	this.contact = contact;
	validate(this);
    }
    
    private void validate(final Guest book) {

	final var validator = Validation.buildDefaultValidatorFactory().getValidator();
	final var violations = validator.validate(book);

	if (ObjectUtils.isNotEmpty(violations)) {
	    throw new ConstraintViolationException(violations);
	}
    }
    
    @Override
    public int hashCode() {
	return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object obj) {

	final boolean result;

	if (this == obj) {
	    result = true;
	    
	} else if (obj instanceof Guest other) {
	    result = Objects.equals(this.id, other.id);
	    
	} else {
	    result = false;
	}

	return result;
    }

    @Override
    public String toString() {
	final var builder = new StringBuilder(34);
	builder.append("Guest [id=").append(id) //
			.append(", contact=").append(contact) //
			.append(", name=").append(name) //
			.append(']');
	
	return builder.toString();
    }

    public String getId() {
	return id;
    }
}
