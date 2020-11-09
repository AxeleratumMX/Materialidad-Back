package com.mx.axeleratum.americantower.contract.documentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author satish sharma
 *
 */

@EnableEurekaClient
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class DocumentationSwaggerAppApplication {

	@Value("${spring.application.name}")
	private String appName;


	public static void main(String[] args) {
		SpringApplication.run(DocumentationSwaggerAppApplication.class, args);
	}
}
