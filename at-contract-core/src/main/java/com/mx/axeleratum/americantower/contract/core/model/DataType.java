package com.mx.axeleratum.americantower.contract.core.model;

public enum DataType {
	number("number"),string("string"),option("option"),email("email"),date("date"),time("time"),booleano("boolean"), checkbox("checkbox");

	private final String dataType;

	private DataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String toString() {
		return this.dataType;
	}
}
