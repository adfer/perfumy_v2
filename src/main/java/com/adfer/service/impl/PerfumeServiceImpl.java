package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.repository.PerfumeRepository;
import com.adfer.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
  public Optional<Perfume> getPerfumeById(long id) {
    return Optional.ofNullable(perfumeRepository.findOne(id));
  }

  @Override
  public List<Perfume> getPerfumesByCategory(PerfumeCategory category) {
    return perfumeRepository.findByCategory(category);
  }

  @Override
  public Perfume add(Perfume perfume) {
    return perfumeRepository.save(perfume);
  }

  @Override
  public List<Perfume> add(List<Perfume> perfumes) {
    List<Perfume> persPerfumes = new ArrayList<>(perfumes.size());
    perfumeRepository.save(perfumes).spliterator().forEachRemaining(perfume -> persPerfumes.add(perfume));
    return persPerfumes;
  }

  @Override
  public void remove(long perfumeId) {
    perfumeRepository.delete(perfumeId);
  }

  @Override
  public void remove(Perfume perfume) {
    perfumeRepository.delete(perfume);
  }
}
