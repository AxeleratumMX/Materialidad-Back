package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;

@Data
public class AutorizarUsuarios {
	private Boolean crear;
	private Boolean editar;
	private Boolean ver;	
	private Boolean listar ;
	private Boolean eliminar;
}
