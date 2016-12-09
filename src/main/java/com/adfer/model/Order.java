package com.adfer.model;

import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import lombok.Getter;

import java.util.List;

@Getter
public class Order {
  private OrderHeader orderHeader;
  private List<OrderDetail> orderDetails;

  public Order(OrderHeader orderHeader, List<OrderDetail> orderDetails) {
    this.orderHeader = orderHeader;
    this.orderDetails = orderDetails;
  }
}
