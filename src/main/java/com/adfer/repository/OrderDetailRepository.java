package com.adfer.repository;

import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by adrianferenc on 11.11.2016.
 */
@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderHeaderId(Long orderHeaderId);

    void deleteByOrderHeaderId(Long orderHeaderId);
}
