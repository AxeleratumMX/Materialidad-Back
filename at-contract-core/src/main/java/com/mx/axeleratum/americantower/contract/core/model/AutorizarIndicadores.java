package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;

@Data
public class AutorizarIndicadores {
	private Boolean graficaVGeneral;
	private Boolean graficaFirmados;
	private Boolean graficaCancelados;
	private Boolean ultimosActivos;
	private Boolean ultimosFirma;
	private Boolean ultimosRevision;
	private Boolean ultimosCancelados;
}
