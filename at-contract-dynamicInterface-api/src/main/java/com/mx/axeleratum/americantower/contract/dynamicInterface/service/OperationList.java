package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.OperationException;
import com.mx.axeleratum.americantower.contract.core.model.Param;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OperationList extends Operations {

	public /*Boolean*/ void enableEditByListValue(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		String paramListId = "list";
		String paramListValuesId = "value";
		Boolean error = false;
		Param paramList = null;
		try {
			paramList = isParamNull(currentValue, paramListId);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		ContractValueDto listValue = null;
		try {
			listValue = isValueDontExistOrNullOrEmpty(currentValue, contractValues, paramList);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		List<Param> paramsValues = null;
		try {
			paramsValues = isParamsNull(currentValue, paramListValuesId);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}		
		if(error) {
			throw new OperationException("");
		}
		log.error("List("+listValue.getId()+"): "+listValue.getContent());
		if(listValue.getId().equals("porcentajeMinimo")) {
			log.debug("parar");
		}
		List<String> listValues = new ArrayList<String>();
		for(int i=0,n=paramsValues.size();i<n;i++) {
			listValues.add(paramsValues.get(i).getValue());
		}		
		if(listValues.contains(listValue.getContent())) {			
			currentValue.setEditable(true);
		}else {
			currentValue.setEditable(false);
		}
		log.error("List("+currentValue.getId()+") Editable: "+currentValue.getEditable());
		//return true;
	}

}
