package com.apress.springbootrecipes.order;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void ordersEventStream() throws Exception {

	when(orderService.findAll()).thenReturn(List.of(new Order("1234", BigDecimal.TEN)));

	final var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))//
		.andExpect(request().asyncStarted()) //
		.andDo(MockMvcResultHandlers.log()) //
		.andReturn(); //

	mockMvc.perform(asyncDispatch(mvcResult)).andDo(MockMvcResultHandlers.log()) //
		.andExpect(status().isOk()) //
		.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM_VALUE)) //
		.andExpect(content().string(Matchers.startsWith("data:{\"id\":\"1234\",\"amount\":10}"))); //

    }
}