package com.adfer.controller;

import com.adfer.model.Order;
import com.adfer.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAddTwoOrdersToModelAndRedirectToOrderListView() throws Exception {
        //given
        Order order1 = new Order();
        Order order2 = new Order();

        given(orderService.getAll()).willReturn(Arrays.asList(order1, order2));

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/order/list"));

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("orders", hasSize(2)))
                .andExpect(model().attribute("orders", hasItems(order1, order2)))
                .andExpect(view().name("order/list"));

        verify(orderService, times(1)).getAll();
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void whenNoOrderInDatabaseThenShouldAddEmptyListToModelAndRedirectToOrderListView() throws Exception {
        //given
        given(orderService.getAll()).willReturn(Collections.emptyList());

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/order/list"));

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("orders", hasSize(0)))
                .andExpect(view().name("order/list"));

        verify(orderService, times(1)).getAll();
        verifyNoMoreInteractions(orderService);
    }

}