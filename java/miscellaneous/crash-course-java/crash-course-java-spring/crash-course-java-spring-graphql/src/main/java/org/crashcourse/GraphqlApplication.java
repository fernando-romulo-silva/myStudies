package org.crashcourse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.crashcourse.domain.Todo;
import org.crashcourse.domain.TodoItem;
import org.crashcourse.domain.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphqlApplication implements CommandLineRunner {

    public static void main(String[] args) {
	SpringApplication.run(GraphqlApplication.class, args);
    }

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void run(final String... args) throws Exception {

	// -------------------------------------------
	final var todoItem01 = new TodoItem(1L, "Note 01", LocalDate.now().plusDays(3), false, null, LocalDateTime.now(), null);
	final var todoItem02 = new TodoItem(2L, "Note 02", LocalDate.now().plusDays(4), false, null, LocalDateTime.now(), null);
	final var todoItem03 = new TodoItem(3L, "Note 03", LocalDate.now().plusDays(4), false, null, LocalDateTime.now(), null);

	final List<TodoItem> todoItems01 = new ArrayList<>() {
	    private static final long serialVersionUID = 1L;
	    {
		add(todoItem01);
		add(todoItem02);
		add(todoItem03);
	    }
	};

	final var todo01 = new Todo(1L, "Todo title 1", false, LocalDate.now().plusDays(5), todoItems01);

	todoRepository.save(todo01);

	// -------------------------------------------

	final var todoItem04 = new TodoItem(4L, "Note 01", LocalDate.now().plusDays(3), false, null, LocalDateTime.now(), null);
	final var todoItem05 = new TodoItem(5L, "Note 02", LocalDate.now().plusDays(4), false, null, LocalDateTime.now(), null);
	final var todoItem06 = new TodoItem(6L, "Note 03", LocalDate.now().plusDays(4), false, null, LocalDateTime.now(), null);

	final List<TodoItem> todoItems02 = new ArrayList<>() {

	    private static final long serialVersionUID = 1L;
	    {
		add(todoItem04);
		add(todoItem05);
		add(todoItem06);
	    }
	};

	final var todo02 = new Todo(1L, "Todo title 2", false, LocalDate.now().plusDays(5), todoItems02);

	todoRepository.save(todo02);

    }

}
