package org.crashcourse.infra.converter;

import java.util.List;

import org.crashcourse.domain.Todo;
import org.crashcourse.infra.dto.TodoDto;
import org.crashcourse.infra.dto.TodoItemDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class TodoDtoConverter implements Converter<Todo, TodoDto> {

    private final TodoItemDtoConverter todoItemDtoConverter;
    
    TodoDtoConverter(final TodoItemDtoConverter todoItemDtoConverter) {
	super();
	this.todoItemDtoConverter = todoItemDtoConverter;
    }

    @Override
    public TodoDto convert(final Todo source) {
        List<TodoItemDto> todoItems = source.getItems().stream().map(todoItemDtoConverter::convert).toList();
        return new TodoDto(source.getId(), source.getTitle(), source.isMarkAsComplete(), todoItems);
    }
}
