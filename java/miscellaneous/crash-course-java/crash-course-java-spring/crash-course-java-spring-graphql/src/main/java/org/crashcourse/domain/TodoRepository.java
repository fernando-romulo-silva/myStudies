package org.crashcourse.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import graphql.com.google.common.base.Objects;

@Component
public class TodoRepository {

    private final List<Todo> todos = new ArrayList<>();

    public Optional<Todo> findById(final long todoId) {
	return todos.stream()
			.filter(f -> Objects.equal(f.getId(), todoId))
			.findFirst();
    }

    public List<Todo> findAll() {
	return new ArrayList<>(todos);
    }

    public Todo save(final Todo todo) {
	todo.setId((long) todos.size() + 1);
	todos.add(todo);
	return todo;
    }
}
