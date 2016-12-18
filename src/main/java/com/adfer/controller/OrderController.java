package com.adfer.controller;

import com.adfer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@Controller
@RequestMapping("admin/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("list")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "order/list";
    }
}
