package com.mx.axeleratum.americantower.contract.notification.service;

import com.mx.axeleratum.americantower.contract.core.dto.CamundaTaskRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaVariableDto;
import com.mx.axeleratum.americantower.contract.core.model.ContractStatusType;
import com.mx.axeleratum.americantower.contract.core.model.ResponseType;
import com.mx.axeleratum.americantower.contract.notification.dto.CommentDto;
import com.mx.axeleratum.americantower.contract.notification.dto.CompleteTaskDto;
import com.mx.axeleratum.americantower.contract.notification.dto.ResponseTaskDto;
import com.mx.axeleratum.americantower.contract.notification.dto.TaskDto;
import com.mx.axeleratum.americantower.contract.notification.util.BuilderDtoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class InboxService {



    @Autowired
    CamundaBpmService camundaBpmService;


    public List<TaskDto> findTasksByUserName(String username) {
        List<TaskDto> tasks = new ArrayList<>();
        List<LinkedHashMap> camundaTaskDtoList = camundaBpmService.findTaskLiskByUser(username);
        if (Objects.nonNull(camundaTaskDtoList)) {
            camundaTaskDtoList.stream().forEach( c -> {
                Map<String,LinkedHashMap> variablesMap = camundaBpmService.findVariablesTaskLisk(c.get("id").toString());
                TaskDto taskDto = BuilderDtoUtil.newTaskDto(c,variablesMap);
                tasks.add(taskDto);
                }
            );
        }
        return tasks;
    }

    public void responseTask(ResponseTaskDto responseTaskDto) {
        String paramNanme = "";
        boolean paramValue = (responseTaskDto.getResponse() == ResponseType.ACCEPT || responseTaskDto.getResponse() == ResponseType.APPROVE);
        if (responseTaskDto.getContractStatusKey().equals(ContractStatusType.REVISION.toString())) {
            paramNanme = "responseRevisor";
        } else if (responseTaskDto.getContractStatusKey().equals(ContractStatusType.FIRMA.toString())) {
            paramNanme = "responseFirma";
        }

        Map values = new HashMap<String,Object>();
        values.put(paramNanme ,new CamundaVariableDto(paramValue));
        CamundaTaskRequestDto camundaTaskRequestDto = new CamundaTaskRequestDto();
        camundaTaskRequestDto.setVariables(values);

        String comments = "";

        if (!paramValue) {
             comments = "Rechazado: " + responseTaskDto.getComments();
            camundaBpmService.sendCommentsTask(responseTaskDto.getTaskId(),comments);
        }
        camundaBpmService.completeTask(responseTaskDto.getTaskId(),camundaTaskRequestDto);

    }

    public List<CommentDto> findComments(String processInstanceId) {
        List<LinkedHashMap> list = camundaBpmService.findTaskLiskByProcessInstance(processInstanceId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        list.stream().forEach( l -> {
                    List<LinkedHashMap> listaComentarios = camundaBpmService.findCommentsByTaskId((String) l.get("id"));
                    if (Objects.nonNull(listaComentarios) && listaComentarios.size()>0) {
                        CommentDto commentDto = new CommentDto();
                        commentDto.setUserId((String)l.get("assignee"));
                        commentDto.setTaskId((String) l.get("id"));
                        commentDto.setMessage((String)listaComentarios.get(0).get("message"));
                        commentDto.setFecha((String)listaComentarios.get(0).get("time"));
                        commentDtoList.add(commentDto);
                    }
                }
        );
        return commentDtoList;
    }

    public void completeTask(CompleteTaskDto completeTaskDto) {
        camundaBpmService.completeTask(completeTaskDto.getTaskId(),new CamundaTaskRequestDto());
    }


}
