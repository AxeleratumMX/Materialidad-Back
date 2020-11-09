package com.mx.axeleratum.americantower.contract.alert.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AlertDtoEntry {
    private String status;
    private String assetId;
    private String folio;
    private String client;
    private String tipoContrato;
    private String subTipoContrato;
    private Boolean notify;
    private Boolean reminder;
    private Date createdDate;
}
