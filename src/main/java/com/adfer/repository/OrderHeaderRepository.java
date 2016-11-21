package com.adfer.repository;

import com.adfer.entity.OrderHeader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by adrianferenc on 11.11.2016.
 */
@Repository
public interface OrderHeaderRepository extends CrudRepository<OrderHeader, Long> {
}
