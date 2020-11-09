package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TemplateDto {
	private String  idTemplate;
	private String  clientId;
	private String  tipoContrato;
	private String  name;
	private String  abstractType;
	private String  leaseType;
	private String  acuerdo;
	private String  estado;
	private String  content;
	private String  tipoContratoOracle;
	private String  subTipoContratoOracle;
	private Integer idActivo;
	private Integer version;
	private List<ContractValueDto> values;
	private List<SectionDto> sections;
	private Date createdDate;

}
