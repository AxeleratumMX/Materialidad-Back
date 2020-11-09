package com.mx.axeleratum.americantower.contract.core.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "factorRenta")
public class FactorRentaEquipo  {
    private String id;
    private Integer version;
    private FactorRentaEquipoType type;
    private String description;
    private BigDecimal factor;
    @CreatedDate
    private Date createdDate;
}
