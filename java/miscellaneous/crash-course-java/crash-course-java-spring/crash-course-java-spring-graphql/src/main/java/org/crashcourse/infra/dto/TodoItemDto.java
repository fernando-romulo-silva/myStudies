package org.crashcourse.infra.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record TodoItemDto (

    Long id,
    
    String notes,

    @JsonFormat(pattern = "dd-MMM-yyyy")
    LocalDate dueDate,
    
    boolean markAsComplete,

    @JsonFormat(pattern = "dd-MMM-yyyy")
    LocalDate completionDate,

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm")
    LocalDateTime createdAt,

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm")
    LocalDateTime updatedAt) {
}
