package com.adfer.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Created by adrianferenc on 11.11.2016.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long orderHeaderId;
    @OneToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    private Perfume perfume;
    @Column(nullable = false)
    @Min(1)
    private Integer quantity;

}
