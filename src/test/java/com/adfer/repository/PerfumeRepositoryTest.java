package com.adfer.repository;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PerfumeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PerfumeRepository perfumeRepository;

    private Perfume perfume;

    @Before
    public void setUp() throws Exception {
        perfume = Perfume.builder()
            .brand("Brand name")
            .name("Perfume name")
            .category(PerfumeCategory.UNKNOWN)
            .build();
    }

    @Test
    public void shouldPersistPerfume(){
        //when
        perfumeRepository.save(perfume);

        //then
        Perfume persPerfume = entityManager.find(Perfume.class, perfume.getId());
        assertThat(persPerfume).isNotNull();
        assertThat(persPerfume.getId()).isEqualTo(perfume.getId());
        assertThat(persPerfume.getBrand()).isEqualToIgnoringCase("Brand name");
        assertThat(persPerfume.getName()).isEqualTo("Perfume name");
        assertThat(persPerfume.getCategory()).isEqualTo(PerfumeCategory.UNKNOWN);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionMissingPerfumeName(){
        //given
        Perfume perfume = Perfume.builder()
            .brand("Brand name")
            .category(PerfumeCategory.WOMEN)
            .build();

        //when
        perfumeRepository.save(perfume);
    }

    @Test
    public void shouldReturnPerfumeById() {
        //given
        Long perfumeId = entityManager.persistAndGetId(perfume, Long.class);

        //when
        Perfume persPerfume = perfumeRepository.findOne(perfumeId);

        //then
        assertThat(persPerfume).isNotNull();
        assertThat(persPerfume.getId()).isEqualTo(perfumeId);
        assertThat(persPerfume.getBrand()).isEqualTo("Brand name");
        assertThat(persPerfume.getName()).isEqualTo("Perfume name");
    }

    @Test
    public void shouldReturnNullNoPerfumeWithGivenId(){
        //given
        Long perfumeId = entityManager.persistAndGetId(perfume, Long.class);

        //when
        Perfume persPerfume = perfumeRepository.findOne(perfumeId + 100);

        //then
        assertThat(persPerfume).isNull();
    }

    @Test
    public void shouldReturnAllPerfumesByCategory(){
        //given
        Perfume perfume1 = Perfume.builder()
                .brand("Brand 1")
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume2 = Perfume.builder()
                .brand("Brand 2")
                .name("Perfume 2")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume3 = Perfume.builder()
                .brand("Brand 3")
                .name("Perfume 3")
                .category(PerfumeCategory.MEN)
                .build();
        Perfume perfume4 = Perfume.builder()
                .brand("Brand 4")
                .name("Perfume 4")
                .build();
        entityManager.persist(perfume1);
        entityManager.persist(perfume2);
        entityManager.persist(perfume3);
        entityManager.persist(perfume4);

        //when
        List<Perfume> perfumes = perfumeRepository.findByCategory(PerfumeCategory.WOMEN);

        //then
        assertThat(perfumes.size()).isEqualTo(2);
        assertThat(perfumes.get(0).getCategory()).isEqualTo(PerfumeCategory.WOMEN);
        assertThat(perfumes.get(1).getCategory()).isEqualTo(PerfumeCategory.WOMEN);
    }

    @Test
    public void shouldReturnEmptyListNoPerfumeWithGivenCategory(){
        //given
        Perfume perfume1 = Perfume.builder()
                .brand("Brand 1")
                .name("Perfume 1")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume2 = Perfume.builder()
                .brand("Brand 2")
                .name("Perfume 2")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume3 = Perfume.builder()
                .brand("Brand 3")
                .name("Perfume 3")
                .category(PerfumeCategory.WOMEN)
                .build();
        Perfume perfume4 = Perfume.builder()
                .brand("Brand 4")
                .name("Perfume 4")
                .build();
        entityManager.persist(perfume1);
        entityManager.persist(perfume2);
        entityManager.persist(perfume3);
        entityManager.persist(perfume4);

        //when
        List<Perfume> perfumes = perfumeRepository.findByCategory(PerfumeCategory.MEN);

        //then
        assertThat(perfumes.size()).isEqualTo(0);
    }

}
