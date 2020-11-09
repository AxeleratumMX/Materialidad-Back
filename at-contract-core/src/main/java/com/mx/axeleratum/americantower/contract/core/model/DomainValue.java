package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "domainvalues")
public class DomainValue {
    @Id
    private String id;
    private String key;
    private String value;
    private String description;
    private Integer order;
    private String domainId;
    private String subDomainValue;
    private Boolean active;
}
