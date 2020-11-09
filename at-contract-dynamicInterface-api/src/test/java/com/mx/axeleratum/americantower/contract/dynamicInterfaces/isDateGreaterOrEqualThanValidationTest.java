package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class isDateGreaterOrEqualThanValidationTest extends ValidationTest {

	// (18)fechaInicioVigencia > (17)fechaFirmaContrato
	// isDateGreaterOrEqualThan
	@Test
	public void fechaInicioVigencia_isDateGreaterOrEqualThan_FirmaContrato() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		String firtDateId = "fechaInicioVigencia";
		String secondDateId = "fechaFirmaContrato";

		master.deleteAllTemplates();

		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

	    contractDto = isDateGreaterOrEqualThan_BothNull(contractDto,firtDateId,secondDateId);
		contractDto = isDateGreaterOrEqualThan_BothEnty(contractDto,firtDateId,secondDateId);
		contractDto = isDateGreaterOrEqualThan_OkAndEnty(contractDto,firtDateId,secondDateId);
		contractDto = isDateGreaterOrEqualThan_EntyAndOk(contractDto,firtDateId,secondDateId);
		contractDto = isDateGreaterOrEqualThan_EsMenor(contractDto,firtDateId,secondDateId);
		contractDto = isDateGreaterOrEqualThan_EsEqual(contractDto,firtDateId,secondDateId);
		contractDto = isDateGreaterOrEqualThan_BothOk(contractDto,firtDateId,secondDateId);
	}
		
	private ContractTemplateDto isDateGreaterOrEqualThan_BothOk(ContractTemplateDto contractDto,String firtDateId, String secodDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-09-01";
		String secondDateValue =  "2020-08-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secodDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterOrEqualThan");
	
		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secodDateId);
		check(secodDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		log.info(firtDate.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(0, firtDateId, firtDateValue, resultFirtDate);
		
		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secodDateId);		
		infoResult(0, secodDateId, secondDateValue, resultSecondDate);

		return contractDto;

	}

	private ContractTemplateDto isDateGreaterOrEqualThan_EsEqual(ContractTemplateDto contractDto,
			String firtDateId, String secodDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-08-01";
		String secondDateValue =  "2020-08-01";
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secodDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterOrEqualThan");
	
		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secodDateId);
		check(secodDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		log.info(firtDate.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(0, firtDateId, firtDateValue, resultFirtDate);
		
		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secodDateId);		
		infoResult(0, secodDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterOrEqualThan_EsMenor(ContractTemplateDto contractDto,String firtDateId, String secodDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-07-01";
		String secondDateValue =  "2020-08-01";
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secodDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterOrEqualThan");
	
		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secodDateId);
		check(secodDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		log.info(firtDate.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(1, firtDateId, firtDateValue, resultFirtDate);
		
		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secodDateId);		
		infoResult(0, secodDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}


	private ContractTemplateDto isDateGreaterOrEqualThan_EntyAndOk(ContractTemplateDto contractDto,	String firtDateId, String secondDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "";
		String secondDateValue =  "2020-08-01";
			
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterOrEqualThan");
	
		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		log.info(firtDate.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(1, firtDateId, firtDateValue, resultFirtDate);
		
		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);		
		infoResult(0, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterOrEqualThan_OkAndEnty(ContractTemplateDto contractDto,	String firtDateId, String secondDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-08-01";
		String secondDateValue = "";
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterOrEqualThan");
	
		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		log.info(firtDate.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(1, firtDateId, firtDateValue, resultFirtDate);
		
		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);		
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterOrEqualThan_BothNull(ContractTemplateDto contractDto, String firtDateId,String secondDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = null;
		String secondDateValue = null;
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterOrEqualThan");
	
		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		log.info(firtDate.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(2, firtDateId, firtDateValue, resultFirtDate);
		
		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);		
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterOrEqualThan_BothEnty(ContractTemplateDto contractDto, String firtDateId,String secondDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "";
		String secondDateValue = "";
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterOrEqualThan");
	
		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		log.info(firtDate.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(2, firtDateId, firtDateValue, resultFirtDate);
		
		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);		
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);
		
		return contractDto;
	}

}
