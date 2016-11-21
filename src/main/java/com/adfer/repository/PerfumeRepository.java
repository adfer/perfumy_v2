package com.adfer.repository;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Repository
public interface PerfumeRepository extends CrudRepository<Perfume, Long>{
    List<Perfume> findByCategory(PerfumeCategory category);
}
