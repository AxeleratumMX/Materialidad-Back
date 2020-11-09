package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class rentaTerrenoTest extends ValidationTest {

	/**
	 * idActivo 85663 (tres entradas ok) 15917.53*((100/4)/100) = 3.979,38
	 */
	@Test
	public void rentaTerrenoIdActivoOk() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		//master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);
		String currentId = "rentaTerreno";
		String valueIdActivo = "85663";
	
		//Seteo el id de activo
		contractDto.getTemplateInstance().setIdActivo(Integer.valueOf(valueIdActivo));
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();
		
		ContractValueDto currenValue = ContractValueDto.findById(dtoValues, currentId);
		check(currentId,currenValue.getContent(), currenValue);
		assertThat(currenValue.getOperation().getName()).isEqualTo("rentaTerreno");

		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(currenValue.getOperation().getName());

		
		ContractValueDto result = ContractValueDto.findById(dtoValues,currentId);
		infoResult(0, currentId, result.getContent(), result);		
	}
	
	/**
	 * idActivo = 85625 (3 entradas, 2 ok y una mal)  4375*((100/3)/100) = 1.443,75
	 */	
	@Test
	public void rentaTerrenoIdActivoMal() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		//master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);
		String currentId = "rentaTerreno";
		String valueIdActivo = "85625";
	
		//Seteo el id de activo
		contractDto.getTemplateInstance().setIdActivo(Integer.valueOf(valueIdActivo));
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();
		
		ContractValueDto currenValue = ContractValueDto.findById(dtoValues, currentId);
		check(currentId,currenValue.getContent(), currenValue);
		assertThat(currenValue.getOperation().getName()).isEqualTo("rentaTerreno");

		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(currenValue.getOperation().getName());

		
		ContractValueDto result = ContractValueDto.findById(dtoValues,currentId);
		infoResult(0, currentId, result.getContent(), result);		
	}
}	
