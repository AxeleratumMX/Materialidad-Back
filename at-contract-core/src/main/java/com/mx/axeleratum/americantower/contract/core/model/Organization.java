package com.mx.axeleratum.americantower.contract.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "organizations") 
public class Organization {
	@Id
	String id;
	
	@Indexed(unique=true)
	String name;
	
	Boolean activo;
}
