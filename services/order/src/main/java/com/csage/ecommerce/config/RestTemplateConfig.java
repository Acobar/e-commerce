package com.csage.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    //just a basic rest template bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
