package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class DomainDto {
    private String name;
    private String description;
    private List<DomainValueDto> domainValues;
}
