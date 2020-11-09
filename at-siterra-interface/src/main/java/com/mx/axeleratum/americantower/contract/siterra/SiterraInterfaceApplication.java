package com.mx.axeleratum.americantower.contract.siterra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.mx.axeleratum.americantower.contract")
public class SiterraInterfaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SiterraInterfaceApplication.class, args);
    }
}
