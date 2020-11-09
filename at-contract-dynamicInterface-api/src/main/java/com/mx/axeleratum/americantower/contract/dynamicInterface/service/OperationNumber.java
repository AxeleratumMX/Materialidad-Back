package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import com.mx.axeleratum.americantower.contract.core.exception.OperationException;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.core.model.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OperationNumber extends Operations {
	public static int DECIMAL_SCALE = 4;
	String REGEXT_INTEGER_POSITIVE_ONLY = "\\d+";
	String REGEXT_INTEGER_DECIMAL = "[0-9]+([\\.,][0-9]+)?";

	public static Number fromString(String strNumber) throws Exception {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Boolean error = false;
		BigDecimal num = null;
		try {
			num = new BigDecimal(strNumber);
		} catch (Exception e) {
			error = true;
		}
		if (error) {
			strNumber = strNumber.replace(".", "*");
			strNumber = strNumber.replace(",", ".");
			strNumber = strNumber.replace("*", ",");
			try {
				num = new BigDecimal(strNumber);
				error = false;
			} catch (Exception e) {
				error = true;
			}
		}
		if(error) {
			throw new NumberFormatException("Formato de número error");
		}
		return num;
	}

	private static Number isValidNumber(ContractValueDto currentValue, ContractValueDto valueParam) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Number num = null;
		try {
			num = fromString(valueParam.getContent());
		} catch (Exception e) {
			valueParam.getOperationErrors().add(ERROR_FORMAT_NUMBER);
			currentValue.getOperationErrors().add(String.format(ERROR_DEPENDENCY_FORMAT_NUMBER, valueParam.getId()));
			throw new OperationException(String.format(ERROR_DEPENDENCY, currentValue.getId()));
		}
		valueParam.getOperationErrors().remove(ERROR_FORMAT_NUMBER);
		currentValue.getOperationErrors().remove(String.format(ERROR_DEPENDENCY_FORMAT_NUMBER, valueParam.getId()));
		return num;
	}

	static Number validParam(ContractValueDto currentValue, List<ContractValueDto> contractValues,
			String paramDateName) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Boolean error = false;
		Param paramDate = null;
		try {
			paramDate = isParamNull(currentValue, paramDateName);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		if (error) {
			throw new OperationException();
		}
		Number num = validParam(currentValue, contractValues, paramDate);
		return num;
	}

	private static Number validParam(ContractValueDto currentValue, List<ContractValueDto> contractValues,
			Param paramNumber) {
		ContractValueDto value = null;
		Boolean error = false;
		try {
			value = isValueDontExistOrNullOrEmpty(currentValue, contractValues, paramNumber);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		if (error) {
			throw new OperationException();
		}
		Number num = null;
		try {
			num = isValidNumber(currentValue, value);
		} catch (Exception e) {
			error = true;
		}
		if (error) {
			throw new OperationException();
		}
		return num;
	}

	public static Integer validPositiveInteger(ContractValueDto currentValue, List<ContractValueDto> contractValues,
			String paramName) {
		BigDecimal value = (BigDecimal) validParam(currentValue, contractValues, paramName);
		BigDecimal fractionalPart = value.remainder(BigDecimal.ONE);
		ContractValueDto valueParam = ContractValueDto.findById(contractValues, paramName);
		if (fractionalPart.compareTo(BigDecimal.ZERO) != 0) {
			valueParam.getOperationErrors().add(ERROR_FORMAT_INTEGER_POSITIVE);
			currentValue.getOperationErrors()
					.add(String.format(ERROR_DEPENDENCY_FORMAT_INTEGER_POSITIVE, valueParam.getId()));
			throw new OperationException(String.format(ERROR_DEPENDENCY_FORMAT_INTEGER_POSITIVE, currentValue.getId()));
		}
		valueParam.getOperationErrors().remove(ERROR_FORMAT_INTEGER_POSITIVE);
		currentValue.getOperationErrors()
				.remove(String.format(ERROR_DEPENDENCY_FORMAT_INTEGER_POSITIVE, valueParam.getId()));
		return value.intValue();
	}

	public void add(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Param> params = ContractValueDto.findAllParamById(currentValue.getOperation().getParams(), "value");
		List<Number> numbers = new ArrayList<Number>();
		Boolean error = false;
		for (int i = 0, n = params.size(); i < n; i++) {
			try {
				Number number = validParam(currentValue, contractValues, params.get(i));
				numbers.add(number);
			} catch (Exception e) {
				error = true;
			}
		}
		if (error) {
			throw new OperationException();
		}
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0, n = numbers.size(); i < n; i++) {
			sum = sum.add((BigDecimal) numbers.get(i));
		}
		sum.setScale(DECIMAL_SCALE, BigDecimal.ROUND_UP);
		currentValue.setContent(sum+"");
	}
	
	public void subtraction(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Param> params = ContractValueDto.findAllParamById(currentValue.getOperation().getParams(), "value");
		List<Number> numbers = new ArrayList<Number>();
		Boolean error = false;
		for (int i = 0, n = params.size(); i < n; i++) {
			try {
				Number number = validParam(currentValue, contractValues, params.get(i));
				numbers.add(number);
			} catch (Exception e) {
				error = true;
			}
		}
		if (error) {
			throw new OperationException();
		}
		BigDecimal subtraction = BigDecimal.ZERO;
		if (numbers.size()>0) {
			 subtraction =(BigDecimal) numbers.get(0);
		}

		for (int i = 1, n = numbers.size(); i < n; i++) {
			subtraction = subtraction.subtract((BigDecimal) numbers.get(i));
		}
		subtraction.setScale(DECIMAL_SCALE, BigDecimal.ROUND_UP);
		currentValue.setContent(subtraction+"");
	}
}
