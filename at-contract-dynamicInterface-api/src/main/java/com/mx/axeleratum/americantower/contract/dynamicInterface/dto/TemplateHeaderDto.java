package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import lombok.Data;

@Data
public class TemplateHeaderDto {
	private String  idTemplate;
	private String  client;
	private String  tipoContrato;
	private String  name;
	private String  abstractType;
	private String  leaseType;
	private String  acuerdo;
	private String  estado;
	private String  tipoContratoOracle;
	private String  subTipoContratoOracle;
	private Integer idActivo;
	private Integer version;
}
