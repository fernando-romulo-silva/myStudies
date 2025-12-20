package org.crashcourse.infra.dto;

import java.util.List;

public record TodoDto(
		Long id, 
		String title, 
		boolean markAsComplete, 
		List<TodoItemDto> items) {
}
