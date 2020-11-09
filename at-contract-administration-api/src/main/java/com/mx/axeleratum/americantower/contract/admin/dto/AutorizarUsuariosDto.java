package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class AutorizarUsuariosDto {
	private Boolean crear;
	private Boolean editar;
	private Boolean ver;	
	private Boolean listar ;
	private Boolean eliminar;
}
