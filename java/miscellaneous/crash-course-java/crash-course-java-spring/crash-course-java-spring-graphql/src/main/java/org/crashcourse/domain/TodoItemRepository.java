package org.crashcourse.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import graphql.com.google.common.base.Objects;

@Component
public class TodoItemRepository {

    
    private final List<TodoItem> todos = new ArrayList<>();

    public Optional<TodoItem> findById(final long todoId) {
	return todos.stream()
			.filter(f -> Objects.equal(f.getId(), todoId))
			.findFirst();
    }

    public boolean existsById(long id) {
	return !findById(id).isEmpty();
    }
    
    public TodoItem save(final TodoItem todo) {
	todo.setId((long) todos.size() + 1);
	todos.add(todo);
	return todo;
    }
}
