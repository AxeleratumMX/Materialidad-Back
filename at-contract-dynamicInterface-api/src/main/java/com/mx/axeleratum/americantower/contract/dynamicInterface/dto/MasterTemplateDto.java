package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MasterTemplateDto {
	private String  id;
	private String  tipo;
	private String  nameTemplate;
	List<ContractValueDto> values;
	List<SectionDto> sections;
}
