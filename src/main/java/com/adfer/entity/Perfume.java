package com.adfer.entity;

import com.adfer.enums.PerfumeCategory;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Getter
@Builder
@Entity
public class Perfume {

    @Id
    @GeneratedValue
    private Long id;

    private String brand;
    private String name;
    private PerfumeCategory category;

}
