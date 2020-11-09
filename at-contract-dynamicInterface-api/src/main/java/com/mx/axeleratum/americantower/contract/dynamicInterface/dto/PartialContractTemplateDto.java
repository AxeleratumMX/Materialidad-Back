package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import lombok.Data;

@Data
public class PartialContractTemplateDto {
	private String	id;
	private String  idContract;
	private String  clientId;
	private String  tipoContrato;
	private String  tipoContratoOracle;
	private String  subTipoContratoOracle;
	private String  estado;

}
