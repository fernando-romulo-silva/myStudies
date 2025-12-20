package org.crashcourse.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Todo {
    
    private Long id;

    private String title;
    
    private boolean markAsComplete;
    
    private LocalDate completionDate;

    private List<TodoItem> items = new ArrayList<>();

    public Todo(final Long id, final String title, final boolean markAsComplete, final LocalDate completionDate, final List<TodoItem> items) {
	
	super();
	this.id = id;
	this.title = title;
	this.markAsComplete = markAsComplete;
	this.completionDate = completionDate;
	this.items = items;
    }

    public Long getId() {
	return id;
    }

    public String getTitle() {
	return title;
    }

    public boolean isMarkAsComplete() {
	return markAsComplete;
    }

    public List<TodoItem> getItems() {
	return items;
    }
    
    public LocalDate getCompletionDate() {
        return completionDate;
    }

    void setId(final Long id) {
	this.id = id;
    }
}
