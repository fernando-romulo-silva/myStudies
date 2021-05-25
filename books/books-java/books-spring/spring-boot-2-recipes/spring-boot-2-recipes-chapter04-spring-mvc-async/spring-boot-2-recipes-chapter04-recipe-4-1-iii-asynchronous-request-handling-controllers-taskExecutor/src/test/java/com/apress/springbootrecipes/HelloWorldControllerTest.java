package com.apress.springbootrecipes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(HelloWorldController.class)
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloWorldController() throws Exception {

	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))//
		.andExpect(request().asyncStarted()) //
		.andDo(MockMvcResultHandlers.print()) //
		.andReturn(); //

	mockMvc.perform(asyncDispatch(mvcResult)) //
		.andExpect(status().isOk()) //
		.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)) //
		.andExpect(content().string("Hello World, from Spring Boot 2!")); //
    }
}
