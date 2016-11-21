package com.adfer.repository;

import com.adfer.entity.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Integer> {
}
