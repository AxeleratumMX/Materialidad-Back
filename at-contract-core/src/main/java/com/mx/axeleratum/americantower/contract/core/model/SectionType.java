package com.mx.axeleratum.americantower.contract.core.model;

public enum SectionType {
	list("list"),value("value");

	private final String dataType;

	private SectionType(String dataType) {
		this.dataType = dataType;
	}
	
	public String toString() {
		return this.dataType;
	}
}
