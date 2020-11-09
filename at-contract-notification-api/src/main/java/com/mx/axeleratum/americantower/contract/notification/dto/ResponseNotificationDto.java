package com.mx.axeleratum.americantower.contract.notification.dto;

import com.mx.axeleratum.americantower.contract.core.model.ResponseType;
import lombok.Data;

@Data
public class ResponseNotificationDto {
    private String id;
    private ResponseType response;
    private String comments;
}
