package com.adfer.service.impl;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.model.Order;
import com.adfer.repository.OrderDetailRepository;
import com.adfer.repository.OrderHeaderRepository;
import com.adfer.service.OrderService;
import com.adfer.service.ShoppingCartService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by adrianferenc on 13.11.2016.
 */
public class OrderServiceImpl implements OrderService {
  private final OrderHeaderRepository orderHeaderRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final ShoppingCartService shoppingCartService;

  @Autowired
  public OrderServiceImpl(OrderHeaderRepository orderHeaderRepository, OrderDetailRepository orderDetailRepository, ShoppingCartService shoppingCartService) {
    this.orderHeaderRepository = orderHeaderRepository;
    this.orderDetailRepository = orderDetailRepository;
    this.shoppingCartService = shoppingCartService;
  }

  @Override
  public Order makeOrder(Customer customer) {
    //TODO add transaction, add checks if saved correctly
    Preconditions.checkNotNull(customer, "Customer must not by null");
    Preconditions.checkArgument(shoppingCartService.getAll().size() > 0, "At least one item in shopping cart is required to make order");

    OrderHeader orderHeader = orderHeaderRepository.save(OrderHeader.builder()
        .customer(customer)
        .build());

    List<OrderDetail> orderDetails = new ArrayList<>(shoppingCartService.getAll().size());

    orderDetailRepository.save(shoppingCartService.getAll().stream()
        .map(detail -> OrderDetail.builder()
            .header(orderHeader)
            .perfume(detail.getPerfume())
            .quantity(detail.getQuantity())
            .build())
        .collect(Collectors.toList()))
        .forEach(detail -> orderDetails.add(detail));

    return new Order(orderHeader, orderDetails);
  }
}
