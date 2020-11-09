package com.mx.axeleratum.americantower.contract.admin.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PassthruDto {
    String id;
    Long idActivo;
    BigDecimal grossAmmount;
    BigDecimal landMonAmmount;
    BigDecimal percentage;
}
