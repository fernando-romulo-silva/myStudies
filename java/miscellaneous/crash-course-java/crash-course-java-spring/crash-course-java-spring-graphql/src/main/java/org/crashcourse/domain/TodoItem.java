package org.crashcourse.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoItem {

    private Long id;
    
    private String notes;
    
    private LocalDate dueDate;
    
    private boolean markAsComplete;
    
    private LocalDate completionDate;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Todo todo;

    public TodoItem() {
	super();
    }
    
    public TodoItem(
	      final Long id,
	      final String notes, 
	      final LocalDate dueDate, 
	      final boolean markAsComplete, 
	      final LocalDate completionDate, 
	      final LocalDateTime createdAt, 
	      final LocalDateTime updatedAt) {
	
	super();
	this.setId(id);
	this.notes = notes;
	this.dueDate = dueDate;
	this.markAsComplete = markAsComplete;
	this.completionDate = completionDate;
	this.createdAt = createdAt;
	this.updatedAt = updatedAt;
    }

    public Long getId() {
	return id;
    }

    public String getNotes() {
	return notes;
    }

    public LocalDate getDueDate() {
	return dueDate;
    }

    public boolean isMarkAsComplete() {
	return markAsComplete;
    }

    public LocalDate getCompletionDate() {
	return completionDate;
    }

    public LocalDateTime getCreatedAt() {
	return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
	return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
	this.updatedAt = updatedAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
	this.createdAt = createdAt;
    }

    public void setId(Long id) {
	this.id = id;
    }
    
    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

}
