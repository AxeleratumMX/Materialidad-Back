package com.mx.axeleratum.americantower.contract.notification.dto;

import lombok.Data;

@Data
public class DynamicContractDto {
    private String	id;
    private String  idContract;
    private String  clientId;
    private String  tipoContrato;
    private String  tipoContratoOracle;
    private String  subTipoContratoOracle;
    private String  estado;

}
