package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import com.mx.axeleratum.americantower.contract.core.exception.OperationException;
import com.mx.axeleratum.americantower.contract.core.model.Param;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.SectionDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Operations {
	final String START_DATE = "startDate";
	final String END_DATE = "endDate";
	final String DATE = "date";
	final String NUM_YEARS = "numYears";
	final static String ERROR_VALUE_NULL = "El valor es requerido";
	final String FORMAT_DATE = "yyyy-MM-dd";
	static String ERROR_PARAM_ID_NO_FOUND = "El parámetro id '%s' debe existir en %s";
	static String ERROR_VALUE_ID_NO_FOUND = "El id '%s' debe existir en la lista de valores del contrato";
	static String ERROR_DEPENDENCY = "Debe ingresar un valor en %s";
	static String ERROR_FORMAT_DATE = "Formato de fecha incorrecto";
	static String ERROR_DEPENDENCY_FORMAT_DATE = "Formato de fecha incorrecto en %s";
	static String ERROR_FORMAT_NUMBER = "Formato de número incorrecto";
	static String ERROR_FORMAT_INTEGER_POSITIVE = "El número debe ser un entero positivo";
	static String ERROR_DEPENDENCY_FORMAT_NUMBER = "Formato de número incorrecto en %s";
	static String ERROR_DEPENDENCY_FORMAT_INTEGER_POSITIVE = "El número en %s debe ser un entero positivo";
	static String ERROR_PREVIOUS_DATE_GREATER_OR_EQUAL = "La fecha ingresada debe ser mayor o igual que la fecha ingresada en %s";
	static String ERROR_PREVIOUS_DATE_GREATER = "La fecha ingresada debe ser mayor que la fecha ingresada en %s";
	static String ERROR_DEPENDENCY_SECTION = "La seccion %s no existe";
	static String ERROR_DEPENDENCY_SECTION_LIST = "La seccion %s debe contener al menos un elemento";
	static String ERROR_FORMAT_COL = "Error en la tabla %s. la columna %s no es de tipo numerico";

	protected static ContractValueDto getByCurrentParamId(ContractValueDto currentValue,List<ContractValueDto> contractValues,String paramName) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+paramName);
		List<Param> params = currentValue.getOperation().getParams();
		Param param = ContractValueDto.findParamById(params, paramName);
		ContractValueDto value = ContractValueDto.findById(contractValues, param.getValue());
		return value;
	}
	
	protected static Param isParamNull(ContractValueDto currentValue, String paramName) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+paramName);
		List<Param> params = currentValue.getOperation().getParams();
		Param param = ContractValueDto.findParamById(params, paramName);
		if (param == null) {
			currentValue.getOperationErrors()
					.add(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
			throw new OperationException(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
		return param;
	}
	
	protected static Param isOnLoadParamNull(ContractValueDto currentValue, String paramName) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+paramName);
		List<Param> params = currentValue.getOnLoad().getParams();
		Param param = ContractValueDto.findParamById(params, paramName);
		if (param == null) {
			currentValue.getOperationErrors()
					.add(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
			throw new OperationException(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
		return param;
	}

	protected static List<Param> isParamsNull(ContractValueDto currentValue, String paramName) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+paramName);
		List<Param> params = currentValue.getOperation().getParams();
		List<Param> paramValues = ContractValueDto.findAllParamById(params, paramName);
		if (paramValues == null || paramValues.size()<=0) {
			currentValue.getOperationErrors()
					.add(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
			throw new OperationException(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_PARAM_ID_NO_FOUND, paramName, currentValue.getDescription()));
		return paramValues;
	}
	
	protected static ContractValueDto isValueDontExistOrNullOrEmpty(ContractValueDto currentValue, List<ContractValueDto> contractValues, Param param) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+param.getValue());
		ContractValueDto value = ifExistValue(currentValue, contractValues, param);
		if (value.getContent() == null || value.getContent().trim().isEmpty()) {
			value.getOperationErrors().add(ERROR_VALUE_NULL);
			if (!value.getDescription().equals(currentValue.getDescription())) {
				currentValue.getOperationErrors().add(String.format(ERROR_DEPENDENCY, value.getDescription()));
			}
			throw new OperationException(String.format(ERROR_DEPENDENCY, value.getDescription()));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_DEPENDENCY,value.getDescription()));
		value.getOperationErrors().remove(ERROR_VALUE_NULL);
		return value;
	}
	
	protected static ContractValueDto isValueDontExistOrNullOrEmpty(ContractValueDto currentValue, List<ContractValueDto> contractValues,String paramValue) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+paramValue);
		ContractValueDto value = ifExistValue(currentValue, contractValues, paramValue);
		if (value.getContent() == null || value.getContent().trim().isEmpty()) {
			value.getOperationErrors().add(ERROR_VALUE_NULL);
			if (!value.getDescription().equals(currentValue.getDescription())) {
				currentValue.getOperationErrors().add(String.format(ERROR_DEPENDENCY, value.getDescription()));
			}
			throw new OperationException(String.format(ERROR_DEPENDENCY, value.getDescription()));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_DEPENDENCY,value.getDescription()));
		value.getOperationErrors().remove(ERROR_VALUE_NULL);
		return value;
	}
	
	protected static SectionDto ifExistSection(ContractValueDto currentValue, ContractTemplateDto contract, Param param) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+param.getValue());
		List<SectionDto> sections = contract.getTemplateInstance().getSections();
		SectionDto section = SectionDto.findParamById(sections,param.getValue());
		if (section==null) {
			currentValue.getOperationErrors().add(String.format(ERROR_DEPENDENCY_SECTION, param.getValue()));
			throw new OperationException(String.format(ERROR_DEPENDENCY_SECTION, param.getValue()));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_DEPENDENCY_SECTION,param.getValue()));
		return section;
	}
	
	protected static SectionDto ifExistSection(ContractValueDto currentValue, ContractTemplateDto contract,String seccionId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+seccionId);
		List<SectionDto> sections = contract.getTemplateInstance().getSections();
		SectionDto section = SectionDto.findParamById(sections,seccionId);
		if (section==null) {
			currentValue.getOperationErrors().add(String.format(ERROR_DEPENDENCY_SECTION, seccionId));
			throw new OperationException(String.format(ERROR_DEPENDENCY_SECTION,seccionId));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_DEPENDENCY_SECTION,seccionId));
		return section;
	}

	protected static ContractValueDto ifExistValue(ContractValueDto currentValue, List<ContractValueDto> contractValues,Param param) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+param.getValue());
		ContractValueDto value = ContractValueDto.findById(contractValues, param.getValue());
		if (value == null) {
			currentValue.getOperationErrors().add(String.format(ERROR_VALUE_ID_NO_FOUND, param.getValue()));
			throw new OperationException(String.format(ERROR_VALUE_ID_NO_FOUND, param.getValue()));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_VALUE_ID_NO_FOUND,param.getValue()));
		return value;
	}
	

	protected static ContractValueDto ifExistValue(ContractValueDto currentValue, List<ContractValueDto> contractValues,String valueId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+" "+valueId);
		ContractValueDto value = ContractValueDto.findById(contractValues,valueId);
		if (value == null) {
			currentValue.getOperationErrors().add(String.format(ERROR_VALUE_ID_NO_FOUND,valueId));
			throw new OperationException(String.format(ERROR_VALUE_ID_NO_FOUND,valueId));
		}
		currentValue.getOperationErrors().remove(String.format(ERROR_VALUE_ID_NO_FOUND,valueId));
		return value;
	}
}
