package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "domains")
public class Domain {
    private String id;
    private Domains key;
    private String description;
    @DBRef
    private List<DomainValue> domainValues;

}
