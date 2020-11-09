package com.mx.axeleratum.americantower.contract.bpm.service;

import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.KaleidoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
}
