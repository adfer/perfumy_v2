package com.adfer.service;

import com.adfer.entity.Perfume;
import com.adfer.model.ShoppingCartDetail;

import java.util.List;
import java.util.Optional;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public interface ShoppingCartService {

    void add(Perfume perfume, int quantity);

    boolean remove(long perfumeId);

    Optional<Perfume> get(long perfumeId);

    void clear();

    List<ShoppingCartDetail> getAll();

}
