package com.adfer.controller;

import com.adfer.PerfumyApplication;
import com.adfer.entity.Customer;
import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.exception.OrderDetailNotFoundException;
import com.adfer.model.Order;
import com.adfer.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PerfumyApplication.class)
@WebAppConfiguration
public class OrderRestControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnTwoOrders() throws Exception {
        //given
        Order order1 = createOrder(1L);
        Order order2 = createOrder(2L);

        given(orderService.getAll()).willReturn(Arrays.asList(order1, order2));

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/order"));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].orderHeader.id", is(1)))

                .andExpect(jsonPath("$[0].orderHeader.customer.firstName", is("First name")))
                .andExpect(jsonPath("$[0].orderHeader.customer.lastName", is("Last name")))

                .andExpect(jsonPath("$[0].orderDetails", hasSize(2)))

                .andExpect(jsonPath("$[0].orderDetails[0].id", is(101)))
                .andExpect(jsonPath("$[0].orderDetails[0].orderHeaderId", is(1)))
                .andExpect(jsonPath("$[0].orderDetails[0].perfume.id", is(50)))
                .andExpect(jsonPath("$[0].orderDetails[0].perfume.name", is("Perfume name")))
                .andExpect(jsonPath("$[0].orderDetails[0].perfume.brand", is("Brand name")))
                .andExpect(jsonPath("$[0].orderDetails[0].perfume.category", is(PerfumeCategory.WOMEN.name())))
                .andExpect(jsonPath("$[0].orderDetails[0].quantity", is(1)))

                .andExpect(jsonPath("$[0].orderDetails[1].id", is(201)))
                .andExpect(jsonPath("$[0].orderDetails[1].orderHeaderId", is(1)))
                .andExpect(jsonPath("$[0].orderDetails[1].perfume.id", is(50)))
                .andExpect(jsonPath("$[0].orderDetails[1].perfume.name", is("Perfume name")))
                .andExpect(jsonPath("$[0].orderDetails[1].perfume.brand", is("Brand name")))
                .andExpect(jsonPath("$[0].orderDetails[1].perfume.category", is(PerfumeCategory.WOMEN.name())))
                .andExpect(jsonPath("$[0].orderDetails[1].quantity", is(2)))

                .andExpect(jsonPath("$[1].orderHeader.id", is(2)))

                .andExpect(jsonPath("$[1].orderHeader.customer.firstName", is("First name")))
                .andExpect(jsonPath("$[1].orderHeader.customer.lastName", is("Last name")))

                .andExpect(jsonPath("$[1].orderDetails", hasSize(2)))

                .andExpect(jsonPath("$[1].orderDetails[0].id", is(102)))
                .andExpect(jsonPath("$[1].orderDetails[0].orderHeaderId", is(2)))
                .andExpect(jsonPath("$[1].orderDetails[0].perfume.id", is(50)))
                .andExpect(jsonPath("$[1].orderDetails[0].perfume.name", is("Perfume name")))
                .andExpect(jsonPath("$[1].orderDetails[0].perfume.brand", is("Brand name")))
                .andExpect(jsonPath("$[1].orderDetails[0].perfume.category", is(PerfumeCategory.WOMEN.name())))
                .andExpect(jsonPath("$[1].orderDetails[0].quantity", is(1)))

                .andExpect(jsonPath("$[1].orderDetails[1].id", is(202)))
                .andExpect(jsonPath("$[1].orderDetails[1].orderHeaderId", is(2)))
                .andExpect(jsonPath("$[1].orderDetails[1].perfume.id", is(50)))
                .andExpect(jsonPath("$[1].orderDetails[1].perfume.name", is("Perfume name")))
                .andExpect(jsonPath("$[1].orderDetails[1].perfume.brand", is("Brand name")))
                .andExpect(jsonPath("$[1].orderDetails[1].perfume.category", is(PerfumeCategory.WOMEN.name())))
                .andExpect(jsonPath("$[1].orderDetails[1].quantity", is(2)));
    }

    @Test
    public void shouldReturnEmptyList() throws Exception {
        //given
        given(orderService.getAll()).willReturn(Collections.emptyList());

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/order"));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldUpdateOrderDetail() throws Exception {
        //given
        Perfume perfume = Perfume.builder()
                .id(50L)
                .name("Name")
                .brand("Brand")
                .category(PerfumeCategory.MEN)
                .build();

        OrderDetail orderDetail = OrderDetail.builder()
                .id(100L)
                .perfume(perfume)
                .quantity(1)
                .build();

        given(orderService.updateOrderDetail(any(OrderDetail.class))).willReturn(orderDetail);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/rest/order/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(orderDetail)));

        //then
        result.andExpect(status().isOk());

        verify(orderService, times(1)).updateOrderDetail(any(OrderDetail.class));
        verifyNoMoreInteractions(orderService);

    }

    @Test
    public void shouldReturnNotFoundWhenUpdateOrderDetailWhichNotExist() throws Exception {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
                .id(-100L)
                .build();

        given(orderService.updateOrderDetail(any(OrderDetail.class))).willThrow(OrderDetailNotFoundException.class);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/rest/order/detail")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(orderDetail)));

        //then
        result.andExpect(status().isNotFound());

        verify(orderService, times(1)).updateOrderDetail(any(OrderDetail.class));
        verifyNoMoreInteractions(orderService);

    }

    @Test
    public void shouldReturnOkStatusWhenRemovingOrderDetail() throws Exception {
        //given
        doNothing().when(orderService).removeOrderDetailById(eq(100L));

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/order/detail/100"));

        //then
        result.andExpect(status().isOk());

        verify(orderService, times(1)).removeOrderDetailById(any(Long.class));
        verifyNoMoreInteractions(orderService);

    }

    @Test
    public void shouldReturnNotFoundStatusWhenRemovingNotExistingOrderDetail() throws Exception {
        //given
        doThrow(OrderDetailNotFoundException.class).when(orderService).removeOrderDetailById(eq(-100L));

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/order/detail/-100"));

        //then
        result.andExpect(status().isNotFound());

        verify(orderService, times(1)).removeOrderDetailById(any(Long.class));
        verifyNoMoreInteractions(orderService);

    }

    private Order createOrder(long orderHeaderId) {
        Customer customer = Customer.builder()
                .id(900L)
                .firstName("First name")
                .lastName("Last name")
                .build();

        OrderHeader orderHeader = OrderHeader.builder()
                .id(orderHeaderId)
                .customer(customer)
                .build();

        Perfume perfume = Perfume.builder()
                .id(50L)
                .name("Perfume name")
                .brand("Brand name")
                .category(PerfumeCategory.WOMEN)
                .build();


        OrderDetail orderDetail1 = OrderDetail.builder()
                .id(100L + orderHeaderId)
                .orderHeaderId(orderHeaderId)
                .perfume(perfume)
                .quantity(1)
                .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .id(200L + orderHeaderId)
                .orderHeaderId(orderHeaderId)
                .perfume(perfume)
                .quantity(2)
                .build();

        Order order = new Order();
        order.setOrderHeader(orderHeader);
        order.setOrderDetails(Arrays.asList(orderDetail1, orderDetail2));

        return order;
    }

}
