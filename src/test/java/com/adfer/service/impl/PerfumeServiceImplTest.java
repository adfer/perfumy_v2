package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.repository.PerfumeRepository;
import com.adfer.service.PerfumeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public class PerfumeServiceImplTest {

    private PerfumeService perfumeService;

    @Mock
    private PerfumeRepository perfumeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        perfumeService = new PerfumeServiceImpl(perfumeRepository);
    }

    @Test
    public void should_return_one_perfume(){
        //given
        Perfume perfume = Perfume.builder()
            .id(1L)
            .build();
        given(perfumeRepository.findOne(1L)).willReturn(perfume);

        //when
        Perfume persPerfume = perfumeService.getPerfume(1L).get();

        //then
        assertThat(persPerfume ).isNotNull();
        assertThat(persPerfume .getId()).isEqualTo(1L);
    }

    @Test
    public void should_not_find_perfume_no_perfume_with_given_id(){
        //when
        Optional<Perfume> perfume = perfumeService.getPerfume(-100);

        //then
        assertThat(perfume.isPresent()).isFalse();
    }



}
