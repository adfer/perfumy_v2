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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


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
    public void should_return_configuration_entity_for_default_configuration_id() {
        //when
        Configuration persConfiguration = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID);

        //then
        assertNotNull(persConfiguration);
        assertThat(persConfiguration.getId()).isEqualTo(1);
    }

    @Test
    public void should_not_return_configuration_entity_because_incorrectId() {
        //when
        Configuration persConfiguration = configurationRepository.findOne(-100);

        //then
        assertNull(persConfiguration);
    }

    @Test
    public void test_system_available() {
        //when
        Configuration persConfiguration = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID);

        //then
        assertThat(persConfiguration.isServiceAvailable()).isTrue();
    }

    @Test
    public void test_system_unavailable() {
        //given
        Configuration configuration = new Configuration(ConfigurationUtils.DEFAULT_CONFIGURATION_ID, false);
        entityManager.merge(configuration);

        //when
        Configuration persConfiguration = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID);

        //then
        assertThat(persConfiguration.isServiceAvailable()).isFalse();
    }

    @Test
    public void should_update_configuration() {
        //given
        boolean isServiceAvailable = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID).isServiceAvailable();
        Configuration updatedConfiguration = new Configuration(ConfigurationUtils.DEFAULT_CONFIGURATION_ID, false);

        //when
        configurationRepository.save(updatedConfiguration);

        //then
        Configuration persConfiguration = configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID);
        assertThat(persConfiguration.getId()).isEqualTo(ConfigurationUtils.DEFAULT_CONFIGURATION_ID);
        assertThat(persConfiguration.isServiceAvailable()).isFalse();
        assertThat(persConfiguration.getId()).isEqualTo(updatedConfiguration.getId());
        assertThat(persConfiguration.isServiceAvailable()).isEqualTo(updatedConfiguration.isServiceAvailable());
        assertThat(persConfiguration.isServiceAvailable()).isNotEqualTo(isServiceAvailable);
    }

}
