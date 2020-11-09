package com.mx.axeleratum.americantower.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.mx.axeleratum.americantower")
@EnableMongoRepositories(basePackages = "com.mx.axeleratum.americantower")
public class ZuulProxyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyServerApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
