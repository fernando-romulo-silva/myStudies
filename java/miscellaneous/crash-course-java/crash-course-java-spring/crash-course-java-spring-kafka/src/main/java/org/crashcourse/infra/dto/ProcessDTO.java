package org.crashcourse.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProcessDTO (
		
    @JsonProperty
    String bankSlipCode,
    
    @JsonProperty
    String receiptCode) {
}
