package com.mx.axeleratum.americantower.contract.dynamicInterface.service;


import com.mx.axeleratum.americantower.contract.core.dto.CamundaProcessInstanceRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaProcessInstanceResponseDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaTaskDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaTaskRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Service
public class CamundaBpmService {
    @Autowired
    @Qualifier("camundaWebClient")
    WebClient webClient;

    @Value("${camunda.bpm.processDefinitionKey}")
    private String processDefinitionKey;


    public CamundaProcessInstanceResponseDto startProcessInstance(CamundaProcessInstanceRequestDto camundaProcessInstanceRequestDto) {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        //todo manejo de errorres

        WebClient.RequestBodySpec request = webClient.post().uri("/process-definition/key/"+processDefinitionKey+"/start");

        request
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(camundaProcessInstanceRequestDto), CamundaProcessInstanceRequestDto.class));
        //agregar manejo de exceptions!

        CamundaProcessInstanceResponseDto response =  request
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .block()
                .bodyToMono(CamundaProcessInstanceResponseDto.class)
                .block()
                ;
        return  response;


    }

    public List<CamundaTaskDto> getTasksByProcessInstanceId(String processInstanceId) {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        //todo manejo de errorres
        List<CamundaTaskDto> camundaTaskDtoList = webClient.get()
                .uri("/task/?processInstanceId=" + processInstanceId)
                .exchange()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .block()
                .bodyToFlux(CamundaTaskDto.class).collectList().block();
        return camundaTaskDtoList;
    }

    public void completeTask(String taskId , CamundaTaskRequestDto camundaTaskRequestDto) {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        //todo manejo de errorres

        WebClient.RequestBodySpec request = webClient.post().uri("/task/"+taskId+"/complete");

        request
                .body(BodyInserters.fromPublisher(  Mono.just(camundaTaskRequestDto), CamundaTaskRequestDto.class));
        //agregar manejo de exceptions!

        request.exchange().block();

    }


}
