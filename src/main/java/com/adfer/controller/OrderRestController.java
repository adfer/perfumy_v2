package com.adfer.controller;

import com.adfer.entity.OrderDetail;
import com.adfer.model.Order;
import com.adfer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@RestController
@RequestMapping("rest/order")
public class OrderRestController {

    private OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping
    public List<Order> getOrders() {
        return orderService.getAll();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public void updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        orderService.updateOrderDetail(orderDetail);
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.DELETE)
    public void removeOrderDetail(@PathVariable("id") Long orderDetailId){
        orderService.removeOrderDetailById(orderDetailId);
    }
}
