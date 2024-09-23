package com.warehouse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
