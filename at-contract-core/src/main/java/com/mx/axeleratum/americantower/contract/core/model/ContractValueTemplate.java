package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ContractValueTemplate {
 private String id;
 private String sectionId;
 private String description; 
 private DataType dataType;
 private String content;
 private Boolean editable;
 private Boolean optional;
 private String apiRestListOfValue;
 private String regex; //validacion de campo
 private Operation operation;
 private Operation onLoad;
}
