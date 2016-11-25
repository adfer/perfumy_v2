package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.repository.PerfumeRepository;
import com.adfer.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Service
public class PerfumeServiceImpl implements PerfumeService {

  private final PerfumeRepository perfumeRepository;

  @Autowired
  public PerfumeServiceImpl(PerfumeRepository perfumeRepository) {
    this.perfumeRepository = perfumeRepository;
  }

  @Override
  public Optional<Perfume> getPerfume(long id) {
    return Optional.ofNullable(perfumeRepository.findOne(id));
  }
}
