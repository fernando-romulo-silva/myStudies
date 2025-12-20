package org.crashcourse.domain.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "BANK_SLIP")
public class BankSlip {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    @JsonProperty
    private Long id;
    
    @Column(name = "CODE", nullable = false, unique = true)
    @JsonProperty(required = true)
    private String code;
    
    @OneToOne
    @JoinColumn(name = "RECEIPT_ID")
    @JsonProperty
    @JsonUnwrapped
    private Receipt receipt;
    
    @Version
    private int version;
    
    // ---------------------------------------
    
    BankSlip() {
	super();
    }
    
    public BankSlip(final String code, final Receipt receipt) {
	super();
	this.code = code;
	this.receipt = receipt;
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
	    
	} else if (obj instanceof BankSlip other) {
	    result = Objects.equals(this.id, other.id);
	    
	} else {
	    result = false;
	}

	return result;
    }

    @Override
    public String toString() {
	final var builder = new StringBuilder(23);
	builder.append("BankSlip [id=").append(id) //
			.append(", code=").append(code) //
			.append(", nota=").append(receipt) //
			.append(']');
	return builder.toString();
    }

}
