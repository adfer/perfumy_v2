package com.adfer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by adrianferenc on 11.11.2016.
 */
@Getter
@Setter
@Builder
@Entity
public class OrderHeader {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


}
