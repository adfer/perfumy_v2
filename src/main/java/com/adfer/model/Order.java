package com.adfer.model;

import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by adrianferenc on 25.11.2016.
 */
@Getter
@Setter
public class Order {
    private OrderHeader orderHeader;
    private List<OrderDetail> orderDetails;
}
