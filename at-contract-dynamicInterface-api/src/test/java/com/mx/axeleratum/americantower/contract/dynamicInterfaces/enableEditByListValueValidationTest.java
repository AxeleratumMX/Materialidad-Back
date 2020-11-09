package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
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
public class enableEditByListValueValidationTest extends ValidationTest {

	// porcentajeIncremento => habilitado para ingresar Texto
	// Si tipoIncremento = Basis Only enableEditByListValue tipoIncremento
	@Test
	public void porcentajeIncrementoEnableEditByListValueTipoIncrementoBasisOnly() {
		log.info("Ejecutando Test: "+Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

		String currentId = "porcentajeIncremento";
		String list = "tipoIncremento";
		List<String> paramListValues = Arrays.asList("Basic Only");
		String listValueTrue = "Basic Only";
		String listValueFalse = "Leaser Of";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		dtoValues = enabledEditFalse(contractDto, currentId, list, paramListValues, dtoValues, listValueFalse);
		dtoValues = enabledEditTrue(contractDto, currentId, list, paramListValues, dtoValues, listValueTrue);
	}

	// porcentajeMinimo => habilitado para ingresar Texto
	// Si tipoIncremento = Greater Of or Leaser Of enableEditByListValue
	@Test
	public void porcentajeMinimoEnableEditByListValueTipoIncrementoGreaterOfOrLeaserOf() {
		log.info("Ejecutando Test: "+Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

		String currentId = "porcentajeMinimo";
		String list = "tipoIncremento";
		List<String> paramListValues = Arrays.asList("Greater Of", "Leaser Of");
		String listValueFalse = "Basic Only";
		String listValueTrue = "Greater Of";
		String listValueTrue2 = "Leaser Of";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		dtoValues = enabledEditFalse(contractDto, currentId, list, paramListValues, dtoValues, listValueFalse);
		dtoValues = enabledEditTrue(contractDto, currentId, list, paramListValues, dtoValues, listValueTrue);
		dtoValues = enabledEditTrue(contractDto, currentId, list, paramListValues, dtoValues, listValueTrue2);
	}

	// cpi => habilitado para ingresar Texto
	// Si tipoIncremento = Greater Of or Leaser Of enableEditByListValue
	@Test
	public void cpiEnableEditByListValueTipoIncrementoIndexOnly() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

		String currentId = "cpi";
		String list = "tipoIncremento";
		List<String> paramListValues = Arrays.asList("Greater Of", "Leaser Of");
		String listValueFalse = "Basic Only";
		String listValueTrue = "Index Only";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		dtoValues = enabledEditFalse(contractDto, currentId, list, paramListValues, dtoValues, listValueFalse);
		dtoValues = enabledEditTrue(contractDto, currentId, list, paramListValues, dtoValues, listValueTrue);
	}

	private List<ContractValueDto> enabledEditTrue(ContractTemplateDto contractDto, String currentId, String list,
			List<String> paramListValues, List<ContractValueDto> dtoValues, String valueAsignado) {
		log.info("Ejecutando Test: "+Thread.currentThread().getStackTrace()[1].getMethodName() + currentId + " "+list+" = "+valueAsignado);
		dtoValues = enableEditByListValue(contractDto, currentId, list, dtoValues, valueAsignado, true);
		return dtoValues;
	}

	private List<ContractValueDto> enabledEditFalse(ContractTemplateDto contractDto, String currentId, String list,
			List<String> paramListValues, List<ContractValueDto> dtoValues, String valueAsignado) {
		log.info("Ejecutando Test: "+Thread.currentThread().getStackTrace()[1].getMethodName() + currentId + " "+list+" = "+valueAsignado);
		dtoValues = enableEditByListValue(contractDto, currentId, list, dtoValues, valueAsignado, false);
		return dtoValues;
	}

	private List<ContractValueDto> enableEditByListValue(ContractTemplateDto contractDto, String currentId, String list,
			List<ContractValueDto> dtoValues, String valueAsignado, Boolean edit) {
		ContractValueDto currenValue = ContractValueDto.findById(dtoValues, currentId);
		checkEditable(currentId,currenValue.getEditable(), currenValue);
		assertThat(currenValue.getOperation().getName()).isEqualTo("enableEditByListValue");
		
		// Asigno valor a la lista
		ContractValueDto.setValueById(contractDto.getTemplateInstance().getValues(), list, valueAsignado);
		ContractValueDto listValue = ContractValueDto.findById(dtoValues, list);
		checkList(list, valueAsignado, listValue);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		
		log.info(currenValue.getOperation().getName());

		currenValue = ContractValueDto.findById(dtoValues, currentId);
		checkEditable(currentId, edit, currenValue);
		return dtoValues;
	}

}

	
