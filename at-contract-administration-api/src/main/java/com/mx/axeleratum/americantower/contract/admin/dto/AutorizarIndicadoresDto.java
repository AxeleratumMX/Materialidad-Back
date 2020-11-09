package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class AutorizarIndicadoresDto {
	private Boolean graficaVGeneral;
	private Boolean graficaFirmados;
	private Boolean graficaCancelados;
	private Boolean ultimosActivos;
	private Boolean ultimosFirma;
	private Boolean ultimosRevision;
	private Boolean ultimosCancelados;
}
