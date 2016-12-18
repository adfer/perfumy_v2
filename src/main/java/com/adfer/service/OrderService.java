package com.adfer.service;

import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Created by adrianferenc on 13.11.2016.
 */
public interface OrderService {
    OrderHeader makeOrder(OrderHeader orderHeader, List<OrderDetail> orderDetails);

    Optional<Order> getOrderByOrderHeaderId(long orderHeaderId);

    void removeOrderByHeaderId(long orderHeaderId);

    void removeOrderDetailById(long orderDetailId);

    OrderDetail updateOrderDetail(OrderDetail orderDetail);

    List<Order> getAll();
}
