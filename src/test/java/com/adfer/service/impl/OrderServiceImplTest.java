package com.adfer.service.impl;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.model.Order;
import com.adfer.model.ShoppingCartDetail;
import com.adfer.repository.OrderDetailRepository;
import com.adfer.repository.OrderHeaderRepository;
import com.adfer.service.OrderService;
import com.adfer.service.ShoppingCartService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;

/**
 * Created by adrianferenc on 11.11.2016.
 */
public class OrderServiceImplTest {

  private OrderService orderService;

  @Mock
  private OrderHeaderRepository orderHeaderRepository;

  @Mock
  private OrderDetailRepository orderDetailRepository;

  @Mock
  private ShoppingCartService shoppingCartService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    orderService = new OrderServiceImpl(orderHeaderRepository, orderDetailRepository, shoppingCartService);
  }

  @Test
  public void shouldCreateNewOrderWithTwoOrderDetails() {
    //given
    Customer customer = Customer.builder()
        .id(123L)
        .firstName("First name")
        .lastName("Last name")
        .build();

    OrderHeader orderHeader = OrderHeader.builder()
        .id(1L)
        .customer(customer)
        .build();

    Perfume perfume1 = Perfume.builder()
        .id(98L)
        .brand("Brand 1")
        .name("Name 1")
        .category(PerfumeCategory.MEN)
        .build();

    Perfume perfume2 = Perfume.builder()
        .id(99L)
        .brand("Brand 2")
        .name("Name 2")
        .category(PerfumeCategory.WOMEN)
        .build();

    OrderDetail orderDetail1 = OrderDetail.builder()
        .id(101L)
        .header(orderHeader)
        .perfume(perfume1)
        .quantity(1)
        .build();

    OrderDetail orderDetail2 = OrderDetail.builder()
        .id(102L)
        .header(orderHeader)
        .perfume(perfume2)
        .quantity(2)
        .build();

    List<ShoppingCartDetail> shoppingCartDetails = Arrays.asList(
        ShoppingCartDetail.builder().perfume(perfume1).quantity(1).build(),
        ShoppingCartDetail.builder().perfume(perfume2).quantity(2).build()
    );

    given(shoppingCartService.getAll()).willReturn(shoppingCartDetails);
    given(orderHeaderRepository.save(any(OrderHeader.class))).willReturn(orderHeader);
    given(orderDetailRepository.save(anyListOf(OrderDetail.class))).willReturn(Arrays.asList(orderDetail1, orderDetail2));

    //when
    Order order = orderService.makeOrder(customer);

    //then
    assertThat(order).isNotNull();
    assertThat(order.getOrderHeader()).isNotNull();
    assertThat(order.getOrderHeader().getId()).isEqualTo(1L);
    assertThat(order.getOrderHeader().getCustomer().getFirstName()).isEqualTo("First name");
    assertThat(order.getOrderHeader().getCustomer().getLastName()).isEqualTo("Last name");
    assertThat(order.getOrderDetails().size()).isEqualTo(2);
    assertThat(order.getOrderDetails().get(0).getId()).isEqualTo(101L);
    assertThat(order.getOrderDetails().get(0).getHeader().getId()).isEqualTo(1L);
    assertThat(order.getOrderDetails().get(0).getPerfume().getId()).isEqualTo(98L);
    assertThat(order.getOrderDetails().get(0).getQuantity()).isEqualTo(1);
    assertThat(order.getOrderDetails().get(1).getId()).isEqualTo(102L);
    assertThat(order.getOrderDetails().get(1).getHeader().getId()).isEqualTo(1L);
    assertThat(order.getOrderDetails().get(1).getPerfume().getId()).isEqualTo(99L);
    assertThat(order.getOrderDetails().get(1).getQuantity()).isEqualTo(2);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowExceptionWhenCustomerIsNull() {
    //TODO add custom exception to indicate that customer is null (NoCustomerDataException)
    orderService.makeOrder(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenShoppingCartIsEmpty() {
    //TODO add custom exception to indicate that shopping cart is empty (EmptyShoppingCartException)
    //given
    Customer customer = Customer.builder().build();

    given(shoppingCartService.getAll()).willReturn(Collections.EMPTY_LIST);

    //when
    orderService.makeOrder(customer);
  }

}
