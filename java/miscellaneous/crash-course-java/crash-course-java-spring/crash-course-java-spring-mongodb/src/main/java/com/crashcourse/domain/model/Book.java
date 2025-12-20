package com.crashcourse.domain.model;

import static com.crashcourse.domain.model.BookStatus.RESERVED;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
 
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document(collection = "books")
public class Book {
    
    @Id
    private String id;

    @NotNull
    @DocumentReference
    private Guest guest;
    
    @NotNull
    @DocumentReference
    private Room room;
    
//    @NotNull
    @DocumentReference
    private Payment payment;
    
    @NotNull
    @FutureOrPresent
    private LocalDateTime begin;
    
    @NotNull
    @FutureOrPresent
    private LocalDateTime end;
    
    @NotNull
    private BookStatus status = RESERVED;
    
    Book() {
	super();
    }
    
    private Book(final Builder builder) {
	super();
	this.guest = builder.guest;
	this.begin = builder.begin;
	this.end = builder.end;
	this.room = builder.room;
	this.payment = builder.payment;
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
	    
	} else if (obj instanceof Book other) {
	    result = Objects.equals(this.id, other.id);
	    
	} else {
	    result = false;
	}

	return result;
    }

    @Override
    public String toString() {
	final var builder = new StringBuilder(34);
	builder.append("Book [id=").append(id) //
			.append(", status=").append(status) //
			.append(']');
	
	return builder.toString();
    }
    
    public static final class Builder {

	public Guest guest;
	
	public Room room;

	public Payment payment;

	public LocalDateTime begin;

	public LocalDateTime end;

	public Builder with(final Consumer<Builder> function) {
	    function.accept(this);
	    return this;
	}
	
	@Valid
	public Book build() {
	    return validate(new Book(this));
	}

	private Book validate(final Book book) {
	    
	    final var validator = Validation.buildDefaultValidatorFactory().getValidator();
	    final var violations = validator.validate(book);

	    if (ObjectUtils.isNotEmpty(violations)) {
		throw new ConstraintViolationException(violations);
	    }
	    
	    return book;
	}
    }
    
}
