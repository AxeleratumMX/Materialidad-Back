package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import com.mx.axeleratum.americantower.contract.core.model.ContractTower;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OthersTest {

	@Test
	public void getValuesFromContractTower() {

		List<String> strfields = new ArrayList<String>();
		List<Field> fields = new ArrayList<Field>();
		// B.class.getSuperclass().getDeclaredFields();
		Field[] allFields = ContractTower.class.getSuperclass().getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers())) {
				strfields.add(field.getName());
				fields.add(field);
			}
		}
		allFields = ContractTower.class.getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers())) {
				strfields.add(field.getName());
				fields.add(field);
			}
		}

		for (Field field : fields) {
			String tipo = field.getType().getName();
			String[] tp = tipo.split("\\.");
			tipo = (tp.length > 0) ? tp[tp.length - 1] : tipo;
			log.info(">>>> " + field.getName() + " " + tipo);
		}

	}

	@Test
	public void validationTest() {
		ContractTower tower = new ContractTower();
		Errors errors = new BeanPropertyBindingResult(tower, tower.getClass().getName());
		ValidationUtils.rejectIfEmpty(errors, "acuerdo", "Selected acuerdo Details Not Found", "Default mensaje");
		System.out.println(errors);
	}

	@Test
	public void calculosFechas() {
		 
		LocalDate oldDate = LocalDate.of(1982, Month.AUGUST, 31);
		LocalDate newDate = LocalDate.of(2016, Month.NOVEMBER, 9);

		System.out.println(oldDate);
		System.out.println(newDate);

		Period period = Period.between(oldDate, newDate);

		System.out.print(period.getYears() + " years,");
		System.out.print(period.getMonths() + " months,");
		System.out.print(period.getDays() + " days");
	}

	@Test
	public void testFecha() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateString = dtf.format(now);
		System.out.println(dateString);

	}

	@Test
	public void testRenta() {
		int cantidadParticipantes = 2;
		BigDecimal ammount = new BigDecimal("11578.29");
		Double ammountD = new Double("11578.29");
		Double value1 = new Double(100);
		Double value2 = new Double(3);

		double xx = (value1 / value2)/value1;



		double result = ammountD * xx;
		BigDecimal ccc = new BigDecimal(result).setScale(4,BigDecimal.ROUND_UP);
		log.info("sali");

	}
	
}
