package com.adfer.service.impl;

import com.adfer.entity.Configuration;
import com.adfer.repository.ConfigurationRepository;
import com.adfer.service.ConfigurationService;
import com.adfer.utils.ConfigurationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public boolean isServiceAvailable() {
        Optional<Configuration> configuration = Optional.ofNullable(configurationRepository.findOne(ConfigurationUtils.DEFAULT_CONFIGURATION_ID));
        return configuration.isPresent() ? configuration.get().isServiceAvailable() : false;
    }
}
