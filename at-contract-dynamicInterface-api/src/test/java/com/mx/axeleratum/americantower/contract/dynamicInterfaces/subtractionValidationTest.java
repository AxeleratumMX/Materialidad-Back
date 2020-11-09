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
public class subtractionValidationTest extends ValidationTest {

	
	//(35)rentaTotalTorre = (33)renta + (34)rentaEquipoAdicional
	@Test
	public void totalRentaReduccionEqualSubtractionRentaTotalTorreAndMontoReduccion() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

		String responseId = "totalRentaReduccion";
		String firtSubtractingId = "rentaTotalTorre";
		String secondSubtractingId = "montoReduccion";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		dtoValues = subtractionNullAndNull(contractDto, responseId, firtSubtractingId, secondSubtractingId, dtoValues);
		dtoValues = subtractionEntyAndEnty(contractDto, responseId, firtSubtractingId, secondSubtractingId, dtoValues);
		dtoValues = subtractionOkPtoAndEnty(contractDto, responseId, firtSubtractingId, secondSubtractingId, dtoValues);
		dtoValues = subtractionOkComaAndEnty(contractDto, responseId, firtSubtractingId, secondSubtractingId, dtoValues);
		dtoValues = subtractionOkAndOk(contractDto, responseId, firtSubtractingId, secondSubtractingId, dtoValues);
	}
	
	private List<ContractValueDto> subtractionOkAndOk(ContractTemplateDto contractDto, String responseId, String firtSubtractingId,
			String secondSubtractingId, List<ContractValueDto> dtoValues) {
		String firtSubtractingValue = "34.87";
		String secondSubtractingValue = "125656565.655";
		String resultValue = "-125656600.525";
		int errorsFirtSubtracting = 1;
		int errorsSecondSubtracting = 0;
		int errrosResult = 0;
		return subtraction(contractDto, responseId, firtSubtractingId, secondSubtractingId, firtSubtractingValue, secondSubtractingValue,
				resultValue, dtoValues, errorsFirtSubtracting, errorsSecondSubtracting, errrosResult);
	}

	private List<ContractValueDto> subtractionOkComaAndEnty(ContractTemplateDto contractDto, String responseId,
			String firtSubtractingId, String secondSubtractingId, List<ContractValueDto> dtoValues) {
		String firtSubtractingValue = "34,87";
		String secondSubtractingValue = null;
		String resultValue = "";
		int errorsFirtSubtracting = 1;
		int errorsSecondSubtracting = 1;
		int errrosResult = 1;
		return subtraction(contractDto, responseId, firtSubtractingId, secondSubtractingId, firtSubtractingValue, secondSubtractingValue,
				resultValue, dtoValues, errorsFirtSubtracting, errorsSecondSubtracting, errrosResult);
	}

	private List<ContractValueDto> subtractionOkPtoAndEnty(ContractTemplateDto contractDto, String responseId,
			String firtSubtractingId, String secondSubtractingId, List<ContractValueDto> dtoValues) {
		String firtSubtractingValue = "34.87";
		String secondSubtractingValue = null;
		String resultValue = "";
		int errorsFirtSubtracting = 1;
		int errorsSecondSubtracting = 1;
		int errrosResult = 1;
		return subtraction(contractDto, responseId, firtSubtractingId, secondSubtractingId, firtSubtractingValue, secondSubtractingValue,
				resultValue, dtoValues, errorsFirtSubtracting, errorsSecondSubtracting, errrosResult);
	}

	private List<ContractValueDto> subtractionEntyAndEnty(ContractTemplateDto contractDto, String responseId,
			String firtSubtractingId, String secondSubtractingId, List<ContractValueDto> dtoValues) {
		String firtSubtractingValue = "";
		String secondSubtractingValue = "";
		String resultValue = "";
		int errorsFirtSubtracting = 2;
		int errorsSecondSubtracting = 1;
		int errrosResult = 2;
		return subtraction(contractDto, responseId, firtSubtractingId, secondSubtractingId, firtSubtractingValue, secondSubtractingValue,
				resultValue, dtoValues, errorsFirtSubtracting, errorsSecondSubtracting, errrosResult);
	}

	private List<ContractValueDto> subtractionNullAndNull(ContractTemplateDto contractDto, String responseId,
			String firtSubtractingId, String secondSubtractingId, List<ContractValueDto> dtoValues) {
		String firtSubtractingValue = null;
		String secondSubtractingValue = null;
		String resultValue = "";
		int errorsFirtSubtracting = 2;
		int errorsSecondSubtracting = 1;
		int errrosResult = 2;
		return subtraction(contractDto, responseId, firtSubtractingId, secondSubtractingId, firtSubtractingValue, secondSubtractingValue,
				resultValue, dtoValues, errorsFirtSubtracting, errorsSecondSubtracting, errrosResult);
	}

	private List<ContractValueDto> subtraction(ContractTemplateDto contractDto, String responseId, String firtSubtractingId,
			String secondSubtractingId, String firtSubtractingValue, String secondSubtractingValue, String resultValue,
			List<ContractValueDto> dtoValues, int errorsFirtSubtracting, int errorsSecondSubtracting, int errrosResult) {
		ContractValueDto.setValueById(dtoValues, firtSubtractingId, firtSubtractingValue);
		ContractValueDto.setValueById(dtoValues, secondSubtractingId, secondSubtractingValue);

		ContractValueDto firtSubtracting = ContractValueDto.findById(dtoValues, firtSubtractingId);
		check(firtSubtractingId, firtSubtractingValue, firtSubtracting);

		ContractValueDto secondSubtracting = ContractValueDto.findById(dtoValues, secondSubtractingId);
		check(secondSubtractingId, secondSubtractingValue, secondSubtracting);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, response.getContent(), response);
		assertThat(response.getOperation().getName()).isEqualTo("subtraction");
		log.info(response.getOperation().getName());

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtSubtracting = ContractValueDto.findById(dtoValues, firtSubtractingId);
		infoResult(errorsFirtSubtracting, firtSubtractingId, firtSubtractingValue, resultFirtSubtracting);
	
		ContractValueDto resultSecondSubtracting = ContractValueDto.findById(dtoValues, secondSubtractingId);
		infoResult(errorsSecondSubtracting, secondSubtractingId, secondSubtractingValue, resultSecondSubtracting);
		
		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(errrosResult,responseId, resultValue, response);
		return dtoValues;
	}

}
