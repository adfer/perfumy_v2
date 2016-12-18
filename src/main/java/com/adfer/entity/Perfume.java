package com.adfer.entity;

import com.adfer.enums.PerfumeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Perfume {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private String name;

  private String brand;
  private PerfumeCategory category;
}
