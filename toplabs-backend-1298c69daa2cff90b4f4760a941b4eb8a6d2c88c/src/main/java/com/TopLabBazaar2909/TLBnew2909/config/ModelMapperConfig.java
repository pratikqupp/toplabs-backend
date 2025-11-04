package com.TopLabBazaar2909.TLBnew2909.config;

import com.TopLabBazaar2909.TLBnew2909.dto.BookingDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Booking;
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
