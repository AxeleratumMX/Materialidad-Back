package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.OperationException;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.core.model.Param;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OperationBetweenDates extends Operations{

	SimpleDateFormat formatDate = new SimpleDateFormat(FORMAT_DATE);	

	/**
	 * Retorna una fecha a partir de un string
	 * 
	 * @param strDate
	 * @return
	 */
	private Date fromString(String strDate) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.parse(strDate);
		return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
	}
	
    /**
     * Valida que los datos en valueContract pasado por parametro sea una fecha.
     *  
     * @param currentValue
     * @param valueParam
     * @return
     */
	private Date isValidDateFormat(ContractValueDto currentValue, ContractValueDto valueParam) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Date date = null;
		try {
			date = fromString(valueParam.getContent());
		} catch (Exception e) {
			valueParam.getOperationErrors().add(ERROR_FORMAT_DATE);
			currentValue.getOperationErrors().add(String.format(ERROR_DEPENDENCY_FORMAT_DATE, valueParam.getDescription()));
			throw new OperationException(String.format(ERROR_DEPENDENCY, currentValue.getDescription()));
		}
		valueParam.getOperationErrors().remove(ERROR_FORMAT_DATE);
		currentValue.getOperationErrors().remove(String.format(ERROR_DEPENDENCY_FORMAT_DATE, valueParam.getDescription()));
		return date;
	}

	/**
	 * Valida que los datos en valueContract indicado por nombre sea una fecha.
	 * 
	 * @param currentValue
	 * @param contractValues
	 * @param paramDateName
	 * @return
	 */
	Date validParam(ContractValueDto currentValue, List<ContractValueDto> contractValues, String paramDateName) {
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
		Date date = validParam(currentValue, contractValues, paramDate);
		return date;
	}
    
	/**
	 * Valida que un parametro indicado por nombre tenga un valor y que no sea vacio. 
	 * 
	 * @param currentValue
	 * @param contractValues
	 * @param paramDate
	 * @return
	 */
	private Date validParam(ContractValueDto currentValue, List<ContractValueDto> contractValues, Param paramDate) {
		ContractValueDto valueDate = null;
		Boolean error = false;
		try {
			valueDate = isValueDontExistOrNullOrEmpty(currentValue, contractValues, paramDate);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		Date date = null;
		try {
			date = isValidDateFormat(currentValue, valueDate);
		} catch (Exception e) {
			error = true;
		}
		if (error) {
			throw new OperationException();
		}
		return date;
	}

	Date[] validParams(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Boolean error = false;
		Date[] dates = new Date[2];
		try {
			dates[0] = validParam(currentValue, contractValues, START_DATE);
		} catch (Exception e) {
			error = true;
		}
		try {
			dates[1] = validParam(currentValue, contractValues, END_DATE);
		} catch (Exception e) {
			error = true;
		}
		if (error)
			throw new OperationException();
		return dates;
	}

	public Period getPeriodBetweenDates(Date startDate, Date endDate) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		LocalDate dateEnd = new java.sql.Date(endDate.getTime()).toLocalDate();
		LocalDate dateStart = new java.sql.Date(startDate.getTime()).toLocalDate();
		Period period = Period.between(dateStart, dateEnd);
		return period;
	}

	/**
	 * Calcula los años entre dos fechas
	 * 
	 * @param params
	 * @param contractValues
	 * @return
	 */
	public void yearsBetweenDates(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Date[] dates = validParams(currentValue, contractValues);
		Period period = getPeriodBetweenDates(dates[0], dates[1]);
		Integer years = period.getYears();
		log.info("years:" + years);
		currentValue.setContent(years.toString());
	}

	/**
	 * Calcula los meses adicionales entre dos fechas
	 * 
	 * @param params
	 * @param contractValues
	 * @return
	 */
	public void aditionalsMonthsBetweenDates(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Date[] dates = validParams(currentValue, contractValues);
		Period period = getPeriodBetweenDates(dates[0], dates[1]);
		Integer months = period.getMonths();
		log.info("months:" + months);
		currentValue.setContent(months.toString());
	}
	
	private Date addNumYearsToDate(Date date, Integer numAnios) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, numAnios);
		return calendar.getTime();
	}

	/**
	 * Calcula los días adicionales entre dos fechas
	 * 
	 * @param params
	 * @param contractValues
	 * @return
	 */
	public void aditionalsDaysBetweenDates(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Date[] dates = validParams(currentValue, contractValues);
		Period period = getPeriodBetweenDates(dates[0], dates[1]);
		Integer days = period.getDays();
		log.info("days:" + days);
		currentValue.setContent(days.toString());		
	}

	
	/**
	 * Valida que una fecha sea mayor que otra
	 * 
	 * @param currentValue
	 * @param contractValues
	 * @return
	 */
	public void isDateGreaterThan(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Param paramCurrentDate = new Param();
		paramCurrentDate.setId("fechaActual");
		paramCurrentDate.setValue(currentValue.getId());
		Boolean error = false;
		Date currentDate = null;
		try {
			currentDate = validParam(currentValue, contractValues, paramCurrentDate);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		Date previousDate = null;
		try {
			previousDate = validParam(currentValue, contractValues, DATE);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		if (error)
			throw new OperationException();
		ContractValueDto valuePreviousDate = getByCurrentParamId(currentValue,contractValues,DATE);
		if (previousDate.after(currentDate) || previousDate.equals(currentDate)) {
			currentValue.getOperationErrors().add(String.format(ERROR_PREVIOUS_DATE_GREATER, valuePreviousDate.getDescription()));
			throw new OperationException();
		}
		currentValue.getOperationErrors().clear();
	}
	
	/**
	 * Valida que una fecha sea mayor que otra
	 * 
	 * @param currentValue
	 * @param contractValues
	 * @return
	 */
	public  void isDateGreaterOrEqualThan(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Param paramCurrentDate = new Param();
		paramCurrentDate.setId("fechaActual");
		paramCurrentDate.setValue(currentValue.getId());
		Boolean error = false;
		Date currentDate = null;
		try {
			currentDate = validParam(currentValue, contractValues, paramCurrentDate);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		Date previousDate = null;
		try {
			previousDate = validParam(currentValue, contractValues, DATE);
		} catch (OperationException e) {
			log.error(e.getLocalizedMessage());
			error = true;
		}
		if (error)
			throw new OperationException();		
		ContractValueDto valuePreviousDate = getByCurrentParamId(currentValue,contractValues,DATE);
		if (previousDate.after(currentDate)) {
			currentValue.getOperationErrors().add(String.format(ERROR_PREVIOUS_DATE_GREATER_OR_EQUAL, valuePreviousDate.getDescription()));
			throw new OperationException();	
		}		
		currentValue.getOperationErrors().clear();
	}
	
	public void addNumYearsToDate(ContractValueDto currentValue, List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Boolean error = false;
		Date date = null;
		try {
			date = validParam(currentValue, contractValues, DATE);
		} catch (Exception e) {
			error = true;
		}
		Integer numYears = null;
		try {
			numYears = (Integer) OperationNumber.validPositiveInteger(currentValue, contractValues, NUM_YEARS);
		} catch (Exception e) {
			error = true;
		}
		if (error) {
			throw new OperationException();
		}
		Date response = addNumYearsToDate(date, numYears);
		currentValue.setContent(formatDate.format(response));
	}

}
