package com.mx.axeleratum.americantower.contract.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CamundaWebClientConfig {

    @Value("${camunda.api.base.url}")
    private String camundaApiBaseUrl;
    @Value("${camunda.username}")
    private String username;
    @Value("camunda.password")
    private String password;

    private static final String USER_AGENT = "Spring 5 WebClient";


    @Bean(value = "camundaWebClient")
    public WebClient webClient() {

        WebClient webClient = WebClient.builder()
                .baseUrl(camundaApiBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
/*
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .defaultHeaders(header -> header.setBasicAuth(username, password))
*/
                .build();
        return webClient;
    }

}
