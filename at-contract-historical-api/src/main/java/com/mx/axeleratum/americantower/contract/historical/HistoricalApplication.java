package com.mx.axeleratum.americantower.contract.historical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableEurekaClient
@EnableFeignClients
@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = "com.mx.axeleratum.americantower.contract")
@EnableMongoRepositories(basePackages = "com.mx.axeleratum.americantower.contract")
public class HistoricalApplication {
    public static void main(String[] args) {
        SpringApplication.run(HistoricalApplication.class, args);
    }

}


