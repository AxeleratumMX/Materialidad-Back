package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import java.util.List;

import lombok.Data;

@Data
public class StatusDto {
	String id;
	String description;
	List<String> permissions;
}
