package com.mx.axeleratum.americantower.contract.notification.dto;


import lombok.Data;

import java.util.Date;

@Data
public class NotificationDto {
    private String id;
    private String contractId;
    private String processInstanceId;
    private String comments;
    private String receiverUsername;
    private String status;
    private Date sendDate;
    private Date validationDate;
    private Date createdDate;
}
