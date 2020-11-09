package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class AutorizarContratosDto {
	private Boolean crear;
	private Boolean ver;	
	private Boolean listar;
	private Boolean verTrazabilidad;
	private Boolean verHistorial;
	private Boolean cancelar;
	private Boolean descargar;
}
