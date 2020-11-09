package com.mx.axeleratum.americantower.contract.core.model;

public enum OperationType {
	validation("validation"),calculation("calculation");

	private final String dataType;

	private OperationType(String dataType) {
		this.dataType = dataType;
	}
	
	public String toString() {
		return this.dataType;
	}
}
