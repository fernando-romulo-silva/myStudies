package org.crashcourse.controllers;

import java.util.List;

import org.crashcourse.infra.dto.TodoDto;
import org.crashcourse.infra.dto.TodoItemDto;
import org.crashcourse.service.TodoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;


@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    private final TodoService todoService;
    
    TodoController(final TodoService todoService) {
	super();
	this.todoService = todoService;
    }

    @QueryMapping
    public TodoDto getById(@Argument("todoId") final Long todoId) {
        return todoService.getById(todoId);
    }
    
    @QueryMapping
    public List<TodoDto> getAllTodo() {
        return todoService.getAllTodos();
    }
    
    // -----------------------------------------------------------
    
    @SubscriptionMapping("todoStatusChanged")
    public Flux<TodoDto> todoStatusChanged(@Argument("todoId") Long todoId) {

        // A flux is the publisher of data
	return Flux.fromStream(Stream.generate(() -> {

	    try {
		Thread.sleep(1000);
	    } catch (final InterruptedException ex) {
		throw new RuntimeException(ex);
	    }

	    return todoService.getById(todoId);
	}));

    }     

    
//    createTodo(todo:TodoInput):Todo
    @MutationMapping
    public TodoDto createTodo(@Argument(value = "todo") TodoDto todoDto) {
        return todoService.addTodo(todoDto);
    }

//    addTodoItems(todoId:ID,todoItems:[TodoItemInput]):[TodoItem]
    @MutationMapping
    public List<TodoItemDto> addTodoItems(@Argument("todoId") long todoId, @Argument("todoItems") List<TodoItemDto> todoItems) {
        return todoService.addTodoItem(todoId, todoItems);
    }

//    updateTodoItem(todoItem:TodoItemInput):TodoItem    
    @MutationMapping
    public TodoItemDto updateTodoItem(@Argument("todoItem") TodoItemDto todoItem) {
        return todoService.updateTodoItem(todoItem);
    }
}
