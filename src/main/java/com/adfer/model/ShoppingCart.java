package com.adfer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public class ShoppingCart {

    private final Map<Long, ShoppingCartDetail> details = new HashMap<>();

    public Map<Long, ShoppingCartDetail> getShoppingCartDetails() {
        return details;
    }
}
