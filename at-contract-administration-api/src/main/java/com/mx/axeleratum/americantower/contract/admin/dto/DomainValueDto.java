package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class DomainValueDto {
    private String key;
    private String value;
    private String description;
    private String subDomainValue;
    private int order;
    private boolean active = true;

}
