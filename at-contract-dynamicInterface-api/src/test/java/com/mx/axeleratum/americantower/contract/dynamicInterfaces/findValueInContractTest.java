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
public class findValueInContractTest extends ValidationTest {

	// idActivo = valor del idActivo dentro del contrato
	@Test
	public void findValueInContractIdActivo() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);
		String responseIdActivo = "5656565";
		contractDto.getTemplateInstance().setIdActivo(Integer.valueOf(responseIdActivo));
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();
		
		String currentId = "idActivo";
		ContractValueDto currenValue = ContractValueDto.findById(dtoValues, currentId);
		check(currentId,currenValue.getContent(), currenValue);
		assertThat(currenValue.getOperation().getName()).isEqualTo("findValueInContract");

		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(currenValue.getOperation().getName());

		ContractValueDto result = ContractValueDto.findById(dtoValues, currentId);
		infoResult(0, currentId, responseIdActivo, result);		
	}
	
	@Test
	public void findValueInContractIdActivoEnty() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);
		String responseIdActivo = "";
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();
		
		String currentId = "idActivo";
		ContractValueDto currenValue = ContractValueDto.findById(dtoValues, currentId);
		check(currentId,currenValue.getContent(), currenValue);
		assertThat(currenValue.getOperation().getName()).isEqualTo("findValueInContract");

		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(currenValue.getOperation().getName());

		ContractValueDto result = ContractValueDto.findById(dtoValues, currentId);
		infoResult(0, currentId, responseIdActivo, result);		
	}
}
