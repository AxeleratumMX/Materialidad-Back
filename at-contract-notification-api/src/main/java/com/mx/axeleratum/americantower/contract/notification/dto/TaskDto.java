package com.mx.axeleratum.americantower.contract.notification.dto;


import lombok.Data;

@Data
public class TaskDto {
    private String taskId;
    private String processInstanceId;
    private String fecha;
    private String contractId;
    private String cliente;
    private String assetNumber;
    private String contractStatusKey;
    private String contractStatus;
    private String tipoContrato;
    private String subTipoContrato;
    private String folio;
    private String asssignCreateContractUser;

}
