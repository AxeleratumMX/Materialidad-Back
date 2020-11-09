package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class AutorizarTemplatesDto {
	private Boolean crear;
	private Boolean editar;
	private Boolean ver;
	private Boolean eliminar;
	private Boolean listar;
}
