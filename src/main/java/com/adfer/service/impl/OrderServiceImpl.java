package com.adfer.service.impl;

import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.repository.OrderDetailRepository;
import com.adfer.repository.OrderHeaderRepository;
import com.adfer.service.OrderService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by adrianferenc on 13.11.2016.
 */
public class OrderServiceImpl implements OrderService {
    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderServiceImpl(OrderHeaderRepository orderHeaderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderHeader makeOrder(OrderHeader orderHeader, List<OrderDetail> orderDetails) {
        //TODO add transaction, add checks if saved correctly
        Preconditions.checkNotNull(orderHeader, "Order header must not by null");
        Preconditions.checkNotNull(orderDetails, "Order details must not by null");
        Preconditions.checkArgument(orderDetails.size()>0, "At least one order detail is required to make order");
        orderHeaderRepository.save(orderHeader);
        orderDetailRepository.save(orderDetails);
        return orderHeader;
    }
}
