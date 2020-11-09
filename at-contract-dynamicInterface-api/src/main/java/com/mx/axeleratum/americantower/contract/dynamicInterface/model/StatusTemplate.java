package com.mx.axeleratum.americantower.contract.dynamicInterface.model;

import java.util.List;

import lombok.Data;

@Data
public class StatusTemplate {
	String id;
	String description;
	List<String> permissions;
}
