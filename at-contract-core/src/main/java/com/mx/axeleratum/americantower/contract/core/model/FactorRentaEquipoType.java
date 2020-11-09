package com.mx.axeleratum.americantower.contract.core.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum FactorRentaEquipoType {
	MW("MW"), MW4("MW4"), RF("RF"), RRU("RRU");

	private final String key;
	List<String> tipo = Arrays.asList("MW", "MW a 4 años", "RF", "RRU");

	private FactorRentaEquipoType(String key) {
		this.key = key;
	}

	public String getValue() {
		return tipo.get(this.ordinal());
	}

	public static FactorRentaEquipoType getByValue(String value) {
		if (Objects.nonNull(value)) {
			if (FactorRentaEquipoType.MW.getValue().equals(value)) return FactorRentaEquipoType.MW;
			if (FactorRentaEquipoType.MW4.getValue().equals(value)) return FactorRentaEquipoType.MW4;
			if (FactorRentaEquipoType.RF.getValue().equals(value)) return FactorRentaEquipoType.RF;
		}
		//por deafult retorna PRU
		return FactorRentaEquipoType.RRU;
	}

	public String toString() {
		return this.key;
	}
}
