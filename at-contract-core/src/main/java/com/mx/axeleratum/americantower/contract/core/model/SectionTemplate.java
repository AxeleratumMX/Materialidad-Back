package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;

import java.util.List;

@Data
public class SectionTemplate {
	String id;
	String description;
	SectionType type;
	List<Equipment> content; 
}
