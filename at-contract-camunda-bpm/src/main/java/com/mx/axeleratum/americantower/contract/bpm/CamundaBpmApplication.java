package com.mx.axeleratum.americantower.contract.bpm;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableEurekaClient
//@EnableFeignClients
@EnableProcessApplication
@SpringBootApplication(scanBasePackages = "com.mx.axeleratum.americantower.contract")
@EnableMongoRepositories(basePackages = "com.mx.axeleratum.americantower.contract")
public class CamundaBpmApplication {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private RuntimeService runtimeService;


    public static void main(String[] args) {
        SpringApplication.run(CamundaBpmApplication.class, args);
    }

/*
    @EventListener
    private void processPostDeploy(PostDeployEvent event) {

        runtimeService.startProcessInstanceByKey("loanApproval");
    }
*/

}
