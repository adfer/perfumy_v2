package com.adfer.service;

import com.adfer.entity.Perfume;

import java.util.Optional;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public interface PerfumeService {
    Optional<Perfume> getPerfume(long id);
}
