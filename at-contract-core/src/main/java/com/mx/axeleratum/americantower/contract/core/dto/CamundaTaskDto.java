package com.mx.axeleratum.americantower.contract.core.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CamundaTaskDto {
    private String id;
    private String assignee;
    private Date created;
    private String due;
    private String followUp;
    private String delegationState;
    private String description;
    private String executionId;
    private String owner;
    private String parentTaskId;
    private int priority;
    private String processDefinitionId;
    private String processInstanceId;
    private String taskDefinitionKey;
    private String caseExecutionId;
    private String caseInstanceId;
    private String caseDefinitionId;
    private boolean suspended;
    private String formKey;
    private String tenantId;

}



