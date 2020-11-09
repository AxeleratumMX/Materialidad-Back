package com.mx.axeleratum.americantower.contract.core.dto;

import lombok.Data;

@Data
public class CamundaVariableDto {
    private Object value;

    public CamundaVariableDto(Object value) {
        this.value = value;
    }

    /*
    private String type;
    private Date valueInfo;
*/
}



