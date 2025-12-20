package com.crashcourse.domain.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "rooms")
public class Room {

    @Id
    private String id;
    
    @JsonProperty
    private String code;

    @JsonProperty
    private Short floor;

    @JsonProperty
    private RoomType roomType;

    Room() {
	super();
    }

    public Room(final String code, final Short floor, final RoomType roomType) {
	super();
	this.code = code;
	this.floor = floor;
	this.roomType = roomType;
	this.floor = floor;
    }
    
    @Override
    public int hashCode() {
	return Objects.hash(this.code);
    }

    @Override
    public boolean equals(final Object obj) {

	final boolean result;

	if (this == obj) {
	    result = true;
	    
	} else if (obj instanceof Room other) {
	    result = Objects.equals(this.code, other.code);
	    
	} else {
	    result = false;
	}

	return result;
    }

    @Override
    public String toString() {
	final var builder = new StringBuilder(34);
	builder.append("Room [code=").append(code) //
			.append(", roomType=").append(roomType) //
			.append(']');
	
	return builder.toString();
    }

    public String getId() {
	return id;
    }
    
    
}
