package com.warehouse.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

@Configuration
public class MyConfig {
    EanGenerator eanGenerator;

    @Bean
    public BigInteger bigInteger(){
        // 30934809845098456
        return new BigInteger( "353424234234234232");
    }
}
