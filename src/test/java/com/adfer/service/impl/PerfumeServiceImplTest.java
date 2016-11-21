package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.service.PerfumeService;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public class PerfumeServiceImplTest {

    private PerfumeService perfumeService;

    @Before
    public void setUp() throws Exception {
        perfumeService = new PerfumeServiceImpl();
    }

    @Test
    public void shouldReturnOnePerfumeById(){
        //when
        Perfume perfume = perfumeService.getPerfume(1);

        //then
        assertThat(perfume).isNotNull();
        assertThat(perfume.getId()).isEqualTo(1);
    }

}
