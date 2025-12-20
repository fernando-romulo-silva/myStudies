package org.crashcourse.domain.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "RECEIPT")
public class Receipt {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    @JsonProperty
    private Long id;

    @Column(name = "RECEIPT_CODE", nullable = false, unique = true)
    @JsonProperty
    private String code;
    
    @Column(name = "PERSON_ID", nullable = false)
    @JsonProperty
    private String personId;

    @Column(name = "RECEIPT_VALUE", nullable = false)
    @JsonProperty
    private BigDecimal value;

    @Version
    private int version;

    // ---------------------------------------
    
    Receipt() {
	super();
    }
    
    public Receipt(final String code, final String cpf, final BigDecimal value) {
	super();
	this.code = code;
	this.personId = cpf;
	this.value = value;
    }

    // ---------------------------------------
    
    public Long getId() {
	return id;
    }

    // ---------------------------------------

    @Override
    public int hashCode() {
	return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object obj) {

	final boolean result;

	if (this == obj) {
	    result = true;

	} else if (obj instanceof Receipt other) {
	    result = Objects.equals(this.id, other.id);

	} else {
	    result = false;
	}

	return result;
    }

    @Override
    public String toString() {
	final var builder = new StringBuilder(23);
	builder.append("BankSlip [id=").append(id)
			.append(", personId=").append(personId)
			.append(", value=").append(value)
			.append(", code=").append(code)
			.append(']');
	return builder.toString();
    }


}
