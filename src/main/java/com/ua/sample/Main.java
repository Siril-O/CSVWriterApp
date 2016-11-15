package com.ua.sample;

import com.ua.sample.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ApplicationRunner appRunner = context.getBean(ApplicationRunner.class);
        appRunner.main(args);
    }

}
