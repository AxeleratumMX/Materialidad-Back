package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import java.util.Collection;
import java.util.List;

import com.mx.axeleratum.americantower.contract.core.model.SectionType;
import com.mx.axeleratum.americantower.contract.core.util.FindUtils;

import lombok.Data;

@Data
public class SectionDto {
	String id;
	String description;
	SectionType type;
	List<EquipmentDto> content; 
	
	public static SectionDto findParamById(Collection<SectionDto> seccions, String id) {
		return FindUtils.findByProperty(seccions, seccion -> id.equals(seccion.getId()));
	}
	
	public static SectionDto findParamByName(Collection<SectionDto> seccions, String name) {
		return FindUtils.findByProperty(seccions, seccion -> name.equals(seccion.getDescription()));
	}
}
