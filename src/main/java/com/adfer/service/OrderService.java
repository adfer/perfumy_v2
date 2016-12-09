package com.adfer.service;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.model.Order;

import java.util.List;

/**
 * Created by adrianferenc on 13.11.2016.
 */
public interface OrderService {
    Order makeOrder(Customer customer);
}
