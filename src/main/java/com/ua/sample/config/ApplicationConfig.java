package com.ua.sample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.sample.integration.PositionTransformer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by KIRIL on 14.11.2016.
 */
@Configuration
@ComponentScan(basePackages = {"com.ua.sample"})
@ImportResource("classpath:META-INF/spring/*.xml")
@PropertySource("classpath:META-INF/spring/config.properties")
public class ApplicationConfig {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PositionTransformer positionTransformer(){
        return new PositionTransformer();
    }
}
