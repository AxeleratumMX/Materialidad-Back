package com.mx.axeleratum.americantower.contract.notification.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private String taskId;
    private String userId;
    private String message;
    private String fecha;
}
