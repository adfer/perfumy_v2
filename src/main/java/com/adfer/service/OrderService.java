package com.adfer.service;

import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;

import java.util.List;

/**
 * Created by adrianferenc on 13.11.2016.
 */
public interface OrderService {
    boolean makeOrder(OrderHeader orderHeader, List<OrderDetail> orderDetails);
}
