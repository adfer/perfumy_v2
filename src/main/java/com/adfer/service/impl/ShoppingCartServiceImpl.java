package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.model.ShoppingCart;
import com.adfer.model.ShoppingCartDetail;
import com.adfer.service.ShoppingCartService;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCart shoppingCart = new ShoppingCart();

    @Override
    public void add(Perfume perfume, int quantity) {
        Preconditions.checkNotNull(perfume, "Perfume object is required to add to shopping cart");
        Preconditions.checkNotNull(perfume.getId(), "Item ID is required to add to shopping cart");
        Preconditions.checkArgument(quantity > 0, "Quantity must be greater then 0");

        ShoppingCartDetail detail = shoppingCart.getShoppingCartDetails().get(perfume.getId());
        if (detail == null) {
            detail = ShoppingCartDetail.builder()
                    .perfume(perfume)
                    .build();
        }
        detail.setQuantity(detail.getQuantity() + quantity);
        shoppingCart.getShoppingCartDetails().put(perfume.getId(), detail);
    }

    @Override
    public boolean removeByPerfumeId(long perfumeId) {
        return shoppingCart.getShoppingCartDetails().remove(perfumeId) != null;
    }

    @Override
    public Optional<ShoppingCartDetail> getByPerfumeId(long perfumeId) {
        return Optional.ofNullable(shoppingCart.getShoppingCartDetails().get(perfumeId));
    }

    @Override
    public void clear() {
        shoppingCart.getShoppingCartDetails().clear();
    }

    @Override
    public List<ShoppingCartDetail> getAllDetails() {
        return shoppingCart.getShoppingCartDetails().values().stream().collect(Collectors.toList());
    }
}
