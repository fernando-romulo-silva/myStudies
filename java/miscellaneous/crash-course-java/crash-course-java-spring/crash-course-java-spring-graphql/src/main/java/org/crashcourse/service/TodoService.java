package org.crashcourse.service;

import java.util.List;

import org.crashcourse.domain.Todo;
import org.crashcourse.domain.TodoItem;
import org.crashcourse.domain.TodoItemRepository;
import org.crashcourse.domain.TodoRepository;
import org.crashcourse.infra.converter.TodoConverter;
import org.crashcourse.infra.converter.TodoDtoConverter;
import org.crashcourse.infra.converter.TodoItemConverter;
import org.crashcourse.infra.converter.TodoItemDtoConverter;
import org.crashcourse.infra.dto.TodoDto;
import org.crashcourse.infra.dto.TodoItemDto;
import org.crashcourse.infra.error.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoDtoConverter todoDtoConverter;
    
    @Autowired
    private TodoConverter todoConverter;
    
    @Autowired
    private TodoItemDtoConverter todoItemDtoConverter;
    
    @Autowired
    private TodoItemConverter todoItemConverter;
    
    @Autowired
    private TodoRepository todoRepository;
    
    @Autowired
    private TodoItemRepository todoItemRepository;
    
    public List<TodoDto> getAllTodos() {
	return todoRepository.findAll().stream().map(todoDtoConverter::convert).toList();
    }
    
    public TodoDto getById(final Long id) {
	
	final var result = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo item with id '" + id + "'  not found"));
	
	return todoDtoConverter.convert(result);
    }
    
    
    public TodoDto addTodo(TodoDto todoDto) {
    
	Todo todo = todoConverter.convert(todoDto);
        Todo savedTodo = todoRepository.save(todo);

        return todoDtoConverter.convert(savedTodo);
    }

    public List<TodoItemDto> addTodoItem(long todoId, List<TodoItemDto> todoItemDtoList) {
        
	final var todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo with id '" + todoId + "'  not found"));

        return todoItemDtoList.stream().map(todoItemDto -> {
            TodoItem todoItem = todoItemConverter.convert(todoItemDto);
            todoItem.setTodo(todo);
            todoItem = todoItemRepository.save(todoItem);
            return todoItemDtoConverter.convert(todoItem);
        }).toList();
    }

    public TodoItemDto updateTodoItem(TodoItemDto todoItemDto) {
        
	if (!todoItemRepository.existsById(todoItemDto.id())) {
            throw new ResourceNotFoundException("Todo item with id '" + todoItemDto.id() + "'  not found");
        }
        
        todoItemRepository.save(todoItemConverter.convert(todoItemDto));
        return todoItemDto;
    }
}
