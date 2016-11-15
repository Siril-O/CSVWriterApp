package com.ua.sample.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by KIRIL on 14.11.2016.
 */
@Configuration
@ComponentScan(basePackages = {"com.ua.sample"})
@ImportResource("classpath:META-INF/spring/*.xml")
public class ApplicationConfig {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
