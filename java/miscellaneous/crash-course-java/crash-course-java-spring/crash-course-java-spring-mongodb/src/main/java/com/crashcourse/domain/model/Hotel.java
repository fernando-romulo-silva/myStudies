package com.crashcourse.domain.model;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;

@Document(collection = "hotels")
public class Hotel {

    @Id
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String city;

    @JsonProperty
    private Short score;

    @DBRef
    @JsonProperty
    private List<Room> rooms;

    Hotel() {
	super();
    }

    private Hotel(final Builder builder) {
	this.name = builder.name;
	this.city = builder.city;
	this.score = builder.score;
	this.rooms = builder.rooms;
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

	} else if (obj instanceof Hotel other) {
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
			.append(", name=").append(name) //
			.append(']');

	return builder.toString();
    }

    public static final class Builder {

	public String name;

	public String city;

	public Short score;

	public List<Room> rooms;

	@Valid
	public Hotel build() {
	    return new Hotel(this);
	}

	public Builder with(final Consumer<Builder> function) {
	    function.accept(this);
	    return this;
	}
    }

    public String getId() {
	return id;
    }
}
