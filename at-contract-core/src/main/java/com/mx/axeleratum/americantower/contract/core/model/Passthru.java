package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Passthru {
    @Id
    String id;
    Long idActivo;
    BigDecimal grossAmmount;
    BigDecimal landMonAmmount;
    BigDecimal percentage;
}
