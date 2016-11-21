package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.service.PerfumeService;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public class PerfumeServiceImpl implements PerfumeService {
    @Override
    public Perfume getPerfume(long id) {
        Perfume perfume = Perfume.builder()
                .id(id)
                .build();
        return perfume;
    }
}
