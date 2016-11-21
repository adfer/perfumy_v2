package com.adfer.service.impl;

import com.adfer.entity.Configuration;
import com.adfer.repository.ConfigurationRepository;
import com.adfer.service.ConfigurationService;
import com.adfer.utils.ConfigurationUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

/**
 * Created by adrianferenc on 08.11.2016.
 */

public class ConfigurationServiceImplTest {

    private ConfigurationService configurationService;

    @Mock
    private ConfigurationRepository configurationRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        configurationService = new ConfigurationServiceImpl(configurationRepository);
    }

    @Test
    public void checkIsSystemAvailable_shouldBeTrue(){
        //given
        Configuration defaultConfiguration = new Configuration(ConfigurationUtils.DEFAULT_CONFIGURATION_ID, true);
        given(configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID)).willReturn(defaultConfiguration);

        //then
        assertTrue(configurationService.isServiceAvailable());
    }

}
