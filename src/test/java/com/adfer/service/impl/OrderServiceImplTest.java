package com.adfer.service.impl;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.exception.OrderDetailNotFoundException;
import com.adfer.model.Order;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
                .orderHeaderId(orderHeader.getId())
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
    public void shouldThrowExceptionWhenOrderDetailsListIsNull() {
        //given
        OrderHeader orderHeader = OrderHeader.builder().build();

        //when
        orderService.makeOrder(orderHeader, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenOrderDetailsListIsEmpty() {
        //given
        OrderHeader orderHeader = OrderHeader.builder().build();

        //when
        orderService.makeOrder(orderHeader, Collections.emptyList());
    }

    @Test
    public void shouldReturnOrderByOrderHeaderId() {
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
                .orderHeaderId(orderHeader.getId())
                .perfume(perfume)
                .quantity(2)
                .build();

        given(orderHeaderRepository.findOne(1L)).willReturn(orderHeader);
        given(orderDetailRepository.findByOrderHeaderId(orderHeader.getId())).willReturn(Arrays.asList(orderDetail));

        //when
        Optional<Order> order = orderService.getOrderByOrderHeaderId(1L);

        //then
        assertThat(order.isPresent()).isTrue();
    }

    @Test
    public void shouldReturnNothingBecauseNoOrderHeaderWithSelectedId() {
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
                .orderHeaderId(orderHeader.getId())
                .perfume(perfume)
                .quantity(2)
                .build();

        given(orderHeaderRepository.findOne(1L)).willReturn(orderHeader);
        given(orderDetailRepository.findByOrderHeaderId(orderHeader.getId())).willReturn(Arrays.asList(orderDetail));

        //when
        Optional<Order> order = orderService.getOrderByOrderHeaderId(-100L);

        //then
        assertThat(order.isPresent()).isFalse();
    }

    @Test
    public void whenRemovingOrderByOrderIdThenShouldRemoveOrderHeaderAndOrderDetails() {
        //given
        OrderHeader orderHeader = OrderHeader.builder()
                .id(1L)
                .build();
        given(orderHeaderRepository.findOne(eq(1L))).willReturn(orderHeader);

        //when
        orderService.removeOrderByHeaderId(1L);

        //then
        verify(orderHeaderRepository, times(1)).delete(eq(1L));
        verify(orderDetailRepository, times(1)).deleteByOrderHeaderId(eq(1L));

    }

    @Test
    public void shouldRemoveOrderDetailById() {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
                .id(100L)
                .build();
        given(orderDetailRepository.findOne(eq(100L))).willReturn(orderDetail);

        //when
        orderService.removeOrderDetailById(100L);

        //then
        verify(orderDetailRepository, times(1)).delete(100L);

    }

    @Test
    public void shouldUpdateAlreadyExistingOrderOrderDetail() {
        //given
        Perfume perfume1 = Perfume.builder()
                .id(99L)
                .brand("Brand 1")
                .name("Name 1")
                .category(PerfumeCategory.MEN)
                .build();

        Perfume perfume2 = Perfume.builder()
                .id(22L)
                .brand("Brand 1")
                .name("Name 1")
                .category(PerfumeCategory.MEN)
                .build();

        OrderDetail orderDetail = OrderDetail.builder()
                .id(100L)
                .orderHeaderId(1L)
                .perfume(perfume1)
                .quantity(2)
                .build();

        OrderDetail changedOrderDetail = OrderDetail.builder()
                .id(100L)
                .orderHeaderId(1L)
                .perfume(perfume2)
                .quantity(30)
                .build();

        given(orderDetailRepository.findOne(100L)).willReturn(orderDetail);
        given(orderDetailRepository.save(eq(orderDetail))).willReturn(changedOrderDetail);

        //when
        OrderDetail persOrderDetail = orderService.updateOrderDetail(orderDetail);

        //then
        assertThat(persOrderDetail).isNotNull();
        assertThat(persOrderDetail.getId()).isEqualTo(100L);
        assertThat(persOrderDetail.getOrderHeaderId()).isEqualTo(1L);
        assertThat(persOrderDetail.getPerfume()).isEqualToComparingFieldByField(perfume2);
        assertThat(persOrderDetail.getQuantity()).isEqualTo(30);

        verify(orderDetailRepository, times(1)).save(orderDetail);

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenOrderDetailHasNoIdThenShouldThrowIllegalArgumentException() {
        //given
        OrderDetail notPersistentOrderDetail = OrderDetail.builder()
                .build();

        //when
        orderService.updateOrderDetail(notPersistentOrderDetail);
    }

    @Test(expected = OrderDetailNotFoundException.class)
    public void whenOrderDetailIsNotPersistentThenShouldThrowNullPointerException() {
        //given
        OrderDetail notPersistentOrderDetail = OrderDetail.builder()
                .id(100L)
                .build();

        given(orderDetailRepository.findOne(eq(100L))).willReturn(null);

        //when
        orderService.updateOrderDetail(notPersistentOrderDetail);
    }

    @Test
    public void shouldReturnListWithTwoOrders() {
        //given
        OrderHeader orderHeader1 = OrderHeader.builder()
                .id(1L)
                .build();

        OrderDetail orderDetail1 = OrderDetail.builder()
                .orderHeaderId(1L)
                .build();

        OrderHeader orderHeader2 = OrderHeader.builder()
                .id(2L)
                .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .orderHeaderId(2L)
                .build();
        OrderDetail orderDetail3 = OrderDetail.builder()
                .orderHeaderId(2L)
                .build();

        given(orderHeaderRepository.findAll()).willReturn(Arrays.asList(orderHeader1, orderHeader2));
        given(orderDetailRepository.findByOrderHeaderId(eq(1L))).willReturn(Arrays.asList(orderDetail1));
        given(orderDetailRepository.findByOrderHeaderId(eq(2L))).willReturn(Arrays.asList(orderDetail2, orderDetail3));

        //when
        List<Order> orders = orderService.getAll();

        //then
        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).getOrderHeader()).isNotNull();
        assertThat(orders.get(0).getOrderHeader().getId()).isEqualTo(1L);
        assertThat(orders.get(0).getOrderDetails().size()).isEqualTo(1);
        assertThat(orders.get(1).getOrderHeader()).isNotNull();
        assertThat(orders.get(1).getOrderHeader().getId()).isEqualTo(2L);
        assertThat(orders.get(1).getOrderDetails().size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnEmptyListWhenNoOrdersInDatabase() {
        //given
        given(orderHeaderRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Order> orders = orderService.getAll();

        //then
        assertThat(orders.size()).isEqualTo(0);
    }

}
