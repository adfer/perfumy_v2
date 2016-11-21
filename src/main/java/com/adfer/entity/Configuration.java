package com.adfer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Configuration {

    @Id
    private Integer id;
    private boolean serviceAvailable;
}
