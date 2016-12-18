package com.adfer.controller;

import com.adfer.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@Controller
@RequestMapping("cart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @RequestMapping("list")
    public String getShoppingCart(Model model) {
        model.addAttribute("details", shoppingCartService.getAllDetails());
        return "shoppingCart/list";
    }
}
