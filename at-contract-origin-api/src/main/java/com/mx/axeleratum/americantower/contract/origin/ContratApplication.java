package com.mx.axeleratum.americantower.contract.origin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.mx.axeleratum.americantower.contract")
@EnableMongoRepositories(basePackages = "com.mx.axeleratum.americantower.contract")
public class ContratApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContratApplication.class, args);
    }

}


