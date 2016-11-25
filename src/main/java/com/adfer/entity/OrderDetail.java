package com.adfer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Created by adrianferenc on 11.11.2016.
 */
@Getter
@Setter
@Builder
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_header_id", nullable = false)
    private OrderHeader header;
    @OneToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    private Perfume perfume;
    @Column(nullable = false)
    @Min(1)
    private Integer quantity;

}
