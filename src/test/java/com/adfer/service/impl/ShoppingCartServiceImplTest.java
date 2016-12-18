package com.adfer.service.impl;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.model.ShoppingCartDetail;
import com.adfer.service.ShoppingCartService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

;

/**
 * Created by adrianferenc on 08.11.2016.
 */
public class ShoppingCartServiceImplTest {

    private ShoppingCartService shoppingCartService;

    @Before
    public void setUp() throws Exception {
        shoppingCartService = new ShoppingCartServiceImpl();
    }

    @Test
    public void shouldReturnTwoPerfumesFromShoppingCart() {
        //given
        Perfume perfume1 = Perfume.builder()
                .id(1L)
                .brand("Brand 1")
                .name("Perfume 1")
                .build();
        Perfume perfume2 = Perfume.builder()
                .id(2L)
                .brand("Brand 2")
                .name("Perfume 2")
                .build();

        shoppingCartService.add(perfume1, 15);
        shoppingCartService.add(perfume2, 15);

        //when
        List<ShoppingCartDetail> perfumes = shoppingCartService.getAllDetails();

        //then
        assertThat(perfumes.size()).isEqualTo(2);
        assertThat(perfumes.get(0).getPerfume().getId()).isEqualTo(1L);
        assertThat(perfumes.get(0).getPerfume().getBrand()).isEqualTo("Brand 1");
        assertThat(perfumes.get(0).getPerfume().getName()).isEqualTo("Perfume 1");
        assertThat(perfumes.get(1).getPerfume().getId()).isEqualTo(2L);
        assertThat(perfumes.get(1).getPerfume().getBrand()).isEqualTo("Brand 2");
        assertThat(perfumes.get(1).getPerfume().getName()).isEqualTo("Perfume 2");
    }

    @Test
    public void shouldRemoveOnePerfumeFromShoppingCart() {
        //given
        Perfume perfume1 = Perfume.builder()
                .id(1L)
                .brand("Brand 1")
                .name("Perfume 1")
                .build();
        Perfume perfume2 = Perfume.builder()
                .id(2L)
                .brand("Brand 2")
                .name("Perfume 2")
                .build();

        shoppingCartService.add(perfume1, 15);
        shoppingCartService.add(perfume2, 15);

        //when
        boolean isRemoved = shoppingCartService.removeByPerfumeId(1L);
        List<ShoppingCartDetail> allPerfumes = shoppingCartService.getAllDetails();

        //then
        assertThat(isRemoved).isTrue();
        assertThat(allPerfumes.size()).isEqualTo(1);
        assertThat(allPerfumes.get(0).getPerfume()).isNotNull();
        assertThat(allPerfumes.get(0).getPerfume().getId()).isEqualTo(2L);
        assertThat(allPerfumes.get(0).getPerfume().getName()).isEqualTo("Perfume 2");
    }

    @Test
    public void shouldRemoveAllPerfumesFromShoppingCart() {
        //given
        Perfume perfume1 = Perfume.builder()
                .id(1L)
                .brand("Brand 1")
                .name("Perfume 1")
                .build();
        Perfume perfume2 = Perfume.builder()
                .id(2L)
                .brand("Brand 2")
                .name("Perfume 2")
                .build();
        Perfume perfume3 = Perfume.builder()
                .id(3L)
                .brand("Brand 3")
                .name("Perfume 3")
                .build();

        shoppingCartService.add(perfume1, 15);
        shoppingCartService.add(perfume2, 15);
        shoppingCartService.add(perfume3, 15);

        //when
        shoppingCartService.clear();

        //then
        assertThat(shoppingCartService.getAllDetails().size()).isEqualTo(0);
    }

    @Test
    public void shouldAddTwoPiecesOfPerfumeToShoppingCart() {
        //given
        Perfume perfume = Perfume.builder()
                .id(1L)
                .brand("Brand 1")
                .name("Perfume 1")
                .category(PerfumeCategory.MEN)
                .build();

        //when
        shoppingCartService.add(perfume, 2);

        //then
        List<ShoppingCartDetail> allPerfumes = shoppingCartService.getAllDetails();

        assertThat(allPerfumes.size()).isEqualTo(1);
        assertThat(allPerfumes.get(0).getPerfume()).isNotNull();
        assertThat(allPerfumes.get(0).getPerfume().getId()).isEqualTo(1L);
        assertThat(allPerfumes.get(0).getPerfume().getBrand()).isEqualTo("Brand 1");
        assertThat(allPerfumes.get(0).getPerfume().getName()).isEqualTo("Perfume 1");
        assertThat(allPerfumes.get(0).getPerfume().getCategory()).isEqualTo(PerfumeCategory.MEN);
        assertThat(allPerfumes.get(0).getQuantity()).isEqualTo(2);
    }

    @Test(expected = NullPointerException.class)
    public void addToShoppingCartShouldThrowExceptionMissingPerfumeId() {
        //given
        Perfume perfume = Perfume.builder()
                .brand("Brand 1")
                .name("Perfume 1")
                .build();

        //when
        shoppingCartService.add(perfume, 1);
    }

    @Test(expected = NullPointerException.class)
    public void addToShoppingCartShouldThrowExceptionWhenPerfumeIsNull() {
        //given
        Perfume perfume = null;

        //when
        shoppingCartService.add(perfume, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addToShoppingCartShouldThrowExceptionWhenQuantityIsZero() {
        //given
        Perfume perfume = Perfume.builder()
            .id(1L)
            .build();

        //when
        shoppingCartService.add(perfume, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addToShoppingCartShouldThrowExceptionWhenQuantityIsLessThenZero() {
        //given
        Perfume perfume = Perfume.builder()
            .id(1L)
            .build();

        //when
        shoppingCartService.add(perfume, -10);
    }

    @Test
    public void shouldGetOnePerfumeById() {
        //given
        Perfume perfume1 = Perfume.builder()
                .id(1L)
                .brand("Brand 1")
                .name("Perfume 1")
                .build();
        Perfume perfume2 = Perfume.builder()
                .id(2L)
                .brand("Brand 2")
                .name("Perfume 2")
                .build();

        shoppingCartService.add(perfume1, 1);
        shoppingCartService.add(perfume2, 1);

        //when
        ShoppingCartDetail shoppingCartDetail = shoppingCartService.getByPerfumeId(2L).get();

        //then
        assertThat(shoppingCartDetail).isNotNull();
        assertThat(shoppingCartDetail.getPerfume()).isNotNull();
        assertThat(shoppingCartDetail.getPerfume().getId()).isEqualTo(2L);
        assertThat(shoppingCartDetail.getPerfume().getName()).isEqualTo("Perfume 2");
        assertThat(shoppingCartDetail.getPerfume().getBrand()).isEqualTo("Brand 2");
    }

    @Test
    public void shouldReturnNullMissingPerfumeWithGivenIdInShoppingCart() {
        //given
        Perfume perfume1 = Perfume.builder()
                .id(1L)
                .brand("Brand 1")
                .name("Perfume 1")
                .build();

        shoppingCartService.add(perfume1, 1);

        //when
        Optional<ShoppingCartDetail> shoppingCartDetail = shoppingCartService.getByPerfumeId(-200L);

        //then
        assertThat(shoppingCartDetail.isPresent()).isFalse();
    }

    @Test
    public void shouldReturnEmptyListNoPerfumeInShoppingCart(){
        //when
        List<ShoppingCartDetail> perfumes = shoppingCartService.getAllDetails();

        //then
        assertThat(perfumes).isEmpty();
    }

    @Test
    public void whenAddingPerfumeAlreadyExistingThenShouldIncreaseQuantity(){
        //given
        Perfume perfume1 = Perfume.builder()
                .id(1L)
                .brand("Brand 1")
                .name("Perfume 1")
                .build();

        shoppingCartService.add(perfume1, 1);

        //when
        shoppingCartService.add(perfume1, 2);

        //then
        assertThat(shoppingCartService.getAllDetails().size()).isEqualTo(1);
        assertThat(shoppingCartService.getByPerfumeId(1L).isPresent()).isTrue();
        assertThat(shoppingCartService.getByPerfumeId(1L).get().getPerfume().getId()).isEqualTo(1L);
        assertThat(shoppingCartService.getByPerfumeId(1L).get().getQuantity()).isEqualTo(3);
    }

}
