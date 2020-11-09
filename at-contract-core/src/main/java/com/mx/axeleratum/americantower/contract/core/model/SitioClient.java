package com.mx.axeleratum.americantower.contract.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "sitio_client")
@Data
public class SitioClient {
    @Id
    String id;	
    Long idActivo;
	String idClient;
	String nameClient;
}
