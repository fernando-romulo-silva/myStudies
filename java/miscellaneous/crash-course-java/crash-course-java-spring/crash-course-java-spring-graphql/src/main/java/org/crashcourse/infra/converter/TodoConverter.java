package org.crashcourse.infra.converter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.crashcourse.domain.Todo;
import org.crashcourse.infra.dto.TodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class TodoConverter implements Converter<TodoDto, Todo> {
    
    @Autowired
    private TodoItemConverter todoItemConverter;
    
    @Override
    public Todo convert(final TodoDto source) {
	
	final var index = new AtomicLong();
	
	final var items = source.items()
			.stream()
			.map(todoItemConverter::convert)
			.peek(i -> {
			    if (Objects.isNull(i.getId())) {
				i.setId(index.incrementAndGet());
			    }
			})
			.toList();
	
        return new Todo(source.id(), source.title(), source.markAsComplete(), source.markAsComplete() ? LocalDate.now() : null, items);
    }
}
