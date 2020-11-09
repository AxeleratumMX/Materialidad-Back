package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ContractTemplateDto {
	private String	id;
	private String  idContract;
	private String  processInstanceId;
	private TemplateDto templateInstance;
	private Date createdDate;
	private Date lastModifiedDate;

	public Map<String,ContractValueDto> listToMapContractValue() {
		Map<String,ContractValueDto> map = Maps.uniqueIndex(templateInstance.getValues(), ContractValueDto::getId);
		return map;
	}
}
