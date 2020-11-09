package com.mx.axeleratum.americantower.contract.core.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CamundaProcessInstanceRequestDto {

    Map<String,Object> variables;
    String businessKey;
    boolean withVariablesInReturn;
}



