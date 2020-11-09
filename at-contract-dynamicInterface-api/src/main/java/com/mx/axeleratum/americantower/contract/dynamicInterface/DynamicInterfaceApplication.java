package com.mx.axeleratum.americantower.contract.dynamicInterface;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableSpringDataWebSupport
@EnableEurekaClient
@EnableFeignClients
@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = "com.mx.axeleratum.americantower.contract")
@EnableMongoRepositories(basePackages = "com.mx.axeleratum.americantower.contract")
public class DynamicInterfaceApplication {
    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(DynamicInterfaceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


