package com.mx.axeleratum.americantower.contract.alert.model;


import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Alert {
    @Id
    private String id;
    private String status;
    private String assetId;
    private String folio;
    private String client;
    private String tipoContrato;
    private String subTipoContrato;
    private Boolean notify;
    private Boolean reminder;
    @CreatedDate
    private Date createdDate;
}
