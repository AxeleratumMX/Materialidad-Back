package com.mx.axeleratum.americantower.contract.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${kaleido.api.base.url}")
    private String kaleidoApiBaseUrl;
    @Value("${kaleido.username}")
    private String username;
    @Value("${encrypted.kaleido.password}")
    private String password;

    private static final String USER_AGENT = "Spring 5 WebClient";


    @Bean(value = "kaleidoWebClient")
    public WebClient webClient() {

        WebClient webClient = WebClient.builder()
                .baseUrl(kaleidoApiBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .defaultHeaders(header -> header.setBasicAuth(username, password))
                .build();
        return webClient;
    }

}
