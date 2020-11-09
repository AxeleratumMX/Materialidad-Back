package com.mx.axeleratum.americantower.contract.core.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "notifications")
public class Notification {

    public static String STATUS_CREATED = "CREATED";
    public static String STATUS_VIEWER = "VIEWER";
    public static String STATUS_ANSWERED = "ANSWERED";

    @Id
    private String id;
    private String contractId;
    private String processInstanceId;
    private String comments;
    private String receiverUsername;


    //CREATED / VIEWER / ANSWERED
    private String status;

    @CreatedDate
    private Date sendDate;
    private Date validationDate;

    @CreatedDate
    private Date createdDate;
}
