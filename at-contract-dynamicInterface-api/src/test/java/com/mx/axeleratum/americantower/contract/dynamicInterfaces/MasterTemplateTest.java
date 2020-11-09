package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.DynamicInterfaceMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.model.MasterTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.MasterTemplateService;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.ValidatorService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class MasterTemplateTest {

	@Autowired
	MasterTemplateService service;

	@Autowired
	ValidatorService validation;
	
	@Autowired
	private DynamicInterfaceMapper dynamicInterfaceMapperMapper;

	@Test
	public void testFindAllTemplate() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<MasterTemplate> templates = service.findAllMasterTemplate();
		log.info("Cantidad de templates masters"+templates.size());
		assertEquals(templates.size()>=0,true);
	}
	
	
	/**
	 * crear TOWER Template
	 */
	@Test 
	public void addMasterTOWERTemplate() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		MasterTemplate tower = service.findMasterTemplateByType("TOWER");
		if(tower==null) {
			tower = service.getInstance("tower");
			service.saveMasterTemplate(tower);
		}
	}

	/**
	 * Buscar una instancia de mastertemplate
	 */
	@Test
	public void findMasterTemplateInstance() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		MasterTemplate tower = null;
		try {
			tower = service.getJsonFromFile("tower.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(tower);
	}

	/**
	 * Probar encontrar valores en contractValues para realizar operaciones
	 */
	@Test
	public void findContractValue() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		MasterTemplate tower = null;
		try {
			tower = service.getJsonFromFile("tower.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(tower);
		List<ContractValueDto> contractValues = dynamicInterfaceMapperMapper.toListContractValueDto(tower.getValues());
		ContractValueDto value = ContractValueDto.findById(contractValues, "fechaInicioVigencia");
		log.info("Value: " + value);
		assertNotNull(value);
		assertEquals(value.getId(), "fechaInicioVigencia");
	}

	// FIXME TEST ahora valida pasando el contrato completo
//	/**
//	 * Buscar operaciones en la template y ejecutar las operaciones que de designan
//	 * 
//	 */
//	@Test
//	public void ejecutarMethodByReflect() throws ParseException {
//		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
//		MasterTemplate tower = null;
//
//		try {
//			tower = service.getJsonFromFile("tower.json");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<ContractValueDto> contractValues = dynamicInterfaceMapperMapper.toContractValueDto(tower.getValues());
//		log.info(validation.processOperation(contractValues)+"");
//	}

}
