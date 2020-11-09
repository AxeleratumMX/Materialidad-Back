package com.mx.axeleratum.americantower.contract.core.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "photousers") 
public class UserPhoto {
    @Id
    private String id;
    private String fileName;
    private long size;
    private String type;
    private Binary imagen;
}
