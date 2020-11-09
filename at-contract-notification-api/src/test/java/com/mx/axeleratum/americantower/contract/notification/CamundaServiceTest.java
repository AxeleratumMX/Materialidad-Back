package com.mx.axeleratum.americantower.contract.notification;

import com.mx.axeleratum.americantower.contract.core.dto.CamundaTaskDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaTaskRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaVariableDto;
import com.mx.axeleratum.americantower.contract.notification.dto.CommentDto;
import com.mx.axeleratum.americantower.contract.notification.service.CamundaBpmService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CamundaServiceTest {
    @Autowired
    CamundaBpmService camundaBpmService;

    @Test
    public void testPass() {
        log.info("pass");
    }

    @Test
    public void testGetData() {
        CamundaTaskDto taskDto = camundaBpmService.getTask("3bda0041-d20a-11ea-875e-06bb47f720ea");

        log.info("sali");
    }

    @Test
    public void testGetListData() {
        List<LinkedHashMap> taskDto = camundaBpmService.findTaskLiskByUser("userFirmante");

        log.info("sali");
    }

    @Test
    public void  responseTask() {
        Map values = new HashMap<String,Object>();
        values.put("okRevision",new CamundaVariableDto(true));
        values.put("variableParam" ,new CamundaVariableDto("Valor Gaby"));

        CamundaTaskRequestDto camundaTaskResponseDto = new CamundaTaskRequestDto();
        camundaTaskResponseDto.setVariables(values);
        String taskId = "90cd172c-d7df-11ea-88e1-0a4f912861ec";
        camundaBpmService.completeTask(taskId,camundaTaskResponseDto);

        log.info("sali");


    }
    @Test
    public void testFindTaskByProcessInstanceId() {
        String processInstanceId = "d9d270a6-dda9-11ea-a336-000d3aebfaf7";
        List<LinkedHashMap> list = camundaBpmService.findTaskLiskByProcessInstance(processInstanceId);
        log.info("sali");
    }

    @Test
    public void testFindCommentsTaskByProcessInstanceId() {
        String processInstanceId = "d9d270a6-dda9-11ea-a336-000d3aebfaf7";
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


        log.info("sali");



    }






}
