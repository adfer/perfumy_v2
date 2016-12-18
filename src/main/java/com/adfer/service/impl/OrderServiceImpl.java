package com.adfer.service.impl;

import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.exception.OrderDetailNotFoundException;
import com.adfer.exception.OrderHeaderNotFoundException;
import com.adfer.model.Order;
import com.adfer.repository.OrderDetailRepository;
import com.adfer.repository.OrderHeaderRepository;
import com.adfer.service.OrderService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by adrianferenc on 13.11.2016.
 */
@Service
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
        Preconditions.checkArgument(orderDetails.size() > 0, "At least one order detail is required to make order");
        orderHeaderRepository.save(orderHeader);
        orderDetailRepository.save(orderDetails);
        return orderHeader;
    }

    @Override
    public Optional<Order> getOrderByOrderHeaderId(long orderHeaderId) {
        Order order = null;

        OrderHeader orderHeader = orderHeaderRepository.findOne(orderHeaderId);
        if (orderHeader != null) {
            order = new Order();
            order.setOrderHeader(orderHeader);
            order.setOrderDetails(orderDetailRepository.findByOrderHeaderId(orderHeaderId));
        }

        return Optional.ofNullable(order);
    }

    @Override
    public void removeOrderByHeaderId(long orderHeaderId) {
        if (orderHeaderRepository.findOne(orderHeaderId) == null) {
            throw new OrderHeaderNotFoundException(orderHeaderId);
        }
        orderDetailRepository.deleteByOrderHeaderId(orderHeaderId);
        orderHeaderRepository.delete(orderHeaderId);
    }

    @Override
    public void removeOrderDetailById(long orderDetailId) throws OrderDetailNotFoundException {
        if (orderDetailRepository.findOne(orderDetailId) == null) {
            throw new OrderDetailNotFoundException(orderDetailId);
        }
        orderDetailRepository.delete(orderDetailId);
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        Preconditions.checkArgument(orderDetail.getId() != null, "Order detail's ID must not be null!");
        if (orderDetailRepository.findOne(orderDetail.getId()) == null) {
            throw new OrderDetailNotFoundException(orderDetail.getId());
        }

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        orderHeaderRepository.findAll().forEach(header -> {
            Order order = new Order();
            order.setOrderHeader(header);
            order.setOrderDetails(orderDetailRepository.findByOrderHeaderId(header.getId()));
            orders.add(order);
        });
        return orders;
    }
}
