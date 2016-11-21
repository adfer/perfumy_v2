package com.adfer.service.impl;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.repository.OrderDetailRepository;
import com.adfer.repository.OrderHeaderRepository;
import com.adfer.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

/**
 * Created by adrianferenc on 11.11.2016.
 */
public class OrderServiceImplTest {

    private OrderService orderService;

    @Mock
    private OrderHeaderRepository orderHeaderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderServiceImpl(orderHeaderRepository, orderDetailRepository);
    }

    @Test
    public void shouldCreateNewOrder() {
        throw new RuntimeException("Popraw ten test!");
//        //given
//        Perfume perfume = Perfume.builder()
//                .id(99L)
//                .brand("Brand 1")
//                .name("Name 1")
//                .category(PerfumeCategory.MEN)
//                .build();
//
//        Customer customer = Customer.builder()
//                .id(123L)
//                .firstName("First name")
//                .lastName("Last name")
//                .build();
//
//        OrderHeader orderHeader = OrderHeader.builder()
//                .id(1L)
//                .customer(customer)
//                .build();
//
//        OrderDetail orderDetail = OrderDetail.builder()
//                .id(100L)
//                .header(orderHeader)
//                .perfume(perfume)
//                .quantity(2)
//                .build();
//
//        given(orderHeaderRepository.save(orderHeader)).willReturn(orderHeader);
//        given(orderDetailRepository.save(orderDetail)).willReturn(orderDetail);
//
//        //when
//        boolean result = orderService.makeOrder(orderHeader, Arrays.asList(orderDetail));
//
//        //then
//        assertTrue(result);
    }
}
