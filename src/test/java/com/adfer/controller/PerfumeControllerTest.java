package com.adfer.controller;

import com.adfer.entity.Perfume;
import com.adfer.enums.PerfumeCategory;
import com.adfer.service.PerfumeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by adrianferenc on 18.12.2016.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PerfumeController.class)
public class PerfumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerfumeService perfumeService;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(perfumeService);

    }

    @Test
    public void shouldReturnPerfumesForGivenCategoryAndRedirectToPerfumeListView() throws Exception{
        //given
        Perfume perfume1 = Perfume.builder().build();
        Perfume perfume2 = Perfume.builder().build();

        given(perfumeService.getPerfumesByCategory(eq(PerfumeCategory.WOMEN))).willReturn(Arrays.asList(perfume1, perfume2));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/perfume/women"))
                .andExpect(status().isOk())
                .andExpect(view().name("perfume/list"))
                .andExpect(model().attribute("perfumes", hasSize(2)))
                .andExpect(model().attribute("perfumes", hasItems(perfume1, perfume2)));

        verify(perfumeService, times(1)).getPerfumesByCategory(PerfumeCategory.WOMEN);
        verifyNoMoreInteractions(perfumeService);

    }

    @Test
    public void whenPerfumeCategoryNotExistThenBadRequestAndRedirectToBadRequestPage() throws Exception{
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/perfume/some_unknown_category"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/badRequest"));

        verifyZeroInteractions(perfumeService);

    }

}