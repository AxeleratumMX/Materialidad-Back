package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;

@Data
public class AutorizarContratos {
	private Boolean crear;
	private Boolean ver;	
	private Boolean listar;
	private Boolean verTrazabilidad;
	private Boolean verHistorial;
	private Boolean cancelar;
	private Boolean descargar;
}
