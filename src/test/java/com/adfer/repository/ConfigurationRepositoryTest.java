package com.adfer.repository;

import com.adfer.entity.Configuration;
import com.adfer.utils.ConfigurationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by adrianferenc on 08.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ConfigurationRepositoryTest {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration(ConfigurationUtils.DEFAULT_CONFIGURATION_ID, true);
        entityManager.persist(configuration);
    }

    @Test
    public void shouldReturnConfigurationEntity() {
        //when
        Configuration persConfiguration = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID);

        //then
        assertThat(persConfiguration.getId()).isEqualTo(1);
        assertThat(persConfiguration.isServiceAvailable()).isTrue();
    }

    @Test
    public void shouldUpdateConfiguration() {
        //given
        boolean isServiceAvailable = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID).isServiceAvailable();
        Configuration updatedConfiguration = new Configuration(ConfigurationUtils.DEFAULT_CONFIGURATION_ID, false);
        entityManager.merge(updatedConfiguration);

        //when
        Configuration persConfiguration = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID);

        //then
        assertThat(persConfiguration.getId()).isEqualTo(1);
        assertThat(persConfiguration.isServiceAvailable()).isFalse();
        assertThat(persConfiguration.getId()).isEqualTo(updatedConfiguration.getId());
        assertThat(persConfiguration.isServiceAvailable()).isEqualTo(updatedConfiguration.isServiceAvailable());
        assertThat(persConfiguration.isServiceAvailable()).isNotEqualTo(isServiceAvailable);
    }

}
