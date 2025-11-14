package com.toplabs.bazaar.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT) // use strict mapping
                .setSkipNullEnabled(true);

        // ✅ Skip ambiguous mappings
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return modelMapper;
    }
}
