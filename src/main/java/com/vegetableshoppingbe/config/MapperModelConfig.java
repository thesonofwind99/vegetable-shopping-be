package com.vegetableshoppingbe.config;

import com.vegetableshoppingbe.dto.request.ProductRequest;
import com.vegetableshoppingbe.entity.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperModelConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        PropertyMap<ProductRequest, Product> productMap = new PropertyMap<>() {
            protected void configure() {
                skip(destination.getProductId());
            }
        };

        mapper.addMappings(productMap);
        return mapper;
    }

}
