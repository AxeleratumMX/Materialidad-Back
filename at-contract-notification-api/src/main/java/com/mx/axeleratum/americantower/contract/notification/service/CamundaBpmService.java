package com.mx.axeleratum.americantower.contract.notification.service;


import com.mx.axeleratum.americantower.contract.core.dto.CamundaCommentDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaTaskDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaTaskRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class CamundaBpmService {
    @Autowired
    @Qualifier("camundaWebClient")
    WebClient webClient;

    public CamundaTaskDto getTask(String taskId) {
        //todo manejo de errorres
        CamundaTaskDto camundaTaskDtoMono = webClient.get()
                .uri("/task/" + taskId)
                .exchange()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .block()
                .bodyToMono(CamundaTaskDto.class)
                .block();
        return camundaTaskDtoMono;
    }

    public List<LinkedHashMap> findTaskLiskByUser(String username) {
        //todo manejo de errorres
        List<LinkedHashMap> camundaTaskList = webClient.get()
                .uri("/task?assignee=" + username + "&sortBy=created&sortOrder=desc")
                .exchange()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .block()
                .bodyToMono(List.class)
                .block();
        return camundaTaskList;

    }


    public List<LinkedHashMap> findTaskLiskByProcessInstance(String processInstanceId) {
        //todo manejo de errorres
        List<LinkedHashMap> camundaTaskList = webClient.get()
                .uri("/history/task?processInstanceId=" + processInstanceId)
                .exchange()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .block()
                .bodyToMono(List.class)
                .block();
        return camundaTaskList;

    }

    public List<LinkedHashMap> findCommentsByTaskId(String taskId) {
        //todo manejo de errorres
        List<LinkedHashMap> camundaTaskList = webClient.get()
                .uri("/task/"+taskId+"/comment")
                .exchange()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .block()
                .bodyToMono(List.class)
                .block();
        return camundaTaskList;

    }



    public void completeTask(String taskId , CamundaTaskRequestDto camundaTaskRequestDto) {
        //todo manejo de errorres

        WebClient.RequestBodySpec request = webClient.post().uri("/task/"+taskId+"/complete");

        request
                .body(BodyInserters.fromPublisher(  Mono.just(camundaTaskRequestDto), CamundaTaskRequestDto.class));
        //agregar manejo de exceptions!

        request.exchange().block();

    }


    public Map<String,LinkedHashMap> findVariablesTaskLisk(String taskId) {
        //todo manejo de errorres
        Map<String,LinkedHashMap> variablesMap = webClient.get()
                .uri("/task/"+taskId+"/variables")
                .exchange()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .block()
                .bodyToMono(Map.class)
                .block();
        return variablesMap;

    }

    public void sendCommentsTask(String taskId , String comments) {
        //todo manejo de errorres

        CamundaCommentDto commentDto = new CamundaCommentDto(comments);

        WebClient.RequestBodySpec request = webClient.post().uri("/task/"+taskId+"/comment/create");

        request
                .body(BodyInserters.fromPublisher(  Mono.just(commentDto), CamundaCommentDto.class));
        //agregar manejo de exceptions!

        request.exchange().block();

    }


}
