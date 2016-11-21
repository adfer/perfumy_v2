package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.model.ShoppingCart;
import com.adfer.model.ShoppingCartDetail;
import com.adfer.service.ShoppingCartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCart shoppingCart = new ShoppingCart();

    @Override
    public void add(Perfume perfume, int quantity) {
        notNull(perfume, "Perfume object is required to add to shopping cart");
        notNull(perfume.getId(), "Item ID is required to add to shopping cart");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must not be 0 or negative");

        ShoppingCartDetail detail = ShoppingCartDetail.builder()
                .perfume(perfume)
                .quantity(quantity)
                .build();
        shoppingCart.getShoppingCartDetails().put(perfume.getId(), detail);
    }

    @Override
    public boolean remove(long perfumeId) {
        return shoppingCart.getShoppingCartDetails().remove(perfumeId) != null;
    }

    @Override
    public Optional<Perfume> get(long perfumeId) {
        Optional<ShoppingCartDetail> shoppingCartDetail = Optional.ofNullable(shoppingCart.getShoppingCartDetails().get(perfumeId));
        return shoppingCartDetail.isPresent() ? Optional.ofNullable(shoppingCartDetail.get().getPerfume()) : Optional.empty();
    }

    @Override
    public void clear() {
        shoppingCart.getShoppingCartDetails().clear();
    }

    @Override
    public List<ShoppingCartDetail> getAll() {
        return shoppingCart.getShoppingCartDetails().values().stream().collect(Collectors.toList());
    }
}
