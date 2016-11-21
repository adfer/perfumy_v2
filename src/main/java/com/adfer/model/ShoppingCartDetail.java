package com.adfer.model;

import com.adfer.entity.Perfume;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by adrianferenc on 09.11.2016.
 */
@Getter
@Setter
@Builder
public class ShoppingCartDetail {

    private Perfume perfume;
    private int quantity;

}
