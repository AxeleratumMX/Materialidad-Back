package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import com.mx.axeleratum.americantower.contract.core.model.DataType;
import com.mx.axeleratum.americantower.contract.core.model.Operation;
import com.mx.axeleratum.americantower.contract.core.model.Param;
import com.mx.axeleratum.americantower.contract.core.util.FindUtils;
import lombok.Data;
import lombok.ToString;

import java.util.*;

@ToString
@Data
public class ContractValueDto {
	String id;
	String sectionId;
	String description;
	DataType dataType;
	String content;
	Boolean editable;
	Boolean optional;
	String apiRestListOfValue;
	String regex; // validacion de campo
	Operation operation;
	Operation onLoad;
	Set<String> operationErrors = new HashSet<String>();

	public static ContractValueDto findById(Collection<ContractValueDto> values, String id) {
		return FindUtils.findByProperty(values, value -> id.equals(value.getId()));
	}
	
	public static Param findParamById(Collection<Param> params, String id) {
		return FindUtils.findByProperty(params, param -> id.equals(param.getId()));
	}

	public static List<Param> findAllParamById(Collection<Param> params, String id) {
		return FindUtils.findAllByProperty(params, param -> id.equals(param.getId()));
	}

	public static void setValueById(List<ContractValueDto> values, String id, String newValue) {
		Iterator<ContractValueDto> iValues = values.iterator();
		while(iValues.hasNext()) {
			ContractValueDto value = iValues.next();
			if(id.equals(value.getId())) {
				value.setContent(newValue);
			}
		}
	}
}
