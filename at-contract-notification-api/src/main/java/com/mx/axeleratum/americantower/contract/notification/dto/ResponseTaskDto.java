package com.mx.axeleratum.americantower.contract.notification.dto;

import com.mx.axeleratum.americantower.contract.core.model.ResponseType;
import lombok.Data;

@Data
public class ResponseTaskDto {
    private String taskId;
    private String contractId;
    private ResponseType response;
    private String comments;
    private String contractStatusKey;
    private String responseUser;
}
