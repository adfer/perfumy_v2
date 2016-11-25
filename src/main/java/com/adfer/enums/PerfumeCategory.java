package com.adfer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@AllArgsConstructor
@Getter
public enum PerfumeCategory {
  MEN("Men"), WOMEN("Women"), UNKNOWN("unknown");

  private final String name;
}
