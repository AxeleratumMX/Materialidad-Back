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
public class isDateGreaterThanValidationTest extends ValidationTest {

//	// (22)fechaTerminoVigencia > (17)fechaFirmaContrato y (18)FechaInicioVigencia
//	// isDateGreaterThan
//	@Test
//	public void fechaTerminoVigencia_isDateGreaterThan_fechaInicioVigencia() {
//		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
//		master.deleteAllTemplates();
//
//		String firtDateId = "fechaTerminoVigencia";
//		String secondDateId = "fechaInicioVigencia";
//		String dependencyId = "fechaFirmaContrato";
//		String dependencyValueMenor = "2021-08-01";
//		String dependencyValueEqual = "2020-05-01";
//
//		ContractTemplate contract = getAnyTowerContract();
//		assertThat(contract).isNotNull();
//		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);
//
//		contractDto = isDateGreaterThan_BothNull(contractDto, firtDateId, secondDateId,2,2);
//		contractDto = isDateGreaterThan_BothEnty(contractDto, firtDateId, secondDateId,2,2);
//		contractDto = isDateGreaterThan_OkAndEnty(contractDto, firtDateId, secondDateId,1,2);
//		contractDto = isDateGreaterThan_EntyAndOk(contractDto, firtDateId, secondDateId,1,2);
//		contractDto = isDateGreaterThan_EsMenor(contractDto, firtDateId, secondDateId,1,2);
//		contractDto = isDateGreaterThan_BothOkButErrorDependency(contractDto, firtDateId, secondDateId,0,1);
//
//		ContractValueDto.setValueById(contractDto.getTemplateInstance().getValues(), dependencyId,dependencyValueMenor);
//		ContractValueDto dependency = ContractValueDto.findById(contractDto.getTemplateInstance().getValues(),dependencyId);
//		check(dependencyId, dependencyValueMenor, dependency);
//		contractDto = isDateGreaterThan_BothOkDependencyMenor(contractDto, firtDateId, secondDateId,0,1);
//
//		ContractValueDto.setValueById(contractDto.getTemplateInstance().getValues(), dependencyId,dependencyValueEqual);
//		dependency = ContractValueDto.findById(contractDto.getTemplateInstance().getValues(), dependencyId);
//		check(dependencyId, dependencyValueEqual, dependency);
//		contractDto = isDateGreaterThan_BothOk(contractDto, firtDateId, secondDateId);
//	}

	// (23)fechaInicioRenta > (17)fechaFirmaContrato y (18)FechaInicioVigencia
	// isDateGreaterThan
	@Test
	public void fechaInicioRenta_isDateGreaterThan_fechaInicioVigencia() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();

		String firtDateId = "fechaInicioRenta";
		String secondDateId = "fechaInicioVigencia";
		String dependencyId = "fechaFirmaContrato";
		String dependencyValueMenor = "2021-08-01";
		String dependencyValueEqual = "2020-05-01";

		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

		contractDto = isDateGreaterThan_BothNull(contractDto, firtDateId, secondDateId,2,2);
		contractDto = isDateGreaterThan_BothEnty(contractDto, firtDateId, secondDateId,2,2);
		contractDto = isDateGreaterThan_OkAndEnty(contractDto, firtDateId, secondDateId,1,2);
		contractDto = isDateGreaterThan_EntyAndOk(contractDto, firtDateId, secondDateId,1,2);
		contractDto = isDateGreaterThan_EsMenor(contractDto, firtDateId, secondDateId,1,2);
		contractDto = isDateGreaterThan_BothOkButErrorDependency(contractDto, firtDateId, secondDateId,0,1);

		ContractValueDto.setValueById(contractDto.getTemplateInstance().getValues(), dependencyId,dependencyValueMenor);
		ContractValueDto dependency = ContractValueDto.findById(contractDto.getTemplateInstance().getValues(),dependencyId);
		check(dependencyId, dependencyValueMenor, dependency);
		contractDto = isDateGreaterThan_BothOkDependencyMenor(contractDto, firtDateId, secondDateId,0,1);

		ContractValueDto.setValueById(contractDto.getTemplateInstance().getValues(), dependencyId,dependencyValueEqual);
		dependency = ContractValueDto.findById(contractDto.getTemplateInstance().getValues(), dependencyId);
		check(dependencyId, dependencyValueEqual, dependency);
		contractDto = isDateGreaterThan_BothOk(contractDto, firtDateId, secondDateId);
	}

	private ContractTemplateDto isDateGreaterThan_BothOk(ContractTemplateDto contractDto, String firtDateId,
			String secondDateId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2021-08-01";
		String secondDateValue = "2020-05-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);
		
//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());
		
		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(0, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(0, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterThan_BothOkDependencyMenor(ContractTemplateDto contractDto,
			String firtDateId, String secondDateId,int firtErrors,int secondErrors) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2021-08-01";
		String secondDateValue = "2020-05-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());
		
		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(firtErrors, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(secondErrors, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterThan_BothOkButErrorDependency(ContractTemplateDto contractDto,
			String firtDateId, String secondDateId,int firtErrors,int secondErrors) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2021-08-01";
		String secondDateValue = "2020-05-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());
		
		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(firtErrors, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(secondErrors, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterThan_EsMenor(ContractTemplateDto contractDto, String firtDateId,
			String secondDateId,int firtErrors,int secondErrors) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-05-01";
		String secondDateValue = "2021-08-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
//		contractDto = validation.processOperation(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());
		
		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(firtErrors, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(secondErrors, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterThan_EntyAndOk(ContractTemplateDto contractDto, String firtDateId,
			String secondDateId,int firtErrors,int secondErrors) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "";
		String secondDateValue = "2020-05-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
//		contractDto = validation.processOperation(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());
		
		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(firtErrors, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(secondErrors, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterThan_OkAndEnty(ContractTemplateDto contractDto, String firtDateId,
			String secondDateId,int firtErrors,int secondErrors) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-05-01";
		String secondDateValue = "";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);
		dtoValues = validation.processOperation(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(firtErrors, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(secondErrors, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterThan_BothEnty(ContractTemplateDto contractDto, String firtDateId,
			String secondDateId,int firtErrors,int secondErrors) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "";
		String secondDateValue = "";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

//		dtoValues = validation.processOperation(contractDto);
//		contractDto = validation.processOperation(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(firtErrors, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(secondErrors, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

	private ContractTemplateDto isDateGreaterThan_BothNull(ContractTemplateDto contractDto, String firtDateId,
			String secondDateId,int firtErrors,int secondErrors) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = null;
		String secondDateValue = null;

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);
		assertThat(firtDate.getOperation().getName()).isEqualTo("isDateGreaterThan");
		log.info(firtDate.getOperation().getName());

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);
		
//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
	    infoResult(firtErrors, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
	    infoResult(secondErrors, secondDateId, secondDateValue, resultSecondDate);

		return contractDto;
	}

}
