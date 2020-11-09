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

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class addValidationTest extends ValidationTest {

	//(35)rentaTotalTorre = (33)renta + (34)rentaEquipoAdicional
	@Test
	public void rentaTotalTorreEqualAddRentaAndRentaEquipoAdicional() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

		
		String responseId = "rentaTotalTorre";
		String firtAddingId = "renta";
		String secondAddingId = "rentaEquipoAdicional";
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		
//		dtoValues = addNullAnd0(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,1,0,0);
//		dtoValues = addEntyAndEnty(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,1,1,0);
//		dtoValues = addOkPtoAndEnty(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,0,1,2);
//		dtoValues = addOkComaAndEnty(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,0,1,2);
//		dtoValues = addOkAndOk(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,0,1,0);

	}	

//	//(39)rentaTotalTerreno = (37)rentaTerreno + (38)rentaEspacioAdicional
//	@Test
//	public void rentaTotalTerrenoEqualAddRentaAndRentaEquipoAdicional() {
//		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
//		master.deleteAllTemplates();
//		ContractTemplate contract = getAnyTowerContract();
//		assertThat(contract).isNotNull();
//		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);
//
//		String responseId = "rentaTotalTerreno";
//		String firtAddingId = "rentaTerreno";
//		String secondAddingId = "rentaEspacioAdicional";
//
//		
//		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();
//		
//		int errorsFirtAdding = 1;
//		int errorsSecondAdding = 1;
//		int errrosResult = 2;
//		dtoValues = addNullAndNull(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,errorsFirtAdding,errorsSecondAdding,errrosResult);
//
//		errorsFirtAdding = 1;
//		errorsSecondAdding = 1;
//		errrosResult = 2;
//		dtoValues = addEntyAndEnty(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,errorsFirtAdding,errorsSecondAdding,errrosResult);
//
//		errorsFirtAdding = 0;
//		errorsSecondAdding = 1;
//		errrosResult = 1;
//		dtoValues = addOkPtoAndEnty(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,errorsFirtAdding,errorsSecondAdding,errrosResult);
//		
//		errorsFirtAdding = 0;
//		errorsSecondAdding = 1;
//		errrosResult = 1;
//		dtoValues = addOkComaAndEnty(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,errorsFirtAdding,errorsSecondAdding,errrosResult);
//		
//		errorsFirtAdding = 0;
//		errorsSecondAdding = 0;
//		errrosResult = 0;
//		dtoValues = addOkAndOk(contractDto, responseId, firtAddingId, secondAddingId, dtoValues,errorsFirtAdding,errorsSecondAdding,errrosResult);
//	}

	private List<ContractValueDto> addOkAndOk(ContractTemplateDto contractDto, String responseId, String firtAddingId, String secondAddingId, List<ContractValueDto> dtoValues, 
			   int errorsFirtAdding, int errorsSecondAdding, int errrosResult) {
		String firtAddingValue = "34.87";
		String secondAddingValue = "125656565.655";
		String resultValue = "125656600.525";
		return add(contractDto, responseId, firtAddingId, secondAddingId, firtAddingValue, secondAddingValue, resultValue, 
				dtoValues, errorsFirtAdding, errorsSecondAdding, errrosResult);
	}

	private List<ContractValueDto> addOkComaAndEnty(ContractTemplateDto contractDto, String responseId,
			String firtAddingId, String secondAddingId, List<ContractValueDto> dtoValues,
			int errorsFirtAdding, int errorsSecondAdding, int errrosResult) {
		String firtAddingValue = "34,87";
		String secondAddingValue = null;
		String resultValue = "";
		return add(contractDto, responseId, firtAddingId, secondAddingId, firtAddingValue, secondAddingValue,
				resultValue, dtoValues, errorsFirtAdding, errorsSecondAdding, errrosResult);
	}

	private List<ContractValueDto> addOkPtoAndEnty(ContractTemplateDto contractDto, String responseId,
			String firtAddingId, String secondAddingId, List<ContractValueDto> dtoValues,
			int errorsFirtAdding,int errorsSecondAdding,int errrosResult) {
		String firtAddingValue = "34.87";
		String secondAddingValue = null;
		String resultValue = "";
		return add(contractDto, responseId, firtAddingId, secondAddingId, firtAddingValue, secondAddingValue,
				resultValue, dtoValues, errorsFirtAdding, errorsSecondAdding, errrosResult);
	}

	private List<ContractValueDto> addEntyAndEnty(ContractTemplateDto contractDto, String responseId,
			String firtAddingId, String secondAddingId, List<ContractValueDto> dtoValues,
			int errorsFirtAdding,int errorsSecondAdding,int errrosResult) {
		String firtAddingValue = "";
		String secondAddingValue = "";
		String resultValue = "";
		return add(contractDto, responseId, firtAddingId, secondAddingId, firtAddingValue, secondAddingValue,
				resultValue, dtoValues, errorsFirtAdding, errorsSecondAdding, errrosResult);
	}

	private List<ContractValueDto> addNullAndNull(ContractTemplateDto contractDto, String responseId,
			String firtAddingId, String secondAddingId, List<ContractValueDto> dtoValues,
			int errorsFirtAdding,int errorsSecondAdding,int errrosResult) {
		String firtAddingValue = null;
		String secondAddingValue = null;
		String resultValue = "0";
		return add(contractDto, responseId, firtAddingId, secondAddingId, firtAddingValue, secondAddingValue,
				resultValue, dtoValues, errorsFirtAdding, errorsSecondAdding, errrosResult);
	}
	
	private List<ContractValueDto> addNullAnd0(ContractTemplateDto contractDto, String responseId,
			String firtAddingId, String secondAddingId, List<ContractValueDto> dtoValues,
			int errorsFirtAdding,int errorsSecondAdding,int errrosResult) {
		String firtAddingValue = null;
		String secondAddingValue = "0"; 
		String resultValue = "0";
		return add(contractDto, responseId, firtAddingId, secondAddingId, firtAddingValue, secondAddingValue,
				resultValue, dtoValues, errorsFirtAdding, errorsSecondAdding, errrosResult);
	}

	private List<ContractValueDto> add(ContractTemplateDto contractDto, String responseId, String firtAddingId,
			String secondAddingId, String firtAddingValue, String secondAddingValue, String resultValue,
			List<ContractValueDto> dtoValues, int errorsFirtAdding, int errorsSecondAdding, int errrosResult) {
		ContractValueDto.setValueById(dtoValues, firtAddingId, firtAddingValue);
		ContractValueDto.setValueById(dtoValues, secondAddingId, secondAddingValue);

		ContractValueDto firtAdding = ContractValueDto.findById(dtoValues, firtAddingId);
		check(firtAddingId, firtAddingValue, firtAdding);

		ContractValueDto secondAdding = ContractValueDto.findById(dtoValues, secondAddingId);
		check(secondAddingId, secondAddingValue, secondAdding);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, response.getContent(), response);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtAdding = ContractValueDto.findById(dtoValues, firtAddingId);
		infoResult(errorsFirtAdding, firtAddingId, firtAddingValue, resultFirtAdding);
	
		ContractValueDto resultSecondAdding = ContractValueDto.findById(dtoValues, secondAddingId);
		infoResult(errorsSecondAdding, secondAddingId, secondAddingValue, resultSecondAdding);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(errrosResult, responseId, resultValue, response);
		return dtoValues;
	}

}
