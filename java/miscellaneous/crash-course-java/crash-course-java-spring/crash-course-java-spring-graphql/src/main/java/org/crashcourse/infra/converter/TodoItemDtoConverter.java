package org.crashcourse.infra.converter;

import org.crashcourse.domain.TodoItem;
import org.crashcourse.infra.dto.TodoItemDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class TodoItemDtoConverter implements Converter<TodoItem, TodoItemDto> {

    @Override
    public TodoItemDto convert(final TodoItem source) {
        return new TodoItemDto(
        		source.getId(), 
        		source.getNotes(), 
        		source.getDueDate(), 
        		source.isMarkAsComplete(), 
        		source.getCompletionDate(), 
        		source.getCreatedAt(), 
        		source.getUpdatedAt()
        );
    }
}
