package com.apress.springbootrecipes.library.rest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import com.apress.springbootrecipes.library.Book;
import com.apress.springbootrecipes.library.BookService;

@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

//    @Autowired
//    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    
    @LocalServerPort
    private int port;
    
    @Test
    public void shouldAddBook() throws Exception {
	
	when(bookService.create(any(Book.class))).thenReturn(new Book("123456789", "Test Book Stored", "T. Author"));
	
	final var response = given() //
			.auth() //
			.basic("admin@books.io", "secret") //
			.header("Content-Type", "application/javascript") //
			.body("{ \"isbn\" : \"123456789\", \"title\" : \"Test Book\", \"authors\" : [\"T. Author\"] }") //
			.post("http://127.0.0.1:"+port+"/books") //
			.andReturn();
	
	assertThat(response.getStatusCode()).isEqualTo(200);
	
//	mockMvc.perform(post("/books") //
//		.contentType(MediaType.APPLICATION_JSON) //
//		.content()) //
//	.andExpect(status().isCreated()) //
//	.andExpect(header().string("Location", "http://localhost/books/123456789")); //
    }

    @Test
    public void shouldReturnListOfBooks() throws Exception {
	
	final var list = List.of(new Book("123", "Spring 5 Recipes", "Marten Deinum", "Josh Long"), new Book("321", "Pro Spring MVC", "Marten Deinum", "Colin Yates"));

	when(bookService.findAll()).thenReturn(list); //
	
	final var response = given() //
			.auth() //
			.basic("admin@books.io", "secret") //
			.get("http://127.0.0.1:"+port+"/books") //
			.then()
		            .extract().jsonPath().getList(".", Book.class);

	System.out.println(response);
	
//	mockMvc.perform(get("/books"))//
//		.andExpect(status().isOk()) //
//		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))) //
//		.andExpect(MockMvcResultMatchers.jsonPath("$[*].isbn", Matchers.containsInAnyOrder("123", "321"))) //
//		.andExpect(MockMvcResultMatchers.jsonPath("$[*].title", Matchers.containsInAnyOrder("Spring 5 Recipes", "Pro Spring MVC")));
    }

    @Test
    public void shouldReturn404WhenBookNotFound() throws Exception {

	when(bookService.find(anyString())).thenReturn(Optional.empty());

	// mockMvc.perform(get("/books/123")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBookWhenFound() throws Exception {

	when(bookService.find(anyString())).thenReturn(Optional.of(new Book("123", "Spring 5 Recipes", "Marten Deinum", "Josh Long")));

//	mockMvc.perform(get("/books/123"))//
//		.andExpect(status().isOk()) //
//		.andExpect(MockMvcResultMatchers.jsonPath("$.isbn", Matchers.equalTo("123"))) //
//		.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.equalTo("Spring 5 Recipes"))); //
    }

}
