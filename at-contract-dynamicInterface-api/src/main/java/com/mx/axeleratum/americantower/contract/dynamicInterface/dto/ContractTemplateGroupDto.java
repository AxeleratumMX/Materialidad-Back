package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;


import java.util.List;

import lombok.Data;

@Data
public class ContractTemplateGroupDto {
    String id;
	String name;
	private List<String> contractIds;
	private List<String> valuesIds;
	private String masterContractId;
}
