package com.mx.axeleratum.americantower.contract.core.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "contracttemplategroups")
public class ContractTemplateGroup {
	@Id	
    String id;
	String name;
	private List<String> contractIds;
	private List<String> valuesIds;
	private String masterContractId;
}
