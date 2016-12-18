package com.adfer.service;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;

import java.util.List;
import java.util.Optional;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public interface PerfumeService {
    Optional<Perfume> getPerfumeById(long id);

    List<Perfume> getPerfumesByCategory(PerfumeCategory category);

    Perfume add(Perfume perfume);

    List<Perfume> add(List<Perfume> perfumes);

    void remove(long perfumeId);

    void remove(Perfume perfume);
}
