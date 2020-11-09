package com.mx.axeleratum.americantower.contract.notification.service;

import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.KaleidoResponseDto;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KaleidoService {
    @Autowired
    @Qualifier("kaleidoWebClient")
    WebClient webClient;

    public KaleidoResponseDto postData(KaleidoRequestDto kaleidoRequestDto) {
        log.info("Enviando informacion a Kaleido " + kaleidoRequestDto);
        WebClient.RequestBodySpec request = webClient.post();

        request
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(  Mono.just(kaleidoRequestDto), KaleidoRequestDto.class));
        //agregar manejo de exceptions!

        KaleidoResponseDto response =  request
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KaleidoResponseDto.class)
                .block()
                ;
        return  response;
    }

    public void postDataNotBlocking(KaleidoRequestDto kaleidoRequestDto) {
        log.info("Enviando informacion a Kaleido " + kaleidoRequestDto);
        WebClient.RequestBodySpec request = webClient.post();
        request
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(  Mono.just(kaleidoRequestDto), KaleidoRequestDto.class));

        request
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,response -> Mono.just(new BussinessServiceException("Error 400 al invocar Kaleido")))
                .onStatus(HttpStatus::is5xxServerError,response -> Mono.just(new BussinessServiceException("Error 500 al invocar Kaleido")))
                .bodyToMono(KaleidoResponseDto.class)
        ;

    }

}
