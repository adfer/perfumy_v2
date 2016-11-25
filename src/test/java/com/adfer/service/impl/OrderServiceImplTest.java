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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
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
    //given
    Perfume perfume = Perfume.builder()
        .id(99L)
        .brand("Brand 1")
        .name("Name 1")
        .category(PerfumeCategory.MEN)
        .build();

    Customer customer = Customer.builder()
        .id(123L)
        .firstName("First name")
        .lastName("Last name")
        .build();

    OrderHeader orderHeader = OrderHeader.builder()
        .id(1L)
        .customer(customer)
        .build();

    OrderDetail orderDetail = OrderDetail.builder()
        .id(100L)
        .header(orderHeader)
        .perfume(perfume)
        .quantity(2)
        .build();

    given(orderHeaderRepository.save(orderHeader)).willReturn(orderHeader);
    given(orderDetailRepository.save(orderDetail)).willReturn(orderDetail);

    //when
    OrderHeader persOrderHeader = orderService.makeOrder(orderHeader, Arrays.asList(orderDetail));

    //then
    assertThat(persOrderHeader).isNotNull();
    assertThat(persOrderHeader.getId()).isEqualTo(1L);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWhenOrderHeaderIsNull() {
    //given
    OrderDetail orderDetail = OrderDetail.builder().build();

    //when
    orderService.makeOrder(null, Arrays.asList(orderDetail));
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWhenOrderDetailListIsNull() {
    //given
    OrderHeader orderHeader = OrderHeader.builder().build();

    //when
    orderService.makeOrder(orderHeader, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenOrderDetailListIsEmpty() {
    //given
    OrderHeader orderHeader = OrderHeader.builder().build();

    //when
    orderService.makeOrder(orderHeader, Collections.emptyList());
  }

}
