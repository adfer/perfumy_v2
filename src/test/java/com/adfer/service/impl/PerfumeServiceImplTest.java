package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.repository.PerfumeRepository;
import com.adfer.service.PerfumeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    public void shouldReturnOnePerfumeForGivenId(){
        //given
        Perfume perfume = Perfume.builder()
            .id(1L)
            .build();
        given(perfumeRepository.findOne(1L)).willReturn(perfume);

        //when
        Perfume persPerfume = perfumeService.getPerfumeById(1L).get();

        //then
        assertThat(persPerfume ).isNotNull();
        assertThat(persPerfume .getId()).isEqualTo(1L);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenNoPerfumeWithGivenIdThen(){
        //when
        Optional<Perfume> perfume = perfumeService.getPerfumeById(-100);

        //then
        assertThat(perfume.isPresent()).isFalse();
    }

    @Test
    public void shouldReturnTwoPerfumesOfCategoryWomen(){
        //given
        Perfume perfume1 = Perfume.builder()
                .id(1L)
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume2 = Perfume.builder()
                .id(2L)
                .name("Perfume 2")
                .category(PerfumeCategory.WOMEN)
                .build();

        given(perfumeRepository.findByCategory(eq(PerfumeCategory.WOMEN))).willReturn(Arrays.asList(perfume1, perfume2));

        //when
        List<Perfume> perfumes = perfumeService.getPerfumesByCategory(PerfumeCategory.WOMEN);

        //then
        assertThat(perfumes.size()).isEqualTo(2);
        assertThat(perfumes.get(0).getCategory()).isEqualByComparingTo(PerfumeCategory.WOMEN);
        assertThat(perfumes.get(1).getCategory()).isEqualByComparingTo(PerfumeCategory.WOMEN);
    }

    @Test
    public void shouldReturnEmptyListWhenNoPerfumesOfGivenCategory(){
        //given
        given(perfumeRepository.findByCategory(eq(PerfumeCategory.WOMEN))).willReturn(Collections.emptyList());

        //when
        List<Perfume> perfumes = perfumeService.getPerfumesByCategory(PerfumeCategory.WOMEN);

        //then
        assertThat(perfumes.size()).isEqualTo(0);
    }

    @Test
    public void shouldAddOnePerfumeToDatabase(){
        //given
        Perfume perfume = Perfume.builder()
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();

        Perfume persPerfume = Perfume.builder()
                .id(1L)
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();

        given(perfumeRepository.save(eq(perfume))).willReturn(persPerfume);

        //when
        Perfume addedPerfume = perfumeService.add(perfume);

        //then
        assertThat(addedPerfume).isNotNull();
        assertThat(addedPerfume.getId()).isEqualTo(1L);
        verify(perfumeRepository, times(1)).save(any(Perfume.class));
    }

    @Test
    public void shouldAddThreePerfumesToDatabase(){
        //given
        Perfume perfume1 = Perfume.builder()
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume2 = Perfume.builder()
                .name("Perfume 2")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume3 = Perfume.builder()
                .name("Perfume 3")
                .category(PerfumeCategory.WOMEN)
                .build();

        List<Perfume> perfumesToAdd = Arrays.asList(perfume1, perfume2, perfume3);

        Perfume persPerfume1 = Perfume.builder()
                .id(1L)
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume persPerfume2 = Perfume.builder()
                .id(2L)
                .name("Perfume 2")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume persPerfume3 = Perfume.builder()
                .id(3L)
                .name("Perfume 3")
                .category(PerfumeCategory.WOMEN)
                .build();



        given(perfumeRepository.save(perfumesToAdd)).willReturn(Arrays.asList(persPerfume1, persPerfume2, persPerfume3));

        //when
        List<Perfume> persPerfumes = perfumeService.add(perfumesToAdd);

        //then
        assertThat(persPerfumes.size()).isEqualTo(3);
        assertThat(persPerfumes.get(0).getId()).isEqualTo(1L);
        assertThat(persPerfumes.get(1).getId()).isEqualTo(2L);
        assertThat(persPerfumes.get(2).getId()).isEqualTo(3L);
        verify(perfumeRepository, times(1)).save(anyCollectionOf(Perfume.class));
    }

    @Test
    public void shouldRemoveOnePerfumeById(){
        //when
        perfumeService.remove(1L);

        //then
        verify(perfumeRepository, times(1)).delete(eq(1L));
    }

    @Test
    public void shouldRemoveOnePerfume(){
        //given
        Perfume perfume = Perfume.builder()
                .id(1L)
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();

        //when
        perfumeService.remove(perfume);

        //then
        verify(perfumeRepository, times(1)).delete(eq(perfume));
    }

}
