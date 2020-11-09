package com.mx.axeleratum.americantower.contract.notification.dto;

import lombok.Data;

@Data
public class CompleteTaskDto {
    private String taskId;
    private String contractId;
    private String responseUser;
    private String contractStatusKey;

}
