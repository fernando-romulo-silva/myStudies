package org.crashcourse.infra.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.crashcourse.domain.TodoItem;
import org.crashcourse.infra.dto.TodoItemDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class TodoItemConverter implements Converter<TodoItemDto, TodoItem> {

    @Override
    public TodoItem convert(final TodoItemDto source) {

        final var todoItem = new TodoItem(
        		source.id(), 
        		source.notes(),
        		source.dueDate(),
        		source.markAsComplete(),
        		source.markAsComplete() ? LocalDate.now() : null,
			source.createdAt(), 
			source.updatedAt()
        );
        

        if (Objects.nonNull(source.id())) {
            todoItem.setUpdatedAt(LocalDateTime.now());
        } else {
            todoItem.setCreatedAt(LocalDateTime.now());
        }

        return todoItem;
    }
}
