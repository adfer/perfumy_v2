package com.adfer.controller;

import com.adfer.model.ShoppingCartDetail;
import com.adfer.service.ShoppingCartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ShoppingCartController.class)
public class ShoppingCartControllerTest {

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAddShoppingCartDetailsToModelAndRedirectToShoppingCartListView() throws Exception {
        //given
        ShoppingCartDetail detail1 = ShoppingCartDetail.builder().build();
        ShoppingCartDetail detail2 = ShoppingCartDetail.builder().build();

        given(shoppingCartService.getAllDetails()).willReturn(Arrays.asList(detail1, detail2));

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/list"));

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("details", hasSize(2)))
                .andExpect(model().attribute("details", hasItems(detail1, detail2)))
                .andExpect(view().name("shoppingCart/list"));

        verify(shoppingCartService, times(1)).getAllDetails();
        verifyNoMoreInteractions(shoppingCartService);
    }

    @Test
    public void whenNothingInShoppingCartThenShouldAddEmptyDetailsListToModelAndRedirectToShoppingCartListView() throws Exception {
        //given
        given(shoppingCartService.getAllDetails()).willReturn(Collections.emptyList());

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/list"));

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("details", hasSize(0)))
                .andExpect(view().name("shoppingCart/list"));

        verify(shoppingCartService, times(1)).getAllDetails();
        verifyNoMoreInteractions(shoppingCartService);
    }

}